package com.example.trista.photogallery.mydb;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataStoreImp implements InterDataStore{
    public File populateFiles(){
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "/Android/data/com.example.trista.photogallery/files/Pictures");

        return file;
    }

    public File createFile(String location, String caption, File dir){
        String locationInput = location;
        String captionInput = caption;
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm_ssSS").format(new Date());
        String imageFileName = timeStamp;
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = dir;
        File image = new File(storageDir, imageFileName + "_"
                + locationInput + "_" + captionInput + ".jpg");

        return image;
    }

    public boolean deleteFile(ArrayList<String> deleteArray){
        try{
            for(int i = 0; i < deleteArray.size(); i++){
                File file = new File(deleteArray.get(i));
                file.delete();
            }
        }catch(Exception e){
            Log.d("ERROR MSG: ", "error deleting file.");
            return false;
        }
        return true;
    }

    public boolean renameFile(File dir, File dirNew){
        try{
            File oldFile = dir;
            File newFile = dirNew;
            if(oldFile.exists()){
                return oldFile.renameTo(newFile);
            }
        }catch(Exception e){
            Log.d("ERROR MSG: ", "error renaming file.");
            return false;
        }
        return true;
    }
}
