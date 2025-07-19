package com.example.quotter;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class ExploreActivity extends AppCompatActivity {
    private BottomNavigationView bottomnavegation;
    private EditText searchEditText;
    private Button searchButton,Btnclose,Btnback,BtnBack2,Btnback3,BtnBack4;
    private CardView cardAI1,cardCategories1,CardSuprise,cardCategories2;
    private TextView exploreTitle;
    private ScrollView CategoriesView,QuotesAisView,TrendingView,SupriseView;
    private CardView LoveQuotesId;
    private ScrollView LoveQuotesView;



    private ListView loveQuotesListView;
    private Button btnBackLove;
    private TextView loveQuotesTitle;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_explore);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bottomnavegation = findViewById(R.id.bottomnavegation);
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        cardAI1 = findViewById(R.id.cardAI1);
        cardCategories1 = findViewById(R.id.cardCategories1);
        cardCategories2 = findViewById(R.id.cardCategories2);
        CardSuprise = findViewById(R.id.CardSuprise);
        exploreTitle = findViewById(R.id.exploreTitle);
        Btnclose= findViewById(R.id.Btnclose);
        CategoriesView=findViewById(R.id.CategoriesView);
        //QuotesAisView = findViewById(R.id.QuotesAisView);
        Btnback = findViewById(R.id.Btnback);

//        BtnBack2= findViewById(R.id.BtnBack2);
//        ////SupriseView= findViewById(R.id.SupriseView);
//       // TrendingView= findViewById(R.id.TrendingView);
//        BtnBack4= findViewById(R.id.BtnBack4);
//        Btnback3= findViewById(R.id.Btnback3);




        //araayList
        ArrayList<String> loveQuotes = new ArrayList<>(Arrays.asList(
                "I love you not because of who you are, but because of who I am when I am with you.",
                "Love is not about how many days, months, or years you've been together. It's about how much you love each other every single day.",
                "I saw that you were perfect, and so I loved you. Then I saw that you were not perfect and I loved you even more.",
                "You don’t love someone for their looks, or their clothes, or for their fancy car, but because they sing a song only you can hear.",
                "Love is composed of a single soul inhabiting two bodies.",
                "In all the world, there is no heart for me like yours.",
                "Whatever our souls are made of, his and mine are the same.",
                "You are my today and all of my tomorrows.",
                "To love and be loved is to feel the sun from both sides.",
                "I would rather spend one lifetime with you than face all the ages of this world alone.",
                "I have waited for this opportunity for more than half a century, to repeat to you once again my vow of eternal fidelity and everlasting love.",
                "If I know what love is, it is because of you.",
                "You are my sun, my moon, and all of my stars.",
                "I love you more than I have ever found a way to say to you.",
                "You had me at hello.",
                "I am catastrophically in love with you.",
                "My love for you is a journey; starting at forever and ending at never.",
                "You are the finest, loveliest, tenderest, and most beautiful person I have ever known—and even that is an understatement.",
                "You make me want to be a better man.",
                "I swear I couldn’t love you more than I do right now, and yet I know I will tomorrow.",
                "Your love shines in my heart as the sun that shines upon the earth.",
                "I love being yours and knowing that you're mine.",
                "Love is the closest thing we have to magic.",
                "You're the one that I want. Always.",
                "Love recognizes no barriers. It jumps hurdles, leaps fences, penetrates walls to arrive at its destination full of hope.",
                "We loved with a love that was more than love.",
                "I love you not only for what you are, but for what I am when I am with you.",
                "Every love story is beautiful, but ours is my favorite.",
                "I look at you and see the rest of my life in front of my eyes.",
                "Forever is a long time, but I wouldn't mind spending it by your side.",
                "You're my first thought in the morning, and my last thought before I fall asleep.",
                "You are my heart, my life, my one and only thought.",
                "True love is rare, and it's the only thing that gives life real meaning.",
                "You complete me.",
                "I found you, and I found my home.",
                "Loving you was the best decision of my life.",
                "You're the reason I believe in love.",
                "Every time I see you, I fall in love all over again.",
                "I didn't choose you, my heart did.",
                "You make my heart smile.",
                "You're my favorite notification.",
                "I love you endlessly and forever.",
                "You make me feel whole.",
                "You’re the love that came without warning; you had my heart before I could say no.",
                "Being in your arms is my favorite place in the world.",
                "You are my greatest adventure.",
                "You’re the missing piece to my puzzle.",
                "I love you to the moon and back.",
                "Your voice is my favorite sound.",
                "In your smile I see something more beautiful than the stars.",
                "I wish I could turn back the clock. I’d find you sooner and love you longer.",
                "You are my today and all of my tomorrows.",
                "You are the best thing I never planned.",
                "Love is not finding someone to live with; it’s finding someone you can’t live without.",
                "Your love is all I need to feel complete.",
                "You're not my number one. You're my only one.",
                "I never want to stop making memories with you.",
                "With you, I am home.",
                "You are the poem I never knew how to write, and this life is the story I always wanted to tell.",
                "You're the beat in my heart, the light in my soul.",
                "Just when I think that it is impossible to love you any more, you prove me wrong.",
                "You're the reason I laugh a little harder, cry a little less, and smile a lot more.",
                "Your hand fits in mine like it’s made just for me.",
                "Love isn’t something you find. Love is something that finds you.",
                "I didn’t plan on falling in love with you, but I’m glad I did.",
                "You stole my heart, but I’ll let you keep it.",
                "I love you not because of anything you have, but because of something that I feel when I’m near you.",
                "No matter what has happened. No matter what you’ve done. No matter what you will do. I will always love you.",
                "The best feeling is when you look at him and he is already staring.",
                "The best part of me is you.",
                "You're my forever and always.",
                "I fell in love with you because of the million things you never knew you were doing.",
                "I love you more than yesterday and less than tomorrow.",
                "You're the peace I crave in this chaotic world.",
                "You are my happy place.",
                "You had me from the moment you said hello.",
                "I’d choose you in every lifetime.",
                "You are the one I’ve been waiting for.",
                "You and I, it’s like a dream come true.",
                "You’re the answer to every love song I’ve ever heard.",
                "You’re my person.",
                "You make me believe in soulmates.",
                "You're everything I never knew I needed.",
                "You make ordinary moments extraordinary.",
                "When I’m with you, time stands still.",
                "My love for you is infinite.",
                "I still get butterflies even though I’ve seen you a hundred times.",
                "I loved you yesterday, I love you still, I always have, I always will.",
                "You’re my favorite hello and hardest goodbye.",
                "When I’m with you, the world feels right.",
                "You’re the smile I never knew I needed.",
                "You’re the calm in my storm.",
                "I didn’t believe in love at first sight until I met you.",
                "No words are enough to describe how much I love you.",
                "You make me feel things I’ve never felt before.",
                "You’re the reason I wake up with a smile.",
                "You’re the light of my life.",
                "You give my life meaning.",
                "You’re the dream I never want to wake up from.",
                "You are the love of my life.",
                "All that you are is all that I’ll ever need.",
                "With every beat of my heart, I love you more and more.",
                "You are the reason behind my smile.",
                "My heart is and always will be yours."
        ));

        ArrayList<String> motivationQuotes = new ArrayList<>(Arrays.asList(
                "Don’t watch the clock; do what it does. Keep going.",
                "Success is not for the lazy.",
                "Push yourself, because no one else is going to do it for you.",
                "The pain you feel today will be the strength you feel tomorrow.",
                "Great things never come from comfort zones.",
                "Dream it. Believe it. Build it.",
                "Success doesn’t just find you. You have to go out and get it.",
                "Sometimes we’re tested not to show our weaknesses, but to discover our strengths.",
                "Don’t stop when you’re tired. Stop when you’re done.",
                "Do something today that your future self will thank you for.",
                "Hard work beats talent when talent doesn’t work hard.",
                "The way to get started is to quit talking and begin doing.",
                "Don’t limit your challenges. Challenge your limits.",
                "Wake up with determination. Go to bed with satisfaction.",
                "Your limitation—it’s only your imagination.",
                "Push harder than yesterday if you want a different tomorrow.",
                "Little things make big days.",
                "It’s going to be hard, but hard does not mean impossible.",
                "Don’t wait for opportunity. Create it.",
                "Sometimes later becomes never. Do it now.",
                "Stay positive. Work hard. Make it happen.",
                "Believe you can and you’re halfway there.",
                "Success is what comes after you stop making excuses.",
                "Failure is not the opposite of success; it’s part of success.",
                "The harder you work for something, the greater you’ll feel when you achieve it.",
                "Don’t count the days, make the days count.",
                "The secret of getting ahead is getting started.",
                "Winners are not afraid of losing. But losers are.",
                "Act as if what you do makes a difference. It does.",
                "Energy and persistence conquer all things.",
                "If you can dream it, you can do it.",
                "Doubt kills more dreams than failure ever will.",
                "Success is not in what you have, but who you are.",
                "Make each day your masterpiece.",
                "You are stronger than you think.",
                "Start where you are. Use what you have. Do what you can.",
                "Do what you can with all you have, wherever you are.",
                "The best view comes after the hardest climb.",
                "Don’t be afraid to give up the good to go for the great.",
                "Discipline is the bridge between goals and accomplishment.",
                "Success usually comes to those who are too busy to be looking for it.",
                "Keep going. Everything you need will come to you at the perfect time.",
                "Believe in yourself and all that you are.",
                "You didn’t come this far to only come this far.",
                "Success is the sum of small efforts, repeated day in and day out.",
                "Only those who dare to fail greatly can ever achieve greatly.",
                "The key to success is to start before you’re ready.",
                "A little progress each day adds up to big results.",
                "Don’t be pushed around by the fears in your mind.",
                "Take the risk or lose the chance.",
                "When you feel like quitting, think about why you started.",
                "Success doesn’t come from what you do occasionally. It comes from what you do consistently.",
                "Work hard in silence, let success make the noise.",
                "Don’t wish it were easier. Wish you were better.",
                "If it doesn’t challenge you, it won’t change you.",
                "You miss 100% of the shots you don’t take.",
                "Strive for progress, not perfection.",
                "Don’t fear failure. Fear being in the exact same place next year as you are today.",
                "You are capable of amazing things.",
                "You don’t find will power, you create it.",
                "Be stronger than your excuses.",
                "Don’t downgrade your dream just to fit your reality.",
                "Discomfort is the price of admission to a meaningful life.",
                "Success is what happens after you have survived all your mistakes.",
                "You don’t have to be great to start, but you have to start to be great.",
                "Winners focus on winning. Losers focus on winners.",
                "Difficult roads often lead to beautiful destinations.",
                "Be a warrior, not a worrier.",
                "You get what you work for, not what you wish for.",
                "Be the change you want to see in the world.",
                "The difference between ordinary and extraordinary is that little extra.",
                "It always seems impossible until it's done.",
                "There is no substitute for hard work.",
                "Learn from yesterday. Live for today. Hope for tomorrow.",
                "Stay focused and never give up.",
                "If you’re going through hell, keep going.",
                "There are no limits to what you can accomplish.",
                "Stop doubting yourself. Work hard and make it happen.",
                "Believe in the process.",
                "Let your hustle be louder than your mouth.",
                "Chase your dreams with passion and purpose.",
                "Success begins at the end of your comfort zone.",
                "Don’t be afraid to start over. It’s a new chance to rebuild what you want.",
                "Work until your idols become your rivals.",
                "No pressure, no diamonds.",
                "The best revenge is massive success.",
                "Be fearless in the pursuit of what sets your soul on fire.",
                "Make your life a masterpiece.",
                "Mindset is everything.",
                "You were not born to be average.",
                "The only limit is the one you set yourself.",
                "Success is a journey, not a destination.",
                "Dream big. Work hard. Stay focused.",
                "The grind never stops.",
                "Your only limit is your mind.",
                "Make it happen. Shock everyone.",
                "Turn your wounds into wisdom.",
                "Success starts with self-discipline.",
                "You are your only competition.",
                "Fail, learn, grow, repeat.",
                "You got this!"
        ));
        // Find LoveQuotes UI elements
        loveQuotesListView = findViewById(R.id.loveQuotesListView);
        btnBackLove = findViewById(R.id.BtnBackLove);
        loveQuotesTitle = findViewById(R.id.loveQuotesTitle);


// Setup the ArrayAdapter for ListView using your loveQuotes ArrayList
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                loveQuotes
        );
        loveQuotesListView.setAdapter(adapter);

// Back button inside LoveQuotesView hides the LoveQuotesView and shows CategoriesView again
        btnBackLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoveQuotesView.setVisibility(View.GONE);
                CategoriesView.setVisibility(View.VISIBLE);
            }
        });

// Show LoveQuotesView and hide CategoriesView on LoveQuotesId card click
        LoveQuotesId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoveQuotesView.setVisibility(View.VISIBLE);
                CategoriesView.setVisibility(View.GONE);
            }
        });
// 1. Find views
        ScrollView LoveQuotesView = findViewById(R.id.LoveQuotesView);
        TextView loveQuotesTitle = findViewById(R.id.loveQuotesTitle);
        ListView loveQuotesListView = findViewById(R.id.loveQuotesListView);
        Button btnBackLove = findViewById(R.id.BtnBackLove);
        CardView LoveQuotesId = findViewById(R.id.LoveQuotesId); // Your card



// 4. Show Love Quotes when card is clicked
        LoveQuotesId.setOnClickListener(v -> {
            CategoriesView.setVisibility(View.GONE);
            LoveQuotesView.setVisibility(View.VISIBLE);
        });

// 5. Handle back button
        btnBackLove.setOnClickListener(v -> {
            LoveQuotesView.setVisibility(View.GONE);
            CategoriesView.setVisibility(View.VISIBLE);
        });


        bottomnavegation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startActivity(new Intent(ExploreActivity.this, MainActivity.class));
                return true;

            } else if (itemId == R.id.saved) {
                startActivity(new Intent(ExploreActivity.this, MainActivity2.class));
                return true;

            }


            return false;
        });


        /// on btnsearch click show the Input search
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setVisibility(VISIBLE);
                searchButton.setVisibility(INVISIBLE);
                exploreTitle.setVisibility(INVISIBLE);
                Btnclose.setVisibility(VISIBLE);

            }
        });
        Btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setVisibility(INVISIBLE);
                exploreTitle.setVisibility(VISIBLE);
                searchButton.setVisibility(VISIBLE);
                Btnclose.setVisibility(INVISIBLE);

            }
        });
//        BtnBack2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                cardAI1.setVisibility(VISIBLE);
//                cardCategories1.setVisibility(VISIBLE);
//                cardCategories2.setVisibility(VISIBLE);
//                CardSuprise.setVisibility(VISIBLE);
//                exploreTitle.setVisibility(VISIBLE);
//                searchButton.setVisibility(VISIBLE);
//                QuotesAisView.setVisibility(INVISIBLE);
//
//            }
//        });
        Btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardAI1.setVisibility(VISIBLE);
                cardCategories1.setVisibility(VISIBLE);
                cardCategories2.setVisibility(VISIBLE);
                CardSuprise.setVisibility(VISIBLE);
                exploreTitle.setVisibility(VISIBLE);
                searchButton.setVisibility(VISIBLE);
                CategoriesView.setVisibility(INVISIBLE);


            }
        });
//        Btnback3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cardAI1.setVisibility(VISIBLE);
//                cardCategories1.setVisibility(VISIBLE);
//                cardCategories2.setVisibility(VISIBLE);
//                CardSuprise.setVisibility(VISIBLE);
//                exploreTitle.setVisibility(VISIBLE);
//                searchButton.setVisibility(VISIBLE);
//                SupriseView.setVisibility(INVISIBLE);
//
//
//            }
//        });
//        BtnBack4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cardAI1.setVisibility(VISIBLE);
//                cardCategories1.setVisibility(VISIBLE);
//                cardCategories2.setVisibility(VISIBLE);
//                CardSuprise.setVisibility(VISIBLE);
//                exploreTitle.setVisibility(VISIBLE);
//                searchButton.setVisibility(VISIBLE);
//               TrendingView.setVisibility(INVISIBLE);
//
//
//            }
//        });

        //on cardview click triger a LinearLAyout


        cardCategories1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoriesView.setVisibility(VISIBLE);
                cardAI1.setVisibility(INVISIBLE);
                cardCategories1.setVisibility(INVISIBLE);
                cardCategories2.setVisibility(INVISIBLE);
                CardSuprise.setVisibility(INVISIBLE);
                exploreTitle.setVisibility(INVISIBLE);
                searchButton.setVisibility(INVISIBLE);



            }
        });


        LoveQuotesId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoveQuotesView.setVisibility(VISIBLE);
                CategoriesView.setVisibility(INVISIBLE);

            }
        });


    }
    private void showLoveQuotes() {
        loveQuotesListView.setVisibility(View.VISIBLE);
        btnBackLove.setVisibility(View.VISIBLE);
        loveQuotesTitle.setVisibility(View.VISIBLE);

        // Hide other UI elements here if you want (e.g., cards, buttons)
    }

    private void hideLoveQuotes() {
        loveQuotesListView.setVisibility(View.GONE);
        btnBackLove.setVisibility(View.GONE);
        loveQuotesTitle.setVisibility(View.GONE);

        // Show other UI elements here if you want
    }
}

