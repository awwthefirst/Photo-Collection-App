package com.awwthefirst.photocollection;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class AlbumsMenuActivity extends AppCompatActivity implements AlbumMenuRecyclerViewAdapter.OnAlbumClickedListener {

    private static final String TAG = "AlbumsMenuActivity";
    private RecyclerView albumsMenuRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums_menu);

        albumsMenuRecyclerView = findViewById(R.id.albums_menu_recycler_view);

        AlbumMenuRecyclerViewAdapter albumMenuRecyclerViewAdapter =
                new AlbumMenuRecyclerViewAdapter(this); //TODO needs to save and load albums
        albumsMenuRecyclerView.setAdapter(albumMenuRecyclerViewAdapter);
        albumsMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            loadAlbums();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAlbums() throws IOException {
        File dir = getDir("albums", Context.MODE_PRIVATE);
        File[] files = dir.listFiles();
        for (File file : files) {
            Album album = Album.fromJson(new File(file, file.getName() + ".json"));
            addAlbum(album);
        }
    }

    public void createNewAlbum(View view) {
        Intent intent = new Intent(this, CreateAlbumActivity.class);
        startActivity(intent);
    }

    public void addAlbum(Album album) {
        AlbumMenuRecyclerViewAdapter albumMenuRecyclerViewAdapter =
                (AlbumMenuRecyclerViewAdapter) albumsMenuRecyclerView.getAdapter();
        albumMenuRecyclerViewAdapter.addAlbum(album);
    }

    @Override
    public void onAlbumClicked(Album album) {
        AlbumActivity.startNewInstance(this, album.getName());
    }
}