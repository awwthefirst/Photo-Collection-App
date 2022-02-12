package com.awwthefirst.photocollection;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class AlbumContentRecyclerViewAdapter extends RecyclerView.Adapter {

    private static final String TAG = "AlbumRecyclerViewAdapter";
    private Album album;
    private OnImageItemClickedListener onImageItemClickedListener;

    public static class AlbumContentViewHolder extends RecyclerView.ViewHolder
            implements View.OnLongClickListener {

        private final ImageView imageView;
        private final EditText editText;
        private ImageItem imageItem;
        private OnImageItemClickedListener onImageItemClickedListener;

        public AlbumContentViewHolder(@NonNull View itemView,
                                      OnImageItemClickedListener onImageItemClickedListener) {
            super(itemView);

            this.onImageItemClickedListener = onImageItemClickedListener;
            itemView.setOnLongClickListener(this);

            imageView = itemView.findViewById(R.id.image_item_image_view);
            editText = itemView.findViewById(R.id.image_item_text);

            //Updates the ImageItems text
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    imageItem.setText(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }

        public void setImageItem(ImageItem imageItem) {
            this.imageItem = imageItem;
            imageView.setImageURI(imageItem.getImageUri());
            editText.setText(imageItem.getText());
        }

        @Override
        public boolean onLongClick(View v) {
            onImageItemClickedListener.onImageItemLongClicked(imageItem);
            return true;
        }
    }

    public AlbumContentRecyclerViewAdapter(Album album, OnImageItemClickedListener onImageItemClickedListener) {
        this.album = album;
        this.onImageItemClickedListener = onImageItemClickedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_layout,
                parent, false);
        return new AlbumContentViewHolder(view, onImageItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ImageItem imageItem = album.getContents().get(position);
        ((AlbumContentViewHolder)viewHolder).setImageItem(imageItem);
    }

    @Override
    public int getItemCount() {
        return album.getContents().size();
    }

    public void addImageItem(ImageItem imageItem, Context context) {
        album.addImageItem(imageItem, context);
        notifyItemInserted(getItemCount() - 1);
    }

    public Album getAlbum() {
        return album;
    }

    public void deleteImageItem(ImageItem imageItem) {
        int index = album.getContents().indexOf(imageItem);
        album.removeImageItem(imageItem);
        notifyItemRemoved(index);
    }

    public interface OnImageItemClickedListener {
        void onImageItemLongClicked(ImageItem imageItem);
    }
}
