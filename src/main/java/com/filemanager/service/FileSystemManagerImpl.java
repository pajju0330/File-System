package com.filemanager.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.filemanager.models.File;
import com.filemanager.models.FileSystemItem;
import com.filemanager.models.Folder;

public class FileSystemManagerImpl implements FileSystemManager {
    private Folder rootDirectory;

    public FileSystemManagerImpl(String rootName) {
        this.rootDirectory = new Folder(rootName);
    }

    @Override
    public void addFileOrFolder(String parentFolderName, String name, boolean isFolder) {
        Folder parentDir = searchFolder(parentFolderName);
        if (parentDir == null) {
            return; // No parent directory located
        }
        FileSystemItem newItem = isFolder ? new Folder(name) : new File(name);
        parentDir.addItem(newItem);
    }

    @Override
    public void moveFileOrFolder(String sourceName, String destinationFolderName) {
        // Find the source file or directory
        FileSystemItem sourceItem = locateItem(sourceName);
        if (sourceItem == null) {
            return; // Source does not exist
        }

        // Identify the parent directory of the source
        Folder currentParentDir = locateParentFolder(sourceName);
        if (currentParentDir == null) {
            return; // No parent directory found
        }

        // Find the destination folder
        Folder targetFolder = searchFolder(destinationFolderName);
        if (targetFolder == null) {
            return; // Target directory does not exist
        }

        // Move source to target folder
        currentParentDir.removeItem(sourceItem);
        targetFolder.addItem(sourceItem);
    }

    @Override
    public List<String> listContents(String folderName) {
        Folder directory = searchFolder(folderName);
        if (directory == null) {
            return Collections.emptyList(); // Directory not found
        }

        List<String> items = new ArrayList<>();
        for (FileSystemItem item : directory.getItems()) {
            items.add(item.getName());
        }
        return items;
    }

    @Override
    public List<String> listDirectoryStructure() {
        List<String> hierarchy = new ArrayList<>();
        generateDirectoryStructure(hierarchy, rootDirectory, 0);
        return hierarchy;
    }

    private void generateDirectoryStructure(List<String> hierarchy, Folder directory, int level) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < level; i++) {
            indent.append("  ");
        }
        hierarchy.add(indent + "+ " + directory.getName());
        for (FileSystemItem item : directory.getItems()) {
            if (item.isFolder()) {
                generateDirectoryStructure(hierarchy, (Folder) item, level + 1);
            } else {
                hierarchy.add(indent + "  - " + item.getName());
            }
        }
    }

    @Override
    public String searchFileExactMatch(String folderName, String fileName) {
        Folder directory = searchFolder(folderName);
        if (directory == null) {
            return null; // Folder does not exist
        }

        // Check for exact match with case-insensitive comparison
        for (FileSystemItem item : directory.getItems()) {
            if (!item.isFolder() && item.getName().equalsIgnoreCase(fileName)) {
                return item.getName(); // Found exact match
            }
        }
        return null;
    }

    @Override
    public List<String> searchFileLikeMatch(String folderName, String pattern) {
        Folder directory = searchFolder(folderName);
        if (directory == null) {
            return Collections.emptyList(); // Folder not found
        }

        List<String> matchingFiles = new ArrayList<>();
        retrieveMatchingFiles(directory, pattern.toLowerCase(), matchingFiles);
        return matchingFiles;
    }

    private void retrieveMatchingFiles(Folder directory, String pattern, List<String> matchingFiles) {
        for (FileSystemItem item : directory.getItems()) {
            // Look for partial matches (case-insensitive)
            if (!item.isFolder() && item.getName().toLowerCase().contains(pattern)) {
                matchingFiles.add(item.getName());
            } else if (item.isFolder()) {
                retrieveMatchingFiles((Folder) item, pattern, matchingFiles);
            }
        }
    }

    // Helper function to locate a folder by name
    private Folder searchFolder(String folderName) {
        return (Folder) locateItem(folderName);
    }

    // Helper function to locate a file or folder by name
    private FileSystemItem locateItem(String name) {
        return locateItemRecursively(rootDirectory, name);
    }

    private FileSystemItem locateItemRecursively(Folder directory, String name) {
        if (directory.getName().equals(name)) {
            return directory;
        }

        for (FileSystemItem item : directory.getItems()) {
            if (item.getName().equals(name)) {
                return item;
            }

            if (item.isFolder()) {
                FileSystemItem locatedItem = locateItemRecursively((Folder) item, name);
                if (locatedItem != null) {
                    return locatedItem;
                }
            }
        }
        return null;
    }

    // Helper function to find the parent folder of a given file or folder
    private Folder locateParentFolder(String itemName) {
        return locateParentFolderRecursively(rootDirectory, itemName);
    }

    private Folder locateParentFolderRecursively(Folder directory, String itemName) {
        for (FileSystemItem item : directory.getItems()) {
            if (item.getName().equals(itemName)) {
                return directory;
            }
            if (item.isFolder()) {
                Folder foundParent = locateParentFolderRecursively((Folder) item, itemName);
                if (foundParent != null) {
                    return foundParent;
                }
            }
        }
        return null;
    }
}