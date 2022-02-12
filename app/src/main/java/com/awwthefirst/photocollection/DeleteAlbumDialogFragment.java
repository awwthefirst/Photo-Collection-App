package com.awwthefirst.photocollection;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DeleteAlbumDialogFragment extends DialogFragment {

    private AlbumMenuRecyclerViewAdapter albumMenuRecyclerViewAdapter;
    private Album album;

    public DeleteAlbumDialogFragment(AlbumMenuRecyclerViewAdapter albumMenuRecyclerViewAdapter,
                                     Album album) {
        this.albumMenuRecyclerViewAdapter = albumMenuRecyclerViewAdapter;
        this.album = album;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Album");
        builder.setPositiveButton("Delete", (dialog, which) ->
                albumMenuRecyclerViewAdapter.deleteAlbum(album, getContext()));
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        return builder.create();
    }
}
