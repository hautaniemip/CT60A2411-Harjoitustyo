package com.tite.ct60a2411_harjoitustyo;

import static com.tite.ct60a2411_harjoitustyo.ImageLoader.LoadImageFromUrl;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private Button movie1Button;
    private Button movie2Button;
    private ArrayList<Movie> movies;

    private SettingsManager settingsManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingsManager = SettingsManager.getInstance();
        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView image = view.findViewById(R.id.firstImage);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Drawable d = LoadImageFromUrl("https://media.finnkino.fi/1012/Event_13317/landscape_large/FantasticBeasts3_670.jpg");
                    image.setImageDrawable(d);
                    System.out.println("Image loaded");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        movie1Button = view.findViewById(R.id.movie1Button);
        movie2Button = view.findViewById(R.id.movie2Button);

        String url = "https://www.finnkino.fi/xml/Schedule/?area=" + settingsManager.getHomeArea();

        XMLReaderTask reader = new XMLReaderTask(MainActivity.getContext(), url, "Show", MainActivity.getTags());
        reader.setShowDialog(false);
        reader.setCallback(this::dataCallback);
        reader.execute();

    }

    public void dataCallback(ArrayList<String[]> result) {
        if (result == null)
            return;

        movies = new ArrayList<>();

        for (String[] entry : result)
            movies.add(new Movie(entry));

        movie1Button.setText(movies.get(0).getTitle());
        movie1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.getContext(), MovieActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie", movies.get(0));
                intent.putExtras(bundle);
                MainActivity.getContext().startActivity(intent);
            }
        });

        movie2Button.setText(movies.get(1).getTitle());
        movie2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.getContext(), MovieActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie", movies.get(1));
                intent.putExtras(bundle);
                MainActivity.getContext().startActivity(intent);
            }
        });
    }
}
