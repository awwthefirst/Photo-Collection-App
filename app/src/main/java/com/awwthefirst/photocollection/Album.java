package com.awwthefirst.photocollection;

import android.content.Context;
import android.net.Uri;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class Album {
    private ArrayList<ImageItem> contents;
    private Uri thumbnail;
    private String name;

    public Album(Uri thumbnail, String name) {
        this.contents = new ArrayList<>();
        this.thumbnail = thumbnail;
        this.name = name;
    }

    public Album(Uri thumbnail, String name, ArrayList<ImageItem> contents) {
        this(thumbnail, name);
        this.contents = contents;
    }

    public void addImageItem(ImageItem imageItem, Context context) {
        contents.add(imageItem);
        try {
            Uri newUri = Utils.copyImageToInternalStorage(imageItem.getImageUri(), "albums",
                    new File(name + "/" + imageItem.getImageUri().getPath()).getName(), context);
            imageItem.setImageUri(newUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ImageItem> getContents() {
        return contents;
    }

    public String getName() {
        return name;
    }

    public Uri getThumbnail() {
        return thumbnail;
    }

    public void toJson(Context context) throws IOException { //TODO shouldn't override existing albums
        File dir = context.getDir("albums", Context.MODE_PRIVATE);
        File albumDir = new File(dir, name);
        albumDir.mkdir();
        File jsonFile = new File(albumDir, name + ".json");

        try (FileOutputStream out = new FileOutputStream(jsonFile)) {
            JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(out));
            jsonWriter.beginObject();
            jsonWriter.name("thumbnail").value(thumbnail.getPath());
            jsonWriter.name("album_name").value(name);

            jsonWriter.name("contents").beginArray();
            for (ImageItem imageItem : contents) {
                jsonWriter.beginObject();
                jsonWriter.name("image_item_uri").value(imageItem.getImageUri().toString());
                jsonWriter.name("image_item_text").value(imageItem.getText());
                jsonWriter.endObject();
            }
            jsonWriter.endArray();

            jsonWriter.endObject();
            jsonWriter.close();
        }
    }

    public static Album fromJson(File file, boolean loadContents) throws IOException {
        Uri thumbnail = null;
        String albumName = null;
        ArrayList<ImageItem> contents = new ArrayList<>();

        JsonReader jsonReader = new JsonReader(new InputStreamReader(new FileInputStream(file),
                StandardCharsets.UTF_8));
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            switch (name) {
                case "thumbnail":
                    thumbnail = Uri.parse(jsonReader.nextString());
                    break;
                case "album_name":
                    albumName = jsonReader.nextString();
                    break;
                case "contents":
                    if (loadContents) {
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            jsonReader.beginObject();

                            Uri imageItemUri = null;
                            String imageItemText = null;

                            while (jsonReader.hasNext()) {
                                String contentsName = jsonReader.nextName();
                                switch (contentsName) {
                                    case "image_item_uri":
                                        imageItemUri = Uri.parse(jsonReader.nextString());
                                        break;
                                    case "image_item_text":
                                        imageItemText = jsonReader.nextString();
                                        break;
                                }
                            }

                            contents.add(new ImageItem(imageItemUri, imageItemText));
                            jsonReader.endObject();
                        }
                        jsonReader.endArray();
                    } else {
                        jsonReader.skipValue();
                    }
            }
        }
        jsonReader.close();

        return new Album(thumbnail, albumName, contents);
    }
}
