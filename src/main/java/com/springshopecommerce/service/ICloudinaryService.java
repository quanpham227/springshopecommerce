package com.springshopecommerce.service;

import com.springshopecommerce.dto.CloudinaryDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryService {

    CloudinaryDTO upload(MultipartFile file);

    void delete(String publicId);

    ByteArrayResource download(String publicId, int width, int height,
                               boolean isAvatar);
}
