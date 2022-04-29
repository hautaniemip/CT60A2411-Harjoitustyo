package com.tite.ct60a2411_harjoitustyo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShowingFragment extends Fragment {
    private final ArrayList<Integer> areaIds = new ArrayList<>();
    private SettingsManager settingsManager;
    private View view;
    private ListView movieList;
    private TextView errorText;
    private Button dateButton;
    private Date selectedTime;
    private Spinner areaSpinner;
    private int selectedId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_showing, container, false);
        settingsManager = SettingsManager.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        movieList = view.findViewById(R.id.currentMovieList);
        errorText = view.findViewById(R.id.errorText);

        selectedTime = new Date();

        dateButton = view.findViewById(R.id.dateButton);
        dateButton.setText(getString(R.string.date_button, selectedTime.getDate(), selectedTime.getMonth() + 1));
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        areaSpinner = view.findViewById(R.id.areaSpinner);

        updateAreas();

        selectedId = settingsManager.getHomeArea().getId();
        updateList(selectedId);

        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedId = areaIds.get(i);
                updateList(selectedId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

    public void areaCallback(ArrayList<String[]> result) {
        if (result == null) {
            System.out.println("No areas found");
            return;
        }

        ArrayList<String> areas = new ArrayList<>();

        for (String[] entry : result) {
            areas.add(entry[0]);
            areaIds.add(Integer.parseInt(entry[1]));
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, areas);

        // Apply the adapter to the spinner
        areaSpinner.setAdapter(adapter);

        areaSpinner.setSelection(areaIds.indexOf(selectedId));
    }

    private void updateList(int id) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String url = "https://www.finnkino.fi/xml/Schedule/?area=" + id + "&dt=" + format.format(selectedTime);

        XMLReaderTask reader = new XMLReaderTask(MainActivity.getContext(), url, "Show", MainActivity.getTags());
        reader.setCallback(this::dataCallback);
        reader.execute();
    }

    private void updateAreas() {
        String url = "https://www.finnkino.fi/xml/TheatreAreas";

        XMLReaderTask reader = new XMLReaderTask(MainActivity.getContext(), url, "TheatreArea", new String[]{"Name", "ID"});
        reader.setCallback(this::areaCallback);
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
                updateList(selectedId);
            }
        }, selectedTime.getYear() + 1900, selectedTime.getMonth(), selectedTime.getDate());
        datePickerDialog.show();
    }
}
