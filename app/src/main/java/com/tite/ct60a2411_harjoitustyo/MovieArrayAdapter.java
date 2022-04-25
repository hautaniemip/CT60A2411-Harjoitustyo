package com.tite.ct60a2411_harjoitustyo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieArrayAdapter extends BaseAdapter implements ListAdapter {
    private final ArrayList<Movie> list;
    private final Context context;

    public MovieArrayAdapter(Context context, ArrayList<Movie> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return list.get(pos).getID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_movie, null);
        }

        TextView movieText = view.findViewById(R.id.movieText);
        Button showButton = view.findViewById(R.id.showButton);

        movieText.setText(list.get(i).toString());

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Open MovieFragment of specific movie
                System.out.println(list.get(i).getID());
            }
        });
        return view;
    }
}
