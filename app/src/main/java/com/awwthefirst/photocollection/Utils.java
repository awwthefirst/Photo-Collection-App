package com.awwthefirst.photocollection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;
import java.util.function.Function;

public class Utils {

    public static ActivityResultLauncher<Intent> registerToPickImage(Consumer<Uri> consumer,
                                                                     ComponentActivity activity) {
        return  activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        Intent data = result.getData();
                        consumer.accept(data.getData());
                    }
                });
    }

    /**
     * Creates a copy of the image specified with imageUri in internal storage.
     * @param imageUri Specifies the image to be copied.
     * @param destinationDir The folder in internal storage that the image should be copied to.
     * @param resultFileName The name that the copied file should be saved as.
     * @return The Uri of the copy.
     */
    public static Uri copyImageToInternalStorage(Uri imageUri, String destinationDir,
                                                 String resultFileName,
                                                 Context context) throws IOException {
        InputStream in = context.getContentResolver().openInputStream(imageUri);
        File dir = context.getDir(destinationDir, Context.MODE_PRIVATE);
        File destination = new File(dir, resultFileName);
        copy(in, destination);
        return Uri.fromFile(destination);
    }

    private static void copy(InputStream in, File destination) throws IOException {
        try (OutputStream out = new FileOutputStream(destination)) {
            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        }
        in.close();
    }
}
