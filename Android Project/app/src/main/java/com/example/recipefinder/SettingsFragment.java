package com.example.recipefinder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);

        populateView(rootView);

        return rootView;
    }

    public void populateView(final View rootView){
        // Setup Day/Night layout
        Spinner spinner = (Spinner) rootView.findViewById(R.id.settings_day_night_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(), R.array.day_night_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Store settings change
                SharedPreferences sp = rootView.getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                String s = sp.getString("Day/Night", null);
                ;
                if (s != null && s.equals("light")) {
                    s = "dark";
                } else {
                    s = "light";
                }

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("Day/Night", s);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        final SharedPreferences sp = rootView.getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String string = sp.getString("Day/Night", null);
        if(string.equals("dark"))
            spinner.setSelection(1);

        // Setup number layout
        TextInputLayout text = rootView.findViewById(R.id.setting_number_text_input);
        text.setHint(("" + sp.getInt("number", 10)));
        text.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    sp.edit().putInt("number", Math.min(Integer.parseInt(s.toString()), 100)).apply();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });

    }
}
