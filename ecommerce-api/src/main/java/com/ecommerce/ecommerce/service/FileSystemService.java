package com.ecommerce.ecommerce.service;

import java.io.IOException;
import java.io.InputStream;

public interface FileSystemService {
    String createDirectories(String directory) throws IOException;
    boolean fileExists(String path);
    boolean createDirectoryIfDoesNotExist(String directory) throws IOException;
    long copy(InputStream in, String targetPath) throws IOException;
    void delete(String path) throws IOException;
}
