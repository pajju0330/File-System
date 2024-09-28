package com.filemanager.models;

public class File extends FileSystemItem {
    public File(String name) {
        super(name);
    }

    @Override
    public boolean isFolder() {
        return false;
    }
}

