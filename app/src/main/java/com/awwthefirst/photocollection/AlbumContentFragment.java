package com.awwthefirst.photocollection;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumContentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "AlbumContentFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView albumRecyclerView;

    public AlbumContentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlbumContentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlbumContentFragment newInstance(String param1, String param2) {
        AlbumContentFragment fragment = new AlbumContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album_content, container, false);

        albumRecyclerView = view.findViewById(R.id.album_content_recyler_view);

        return view;
    }

    public void openAlbum(ImageItem[] album) {
//        AlbumContentRecyclerViewAdapter albumContentRecyclerViewAdapter = new AlbumContentRecyclerViewAdapter(album);
//        albumRecyclerView.setAdapter(albumContentRecyclerViewAdapter);
//        albumRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void addImageItem(ImageItem imageItem) {
        if (albumRecyclerView.getAdapter() == null) {
            openAlbum(new ImageItem[]{imageItem});
            return;
        }
//        ((AlbumContentRecyclerViewAdapter)albumRecyclerView.getAdapter()).addImageItem(imageItem, );
    }
}