package com.example.myimdb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    private SQLiteHelper sqh;
    private TextView textViewempty;
    private ListView listView;
    private FilmCursorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        listView = view.findViewById(R.id.listView);
        sqh = new SQLiteHelper(getActivity());


        Cursor cursor = sqh.getData();
        adapter = new FilmCursorAdapter(getActivity(), cursor);
        listView.setAdapter(adapter);
        textViewempty = view.findViewById(R.id.emptytexview);

        if (textViewempty != null) {
            if (cursor.getCount() == 0) {
                // Cursor is empty, set the emptyTextView's visibility to VISIBLE
                textViewempty.setVisibility(View.VISIBLE);
            } else {
                // Cursor has data, set the emptyTextView's visibility to GONE
                textViewempty.setVisibility(View.GONE);
            }
        }
        setupSearchView(view);

        return view;
    }

    private void setupSearchView(View view) {
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {

                Cursor newCursor = sqh.searchData(newText);
                adapter.changeCursor(newCursor);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }


    private class FilmCursorAdapter extends CursorAdapter {
        public FilmCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.item_film, parent, false);
        }


        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            final String filmName = cursor.getString(cursor.getColumnIndexOrThrow("filmName"));
            final String filmDirector = cursor.getString(cursor.getColumnIndexOrThrow("filmDirector"));
            final String filmYear = cursor.getString(cursor.getColumnIndexOrThrow("filmYear"));
            final String filmGenre = cursor.getString(cursor.getColumnIndexOrThrow("filmGenre"));
            final String filmPlot = cursor.getString(cursor.getColumnIndexOrThrow("filmPlot"));

            TextView showName = view.findViewById(R.id.filmName);
            Button showDetailsButton = view.findViewById(R.id.showDetailsButton);
            showName.setText(filmName);



            showDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFilmDetails(filmName, filmDirector, filmYear, filmGenre, filmPlot);
                }
            });
        }



    }

    private void showFilmDetails(String filmName, String filmDirector, String filmYear,String filmGenre,String filmPlot) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Film Details");

        String message ="\nName: " + filmName + "\n\nDirector: " + filmDirector + "\n\nYear: " + filmYear +"\n\nGenre:" +filmGenre+ "\n\nPlot:" +filmPlot;
        builder.setMessage(message);

        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
