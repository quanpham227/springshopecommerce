package com.springshopecommerce.service.impl;

import com.springshopecommerce.dto.CloudinaryDTO;
import com.springshopecommerce.dto.ProductDTO;
import com.springshopecommerce.dto.ProductImageDTO;
import com.springshopecommerce.entity.*;
import com.springshopecommerce.exception.CloudinaryException;
import com.springshopecommerce.exception.ImageProcessingException;
import com.springshopecommerce.exception.NotFoundException;
import com.springshopecommerce.exception.UpdateProductException;
import com.springshopecommerce.repository.CategoryRepository;
import com.springshopecommerce.repository.ManufacturerRepository;
import com.springshopecommerce.repository.ProductImageRepository;
import com.springshopecommerce.repository.ProductRepository;
import com.springshopecommerce.service.ICloudinaryService;
import com.springshopecommerce.service.IProductService;
import com.springshopecommerce.utils.ProductUtils;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ICloudinaryService cloudinaryService;

    @Autowired
    private ProductImageRepository  productImageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;



    @Override
    public Page<ProductDTO> findByNameContainsIgnoreCase(String name, Pageable pageable) {
        Page<ProductDTO>  productDTOPage = productRepository.findByNameContainsIgnoreCase(name, pageable);
        return productDTOPage;
    }

    @Override
    public Page<ProductDTO> findAllProductsPaginged(Pageable pageable) {
        Page<ProductDTO> productDTOPage = productRepository.findAllPaginged(pageable);
        return productDTOPage;
    }

    @Override
    public ProductDTO findByProductId(Long id) {
        ProductDTO productDTO = productRepository.findByProductId(id).
                orElseThrow(() -> new NotFoundException("Canot find product with  "+ id));
        return productDTO;

    }


    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        List<ProductImageEntity> productImages = new ArrayList<>();
        if (productDTO.getImageFile() != null &&  productDTO.getImageFile().length > 0) {
            for (MultipartFile file : productDTO.getImageFile()) {
                if (file != null && !file.isEmpty()) {
                    try {
                        String fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
                        CloudinaryDTO cloudinaryDTO = cloudinaryService.upload(file, "products");
                        ProductImageEntity productImage = new ProductImageEntity();
                        productImage.setFileName(fileName);
                        productImage.setPublicId(cloudinaryDTO.getPublicId());
                        productImage.setUrl(cloudinaryDTO.getUrl());

                        // Lưu ProductImageEntity vào cơ sở dữ liệu
                        productImage = productImageRepository.save(productImage);

                        productImages.add(productImage);
                    } catch (Exception e) {
                        throw new CloudinaryException("canot save image");
                    }
                }
            }
        }


        ProductEntity productEntity =new ProductEntity();

        productEntity.setId(productDTO.getId());
        productEntity.setName(productDTO.getName());
        productEntity.setQuantity(productDTO.getQuantity());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setDiscount(productDTO.getDiscount());
        productEntity.setStatus(productDTO.getStatus());


        // Thiết lập ManufacturerEntity cho ProductEntity
        ManufacturerEntity manufacturer = manufacturerRepository.findManufacturerEntityById(productDTO.getManufacturerId())
                .orElseThrow(() -> new NotFoundException("manufacturer not found"));
        productEntity.setManufacturer(manufacturer);

        // Thiết lập CategoryEntity cho ProductEntity
        CategoryEntity category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("category not found"));
        productEntity.setCategory(category);

        // Thiết lập danh sách hình ảnh cho ProductEntity
        productEntity.setImages(productImages);

        // Thiết lập hình ảnh đại diện cho ProductEntity
        if (! productImages.isEmpty()) {
            ProductImageEntity firstImage = productImages.get(0);
            productEntity.setImage(firstImage);
        }

        productEntity = productRepository.save(productEntity);

        return modelMapper.map(productEntity, ProductDTO.class);
    }



    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        // Sao chép giá trị ban đầu
        ProductEntity originalProductEntity = productRepository.findProductEntitiesById(productDTO.getId())
                .orElseThrow(() -> new NotFoundException("Product not found"));
        List<ProductImageEntity> originalProductImages = productImageRepository.getProductImagesByProductId(productDTO.getId()).stream()
                .map(ProductImageDTO -> modelMapper.map(ProductImageDTO, ProductImageEntity.class))
                .collect(Collectors.toList());

        ProductEntity productEntity;
        List<ProductImageEntity> newProductImages;
        try {
            // Cập nhật thông tin sản phẩm
            productEntity = productRepository.findProductEntitiesById(productDTO.getId())
                    .orElseThrow(() -> new NotFoundException("Product not found"));

            if (productDTO.getName() != null) {
                productEntity.setName(productDTO.getName());
            }
            productEntity.setQuantity(productDTO.getQuantity());

            if (productDTO.getPrice() != null) {
                productEntity.setPrice(productDTO.getPrice());
            }
            if (productDTO.getDiscount() != null) {
                productEntity.setDiscount(productDTO.getDiscount());
            }
            if (productDTO.getDescription() != null) {
                productEntity.setDescription(productDTO.getDescription());
            }
            if (productDTO.getStatus() != null) {
                productEntity.setStatus(productDTO.getStatus());
            }
            if (productDTO.getCreateDate() != null) {
                productEntity.setCreateDate(productDTO.getCreateDate());
            }
            if (productDTO.getUpdateDate() != null) {
                productEntity.setUpdateDate(new Date(System.currentTimeMillis()));
            }

            // Thiết lập ManufacturerEntity cho ProductEntity
            ManufacturerEntity manufacturer = manufacturerRepository.findManufacturerEntityById(productDTO.getManufacturerId())
                    .orElseThrow(() -> new NotFoundException("Manufacturer not found"));
            productEntity.setManufacturer(manufacturer);

            // Thiết lập CategoryEntity cho ProductEntity
            CategoryEntity category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found"));
            productEntity.setCategory(category);

            // Xóa hình ảnh cũ
            List<ProductImageEntity> productImages = productImageRepository.getProductImagesByProductId(productDTO.getId()).stream()
                    .map(ProductImageDTO -> modelMapper.map(ProductImageDTO, ProductImageEntity.class))
                    .collect(Collectors.toList());

            for (ProductImageEntity item : productImages) {
                String publicId = item.getPublicId();
                if (publicId != null) {
                    cloudinaryService.delete(publicId);
                }
            }
            productImageRepository.deleteAll(productImages);

            // Tạo danh sách mới cho hình ảnh sản phẩm
            newProductImages = new ArrayList<>();

            // Tải lên hình ảnh mới và tạo các đối tượng ProductImageEntity mới
            if (productDTO.getImageFile() != null && productDTO.getImageFile().length > 0) {
                List<CompletableFuture<ProductImageEntity>> uploadTasks = new ArrayList<>();
                for (MultipartFile file : productDTO.getImageFile()) {
                    // Bỏ qua file rỗng
                    if (file.isEmpty()) {
                        continue;
                    }
                    uploadTasks.add(
                            CompletableFuture.supplyAsync(() -> {
                                try {
                                    String fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
                                    CloudinaryDTO cloudinaryDTO = cloudinaryService.upload(file, "products");
                                    ProductImageEntity productImage = new ProductImageEntity();
                                    productImage.setFileName(fileName);
                                    productImage.setPublicId(cloudinaryDTO.getPublicId());
                                    productImage.setUrl(cloudinaryDTO.getUrl());
                                    return productImage;
                                } catch (CloudinaryException ex) {
                                    throw new CloudinaryException("Failed to upload image to Cloudinary.", ex);
                                }
                            })
                    );
                }

                CompletableFuture<Void> allUploadTasks = CompletableFuture.allOf(uploadTasks.toArray(new CompletableFuture[0]));
                try {
                    allUploadTasks.get();
                } catch (InterruptedException | ExecutionException e) {
                    // Khôi phục trạng thái ban đầu nếu có lỗi
                    productEntity = originalProductEntity;
                    newProductImages = originalProductImages;
                    throw new ImageProcessingException("Failed to upload images.", e);
                }

                newProductImages = uploadTasks.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList());
            }

            if (!newProductImages.isEmpty()) {
                newProductImages = productImageRepository.saveAll(newProductImages);
            }

            // Thiết lập danh sách hình ảnh mới cho ProductEntity
            productEntity.setImages(newProductImages);

            // Thiết lập hình ảnh đại diện cho ProductEntity
            if (!newProductImages.isEmpty()) {
                productEntity.setImage(newProductImages.get(0));
            } else {
                productEntity.setImage(null);
            }

            productEntity = productRepository.save(productEntity);

            return modelMapper.map(productEntity, ProductDTO.class);
        } catch (Exception e) {
            // Khôi phục trạng thái ban đầu nếu có lỗi
            productEntity = originalProductEntity;
            newProductImages = originalProductImages;
            throw new UpdateProductException("Failed to update product.", e);
        }
    }



    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteProductById(id);
    }
}
