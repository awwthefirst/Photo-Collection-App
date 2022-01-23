package com.awwthefirst.photocollection;

import android.net.Uri;

public class ImageItem {

    private Uri imageUri;
    private String text;

    public ImageItem(Uri imageUri, String text) {
        this.imageUri = imageUri;
        this.text = text;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
