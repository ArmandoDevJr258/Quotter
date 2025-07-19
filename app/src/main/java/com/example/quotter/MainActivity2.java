package com.example.quotter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private BottomNavigationView bn;
    private ListView listView;
    private ArrayList<String> quotes;
    private ArrayAdapter<String> adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = findViewById(R.id.listview);
        bn = findViewById(R.id.bottomnavegation);

        dbHelper = new DatabaseHelper(this);
        loadQuotes();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              String selectedQuote = quotes.get(position);
               //dbHelper.deleteQuote(selectedQuote);

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, selectedQuote + "\n");
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

        bn.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    startActivity(new Intent(MainActivity2.this, MainActivity.class));
                    return true;
                }
                else if(item.getItemId() == R.id.explore){
                    startActivity(new Intent(MainActivity2.this, ExploreActivity.class));
                    return true;
                }

                return false;
            }
        });
    }

    private void loadQuotes() {
        quotes = dbHelper.getAllQuotes();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, quotes);
        listView.setAdapter(adapter);
    }
}
