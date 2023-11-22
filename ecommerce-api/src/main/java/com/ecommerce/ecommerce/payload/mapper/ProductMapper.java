package com.ecommerce.ecommerce.payload.mapper;

import com.ecommerce.ecommerce.model.Brand;
import com.ecommerce.ecommerce.model.Category;
import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.payload.request.ProductRequestDTO;
import com.ecommerce.ecommerce.payload.response.ProductResponseDTO;
import com.ecommerce.ecommerce.service.BrandService;
import com.ecommerce.ecommerce.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ModelMapper modelMapper;

    public ProductMapper(CategoryService categoryService, BrandService brandService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.modelMapper = modelMapper;

        modelMapper.getConfiguration().setSkipNullEnabled(true);

        TypeMap<Product, ProductResponseDTO> propertyMapper = modelMapper.createTypeMap(Product.class, ProductResponseDTO.class);

        propertyMapper.addMappings(mapper -> {
                    mapper.map(src -> src.getCategory().getName(), ProductResponseDTO::setCategory);
                    mapper.map(src -> src.getBrand().getName(), ProductResponseDTO::setBrand);
                }
        );

    }

    public Product convertProductRequestDtoToProduct(ProductRequestDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);

        setCategoryAndBrandFieldsOfProduct(product, productDTO);

        return product;
    }

    public ProductResponseDTO convertProducttoProductResponseDTO(Product product) {
        ProductResponseDTO dto = modelMapper.map(product, ProductResponseDTO.class);

        List<String> productImages = product.getImages()
                .stream()
                .map(productImage -> String.format("/api/products/image/%s", productImage.getSystemFilename()))
                .collect(Collectors.toList());

        dto.setProductImages(productImages);
        return dto;
    }

    public Product updateExistingProductFields(Product existingProduct, ProductRequestDTO productDTO) {
        setCategoryAndBrandFieldsOfProduct(existingProduct, productDTO);

        modelMapper.map(productDTO, existingProduct);

        return existingProduct;
    }

    private void setCategoryAndBrandFieldsOfProduct(Product product, ProductRequestDTO productDTO) {
        if (productDTO.getCategory() != null) {
            Category category = categoryService.findCategoryByName(productDTO.getCategory());
            product.setCategory(category);
        }

        if (productDTO.getBrand() != null) {
            Brand brand = brandService.findBrandByName(productDTO.getBrand());
            product.setBrand(brand);
        }
    }
}
