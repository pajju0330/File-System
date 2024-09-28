package com.filemanager.service;

import java.util.Collections;
import java.util.List;
import com.filemanager.models.Folder;

public class FileSystemManagerImpl implements FileSystemManager {
    private Folder root;

    public FileSystemManagerImpl(String rootName) {
        this.root = new Folder(rootName);
    }

    @Override
    public void addFileOrFolder(String parentFolderName, String name, boolean isFolder) {

    }

    @Override
    public void moveFileOrFolder(String sourceName, String destinationFolderName) {

    }

    @Override
    public List<String> listContents(String folderName) {
        return null;
    }

    @Override
    public List<String> listDirectoryStructure() {
        return null;
    }

    @Override
    public String searchFileExactMatch(String folderName, String fileName) {
        return "";
    }

    @Override
    public List<String> searchFileLikeMatch(String folderName, String pattern) {
        return null;
    }

}
