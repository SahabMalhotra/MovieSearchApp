package com.example.moviesearchapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviesearchapp.adapters.MovieAdapter;
import com.example.moviesearchapp.models.Movie;
import com.example.moviesearchapp.viewmodels.MovieViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MovieViewModel movieViewModel;
    private RecyclerView movieRecyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText searchEditText = findViewById(R.id.searchEditText);
        Button searchButton = findViewById(R.id.searchButton);
        movieRecyclerView = findViewById(R.id.movieRecyclerView);

        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieAdapter = new MovieAdapter(this, movieList); // FIXED: Pass "this" as context
        movieRecyclerView.setAdapter(movieAdapter);

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        movieViewModel.getMovieLiveData().observe(this, movies -> {
            if (movies != null && !movies.isEmpty()) {
                movieList.clear();
                movieList.addAll(movies);
                movieAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "No movies found!", Toast.LENGTH_SHORT).show();
            }
        });


        searchButton.setOnClickListener(v -> {
            String title = searchEditText.getText().toString().trim();
            if (!title.isEmpty()) {
                movieViewModel.fetchMovieDetails(title);
            } else {
                Toast.makeText(this, "Enter a movie name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
