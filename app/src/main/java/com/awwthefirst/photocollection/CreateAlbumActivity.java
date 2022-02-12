package com.awwthefirst.photocollection;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class CreateAlbumActivity extends AppCompatActivity {

    private static final String TAG = "CreateAlbumActivity";
    private ActivityResultLauncher<Intent> activityResultLauncher = Utils.registerToPickImage(this::setThumbnail,
            this);
    private ImageView thumbnailImageView;
    private EditText albumNameTextView;
    private TextView errorTextView;
    private Uri thumbnailUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_album);

        thumbnailImageView = findViewById(R.id.create_album_thumnail_image_view);
        albumNameTextView = findViewById(R.id.create_album_name_text_view);
        errorTextView = findViewById(R.id.create_album_error_text_view);
    }

    public void chooseThumbnail(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        activityResultLauncher.launch(gallery);
    }

    private void setThumbnail(Uri uri) {
        thumbnailUri = uri;
        thumbnailImageView.setImageURI(uri);
    }

    public void createAlbum(View view) {
        String albumName = ((SpannableStringBuilder) albumNameTextView.getText()).toString();
        if (albumName.isEmpty()) {
            errorTextView.setVisibility(View.VISIBLE);
            errorTextView.setText(R.string.create_album_no_name_error);
            return;
        }

        if (thumbnailUri == null) {
            errorTextView.setVisibility(View.VISIBLE);
            errorTextView.setText(R.string.create_album_no_thumbnail_error);
            return;
        }

        if (Album.doesAlbumExists(albumName, this)) {
            errorTextView.setVisibility(View.VISIBLE);
            errorTextView.setText(R.string.create_album_alerady_exists_error);
            return;
        }

        try {
            Uri newUri = Utils.copyImageToInternalStorage(thumbnailUri, "thumbnails",
                    albumName, this);
            new Album(newUri, albumName).toJson(this);

            Intent intent = new Intent(this, AlbumsMenuActivity.class);
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}