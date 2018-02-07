package de.daxu.guice.config.fileloader;

import java.io.InputStream;

public interface FileSystem {

    boolean exists(String relativePath);

    void createFile(String relativePath);

    void writeToFile(String relativePath, String content);

    InputStream asInputStream(String relativePath);
}
