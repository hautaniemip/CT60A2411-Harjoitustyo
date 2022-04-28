package com.tite.ct60a2411_harjoitustyo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShowingFragment extends Fragment {
    private View view;
    private ListView movieList;
    private TextView errorText;

    private Button dateButton;
    private Date selectedTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_showing, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        movieList = view.findViewById(R.id.currentMovieList);
        errorText = view.findViewById(R.id.errorText);

        selectedTime = new Date();

        dateButton = view.findViewById(R.id.dateButton);
        dateButton.setText(getString(R.string.date_button, selectedTime.getDate(), selectedTime.getMonth()));
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        updateList();
        super.onViewCreated(view, savedInstanceState);
    }

    // After XML data is read put it into ListView using custom adapter
    public void dataCallback(ArrayList<String[]> result) {
        if (result == null) {
            errorText.setText(R.string.movie_not_found);
            return;
        }

        errorText.setText("");

        ArrayList<Movie> movies = new ArrayList<>();
        for (String[] entry : result) {
            Movie movie = new Movie(entry);
            movies.add(movie);
        }

        MovieArrayAdapter adapter = new MovieArrayAdapter(view.getContext(), movies);
        movieList.setAdapter(adapter);

        if (result.size() == 0) {
            errorText.setText(R.string.movie_not_found);
        }
    }

    private void updateList() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String url = "https://www.finnkino.fi/xml/Schedule/?area=" + TheatreArea.AreaId.STRAND.getId() + "&dt=" + format.format(selectedTime);

        XMLReaderTask reader = new XMLReaderTask(MainActivity.getContext(), url, "Show", MainActivity.getTags());
        reader.setCallback(this::dataCallback);
        reader.execute();
    }

    public void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dateButton.setText(getString(R.string.date_button, day, month + 1));
                selectedTime.setDate(day);
                selectedTime.setMonth(month);
                selectedTime.setYear(year - 1900);
                updateList();
            }
        }, selectedTime.getYear() + 1900, selectedTime.getMonth(), selectedTime.getDate());
        datePickerDialog.show();
    }
}
