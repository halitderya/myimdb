package com.example.myimdb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DeleteAll {

    public interface DeletionListener {
        void onDeletionComplete();
    }

    private DeletionListener listener;

    public DeleteAll(DeletionListener listener) {
        this.listener = listener;
    }

    public void deleteAllRecords(final Context context) {
        final SQLiteHelper sqh = new SQLiteHelper(context);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete All Records");
        builder.setMessage("Are you sure you want to delete all records?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sqh.deleteAllData();
                showDeleteSuccessDialog(context);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showDeleteSuccessDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Deletion Successful");
        builder.setMessage("All records have been deleted successfully.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onDeletionComplete();
                }
            }
        });
        builder.show();
    }
}
