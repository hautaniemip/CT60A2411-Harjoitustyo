package com.tite.ct60a2411_harjoitustyo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class SettingsFragment extends Fragment {
    private SettingsManager settingsManager;
    private Spinner languageSpinner;
    private Spinner fontSpinner;
    private Spinner areaSpinner;
    private EditText archiveDays;

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
        archiveDays = view.findViewById(R.id.editTextDays);

        ArrayAdapter<CharSequence> languageAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.language_array, android.R.layout.simple_spinner_item);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(languageAdapter);
        languageSpinner.setSelection(settingsManager.getLanguageIndex());

        ArrayAdapter<CharSequence> fontAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.fontSize_array, android.R.layout.simple_spinner_item);
        fontAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSpinner.setAdapter(fontAdapter);
        fontSpinner.setSelection(settingsManager.getFontSize());

        ArrayAdapter<CharSequence> areaAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.area_array, android.R.layout.simple_spinner_item);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);
        areaSpinner.setSelection(settingsManager.getHomeArea().ordinal());

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                switch (position) {
                    case 0:
                        MainActivity.setLanguage("en");
                        break;
                    case 1:
                        MainActivity.setLanguage("fi");
                        break;
                    default:
                        MainActivity.setLanguage("en");
                        break;
                }

                if (position != settingsManager.getLanguageIndex())
                    reloadFragment();

                settingsManager.setLanguageIndex(position);
                settingsManager.saveSettings();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        fontSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                MainActivity.setFontSize(position);
                if (position != settingsManager.getFontSize())
                    reloadFragment();
                settingsManager.setFontSize(position);
                settingsManager.saveSettings();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                TheatreArea.AreaId areaID = TheatreArea.AreaId.values()[position];
                settingsManager.setHomeArea(areaID);
                settingsManager.saveSettings();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        archiveDays.setText(String.valueOf(settingsManager.getUpdateArchiveLength()));
        archiveDays.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    int length = Integer.parseInt(charSequence.toString());
                    settingsManager.setUpdateArchiveLength(length);
                    settingsManager.saveSettings();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void reloadFragment() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setReorderingAllowed(false);
        fragmentTransaction.detach(this).commit();
        fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.attach(this).commit();
    }
}
