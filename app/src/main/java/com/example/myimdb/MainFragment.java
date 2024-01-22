package com.example.myimdb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
public class MainFragment extends Fragment {

    private SQLiteHelper sqh;
    private SQLiteDatabase sqdb;
    private LinearLayout mainContainer;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mainContainer = view.findViewById(R.id.mainContainer);


        sqh = new SQLiteHelper(getActivity());
        sqdb = sqh.getReadableDatabase();
        loadData();

        return view;
    }

    private void loadData() {
        Cursor cursor = sqh.getData();

        if (cursor != null && cursor.moveToFirst()) {
            do {

                String name = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.NAME));
                String genre = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.GENRE));
                String year = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.YEAR));
                String plot = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteHelper.PLOT));


                TextView nameTextView = new TextView(getContext());
                nameTextView.setText(name);
                Button showMoreButton = new Button(getContext());
                showMoreButton.setText("Show More");

                LinearLayout dataRow = new LinearLayout(getContext());
                dataRow.setOrientation(LinearLayout.HORIZONTAL);

                dataRow.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, // Genişlik
                        LinearLayout.LayoutParams.WRAP_CONTENT  // Yükseklik
                ));

                dataRow.addView(showMoreButton);

                mainContainer.addView(dataRow);

                showMoreButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        TextView genreTextView = new TextView(getContext());
                        TextView yearTextView = new TextView(getContext());
                        TextView plotTextView = new TextView(getContext());

                        LinearLayout moreContainer = new LinearLayout(getContext());
                        moreContainer.setOrientation(LinearLayout.VERTICAL);




                        genreTextView.setText(genre);
                        yearTextView.setText(year);
                        plotTextView.setText(plot);

                        moreContainer.addView(genreTextView);
                        moreContainer.addView(yearTextView);
                        moreContainer.addView(plotTextView);

                        mainContainer.addView(moreContainer);
                    }

                });

                // TextView ve Button'ı container'a ekleyin



            } while (cursor.moveToNext());

            cursor.close();
        }
    }

}
