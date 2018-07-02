package com.example.trista.photogallery.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.trista.photogallery.R;

import static com.example.trista.photogallery.myapplication.MainActivity.adapter;
import static com.example.trista.photogallery.myapplication.MainActivity.ds;

import java.io.File;

public class Search extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        intent = new Intent(this, MainActivity.class);
    }

    public void search(View view){
        int enddateInput, startdateInput;

        EditText startdate = (EditText) findViewById(R.id.search_startdate);
        if(startdate.getText().toString().equals("")){
            startdateInput = Integer.MIN_VALUE;
        }else{
            startdateInput = Integer.parseInt(startdate.getText().toString());
        }
        intent.putExtra("startdate", startdateInput);

        EditText enddate = (EditText) findViewById(R.id.search_enddate);
        if(enddate.getText().toString().equals("")){
            enddateInput = Integer.MAX_VALUE;
        }else{
            enddateInput = Integer.parseInt(enddate.getText().toString());
        }
        intent.putExtra("enddate", enddateInput);

        EditText location = (EditText) findViewById(R.id.search_location);
        String locationInput = location.getText().toString();
        Log.d("location",locationInput);
        intent.putExtra("location", locationInput);

        EditText caption = (EditText) findViewById(R.id.search_caption);
        String captionInput = caption.getText().toString();
        Log.d("caption",captionInput);
        intent.putExtra("caption", captionInput);

        startActivity(intent);
    }

    public void rename(View view){
        Intent intentMain = getIntent();
        String oldFileName = intentMain.getStringExtra("photoPath");
        String [] fileSplit = oldFileName.split(File.separator);
        EditText newNameInput = findViewById(R.id.renameInput);
        String newName = newNameInput.getText().toString();

        String path = "";
        int i = 0;
        while(i <= fileSplit.length -2){
            path += fileSplit[i] + File.separator;
            i++;
        }
        File newFile = new File(path, newName + ".jpg");
        File oldFile = new File(oldFileName);

        ds.renameFile(oldFile, newFile);

        adapter.notifyDataSetChanged();

        startActivity(intent);
    }
}
