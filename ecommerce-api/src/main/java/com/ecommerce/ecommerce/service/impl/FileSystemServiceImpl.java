package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.service.FileSystemService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileSystemServiceImpl implements FileSystemService {
    @Override
    public String createDirectories(String directory) throws IOException {
        Path path =  Files.createDirectories(Paths.get(directory));
        return path.toString();
    }

    @Override
    public boolean fileExists(String path) {
        return Files.exists(Paths.get(path));
    }

    @Override
    public boolean createDirectoryIfDoesNotExist(String directory) throws IOException {
        if(!fileExists(directory)) {
            createDirectories(directory);
            return true;
        }

        return false;
    }

    @Override
    public long copy(InputStream in, String targetPath) throws IOException {
        return Files.copy(in, Paths.get(targetPath));
    }

    @Override
    public void delete(String path) throws IOException {
        Files.delete(Paths.get(path));
    }
}
