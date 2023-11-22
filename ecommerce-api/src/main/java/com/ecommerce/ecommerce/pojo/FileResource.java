package com.ecommerce.ecommerce.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

@Data
@AllArgsConstructor
public class FileResource {
    String originalFilename;
    MediaType mimetype;
    Resource resource;
}
