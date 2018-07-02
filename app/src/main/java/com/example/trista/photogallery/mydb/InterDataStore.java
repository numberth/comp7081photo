package com.example.trista.photogallery.mydb;

import java.io.File;
import java.util.ArrayList;

public interface InterDataStore {
    File createFile(String location, String caption, File dir);
    boolean deleteFile(ArrayList<String> deleteArray);
    boolean renameFile(File dir, File dirNew);
    File populateFiles();
}
