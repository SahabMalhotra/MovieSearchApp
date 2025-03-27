package com.example.moviesearchapp.viewmodels;

import android.os.AsyncTask;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.moviesearchapp.models.Movie;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieLiveData = new MutableLiveData<>();
    private static final String API_URL = "https://www.omdbapi.com/?apikey=63b4ae58&s=";


    public void fetchMovieDetails(String title) {
        new FetchMovieTask().execute(title);
    }

    public LiveData<List<Movie>> getMovieLiveData() {
        return movieLiveData;
    }

    private class FetchMovieTask extends AsyncTask<String, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(String... params) {
            String title = params[0];
            List<Movie> movies = new ArrayList<>();
            try {

                URL url = new URL(API_URL + title.replace(" ", "+"));
                Log.d("API_REQUEST", "Fetching URL: " + url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);


                int responseCode = connection.getResponseCode();
                Log.d("API_RESPONSE", "Response Code: " + responseCode);

                if (responseCode == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder jsonString = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonString.append(line);
                    }
                    reader.close();

                    Log.d("API_JSON", "Response: " + jsonString.toString());

                    List<Movie> searchResults = parseSearchResultsJson(jsonString.toString());

                    for (Movie movie : searchResults) {
                        Movie detailedMovie = fetchMovieDetailsFromAPI(movie.getTitle());
                        if (detailedMovie != null) {
                            movies.add(detailedMovie);
                        }
                    }
                } else {
                    Log.e("API_ERROR", "Response Code: " + responseCode);
                }
            } catch (Exception e) {
                Log.e("API_ERROR", "Request Failed: " + e.getMessage());
            }
            return movies;
        }


        private List<Movie> parseSearchResultsJson(String json) {
            List<Movie> movies = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.has("Search")) {
                    JSONArray resultsArray = jsonObject.getJSONArray("Search");
                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject movieObject = resultsArray.getJSONObject(i);

                        String title = movieObject.optString("Title", "N/A");
                        String year = movieObject.optString("Year", "N/A");
                        String poster = movieObject.optString("Poster", "N/A");

                        movies.add(new Movie(title, year, "N/A", "N/A", poster, "NA","NA"));
                    }
                }
            } catch (Exception e) {
                Log.e("JSON_ERROR", "Parsing Failed: " + e.getMessage());
            }
            return movies;
        }


        private Movie fetchMovieDetailsFromAPI(String title) {
            try {
                URL url = new URL("https://www.omdbapi.com/?apikey=63b4ae58&t=" + title.replace(" ", "+"));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder jsonString = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonString.append(line);
                    }
                    reader.close();

                    JSONObject movieObject = new JSONObject(jsonString.toString());

                    String titleResult = movieObject.optString("Title", "N/A");
                    String year = movieObject.optString("Year", "N/A");
                    String rating = movieObject.optString("imdbRating", "N/A");
                    String description = movieObject.optString("Plot", "N/A");
                    String poster = movieObject.optString("Poster", "N/A");
                    String genre = movieObject.optString("Genre", "N/A");
                    String runtime = movieObject.optString("Runtime", "N/A");


                    return new Movie(titleResult, year, rating, description, poster,genre, runtime);
                }
            } catch (Exception e) {
                Log.e("API_ERROR", "Failed to fetch detailed movie: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            movieLiveData.setValue(movies);
        }
    }
}
