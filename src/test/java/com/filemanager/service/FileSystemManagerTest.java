package com.filemanager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.filemanager.models.Folder;

public class FileSystemManagerTest {
    // Adding a file to an existing folder
    @Test
    public void test_adding_file_to_existing_folder() {
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("root");
        fileSystemManager.addFileOrFolder("root", "folder1", true);
        fileSystemManager.addFileOrFolder("folder1", "file1.txt", false);
        List<String> contents = fileSystemManager.listContents("folder1");
        assertTrue(contents.contains("file1.txt"));
    }

    // Adding a folder to an existing folder
    @Test
    public void test_adding_folder_to_existing_folder() {
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("root");
        fileSystemManager.addFileOrFolder("root", "folder1", true);
        fileSystemManager.addFileOrFolder("folder1", "subfolder1", true);
        List<String> contents = fileSystemManager.listContents("folder1");
        assertTrue(contents.contains("subfolder1"));
    }

    // Adding a file or folder to a non-existent parent folder
    @Test
    public void test_adding_to_non_existent_parent_folder() {
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("root");
        fileSystemManager.addFileOrFolder("nonExistentFolder", "file1.txt", false);
        List<String> contents = fileSystemManager.listContents("root");
        assertFalse(contents.contains("file1.txt"));
    }

    // Moving a file or folder to a non-existent destination folder
    @Test
    public void test_moving_to_non_existent_destination_folder() {
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("root");
        fileSystemManager.addFileOrFolder("root", "file1.txt", false);
        fileSystemManager.moveFileOrFolder("file1.txt", "nonExistentFolder");
        List<String> contents = fileSystemManager.listContents("root");
        assertTrue(contents.contains("file1.txt"));
    }

    // Moving a non-existent file or folder
    @Test
    public void test_moving_non_existent_file_or_folder() {
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("root");
        fileSystemManager.addFileOrFolder("root", "folder1", true);
        fileSystemManager.moveFileOrFolder("nonExistentFile", "folder1");
        List<String> contents = fileSystemManager.listContents("folder1");
        assertFalse(contents.contains("nonExistentFile"));
    }

    // Listing contents of a folder
    @Test
    public void test_listing_contents_of_folder() {
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("root");
        fileSystemManager.addFileOrFolder("root", "folder1", true);
        fileSystemManager.addFileOrFolder("folder1", "file1.txt", false);
        List<String> contents = fileSystemManager.listContents("folder1");
        assertTrue(contents.contains("file1.txt"));
    }

    // Moving a folder from one folder to another
    @Test
    public void test_move_folder_to_another_folder() {
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("root");
        fileSystemManager.addFileOrFolder("root", "folder1", true);
        fileSystemManager.addFileOrFolder("folder1", "folder2", true);
        fileSystemManager.moveFileOrFolder("folder2", "root");
        List<String> rootContents = fileSystemManager.listContents("root");
        List<String> folder1Contents = fileSystemManager.listContents("folder1");
        assertTrue(rootContents.contains("folder2"));
        assertFalse(folder1Contents.contains("folder2"));
    }

    // Moving a file from one folder to another
    @Test
    public void test_moving_file_to_another_folder() {
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("root");
        fileSystemManager.addFileOrFolder("root", "folder1", true);
        fileSystemManager.addFileOrFolder("root", "folder2", true);
        fileSystemManager.addFileOrFolder("folder1", "file1.txt", false);
        fileSystemManager.moveFileOrFolder("file1.txt", "folder2");
        List<String> contentsFolder1 = fileSystemManager.listContents("folder1");
        List<String> contentsFolder2 = fileSystemManager.listContents("folder2");
        assertFalse(contentsFolder1.contains("file1.txt"));
        assertTrue(contentsFolder2.contains("file1.txt"));
    }

    // Listing the entire directory structure
    @Test
    public void test_listing_entire_directory_structure() {
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("root");
        fileSystemManager.addFileOrFolder("root", "folder1", true);
        fileSystemManager.addFileOrFolder("folder1", "file1.txt", false);
        List<String> structure = fileSystemManager.listDirectoryStructure();
        assertTrue(structure.contains("+ root"));
        assertTrue(structure.contains("  + folder1"));
        assertTrue(structure.contains("    - file1.txt"));
    }

    // Searching for a file with an exact match
    @Test
    public void test_search_file_exact_match() {
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("root");
        fileSystemManager.addFileOrFolder("root", "folder1", true);
        fileSystemManager.addFileOrFolder("folder1", "file1.txt", false);
        String result = fileSystemManager.searchFileExactMatch("folder1", "file1.txt");
        assertEquals("file1.txt", result);
    }

    // Searching for files with a pattern match
    @Test
    public void test_search_file_like_match() {
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("root");
        fileSystemManager.addFileOrFolder("root", "folder1", true);
        fileSystemManager.addFileOrFolder("folder1", "file1.txt", false);
        fileSystemManager.addFileOrFolder("folder1", "file2.jpg", false);
        fileSystemManager.addFileOrFolder("folder1", "subfolder", true);
        fileSystemManager.addFileOrFolder("subfolder", "file3.txt", false);

        List<String> searchResults = fileSystemManager.searchFileLikeMatch("root", ".txt");
        assertEquals(2, searchResults.size());
        assertTrue(searchResults.contains("file1.txt"));
        assertTrue(searchResults.contains("file3.txt"));
    }

    // Listing contents of a non-existent folder
    @Test
    public void test_listing_contents_of_non_existent_folder() {
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("root");
        List<String> contents = fileSystemManager.listContents("non_existent_folder");
        assertTrue(contents.isEmpty());
    }

    // Searching for a file with a pattern that matches no files
    @Test
    public void test_search_file_with_no_matching_pattern() {
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("root");
        fileSystemManager.addFileOrFolder("root", "folder1", true);
        fileSystemManager.addFileOrFolder("folder1", "file1.txt", false);
        List<String> results = fileSystemManager.searchFileLikeMatch("folder1", "pattern");
        assertTrue(results.isEmpty());
    }

    // Searching for a file in a non-existent folder
    @Test
    public void test_searching_for_file_in_non_existent_folder() {
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("root");
        String result = fileSystemManager.searchFileExactMatch("non_existent_folder", "file.txt");
        assertNull(result);
    }

    // Handling of duplicate file or folder names within the same parent folder
    @Test
    public void test_handling_duplicate_names_within_parent_folder() {
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("root");
        fileSystemManager.addFileOrFolder("root", "folder1", true);
        fileSystemManager.addFileOrFolder("folder1", "file1.txt", false);
        fileSystemManager.addFileOrFolder("folder1", "file1.txt", false);
        List<String> contents = fileSystemManager.listContents("folder1");
        assertEquals(1, Collections.frequency(contents, "file1.txt"));
    }

    // Handling of special characters in file and folder names
    @Test
    public void test_handling_special_characters_in_names() {
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("root");
        fileSystemManager.addFileOrFolder("root", "folder$#@!", true);
        fileSystemManager.addFileOrFolder("folder$#@!", "file%^&.txt", false);
        List<String> contents = fileSystemManager.listContents("folder$#@!");
        assertTrue(contents.contains("file%^&.txt"));
    }

    // Performance with a large number of files and folders
    @Test
    public void test_performance_large_number_of_files_and_folders() {
        // Prepare a large number of files and folders
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("root");

        // Add a large number of files and folders
        for (int i = 0; i < 1000; i++) {
            fileSystemManager.addFileOrFolder("root", "folder" + i, true);
            fileSystemManager.addFileOrFolder("folder" + i, "file" + i + ".txt", false);
        }

        // List contents of a specific folder with a large number of items
        List<String> contents = fileSystemManager.listContents("folder500");

        // Assert that the contents contain a specific file
        assertTrue(contents.contains("file500.txt"));
    }

    // Case sensitivity in file and folder names during search operations
    @Test
    public void test_case_sensitivity_search_operations() {
        // Create FileSystemManagerImpl instance
        FileSystemManagerImpl fileSystemManager = new FileSystemManagerImpl("Root");

        // Add files and folders with different case variations
        fileSystemManager.addFileOrFolder("Root", "Folder1", true);
        fileSystemManager.addFileOrFolder("Folder1", "file1.txt", false);
        fileSystemManager.addFileOrFolder("Folder1", "File2.TXT", false);
        fileSystemManager.addFileOrFolder("Folder1", "FOLDER2", true);

        // Search for files with different case variations
        String exactMatch = fileSystemManager.searchFileExactMatch("Folder1", "file1.txt");
        List<String> likeMatchResults = fileSystemManager.searchFileLikeMatch("Folder1", "file");

        // Assertions for case sensitivity
        assertEquals("file1.txt", exactMatch);
        assertTrue(likeMatchResults.contains("file1.txt"));
        assertTrue(likeMatchResults.contains("File2.TXT"));
        assertFalse(likeMatchResults.contains("FOLDER2"));
    }
}
