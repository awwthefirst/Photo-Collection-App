package com.awwthefirst.photocollection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class AlbumMenuRecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<Album> data;
    private OnAlbumClickedListener onAlbumClickedListener;

    public static class AlbumMenuViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private ImageView thumbnailImageView;
        private TextView albumNameTextView;
        private OnAlbumClickedListener onAlbumClickedListener;
        private Album album;

        public AlbumMenuViewHolder(@NonNull View itemView,
                                   OnAlbumClickedListener onAlbumClickedListener) {
            super(itemView);

            this.onAlbumClickedListener = onAlbumClickedListener;

            itemView.setOnClickListener(this);

            thumbnailImageView = itemView.findViewById(R.id.album_thumbnail_image_view);
            albumNameTextView = itemView.findViewById(R.id.album_name_text_view);
        }

        public void setAlbum(Album album) {
            this.album = album;
            thumbnailImageView.setImageURI(album.getThumbnail());
            albumNameTextView.setText(album.getName());
        }

        @Override
        public void onClick(View view) {
            onAlbumClickedListener.onAlbumClicked(album);
        }
    }

    public AlbumMenuRecyclerViewAdapter(OnAlbumClickedListener onAlbumClickedListener, Album[] data) {
        this.onAlbumClickedListener = onAlbumClickedListener;
        this.data = new ArrayList<>(Arrays.asList(data));
    }

    public AlbumMenuRecyclerViewAdapter(OnAlbumClickedListener onAlbumClickedListener) {
        this.onAlbumClickedListener = onAlbumClickedListener;
        this.data = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_menu_item_layout,
                parent, false);
        return new AlbumMenuViewHolder(view, onAlbumClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Album album = data.get(position);
        ((AlbumMenuViewHolder)holder).setAlbum(album);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAlbum(Album album) {
        data.add(album);
        notifyItemInserted(data.size() - 1);
    }

    public interface OnAlbumClickedListener {
        void onAlbumClicked(Album album);
    }
}
