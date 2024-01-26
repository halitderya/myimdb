package com.example.myimdb;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;

public class AddRecordFragment extends Fragment {

    private EditText fieldName, fieldGenre, fieldYear, fieldPlot, fieldDirector;
    private Button filmSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_addrecord, container, false);

        // Initialize EditTexts and Button
        fieldName = view.findViewById(R.id.fieldName);
        fieldGenre = view.findViewById(R.id.fieldGenre);
        fieldYear = view.findViewById(R.id.fieldYear);
        fieldDirector = view.findViewById(R.id.fieldDirector);
        fieldPlot = view.findViewById(R.id.fieldPlot);
        filmSave = view.findViewById(R.id.filmSave);


        // Set onClickListener for the save button
        filmSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        return view;
    }

    private void saveData() {
        String name = fieldName.getText().toString();
        String genre = fieldGenre.getText().toString();
        String year = fieldYear.getText().toString();
        String plot = fieldPlot.getText().toString();

        String director= fieldDirector.getText().toString();


        if(CheckAllFields()) {

                SQLiteHelper dbHelper = new SQLiteHelper(getActivity());
                dbHelper.addData(getActivity(), name, genre, year, plot, director); // Assuming addData() method is modified to accept these parameters



        }
    }


    private boolean CheckAllFields() {
        if (fieldName.length() == 0) {
            fieldName.setError("Name is required");
            return false;
        }

        if (fieldGenre.length() == 0) {
            fieldGenre.setError("Genre is required");
            return false;
        }

        if (fieldYear.length() == 0) {
            fieldYear.setError("Year is required");
            return false;
        }

        if (fieldDirector.length() == 0) {
            fieldDirector.setError("Director is required");
            return false;
        }
        if (fieldPlot.length() < 20) {
            fieldPlot.setError("Plot must be minimum 20 Characters");
            return false;
        }

        return true;
    }


}
