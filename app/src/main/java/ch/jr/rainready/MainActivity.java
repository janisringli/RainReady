package ch.jr.rainready;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;



import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements LocationListener {


    private static String TEMPERATURE = "";
    private static String WIND = "";
    private String API_URL = "https://api.openweathermap.org/data/2.5/weather?";
    private static final String TEMPERATURE_SYMBOL = "°";
    public static final String CHARSET_NAME = "UTF-8";


    private String WEATHER_LAT;
    private String WEATHER_LON;
    private String API_KEY = "790fc27c50273fcd7df986067dee8ab1";
    private TextView weatherTextView;
    private TextView windTextView;





    public String WEATHER_API = API_URL + "lat=" + WEATHER_LAT + "&lon=" + WEATHER_LON + "&units=metric" + "&appid=" + API_KEY;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherTextView = findViewById(R.id.temperatureTextView);
        windTextView = findViewById(R.id.windTextView);

        
    }
}
