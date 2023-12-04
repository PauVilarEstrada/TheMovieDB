package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String JSON_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=78bd1ca29abc997d9aa7ac080240c361";
    private static final String PREFERENCES_NAME = "MyAppPreferences";
    private static final String KEY_DATA = "storedData";
    private static final String URL_popular="https://api.themoviedb.org/3/movie/popular?api_key=78bd1ca29abc997d9aa7ac080240c361";
    private static final String URL_upcoming="https://api.themoviedb.org/3/movie/upcoming?api_key=78bd1ca29abc997d9aa7ac080240c361";
    private static final String URL_top_rated= "https://api.themoviedb.org/3/movie/top_rated?api_key=78bd1ca29abc997d9aa7ac080240c361";

    private List<MovieModelClass> fullMovieList;
    private List<MovieModelClass> displayedMovieList;

    private RecyclerView recyclerView;
    private SearchView searchView;
    private Switch switchLayout;

    private Adaptery adapter;

    private void loadData() {
        GetData getData = new GetData();
        getData.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullMovieList = new ArrayList<>();
        displayedMovieList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview);
        searchView = findViewById(R.id.searchView);
        switchLayout = findViewById(R.id.switchLayout);

        loadStoredData();
        updateRecyclerViewLayout(1);

        Spinner spinner = findViewById(R.id.toolbar_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.movie_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCategory = parentView.getItemAtPosition(position).toString();
                changeMovieCategory(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterMovieList(newText);
                return true;
            }
        });

        // Configuración del Switch
        switchLayout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    updateRecyclerViewLayout(3);
                } else {
                    updateRecyclerViewLayout(1);
                }
            }
        });

        loadData();
    }

    private void filterMovieList(String query) {
        displayedMovieList.clear();

        if (query.isEmpty()) {
            displayedMovieList.addAll(fullMovieList);
        } else {
            for (MovieModelClass movie : fullMovieList) {
                if (movie.getName().toLowerCase().contains(query.toLowerCase())) {
                    displayedMovieList.add(movie);
                }
            }
        }

        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void updateRecyclerViewLayout(int columnasPorFila) {
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, columnasPorFila);
        recyclerView.setLayoutManager(layoutManager);
        putDataIntoRecyclerView(displayedMovieList);
    }

    private void loadStoredData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        String storedData = sharedPreferences.getString(KEY_DATA, "");
        if (!storedData.isEmpty()) {
            fullMovieList = parseStoredData(storedData);
            displayedMovieList.addAll(fullMovieList);
            putDataIntoRecyclerView(displayedMovieList);
        }
    }

    private List<MovieModelClass> parseStoredData(String storedData) {
        List<MovieModelClass> parsedList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(storedData);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                MovieModelClass model = new MovieModelClass();
                model.setId(jsonObject1.getString("vote_average"));
                model.setName(jsonObject1.getString("title"));
                model.setImg(jsonObject1.getString("poster_path"));
                model.setReleaseDate(jsonObject1.getString("release_date"));
                model.setPopularity(jsonObject1.getDouble("popularity"));
                model.setSynopsis(jsonObject1.getString("overview"));
                parsedList.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parsedList;
    }

    private void saveDataLocally(String dataToSave) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_DATA, dataToSave);
        editor.apply();
    }

    private void putDataIntoRecyclerView(List<MovieModelClass> movieList) {
        Log.d("PutData", "Putting data into RecyclerView. Count: " + movieList.size());
        adapter = new Adaptery(this, movieList, switchLayout.isChecked());
        recyclerView.setAdapter(adapter);
    }

    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try {
                URL url = new URL(JSON_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream is = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int data = isr.read();
                while (data != -1) {
                    current += (char) data;
                    data = isr.read();
                }
                return current;

            } catch (IOException e) {
                Log.e("GetData", "Error in doInBackground", e);
                e.printStackTrace();
            }

            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("GetData", "Received data: " + s);

            if (!s.isEmpty()) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    fullMovieList.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        MovieModelClass model = new MovieModelClass();
                        model.setId(jsonObject1.getString("vote_average"));
                        model.setName(jsonObject1.getString("title"));
                        model.setImg(jsonObject1.getString("poster_path"));
                        model.setReleaseDate(jsonObject1.getString("release_date"));
                        model.setPopularity(jsonObject1.getDouble("popularity"));
                        model.setSynopsis(jsonObject1.getString("overview"));
                        fullMovieList.add(model);
                    }

                    saveDataLocally(s);
                    displayedMovieList.clear();
                    displayedMovieList.addAll(fullMovieList);
                    putDataIntoRecyclerView(displayedMovieList);

                } catch (JSONException e) {
                    Log.e("GetData", "Error parsing JSON", e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void changeMovieCategory(String category) {
        if ("Top Rated".equals(category)) {
            JSON_URL = URL_top_rated;
        } else if ("Popular".equals(category)) {
            JSON_URL = URL_popular;
        } else if ("Upcoming".equals(category)) {
            JSON_URL = URL_upcoming;
        }
        loadData();
    }

    private String buildUrl(String category) {
        return "https://api.themoviedb.org/3/movie/" + category + "?api_key=78bd1ca29abc997d9aa7ac080240c361";
    }
}
