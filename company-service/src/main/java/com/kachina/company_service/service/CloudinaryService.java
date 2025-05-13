package com.kachina.company_service.service;

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

    public String uploadBase64Image(String base64, String fileName) {
        Map<String, Object> options = ObjectUtils.asMap(
                "public_id", fileName,
                "overwrite", true,
                "resource_type", "image"
        );

        String url = "";

        try {
            Map result = cloudinary.uploader().upload(base64, options);
            url = result.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return url;
    }

}
