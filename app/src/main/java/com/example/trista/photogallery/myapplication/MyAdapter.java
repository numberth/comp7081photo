package com.example.trista.photogallery.myapplication;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trista.photogallery.R;

import static com.example.trista.photogallery.myapplication.MainActivity.currentPhotoPath;
import static com.example.trista.photogallery.myapplication.MainActivity.selectedDelete;
import static com.example.trista.photogallery.myapplication.MainActivity.selectedDeleteIndex;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<String> galleryList;
    private Context context;

    public MyAdapter(Context context, ArrayList<String> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.title.setText("");
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Bitmap bmp = BitmapFactory.decodeFile(galleryList.get(i));
        viewHolder.img.setImageBitmap(bmp);
        viewHolder.img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedDelete.remove(galleryList.get(i))){
                    selectedDeleteIndex.remove(Integer.valueOf(i));
                    viewHolder.title.setText("");
                    Toast.makeText(context, "#" + (i+1) + " picture deselected", Toast.LENGTH_SHORT).show();
                }else{
                    currentPhotoPath = galleryList.get(i);
                    selectedDelete.add(galleryList.get(i));
                    selectedDeleteIndex.add(i);
                    viewHolder.title.setText("Selected");
                    Toast.makeText(context, "#" + (i+1) + " picture selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img;
        public ViewHolder(View view) {
            super(view);

            title = (TextView)view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }

}