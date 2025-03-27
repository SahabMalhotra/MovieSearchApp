package com.example.moviesearchapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        TextView titleTextView = findViewById(R.id.detailTitle);
        TextView yearTextView = findViewById(R.id.detailYear);
        TextView ratingTextView = findViewById(R.id.detailRating);
        TextView genreTextView = findViewById(R.id.detailGenre);
        TextView runtimeTextView = findViewById(R.id.detailRuntime);
        TextView descriptionTextView = findViewById(R.id.detailDescription);
        ImageView posterImageView = findViewById(R.id.detailPoster);
        Button backButton = findViewById(R.id.backButton);


        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String year = intent.getStringExtra("year");
            String rating = intent.getStringExtra("rating");
            String description = intent.getStringExtra("description");
            String runtime = intent.getStringExtra("runtime");
            String genre = intent.getStringExtra("genre");
            String posterUrl = intent.getStringExtra("posterUrl");


            titleTextView.setText(title != null ? title : "Title not available");
            yearTextView.setText("Year: " + (year != null ? year : "N/A"));
            ratingTextView.setText("Rating: " + (rating != null ? rating : "N/A"));
            genreTextView.setText("Genre: " + genre);
            runtimeTextView.setText("Runtime: " + runtime);
            descriptionTextView.setText(description != null ? description : "Description not available");


            if (posterUrl != null && !posterUrl.isEmpty()) {
                Picasso.get().load(posterUrl).into(posterImageView);
            }
        }


        backButton.setOnClickListener(v -> finish());
    }
}
