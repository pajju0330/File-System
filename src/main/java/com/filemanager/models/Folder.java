package com.filemanager.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Folder extends FileSystemItem {
    private List<FileSystemItem> items;

    public Folder(String name) {
        super(name);
        this.items = new ArrayList<>();
    }

    public void addItem(FileSystemItem item) {
        if(Objects.nonNull(getItem(item.getName()))){
            return;
        }
        items.add(item);
    }

    public void removeItem(FileSystemItem item) {
        items.remove(item);
    }

    public FileSystemItem getItem(String name) {
        for (FileSystemItem item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public boolean isFolder() {
        return true;
    }

    public List<FileSystemItem> getItems() {
        return items;
    }
}

