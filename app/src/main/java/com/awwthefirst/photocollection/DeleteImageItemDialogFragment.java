package com.awwthefirst.photocollection;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DeleteImageItemDialogFragment extends DialogFragment {

    private AlbumContentRecyclerViewAdapter albumContentRecyclerViewAdapter;
    private ImageItem imageItem;

    public DeleteImageItemDialogFragment(AlbumContentRecyclerViewAdapter albumContentRecyclerViewAdapter,
                                     ImageItem imageItem) {
        this.albumContentRecyclerViewAdapter = albumContentRecyclerViewAdapter;
        this.imageItem = imageItem;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Album");
        builder.setPositiveButton("Delete", (dialog, which) ->
                albumContentRecyclerViewAdapter.deleteImageItem(imageItem));
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        return builder.create();
    }
}
