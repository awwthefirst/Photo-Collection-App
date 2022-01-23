package com.awwthefirst.photocollection;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;

public class AlbumActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String ARG_ALBUM_NAME = "album_name";
    private RecyclerView albumContentRecyclerView;

    private ActivityResultLauncher<Intent> activityResultLauncher = Utils.registerToPickImage(uri -> {
        ImageItem imageItem = new ImageItem(uri, "");
        ((AlbumContentRecyclerViewAdapter)albumContentRecyclerView.getAdapter())
                .addImageItem(imageItem, this);
    }, this);

    public static void startNewInstance(Context packageContext, String albumName) {
        Intent intent = new Intent(packageContext, AlbumActivity.class);
        intent.putExtra(ARG_ALBUM_NAME, albumName);
        packageContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        Intent intent = getIntent();
        String albumName = intent.getStringExtra(ARG_ALBUM_NAME);

        File albumDir = getDir("albums", Context.MODE_PRIVATE);
        File albumJson = new File(albumDir, albumName + "/" + albumName + ".json");
        try {
            Album album = Album.fromJson(albumJson);
            albumContentRecyclerView = findViewById(R.id.album_content_recyler_view);
            albumContentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            albumContentRecyclerView.setAdapter(new AlbumContentRecyclerViewAdapter(album));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chooseImage() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        activityResultLauncher.launch(gallery);
    }

    public void addImageItem(View view) {
        chooseImage();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: Test");
        super.onDestroy();
    }
}