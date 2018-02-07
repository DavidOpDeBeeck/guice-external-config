package de.daxu.guice.config.fileloader;

public class FileSystemFactory {

    public FileSystem create(boolean external) {
        return external ? new ExternalFileSystem() : new SystemResourceSystem();
    }
}
