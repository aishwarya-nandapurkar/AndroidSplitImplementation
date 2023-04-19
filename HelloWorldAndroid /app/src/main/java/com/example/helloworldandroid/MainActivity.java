package com.example.helloworldandroid;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.helloworldandroid.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import io.split.android.client.SplitResult;
import io.split.android.client.api.Key;
//import com.google.gson.Gson;

import io.split.android.client.SplitClient;
import io.split.android.client.SplitClientConfig;
import io.split.android.client.SplitFactory;
import io.split.android.client.SplitFactoryBuilder;
import io.split.android.client.api.Key;
import io.split.android.client.events.SplitEvent;
import io.split.android.client.events.SplitEventTask;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    SplitInstance sdkclient;
    Button b1;
    String apikey = "<<APIKEY>>";

    // Build SDK configuration by default
    SplitClientConfig config = SplitClientConfig.builder().logLevel(2)
            .build();
    // Create a new user key to be evaluated
    String matchingKey = "ash";
    Key k = new Key(matchingKey);
    String treatment = "NA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        String matchingKey = "ash";
        Key k = new Key(matchingKey);
        try {
            Log.d("myTag", "Creating SplitFactory object"+": key is"+k);
            sdkclient= new SplitInstance(apikey, k, getApplicationContext());
        } catch (Exception e) {
            System.out.print("Exception: "+e.getMessage());
        }

                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("myTag", "click" + sdkclient.isReady);
                if (sdkclient.isReady==true) {
                     treatment = sdkclient.GetSplitTreatment("dynamic_boxes");
                    Log.d("myTag", "treatment: "+treatment);

                    sdkclient.SendTrackEvent("user", "conversion");
//                        client.Destroy();
                } else Log.d("myTag", "SDK not ready: " + sdkclient.isReady);


                Snackbar.make(view, "Feature flag is "+treatment, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
