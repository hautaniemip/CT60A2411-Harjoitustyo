package com.tite.ct60a2411_harjoitustyo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ArchiveFragment extends androidx.fragment.app.Fragment{
    private MovieArchive movieArchive;
    private ListView movieList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        movieArchive = MovieArchive.getInstance();
        return inflater.inflate(R.layout.fragment_archive, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        movieList = (ListView) view.findViewById(R.id.archiveMovieListView);

        MovieArrayAdapter adapter = new MovieArrayAdapter(view.getContext(), movieArchive.getMovies());
        movieList.setAdapter(adapter);
        super.onViewCreated(view, savedInstanceState);
    }
}
