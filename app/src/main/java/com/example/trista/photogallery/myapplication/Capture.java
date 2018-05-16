package com.example.trista.photogallery.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trista.photogallery.R;

import java.io.File;
import java.io.IOException;

import static com.example.trista.photogallery.myapplication.MainActivity.REQUEST_TAKE_PHOTO;
import static com.example.trista.photogallery.myapplication.MainActivity.ds;

public class Capture extends AppCompatActivity {

    private Uri photoURI = null;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
    }

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {

                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(Capture.this,"file creation error",Toast.LENGTH_LONG).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.trista.photogallery",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
    }

    public File createImageFile() throws IOException {
        EditText location = (EditText) findViewById(R.id.location);
        String locationInput = location.getText().toString();
//        intent.putExtra("location", locationInput);
        EditText caption = (EditText) findViewById(R.id.caption);
        String captionInputRaw = caption.getText().toString();
        String captionInput = captionInputRaw.replace(" ", "-");
//        intent.putExtra("caption", captionInput);

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = ds.createFile(locationInput, captionInput, storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        Log.d("image path", image.getAbsolutePath());
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }
}
