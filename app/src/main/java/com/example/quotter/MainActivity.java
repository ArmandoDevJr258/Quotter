package com.example.quotter;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView quoteText, authorText;
    private Button btnsave, btnfetch, btnlike, btnsearch, btnshare, btnclose;
    private BottomNavigationView bn;
    private ImageView backgroundImage;
    private EditText input;
    OkHttpClient client = new OkHttpClient();
    MediaPlayer mediaPlayer;
    private DatabaseHelper  dbHelper;

    static class Quote {
        String text;
        String author;

        Quote(String text, String author) {
            this.text = text;
            this.author = (author == null || author.equals("null")) ? "Unknown" : author;
        }
    }

    List<Quote> quotesList = new ArrayList<>();
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);

        // Initialize UI elements
        quoteText = findViewById(R.id.quoteText);
        authorText = findViewById(R.id.authorText);
        btnfetch = findViewById(R.id.btnfetch);
        btnsave = findViewById(R.id.btnsave);
        btnlike = findViewById(R.id.btnlike);
        btnsearch = findViewById(R.id.btnsearch);
        btnshare = findViewById(R.id.btnshare);
        input = findViewById(R.id.input);
        btnclose = findViewById(R.id.btnclose);
        backgroundImage = findViewById(R.id.backgroundImage);
        bn = findViewById(R.id.bottomnavegation);

        // Load random background image into ImageView
        loadRandomBackground();

        // Share quote button
        btnshare.setOnClickListener(v -> {
            String quote = quoteText.getText().toString();
            String author = authorText.getText().toString();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, quote + "\n" + author);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });

        // Toggle search input visibility
        btnsearch.setOnClickListener(v -> {
            input.setVisibility(VISIBLE);
            btnsearch.setVisibility(INVISIBLE);
            btnclose.setVisibility(VISIBLE);
        });

        btnclose.setOnClickListener(v -> {
            input.setVisibility(INVISIBLE);
            btnsearch.setVisibility(VISIBLE);
            btnclose.setVisibility(INVISIBLE);
        });

        // Like & Save button effects
        btnlike.setOnClickListener(v -> btnlike.setBackgroundResource(R.drawable.like_svgrepo_com__5_));
        btnsave.setOnClickListener(v -> btnsave.setBackgroundResource(R.drawable.bookmark_filled_svgrepo_com));

        // Bottom navigation item selection
        bn.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                return true;
            } else if (item.getItemId() == R.id.saved) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
                return true;
            }
            return false;
        });


        btnsave.setOnClickListener(v -> {
            String quote = quoteText.getText().toString().trim();
            if (!quote.isEmpty()) {
                dbHelper.saveQuote(quote);
                Toast.makeText(MainActivity.this, "Quote saved!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "No quote to save", Toast.LENGTH_SHORT).show();
            }
        });


        // Fetch new random quote on button click
        btnfetch.setOnClickListener(v -> {
            btnlike.setBackgroundResource(R.drawable.like_svgrepo_com__3_);
            btnsave.setBackgroundResource(R.drawable.saved_svgrepo_com);
            showRandomQuote();
            loadRandomBackground();
        });

        setupChillhopStream();
        loadMultipleZenQuotes();
    }

    private void loadRandomBackground() {
        int width = 1080;
        int height = 1920;
        String imageUrl = "https://picsum.photos/" + width + "/" + height + "?random=" + random.nextInt(1000);

        Picasso.get()
                .load(imageUrl)
                .into(backgroundImage);
    }

    private void loadMultipleZenQuotes() {
        Request request = new Request.Builder()
                .url("https://zenquotes.io/api/quotes")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> quoteText.setText("Failed: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    try {
                        JSONArray array = new JSONArray(json);
                        quotesList.clear();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            quotesList.add(new Quote(obj.getString("q"), obj.getString("a")));
                        }
                        runOnUiThread(MainActivity.this::showRandomQuote);
                    } catch (Exception e) {
                        runOnUiThread(() -> quoteText.setText("Error parsing quotes"));
                    }
                } else {
                    runOnUiThread(() -> quoteText.setText("Error: " + response.code()));
                }
            }
        });
    }

    private void showRandomQuote() {
        if (quotesList.isEmpty()) {
            quoteText.setText("No quotes loaded.");
            authorText.setText("");
            return;
        }
        int index = random.nextInt(quotesList.size());
        Quote q = quotesList.get(index);
        quoteText.setText("\"" + q.text + "\"");
        authorText.setText("- " + q.author);
    }

    private void setupChillhopStream() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build());
            mediaPlayer.setDataSource("https://ice1.somafm.com/groovesalad-128-mp3");
            mediaPlayer.setOnPreparedListener(mp -> {
                mediaPlayer.start();
                Toast.makeText(MainActivity.this, "Playing ambient lo-fi", Toast.LENGTH_SHORT).show();
            });
            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                Toast.makeText(MainActivity.this, "Error playing stream", Toast.LENGTH_SHORT).show();
                return true;
            });
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load stream", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
