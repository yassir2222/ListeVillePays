package com.example.tp_spinner_listview1;

// Keep necessary imports
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView; // Import ListView
import android.widget.Spinner;
import android.widget.Toast; // Optional: For user feedback

// Keep your androidx imports
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections; // Needed for emptyList if adding a prompt
import java.util.HashMap;
import java.util.List;
import java.util.Map; // Use Map interface for better practice

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener { // Implement the listener

    // --- Member Variables ---
    // Use interface types (Map, List) where possible
    Map<String, List<String>> listPaysVille;
    Spinner paysSpinner;            // Reference to the Spinner
    ListView villesListView;        // Reference to the ListView
    ArrayAdapter<String> villesAdapter; // Adapter FOR THE LISTVIEW
    List<String> currentVillesList;  // Data source for villesAdapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Keep or remove as needed
        setContentView(R.layout.activity_main);

        // --- Window Insets Handling ---
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- Initialize Views ---
        paysSpinner = findViewById(R.id.Pays);         // Find Spinner
        villesListView = findViewById(R.id.listVille); // <-- FIX: Find ListView HERE

        // --- Prepare Data ---
        listPaysVille = new HashMap<>(); // Initialize the member variable HashMap
        // Optional: Add a prompt item
        listPaysVille.put("-- Sélectionnez un Pays --", Collections.emptyList());
        listPaysVille.put("France", Arrays.asList("Paris", "Lyon", "Marseille"));
        listPaysVille.put("Allemagne", Arrays.asList("Berlin", "Munich", "Hamburg"));
        listPaysVille.put("Espagne", Arrays.asList("Madrid", "Barcelona", "Valencia"));
        listPaysVille.put("Angleterre", Arrays.asList("London", "Manchester", "Birmingham"));

        // --- Setup Pays (Country) Spinner ---
        // Create a list from the keyset for the adapter (more flexible)
        List<String> paysList = new ArrayList<>(listPaysVille.keySet());
        // Optional: You might want to sort the countries alphabetically
        // Collections.sort(paysList); // Be careful if you added a prompt, sort after adding it

        ArrayAdapter<String> paysAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                paysList); // Use the List<String>
        paysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paysSpinner.setAdapter(paysAdapter);


        currentVillesList = new ArrayList<>();

        villesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                currentVillesList);
        villesListView.setAdapter(villesAdapter);


        paysSpinner.setOnItemSelectedListener(this);

        // Optional: Add listener for ListView item clicks
        villesListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedVille = villesAdapter.getItem(position); // Or currentVillesList.get(position);
            Toast.makeText(MainActivity.this, "Ville sélectionnée: " + selectedVille, Toast.LENGTH_SHORT).show();
        });

        villesListView.setOnItemClickListener((parent, view, position, id) -> {
            // 1. Get the selected city name from the adapter's data source
            String selectedVille = currentVillesList.get(position);
            // String selectedVille = villesAdapter.getItem(position); // Alternative way

            // 2. Create an Intent to launch VilleDetailActivity
            Intent intent = new Intent(MainActivity.this, VilleDetail.class);

            // 3. Put the selected city name as an "extra" in the Intent
            //    Use the SAME KEY defined in VilleDetailActivity
            intent.putExtra(VilleDetail.EXTRA_CITY_NAME, selectedVille);

            // 4. Start the new activity
            startActivity(intent);

            // Optional: Keep the Toast for feedback during development
            // Toast.makeText(MainActivity.this, "Ville cliquée: " + selectedVille, Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.Pays) {

            String selectedPays = parent.getItemAtPosition(position).toString();

            List<String> villes = listPaysVille.get(selectedPays);


            currentVillesList.clear(); // Clear the previous list of cities
            if (villes != null) { // Add the new cities (check for null is good practice)
                currentVillesList.addAll(villes);
            }
            villesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        if (parent.getId() == R.id.Pays) {
            currentVillesList.clear();
            villesAdapter.notifyDataSetChanged();
        }
    }
}