package com.myfirstapp.juancarlosroot.myfirstapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String FOLDER = "MyFirstApp";
    private String CLASS = MainActivity.this.getClass().getSimpleName();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    List<Item> mList;
    static final int REQUEST_TAKE_PHOTO = 1;
    LoadList loadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createImageFile();

                    mList = new ArrayList<>();
                    loadList = new LoadList();
                    loadList.execute(mList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

        mList = new ArrayList<>();
        loadList = new LoadList();
        loadList.execute(mList);
    }

    private File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if(file.exists()){
            if (!file.mkdirs()) {
                Log.e(CLASS, "Directory not created");
            }
        }
        else{

            file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),albumName);
            if (!file.mkdirs()) {
                Log.e(CLASS, "Directory int not created");
            }
        }
        return file;
    }

    private File createImageFile() throws IOException {
        Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getAlbumStorageDir(FOLDER);
        if(storageDir != null){
            File image = new File(
                    storageDir,
                    imageFileName+".jpg"
            );

            // Save a file: path for use with ACTION_VIEW intents
            Uri uriSavedImage = Uri.fromFile(image);
            imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
            startActivityForResult(imageIntent, REQUEST_TAKE_PHOTO);

            return image;
        }

        return null;
    }

    private class LoadList extends AsyncTask<List, Integer, List>
    {

        public LoadList(){

        }

        @Override
        protected List doInBackground(List... params) {
            List<Item> mList = params[0];

            if(getAlbumStorageDir(FOLDER).listFiles() != null){
                File file[] = getAlbumStorageDir(FOLDER).listFiles();
                for (int i = 0; i < file.length; i++){
                    Log.i(CLASS, file[i].getName());
                    mList.add(new Item(file[i].getName(), file[i].getAbsolutePath()));
                }
            }

            return mList;
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            Log.i(CLASS, mList.size() + "  sw");
            mAdapter = new CardAdapter(mList);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
