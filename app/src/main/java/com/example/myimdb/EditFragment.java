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
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class EditFragment extends Fragment {

    private SQLiteHelper sqh;
    private ListView listView;
    private FilmCursorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        listView = view.findViewById(R.id.listView);
        sqh = new SQLiteHelper(getActivity());

        Cursor cursor = sqh.getData();
        adapter = new FilmCursorAdapter(getActivity(), cursor);
        listView.setAdapter(adapter);

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
            return LayoutInflater.from(context).inflate(R.layout.item_film_edit, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            final int filmID = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            final String filmName = cursor.getString(cursor.getColumnIndexOrThrow("filmName"));
            final String filmDirector = cursor.getString(cursor.getColumnIndexOrThrow("filmDirector"));
            final String filmYear = cursor.getString(cursor.getColumnIndexOrThrow("filmYear"));
            final String filmPlot = cursor.getString(cursor.getColumnIndexOrThrow("filmPlot"));
            final String filmGenre = cursor.getString(cursor.getColumnIndexOrThrow("filmGenre"));



            TextView showName = view.findViewById(R.id.filmName);
            Button editDetailsButton = view.findViewById(R.id.editDetailsButton);
            showName.setText(filmName);
            editDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editFilmDetails(filmID,filmName, filmDirector, filmYear, filmPlot,filmGenre);
                }
            });
        }
    }

    private void editFilmDetails(int filmID, String filmName, String filmDirector, String filmYear, String filmPlot,String filmGenre) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.edit_film_dialog, null);

        EditText editFilmID= dialogView.findViewById(R.id.editfilmID);
        EditText editFilmName = dialogView.findViewById(R.id.editFilmName);
        EditText editFilmDirector = dialogView.findViewById(R.id.editFilmDirector);
        EditText editFilmYear = dialogView.findViewById(R.id.editFilmYear);
        EditText editFilmPlot = dialogView.findViewById(R.id.editFilmPlot);
        EditText editFilmGenre = dialogView.findViewById(R.id.editFilmGenre);

        editFilmID.setText(String.valueOf(filmID));
        editFilmName.setText(filmName);
        editFilmDirector.setText(filmDirector);
        editFilmYear.setText(filmYear);
        editFilmPlot.setText(filmPlot);
        editFilmGenre.setText(filmGenre);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Film Details")
                .setView(dialogView)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Get the edited details from the dialog view
                        EditText editFilmID = dialogView.findViewById(R.id.editfilmID);
                        EditText editFilmName = dialogView.findViewById(R.id.editFilmName);
                        EditText editFilmDirector = dialogView.findViewById(R.id.editFilmDirector);
                        EditText editFilmYear = dialogView.findViewById(R.id.editFilmYear);
                        EditText editFilmPlot = dialogView.findViewById(R.id.editFilmPlot);
                        EditText editFilmGenre = dialogView.findViewById(R.id.editFilmGenre);

                        String newName = editFilmName.getText().toString();
                        String newDirector = editFilmDirector.getText().toString();
                        String newYear = editFilmYear.getText().toString();
                        String newPlot = editFilmPlot.getText().toString();
                        String newGenre = editFilmGenre.getText().toString();
                        int filmID=  Integer.parseInt(editFilmID.getText().toString());

                        sqh.updateData(getActivity(), filmID, newName, newYear, newGenre, newPlot, newDirector);

                        Cursor newCursor = sqh.getData();
                        adapter.changeCursor(newCursor);
                        adapter.notifyDataSetChanged();

                        // Close the dialog
                        dialog.dismiss();
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
