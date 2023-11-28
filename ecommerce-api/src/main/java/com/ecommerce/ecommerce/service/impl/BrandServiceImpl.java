package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.exception.AlreadyExistsException;
import com.ecommerce.ecommerce.exception.ConstraintViolationException;
import com.ecommerce.ecommerce.exception.DoesNotExistException;
import com.ecommerce.ecommerce.model.Brand;
import com.ecommerce.ecommerce.repository.BrandRepository;
import com.ecommerce.ecommerce.service.BrandService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand createBrand(String brandName) {
        if (brandRepository.findByName(brandName).isPresent()) {
            throw new AlreadyExistsException("Brand already exists");
        }

        return brandRepository.save(new Brand(brandName));
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public void updateBrand(int id, String brandName) {
        Optional<Brand> brand = brandRepository.findByName(brandName);
        if (brand.isPresent() && brand.get().getId() != id) {
            throw new AlreadyExistsException("Brand already exists");
        }

        int rowsUpdated = brandRepository.updateBrandNameById(id, brandName);

        if (rowsUpdated == 0) {
            throw new DoesNotExistException("Brand does not exist");
        }
    }

    public void deleteBrand(int brandId) {
        try {
            int numRows = brandRepository.deleteById(brandId);

            if (numRows == 0) {
                throw new DoesNotExistException("Brand does not exist");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException("Can't delete brand. There are product(s) under this brand.");
        }
    }

    public Brand findBrandByName(String name) {
        return brandRepository
                .findByName(name)
                .orElseThrow(() -> new DoesNotExistException("Brand does not exist"));
    }
}
