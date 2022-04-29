package com.tite.ct60a2411_harjoitustyo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    private SettingsManager settingsManager;
    private Spinner languageSpinner;
    private Spinner fontSpinner;
    private Spinner areaSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingsManager = SettingsManager.getInstance();
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        languageSpinner = view.findViewById(R.id.languageSpinner);
        fontSpinner = view.findViewById(R.id.fontSizeSpinner);
        areaSpinner = view.findViewById(R.id.homeAreaSpinner);

        ArrayAdapter<CharSequence> languageAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.language_array, android.R.layout.simple_spinner_item);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(languageAdapter);

        ArrayAdapter<CharSequence> fontAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.fontSize_array, android.R.layout.simple_spinner_item);
        fontAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSpinner.setAdapter(fontAdapter);

        ArrayAdapter<CharSequence> areaAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.area_array, android.R.layout.simple_spinner_item);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                settingsManager.setLanguageIndex(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        fontSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                int fontSize = (16 + (position * 8));
                settingsManager.setFontSize(fontSize);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                int areaID = TheatreArea.AreaId.values()[position].getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


}
