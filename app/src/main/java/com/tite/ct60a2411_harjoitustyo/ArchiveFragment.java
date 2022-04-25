package com.tite.ct60a2411_harjoitustyo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ArchiveFragment extends androidx.fragment.app.Fragment {
    private MovieArchive movieArchive;
    private MovieArrayAdapter adapter;
    private EditText archiveSearch;
    private ListView movieList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        movieArchive = MovieArchive.getInstance();
        return inflater.inflate(R.layout.fragment_archive, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        archiveSearch = view.findViewById(R.id.archiveSearch);
        movieList = view.findViewById(R.id.archiveMovieListView);

        adapter = new MovieArrayAdapter(view.getContext(), movieArchive.getMovies());
        movieList.setAdapter(adapter);

        archiveSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
                System.out.println(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
