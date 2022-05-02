package com.tite.ct60a2411_harjoitustyo;

import static com.tite.ct60a2411_harjoitustyo.ImageLoader.LoadImageFromUrl;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class HomeFragment extends Fragment {
    private Button movie1Button;
    private Button movie2Button;
    private ImageButton imageButton;
    private LinearLayout linearLayoutHome;
    private View view;
    private Button popularButton;
    private ArrayList<Movie> movies;

    private SettingsManager settingsManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingsManager = SettingsManager.getInstance();
        view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView imageV = view.findViewById(R.id.firstImage);
        ImageButton imageB = view.findViewById(R.id.imageButton5);
        ImageButton imageB1 = view.findViewById(R.id.imageButton4);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Drawable d;
                    d = LoadImageFromUrl("https://media.finnkino.fi/1012/Event_13317/landscape_large/FantasticBeasts3_670.jpg");
                    imageV.setImageDrawable(d);
/*
                    d = LoadImageFromUrl("https://media.finnkino.fi/1012/Event_13156/portrait_medium/Coda_1080.jpg");
                    imageB.setImageDrawable(d);
                    d = LoadImageFromUrl("https://media.finnkino.fi/1012/Event_13250/portrait_medium/Belfast_1080b.jpg");
                    imageB1.setImageDrawable(d);
*/
                    System.out.println("Image loaded");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        movie1Button = view.findViewById(R.id.movie1Button);
        movie2Button = view.findViewById(R.id.movie2Button);
        popularButton = view.findViewById(R.id.popularButton);
        imageButton = view.findViewById(R.id.imageButton);
        linearLayoutHome = view.findViewById(R.id.linearLayoutHome);

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
                openActivity(movies.get(0));
            }
        });

        movie2Button.setText(movies.get(1).getTitle());
        movie2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(movies.get(1));
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int n = 2;
                    int i = 1;
                    Drawable d;
                    while(movies.get(n) != null) {
                        String s = "imageButton" + i;

                        int resID = getResources().getIdentifier(s, "id", "com.tite.ct60a2411_harjoitustyo");
                        imageButton = (ImageButton) view.findViewById(resID);

                        d = LoadImageFromUrl(movies.get(n).getLargeImageUrl().replaceAll("^http://", "https://"));
                        System.out.println("####" + s + "###");
                        System.out.println("####" + resID + "###");
                        System.out.println("####" + movies.get(n).getLargeImageUrl() + "###");
                        imageButton.setImageDrawable(d);
                        i++;
                        n++;
                        // TODO error catch for unloaded pictures
                    }

                    System.out.println("Image loaded");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        int popularIndex = new Random().nextInt(movies.size());
        popularButton.setText(movies.get(popularIndex).getTitle());
        popularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(movies.get(popularIndex));
            }
        });
    }

    public void openActivity(Movie movie) {
        Intent intent = new Intent(MainActivity.getContext(), MovieActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("movie", movie);
        intent.putExtras(bundle);
        MainActivity.getContext().startActivity(intent);
    }
}
