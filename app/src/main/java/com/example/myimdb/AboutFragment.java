package com.example.myimdb;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AboutFragment extends Fragment {

    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        textView = view.findViewById(R.id.webScrabText); // Ensure you have a TextView with this ID in your layout
        scrapeWebPage("https://en.wikipedia.org/wiki/Arnold_Schwarzenegger");

        return view;
    }

    private void scrapeWebPage(final String urlString) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    boolean firstParagraphFound = false;
                    final StringBuilder firstParagraph = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        if (line.contains("<p>") && !firstParagraphFound) {
                            firstParagraphFound = true;
                        }
                        if (firstParagraphFound) {
                            firstParagraph.append(line).append("\n");
                            if (line.contains("</p>")) {
                                break;
                            }
                        }
                    }
                    reader.close();

                    // Extract text from HTML
                    final String plainText = android.text.Html.fromHtml(firstParagraph.toString()).toString();

                    // Update UI on the main thread
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(plainText); // Set text to TextView
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
