package com.awwthefirst.photocollection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;

public class AlbumsMenuActivity extends AppCompatActivity implements AlbumMenuRecyclerViewAdapter.OnAlbumClickedListener {

    private static final String TAG = "AlbumsMenuActivity";
    private RecyclerView albumsMenuRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums_menu);

        albumsMenuRecyclerView = findViewById(R.id.albums_menu_recycler_view);

        AlbumMenuRecyclerViewAdapter albumMenuRecyclerViewAdapter =
                new AlbumMenuRecyclerViewAdapter(this);
        albumsMenuRecyclerView.setAdapter(albumMenuRecyclerViewAdapter);
        albumsMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = Utils.getInsetDivider(this, 20);
        albumsMenuRecyclerView.addItemDecoration(dividerItemDecoration);

        loadAlbums();
    }

    private void loadAlbums() {
        File dir = getDir("albums", Context.MODE_PRIVATE);
        File[] files = dir.listFiles();
        for (File file : files) {
            try {
                File json = new File(file, file.getName() + ".json");
                if (json.exists()) {
                    Album album = Album.fromJson(json, false);
                    addAlbum(album);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    @Override
    public void onAlbumLongClicked(Album album) {
        DeleteAlbumDialogFragment deleteAlbumDialogueFragment = new DeleteAlbumDialogFragment
                ((AlbumMenuRecyclerViewAdapter) albumsMenuRecyclerView.getAdapter(), album);
        deleteAlbumDialogueFragment.show(getSupportFragmentManager(), "DeleteAlbumDialog");
    }
}