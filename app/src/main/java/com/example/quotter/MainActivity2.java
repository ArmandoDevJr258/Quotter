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
                dbHelper.deleteQuote(selectedQuote);
                quotes.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity2.this, "Quote deleted", Toast.LENGTH_SHORT).show();
            }
        });

        bn.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    startActivity(new Intent(MainActivity2.this, MainActivity.class));
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
