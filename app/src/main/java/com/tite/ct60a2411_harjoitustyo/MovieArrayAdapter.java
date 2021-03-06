package com.tite.ct60a2411_harjoitustyo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MovieArrayAdapter extends BaseAdapter implements Filterable {
    private final ArrayList<Movie> list;
    private final Context context;
    private final LayoutInflater inflater;
    private ArrayList<Movie> filteredList;

    private SettingsManager settings;

    public MovieArrayAdapter(Context context, ArrayList<Movie> list) {
        this.list = list;
        this.filteredList = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int pos) {
        return filteredList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return filteredList.get(pos).getID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = inflater.inflate(R.layout.layout_movie, null);

        settings = SettingsManager.getInstance();

        TextView movieTitle = view.findViewById(R.id.movieTitle);
        TextView movieText = view.findViewById(R.id.movieText);
        Button showButton = view.findViewById(R.id.showButton);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM. HH:mm");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");


        Movie movie = filteredList.get(i);

        SpannableString title = new SpannableString(movie.getTitle());
        title.setSpan(new RelativeSizeSpan(settings.getFontSize() + 1), 0, title.length(), 0);
        movieTitle.setText(title);

        String movieDataString;
        if (movie.getStartTime() != null) {
            movieDataString =
                    context.getString(R.string.rating) + ": " + movie.getRating() + "\n" +
                            context.getString(R.string.showing) + ": " + dateFormat.format(movie.getStartTime()) + "-" + timeFormat.format(movie.getEndTime()) + "\n" +
                            context.getString(R.string.theatre) + ": " + movie.getTheatreName() + ", " + movie.getAuditorium();
        } else {
            movieDataString = context.getString(R.string.rating) + ": " + movie.getRating();
        }
        movieText.setText(movieDataString);

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.getContext(), MovieActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie", movie);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterString = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();

                final ArrayList<Movie> filteringList = list;
                int count = filteringList.size();
                final ArrayList<Movie> tempFilteredList = new ArrayList<>(count);

                for (Movie movie : filteringList) {
                    String movieTitle = movie.getTitle();
                    String originalTitle = movie.getOriginalTitle();

                    // Match search to title or original title
                    if (movieTitle.toLowerCase().contains(filterString) || originalTitle.toLowerCase().contains(filterString))
                        tempFilteredList.add(movie);
                }

                results.values = tempFilteredList;
                results.count = tempFilteredList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<Movie>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
