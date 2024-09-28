package com.filemanager.service;

import java.util.List;

/**
 * Interface representing a file system manager.
 * Provides methods to manage files and folders within a file system.
 */
public interface FileSystemManager {
    /**
     * Adds a file or folder to the system.
     *
     * @param parentFolderName the name of the parent folder
     * @param name the name of the file or folder to add
     * @param isFolder true if adding a folder, false if adding a file
     */
    void addFileOrFolder(String parentFolderName, String name, boolean isFolder);

    /**
     * Moves a file or folder to a new location.
     *
     * @param sourceName the name of the file or folder to move
     * @param destinationFolderName the name of the destination folder
     */
    void moveFileOrFolder(String sourceName, String destinationFolderName);

    /**
     * Lists the contents of a specific folder.
     *
     * @param folderName the name of the folder
     * @return a list of names of files and folders within the specified folder
     */
    List<String> listContents(String folderName);

    /**
     * Returns the directory structure of each file and folder in the file system.
     *
     * @return a list representing the directory structure
     */
    List<String> listDirectoryStructure();

    /**
     * Searches for an exact file match within a specific folder.
     *
     * @param folderName the name of the folder to search within
     * @param fileName the exact name of the file to search for
     * @return the name of the file if found, null otherwise
     */
    String searchFileExactMatch(String folderName, String fileName);

    /**
     * Searches for files by pattern within a specific folder.
     *
     * @param folderName the name of the folder to search within
     * @param pattern the pattern must be part(Contains) of the file name.
     * @return a list of file names that match the pattern
     */
    List<String> searchFileLikeMatch(String folderName, String pattern);
}
