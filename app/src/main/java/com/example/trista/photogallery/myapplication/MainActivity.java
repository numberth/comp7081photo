package com.example.trista.photogallery.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.trista.photogallery.R;
import com.example.trista.photogallery.mydb.DataStoreImp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import static android.content.pm.PackageManager.FEATURE_CAMERA_ANY;

public class MainActivity extends AppCompatActivity{

    public static DataStoreImp ds = new DataStoreImp();
    public static ArrayList<String> selectedDelete = new ArrayList<>();
    public static ArrayList<Integer> selectedDeleteIndex = new ArrayList<>();
    public static MyAdapter adapter;
    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;
    private ArrayList<String> createLists;
    private ArrayList<String> photoGallery;
    public static String currentPhotoPath = null;
    private int currentPhotoIndex = 0;
    private PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        Date minDate = new Date(Long.MIN_VALUE);
        Date maxDate = new Date(Long.MAX_VALUE);
        createLists = populateGallery(minDate, maxDate);
        selectedDeleteIndex.clear();
        selectedDelete.clear();
        adapter = new MyAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);

        repopulateGallery();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
        }
    }

    public void repopulateGallery(){
        Date minDate = new Date(Long.MIN_VALUE);
        Date maxDate = new Date(Long.MAX_VALUE);
        photoGallery = populateGallery(minDate, maxDate);
        Log.d("onCreate, size", Integer.toString(photoGallery.size()));
        currentPhotoIndex = photoGallery.size() -1;
        if (photoGallery.size() > 0)
            currentPhotoPath = photoGallery.get(currentPhotoIndex);
    }

    private ArrayList<String> populateGallery(Date minDate, Date maxDate) {
        photoGallery = new ArrayList<>();
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
            adapter.notifyDataSetChanged();
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
            Toast.makeText(MainActivity.this,"No file explore app found",Toast.LENGTH_LONG).show();
        }
    }

    public void openSearch(View view){
        selectedDeleteIndex.clear();
        selectedDelete.clear();
        Intent openSearch = new Intent(this, Search.class);
        openSearch.putExtra("photoPath", currentPhotoPath);
        startActivity(openSearch);
    }

    public void openCamera(View view){
        pm = this.getPackageManager();

        if(pm.hasSystemFeature(FEATURE_CAMERA_ANY)){
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED){
                Intent openCamera = new Intent(this, Capture.class);
                startActivity(openCamera);
            }else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
                Toast.makeText(MainActivity.this,"No camera permission.",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(MainActivity.this,"No camera available.",Toast.LENGTH_LONG).show();
        }
    }

    public void deletePhotoFile(View view){
        try{
            ds.deleteFile(selectedDelete);
            for(int i = 0; i < selectedDeleteIndex.size(); i++){
                createLists.remove((int)selectedDeleteIndex.get(i));
                adapter.notifyItemRemoved(selectedDeleteIndex.get(i));
            }
            selectedDeleteIndex.clear();
            selectedDelete.clear();
            adapter.notifyDataSetChanged();
        }catch(Exception e){
            Toast.makeText(MainActivity.this,"Error deleting picture.",Toast.LENGTH_LONG).show();
            adapter.notifyDataSetChanged();
        }
    }
}
