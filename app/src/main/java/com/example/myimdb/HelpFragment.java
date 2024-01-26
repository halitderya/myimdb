package com.example.myimdb;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HelpFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        // Set up click listeners for the features
        setUpClickListener(view, R.id.feature_home);
        setUpClickListener(view, R.id.feature_add);
        setUpClickListener(view, R.id.feature_edit);
        setUpClickListener(view, R.id.feature_delete);
        setUpClickListener(view, R.id.feature_deleteall);
        setUpClickListener(view, R.id.feature_email);

        return view;
    }

    private void setUpClickListener(View view, int resourceId) {
        View featureView = view.findViewById(resourceId);
        featureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFeatureSelected(v.getId());
            }
        });
    }

    public void onFeatureSelected(int id) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (id == R.id.feature_home) {
            fragmentTransaction.replace(R.id.fragment_container, new MainFragment());
        } else if (id == R.id.feature_add) {
            fragmentTransaction.replace(R.id.fragment_container, new AddRecordFragment());
        } else if (id == R.id.feature_edit) {
            fragmentTransaction.replace(R.id.fragment_container, new EditFragment());
        } else if (id == R.id.feature_delete) {
            fragmentTransaction.replace(R.id.fragment_container, new DeleteFragment());
        } else if (id == R.id.feature_deleteall) {
            // Assuming MainActivity implements DeletionListener
            if (getActivity() instanceof MainActivity) {
                new DeleteAll((MainActivity) getActivity()).deleteAllRecords((MainActivity) getActivity());
            }
        } else if (id == R.id.feature_email) {
            sendEmailToDeveloper();
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void sendEmailToDeveloper() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822"); // Set MIME type to email
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "s22012416@mail.glyndwr.ac.uk" });
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Help - Arnold MDB");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello Halit,\n\nI need help about the app.\n\n");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
