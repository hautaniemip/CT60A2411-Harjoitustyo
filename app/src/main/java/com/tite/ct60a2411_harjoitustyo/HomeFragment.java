package com.tite.ct60a2411_harjoitustyo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.InputStream;
import java.net.URL;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);

    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView image = view.findViewById(R.id.firstImage);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    Drawable d = LoadImageFromWebOperations("https://media.finnkino.fi/1012/Event_13317/landscape_large/FantasticBeasts3_670.jpg");
                    image.setImageDrawable(d);
                    System.out.println("Image loaded");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, null);
            System.out.println("Loading the photo");
            return d;
        } catch (Exception e) {
            System.out.println("Execption in loafing the image" + e);
            return null;
        }
    }

}
