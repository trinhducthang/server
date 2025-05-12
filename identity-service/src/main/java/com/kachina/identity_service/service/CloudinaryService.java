package com.kachina.identity_service.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadBase64Image(String base64, String fileName) throws IOException {
        Map<String, Object> options = ObjectUtils.asMap(
                "public_id", fileName,
                "overwrite", true,
                "resource_type", "image"
        );

        Map result = cloudinary.uploader().upload(base64, options);
        return result.get("secure_url").toString();
    }

}
