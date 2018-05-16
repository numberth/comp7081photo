package com.example.trista.photogallery.mydb;

import java.io.File;

public interface InterDataStore {
    File createFile(String location, String caption, File dir);
    boolean deleteFile(File dir);
    boolean renameFile(File dir, File dirNew);
    File populateFiles();
}
