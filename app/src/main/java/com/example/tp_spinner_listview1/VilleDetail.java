package com.example.tp_spinner_listview1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class VilleDetail extends AppCompatActivity {

    // Key for the intent extra (use the same key in MainActivity)
    public static final String EXTRA_CITY_NAME = "com.example.tp_spinner_listview1.EXTRA_CITY_NAME";

    // Simple map for descriptions (in a real app, this might come from DB/API)
    private Map<String, String> villeDescriptions;

    TextView tvCityNameDetail;
    TextView tvCityDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Keep if needed
        setContentView(R.layout.activity_ville_detail);

        // Handle Window Insets (adjust ID if needed)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Views
        tvCityNameDetail = findViewById(R.id.tvCityNameDetail);
        tvCityDescription = findViewById(R.id.tvCityDescription);

        // Initialize descriptions (replace with your actual data)
        initializeDescriptions();

        // Get the Intent that started this activity
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_CITY_NAME)) {
            String cityName = intent.getStringExtra(EXTRA_CITY_NAME);

            // Display the city name
            tvCityNameDetail.setText(cityName);

            // Look up and display the description
            String description = villeDescriptions.getOrDefault(cityName, "Description non disponible."); // Provide default
            tvCityDescription.setText(description);

        } else {
            // Handle the error case where the city name wasn't passed
            Toast.makeText(this, "Erreur: Nom de la ville non reçu.", Toast.LENGTH_LONG).show();
            tvCityNameDetail.setText("Erreur");
            tvCityDescription.setText("Impossible de charger les détails.");
            // Optionally finish the activity
            // finish();
        }
    }

    private void initializeDescriptions() {
        villeDescriptions = new HashMap<>();
        villeDescriptions.put("Paris", "Capitale de la France, connue pour la Tour Eiffel et le Louvre.");
        villeDescriptions.put("Lyon", "Ville gastronomique française située au confluent du Rhône et de la Saône.");
        villeDescriptions.put("Marseille", "Port historique sur la Méditerranée, la plus ancienne ville de France.");
        villeDescriptions.put("Berlin", "Capitale de l'Allemagne, riche en histoire et en culture.");
        villeDescriptions.put("Munich", "Ville bavaroise célèbre pour l'Oktoberfest et ses jardins à bière.");
        villeDescriptions.put("Hamburg", "Grande ville portuaire allemande traversée par de nombreux canaux.");
        villeDescriptions.put("Madrid", "Capitale de l'Espagne, connue pour ses musées d'art et sa vie nocturne.");
        villeDescriptions.put("Barcelona", "Ville catalane célèbre pour l'architecture de Gaudí et ses plages.");
        villeDescriptions.put("Valencia", "Ville espagnole connue pour sa Cité des Arts et des Sciences et sa paella.");
        villeDescriptions.put("London", "Capitale de l'Angleterre et du Royaume-Uni, centre financier mondial.");
        villeDescriptions.put("Manchester", "Ville industrielle historique anglaise, connue pour le football.");
        villeDescriptions.put("Birmingham", "Grande ville anglaise avec un réseau de canaux important.");
        // Add descriptions for all your cities
    }
}