package com.example.recipefinder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        final SharedPreferences sp = rootView.getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

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
