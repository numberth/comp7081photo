package com.example.trista.photogallery.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trista.photogallery.R;
import com.example.trista.photogallery.mydb.DataStoreImp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static DataStoreImp ds = new DataStoreImp();
    public static final int REQUEST_TAKE_PHOTO = 1;
    private ArrayList<String> photoGallery;
    private String currentPhotoPath = null;
    private int currentPhotoIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton btnLeft = (ImageButton)findViewById(R.id.clickLeft);
        ImageButton btnRight = (ImageButton)findViewById(R.id.clickRight);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);

        repopulateGallery();
    }

    public void repopulateGallery(){
        Date minDate = new Date(Long.MIN_VALUE);
        Date maxDate = new Date(Long.MAX_VALUE);
        photoGallery = populateGallery(minDate, maxDate);
        Log.d("onCreate, size", Integer.toString(photoGallery.size()));
        currentPhotoIndex = photoGallery.size() -1;
        if (photoGallery.size() > 0)
            currentPhotoPath = photoGallery.get(currentPhotoIndex);
        displayPhoto(currentPhotoPath);
    }

    public void onClick( View v) {
        if (photoGallery.size() > 0){
            switch (v.getId()) {
                case R.id.clickLeft:
                    --currentPhotoIndex;
                    break;
                case R.id.clickRight:
                    ++currentPhotoIndex;
                    break;
                default:
                    break;
            }

            if (currentPhotoIndex < 0)
                currentPhotoIndex = photoGallery.size() -1;
            if (currentPhotoIndex >= photoGallery.size())
                currentPhotoIndex = 0;
        }

        currentPhotoPath = photoGallery.get(currentPhotoIndex);
        Log.d("photoleft, size", Integer.toString(photoGallery.size()));
        Log.d("photoleft, index", Integer.toString(currentPhotoIndex));
        displayPhoto(currentPhotoPath);
    }

    private ArrayList<String> populateGallery(Date minDate, Date maxDate) {
        photoGallery = new ArrayList<String>();
        File file = ds.populateFiles();
        File[] fList = file.listFiles();
        try{
            Intent i = getIntent();
            int sdate = i.getExtras().getInt("startdate");
            int edate = i.getExtras().getInt("enddate");
            String location = i.getStringExtra("location");
            String caption = i.getStringExtra("caption");
            Log.d("start date", "value: " + sdate);
            Log.d("end date", "value: " + edate);
            Log.d("location", "value: " + location);
            Log.d("caption", "value: " + caption);

            if (fList != null) {
                for (File f : file.listFiles()){
                    String[] fileName = f.getName().split("_");
                    int fileDate = Integer.parseInt(fileName[0]);
                    Log.d("file location", "value raw: " + fileName[3]);
                    String fileLocation = fileName[3];
                    Log.d("file location", "value: " + fileLocation);
                    String fileCaption = "";
                    if(fileName.length > 4){
                        fileCaption = fileName[4].replace("-", " ");
                        Log.d("file caption", "value: " + fileCaption);
                    }

                    if(fileDate >= sdate && fileDate <= edate){
                        if(location.equals("") || fileLocation.contains(location)){
                            if(!caption.equals("") && !fileCaption.contains(caption)){
                                Log.d("NO", "NO CAPTION MATCH");
                                continue;
                            }
                            Log.d("location", "location match");
                            photoGallery.add(f.getPath());
                        }
                    }
                }
            }
            return photoGallery;
        }catch(Exception e){
            Log.d("ERROR MSG: ", "no search dates");
        }

        if (fList != null) {
            for (File f : file.listFiles()) {
                photoGallery.add(f.getPath());
                Log.d("ERROR MSG: ", "listing all");
            }
        }
        return photoGallery;
    }

    private void displayPhoto(String path) {
        if(photoGallery.size() > 0){
            Bitmap bit = BitmapFactory.decodeFile(path);
            int halfDeviceWidth = Resources.getSystem().getDisplayMetrics().widthPixels / 2;
            int bitmapWidth = bit.getWidth();
            int bitmapScale = (int)Math.ceil(bitmapWidth/halfDeviceWidth);
            if(bitmapScale < 1)
                bitmapScale = 1;
            int bitmapHeight = (int)Math.ceil(bit.getHeight()/bitmapScale);

            Bitmap scaledBit = bit.createScaledBitmap(bit, halfDeviceWidth, bitmapHeight, false);
            ImageView iv = (ImageView) findViewById(R.id.mImageView);
            iv.setImageBitmap(scaledBit);
        }else{
            Toast.makeText(MainActivity.this,"There are no photos to show.",Toast.LENGTH_LONG).show();
        }
    }

    public void openFolder(View view){
        Uri selectedUri = Uri.parse(getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(selectedUri, "resource/folder");

        if (intent.resolveActivityInfo(getPackageManager(), 0) != null)
        {
            startActivity(intent);
        }
        else
        {
            Toast.makeText(MainActivity.this,"no file explore app found",Toast.LENGTH_LONG).show();
        }
    }

    public void openSearch(View view){
        Intent openSearch = new Intent(this, Search.class);
        openSearch.putExtra("photoPath", currentPhotoPath);
        startActivity(openSearch);
    }

    public void openCamera(View view){
        Intent openCamera = new Intent(this, Capture.class);
        startActivity(openCamera);
    }

    public void deletePhotoFile(View view){
        File file = new File(currentPhotoPath);
        try{
            ds.deleteFile(file);
        }catch(Exception e){
            Toast.makeText(MainActivity.this,"Error deleting picture.",Toast.LENGTH_LONG).show();
        }
        repopulateGallery();
    }

    public ArrayList<String> getPhotoGallery(){
        return photoGallery;
    }

}
