package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        // Recuperar datos de la película seleccionada
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String releaseDate = intent.getStringExtra("release_date");
            double popularity = intent.getDoubleExtra("popularity", 0.0);
            String imageUrl = "https://image.tmdb.org/t/p/w500" + intent.getStringExtra("poster_path");
            String synopsis = intent.getStringExtra("overview");
            // Obtener más datos según sea necesario

            // Inicializar las vistas con los datos de la película
            ImageView imageView = findViewById(R.id.imageView);
            TextView titleTxt = findViewById(R.id.title_txt);
            TextView releaseDateTxt = findViewById(R.id.release_date_txt);
            TextView popularityTxt = findViewById(R.id.popularity_txt);
            TextView synopsisTxt = findViewById(R.id.synopsis_txt);

            // Inicializar más vistas según sea necesario
            titleTxt.setText(title);
            releaseDateTxt.setText("Release Date: " + releaseDate);
            popularityTxt.setText("Popularity: " + popularity + "%");
            synopsisTxt.setText("Synopsis: " + synopsis);

            // Usar Glide para cargar la imagen desde la URL
            Glide.with(this)
                    .load(imageUrl)
                    .into(imageView);
        }
    }
}
