package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.exception.DoesNotExistException;
import com.ecommerce.ecommerce.exception.FileStorageException;
import com.ecommerce.ecommerce.exception.InvalidFileTypeException;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.model.ProductImage;
import com.ecommerce.ecommerce.payload.mapper.ProductMapper;
import com.ecommerce.ecommerce.payload.request.ProductRequestDTO;
import com.ecommerce.ecommerce.payload.response.ProductResponseDTO;
import com.ecommerce.ecommerce.pojo.FileResource;
import com.ecommerce.ecommerce.payload.response.ProductPageDTOProjection;
import com.ecommerce.ecommerce.repository.ProductImageRepository;
import com.ecommerce.ecommerce.repository.ProductRepository;
import com.ecommerce.ecommerce.service.FileSystemService;
import com.ecommerce.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final String uploadDirectory;
    private final ProductMapper productMapper;
    private final FileSystemService fileSystemService;

    public ProductServiceImpl(
                          ProductRepository productRepository,
                          ProductImageRepository productImageRepository,
                          @Value("${uploadDir}") String uploadDirectory,
                              ProductMapper productMapper,
                          FileSystemService fileSystemService) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.uploadDirectory = uploadDirectory;
        this.productMapper = productMapper;
        this.fileSystemService = fileSystemService;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productDTO) {
        Product product = productMapper.convertProductRequestDtoToProduct(productDTO);
        product = productRepository.save(product);

        return productMapper.convertProducttoProductResponseDTO(product);
    }

    @Override
    public void uploadProductImages(List<MultipartFile> files, int productId) {
        Product product = getProductById(productId);
        List<ProductImage> productImages = new ArrayList<>();

        try {
            fileSystemService.createDirectoryIfDoesNotExist(uploadDirectory);

            int order = product.getImages().size() + 1;
            for (MultipartFile file : files) {
                validateFile(file);
                ProductImage productImage = new ProductImage(product, file.getOriginalFilename(), file.getContentType(), order);

                String destinationFile = String.format("%s/%s", uploadDirectory, productImage.getSystemFilename());
                fileSystemService.copy(file.getInputStream(), destinationFile);

                productImages.add(productImage);
                order++;
            }
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file");
        }

        productImageRepository.saveAll(productImages);
    }

    private Product getProductById(int id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new DoesNotExistException("Product does not exist"));
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileStorageException("File is empty.");
        }

        if (!isImage(file.getContentType())) {
            throw new InvalidFileTypeException("Invalid file type");
        }
    }

    private boolean isImage(String contentType) {
        List<String> validMimetypes = Arrays.asList("image/png", "image/jpeg", "image/jpg");

        return validMimetypes.contains(contentType);
    }

    public FileResource getImage(String filename) {
        ProductImage productImage = productImageRepository
                .findBySystemFilename(filename)
                .orElseThrow(() -> new DoesNotExistException("Image does not exist."));

        try {
            Path filePath = Paths.get(uploadDirectory, filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return new FileResource(productImage.getOriginalFilename(),
                        MediaType.parseMediaType(productImage.getMimetype()), resource);
            }
            else {
                throw new DoesNotExistException("Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new DoesNotExistException("Could not read file: " + filename, e);
        }
    }

    @Override
    public ProductResponseDTO getProductResponseDTOById(int productId) {
        Product product = getProductById(productId);

        return productMapper.convertProducttoProductResponseDTO(product);
    }

    @Override
    public List<ProductPageDTOProjection> getProducts(String brandName, String categoryName) {
        return productRepository.getProductsAndMainImage(brandName, categoryName);
    }

    @Override
    public ProductResponseDTO updateProduct(int productId, ProductRequestDTO productDTO) {
        Product existingProduct = getProductById(productId);

        existingProduct = productMapper.updateExistingProductFields(existingProduct, productDTO);
        existingProduct = productRepository.save(existingProduct);

        return productMapper.convertProducttoProductResponseDTO(existingProduct);
    }

    @Override
    public void deleteProduct(int productId) throws IOException {
        Product product = getProductById(productId);

        List<String> productImages = productImageRepository.findSystemFilenamesByProductId(productId);

        for (String filename: productImages) {
            deleteProductImage(filename);
        }

        productRepository.delete(product);
    }

    public void deleteProductImage(String filename) throws IOException {
        int numDeleted = productImageRepository
                .deleteBySystemFilename(filename);

        if (numDeleted == 0) {
            throw new DoesNotExistException("Image does not exist.");
        }

        String filePath = String.format("%s/%s", uploadDirectory, filename);
        fileSystemService.delete(filePath);
    }
}
