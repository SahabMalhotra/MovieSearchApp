package com.example.moviesearchapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.moviesearchapp.databinding.ActivityMovieDetailsBinding;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {
    private ActivityMovieDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String year = intent.getStringExtra("year");
            String rating = intent.getStringExtra("rating");
            String description = intent.getStringExtra("description");
            String runtime = intent.getStringExtra("runtime");
            String genre = intent.getStringExtra("genre");
            String posterUrl = intent.getStringExtra("posterUrl");


            binding.detailTitle.setText(title != null ? title : "Title not available");
            binding.detailYear.setText("Year: " + (year != null ? year : "N/A"));
            binding.detailRating.setText("Rating: " + (rating != null ? rating : "N/A"));
            binding.detailGenre.setText("Genre: " + genre);
            binding.detailRuntime.setText("Runtime: " + runtime);
            binding.detailDescription.setText(description != null ? description : "Description not available");


            if (posterUrl != null && !posterUrl.isEmpty()) {
                Picasso.get().load(posterUrl).into(binding.detailPoster);
            }
        }


        binding.backButton.setOnClickListener(v -> finish());
    }
}
