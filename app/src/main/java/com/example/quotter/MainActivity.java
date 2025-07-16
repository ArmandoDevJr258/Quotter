package com.example.quotter;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

    TextView quoteText, authorText,settings,aboutus;
    private Button btnSave, btnFetch, btnLike, btnSearch, btnShare, btnClose,btnmenu,btnhome;
    private BottomNavigationView bn;
    private ImageView backgroundImage;
    private EditText input;
    private ListView listViewQuotes;
    private DatabaseHelper dbHelper;
    private DrawerLayout drawerLayout;

    // Class to hold both the text and author of a quote
    static class Quote {
        String text;
        String author;

        Quote(String text, String author) {
            this.text = text;
            // Ensure author is never null or the string "null"
            this.author = (author == null || author.equals("null")) ? "Unknown" : author;
        }

        // Override toString for easy display in the ArrayAdapter
        @Override
        public String toString() {
            return "\"" + text + "\" - " + author;
        }
    }

    // Master list of all quotes fetched from the API
    private List<Quote> quotesList = new ArrayList<>();
    // List to hold quotes that match the current search query
    private List<Quote> filteredQuotesList = new ArrayList<>();
    // Adapter for the search results ListView
    private ArrayAdapter<Quote> arrayAdapter;

    private Random random = new Random();
    private OkHttpClient client = new OkHttpClient();
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);

        // Initialize UI elements
        initializeViews();

        // Setup the adapter for the search results list view
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredQuotesList);
        listViewQuotes.setAdapter(arrayAdapter);

        // Load initial data
        loadRandomBackground();
        loadMultipleZenQuotes();
        setupChillhopStream();

        // Setup button listeners
        setupListeners();
    }

    private void initializeViews() {
        quoteText = findViewById(R.id.quoteText);
        authorText = findViewById(R.id.authorText);
        btnFetch = findViewById(R.id.btnfetch);
        btnSave = findViewById(R.id.btnsave);
        btnLike = findViewById(R.id.btnlike);
        btnSearch = findViewById(R.id.btnsearch);
        btnShare = findViewById(R.id.btnshare);
        input = findViewById(R.id.input);
        btnClose = findViewById(R.id.btnclose);
        backgroundImage = findViewById(R.id.backgroundImage);
        bn = findViewById(R.id.bottomnavegation);
        listViewQuotes = findViewById(R.id.listViewQuotes);
        btnmenu = findViewById(R.id.btnmenu);
        btnhome= findViewById(R.id.btnhome);
        settings= findViewById(R.id.settings);
        aboutus = findViewById(R.id.aboutus);
        drawerLayout = findViewById(R.id.drawerLayout);
    }

    private void setupListeners() {
        // --- SEARCH FUNCTIONALITY ---

        // Show search input and list view
        btnSearch.setOnClickListener(v -> {
            input.setVisibility(VISIBLE);
            btnClose.setVisibility(VISIBLE);
            listViewQuotes.setVisibility(VISIBLE);
            btnSearch.setVisibility(INVISIBLE);
            input.requestFocus();
            showKeyboard();
        });


        float fontsize=getIntent().getFloatExtra("fontsize",16f);
        quoteText.setTextSize(fontsize);
        // Hide search input and list view
        btnClose.setOnClickListener(v -> {
            input.setVisibility(INVISIBLE);
            btnClose.setVisibility(INVISIBLE);
            listViewQuotes.setVisibility(GONE);
            btnSearch.setVisibility(VISIBLE);
            input.setText(""); // Clear search text
            hideKeyboard();
        });
        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.setVisibility(VISIBLE);

            }
        });

        // Filter quotes in real-time as the user types
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterQuotes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Handle clicks on search results
        listViewQuotes.setOnItemClickListener((parent, view, position, id) -> {
            if (!filteredQuotesList.isEmpty()) {
                Quote selectedQuote = filteredQuotesList.get(position);
                displayQuote(selectedQuote);
                // Simulate a click on the close button to hide the search UI
                btnClose.performClick();
            }
        });

        // --- OTHER BUTTONS ---

        btnFetch.setOnClickListener(v -> {
            btnLike.setBackgroundResource(R.drawable.like_svgrepo_com__3_);
            showRandomQuote();
            loadRandomBackground();
        });

        btnShare.setOnClickListener(v -> {
            String quote = quoteText.getText().toString();
            String author = authorText.getText().toString();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, quote + "\n" + author);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });

        btnSave.setOnClickListener(v -> {
            String quote = quoteText.getText().toString().trim();
            if (!quote.isEmpty() && !quote.equals("\"\"")) {
                dbHelper.saveQuote(quote);
                Toast.makeText(MainActivity.this, "Quote saved!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "No quote to save.", Toast.LENGTH_SHORT).show();
            }
        });

        btnLike.setOnClickListener(v -> {
            btnLike.setBackgroundResource(R.drawable.like_svgrepo_com__5_);
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.setVisibility(INVISIBLE);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        bn.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                // Already on the home screen, do nothing to prevent reloading
                return true;
            } else if (itemId == R.id.saved) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
                return true;
            }
            return false;
        });
    }

    /**
     * Filters the master quotes list based on a search query and updates the ListView.
     * @param query The text to search for in quote text and author names.
     */
    private void filterQuotes(String query) {
        filteredQuotesList.clear();
        if (query.isEmpty()) {
            arrayAdapter.notifyDataSetChanged();
            return;
        }

        String lowerCaseQuery = query.toLowerCase();
        for (Quote quote : quotesList) {
            if (quote.text.toLowerCase().contains(lowerCaseQuery) ||
                    quote.author.toLowerCase().contains(lowerCaseQuery)) {
                filteredQuotesList.add(quote);
            }
        }
        arrayAdapter.notifyDataSetChanged();
    }

    private void loadRandomBackground() {
        // Using a fixed size for better caching and performance
        String imageUrl = "https://picsum.photos/1080/1920?random=" + random.nextInt(1000);
        Picasso.get().load(imageUrl).into(backgroundImage);
    }

    private void loadMultipleZenQuotes() {
        Request request = new Request.Builder()
                .url("https://zenquotes.io/api/quotes")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Failed to load quotes", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    final String json = response.body().string();
                    try {
                        JSONArray array = new JSONArray(json);
                        quotesList.clear();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            quotesList.add(new Quote(obj.getString("q"), obj.getString("a")));
                        }
                        runOnUiThread(MainActivity.this::showRandomQuote);
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error parsing quotes", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "API Error: " + response.code(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    /**
     * Displays a specific quote object in the main UI.
     * @param quote The Quote object to display.
     */
    private void displayQuote(Quote quote) {
        if (quote != null) {
            quoteText.setText("\"" + quote.text + "\"");
            authorText.setText("- " + quote.author);
        }
    }


    private void showRandomQuote() {
        if (quotesList.isEmpty()) {
            quoteText.setText("No quotes loaded.");
            authorText.setText("");
            return;
        }
        int index = random.nextInt(quotesList.size());
        Quote q = quotesList.get(index);
        displayQuote(q);
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
                Toast.makeText(MainActivity.this, "Playing ambient lo-fi.", Toast.LENGTH_SHORT).show();
            });
            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                Toast.makeText(MainActivity.this, "Error playing stream.", Toast.LENGTH_SHORT).show();
                return true;
            });
            mediaPlayer.prepareAsync();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load stream.", Toast.LENGTH_SHORT).show();
        }
    }

    // --- UTILITY METHODS ---

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}