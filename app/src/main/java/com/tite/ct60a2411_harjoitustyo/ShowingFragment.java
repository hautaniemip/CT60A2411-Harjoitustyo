package com.tite.ct60a2411_harjoitustyo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ShowingFragment extends androidx.fragment.app.Fragment {
    private View view;
    private ListView movieList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_showing, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.movieList = view.findViewById(R.id.currentMovieList);
        // TODO: Move next 5 lines to button press and add are selector
        String url = "https://www.finnkino.fi/xml/Schedule/?area=" + TheatreArea.AreaId.STRAND.getId();
        System.out.println(url);
        XMLReaderTask reader = new XMLReaderTask(MainActivity.getContext(), url, "Show", MainActivity.getTags());
        reader.setCallback(this::dataCallback);
        reader.execute();
        super.onViewCreated(view, savedInstanceState);
    }

    // After XML data is read put it into ListView using custom adapter
    public void dataCallback(ArrayList<String[]> result) {
        // TODO: Show error to user if no movies were found
        if (result == null)
            return;

        ArrayList<Movie> movies = new ArrayList<>();
        for (String[] entry : result) {
            Movie movie = new Movie(entry);
            movies.add(movie);
        }

        MovieArrayAdapter adapter = new MovieArrayAdapter(view.getContext(), movies);
        movieList.setAdapter(adapter);
    }
}
