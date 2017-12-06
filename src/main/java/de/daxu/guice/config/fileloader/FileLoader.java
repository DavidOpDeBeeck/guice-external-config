package de.daxu.guice.config.fileloader;

import java.io.InputStream;

public interface FileLoader {

    InputStream load(String relativePath);
}
