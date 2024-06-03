package com.example.blogue_app;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.myapplication.R;

public class SpinnerActivity extends MainActivity implements AdapterView.OnItemSelectedListener {
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

    }

    public void onNothingSelected(AdapterView<?> parent) {
        Spinner spinner = (Spinner) findViewById(R.id.statusSpinner);
        spinner.setOnItemSelectedListener(this);
    }
}