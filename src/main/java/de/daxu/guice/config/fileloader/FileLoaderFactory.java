package de.daxu.guice.config.fileloader;

public class FileLoaderFactory {

    public FileLoader create(boolean external) {
        return external ? new ExternalFileLoader() : new SystemResourceLoader();
    }
}
