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
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DeleteFragment extends Fragment {

    private SQLiteHelper sqh;
    private ListView listView;
    private FilmCursorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete, container, false);

        listView = (ListView) view.findViewById(R.id.listView);
        sqh = new SQLiteHelper(getActivity());

        Cursor cursor = sqh.getData();
        adapter = new FilmCursorAdapter(getActivity(), cursor);
        listView.setAdapter(adapter);

        return view;
    }

    private class FilmCursorAdapter extends CursorAdapter {

        public FilmCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.delete_film, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView filmNameTextView = (TextView) view.findViewById(R.id.filmName);
            Button deleteButton = (Button) view.findViewById(R.id.deleteButton);

            final String filmName = cursor.getString(cursor.getColumnIndexOrThrow("filmName"));
            final String filmId = cursor.getString(cursor.getColumnIndexOrThrow("_id"));

            filmNameTextView.setText(filmName);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("You are about to delete the film:");

                    String message = "Name: " + filmName + " FilmID: " + filmId;

                    builder.setMessage(message);

                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            sqh.deleteData(Integer.parseInt(filmId));



                            adapter.swapCursor(sqh.getData());


                            showDeleteSuccessDialog();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }

        private void showDeleteSuccessDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Deleted");
            builder.setMessage("The film has been deleted successfully.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
