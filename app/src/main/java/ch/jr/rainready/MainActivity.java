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
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.TextView;


import android.os.AsyncTask;

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

    private static String WEATHER = "";
    private String API_URL = "https://api.openweathermap.org/data/2.5/weather?";
    private static final String TEMPERATURE_SYMBOL = "°";
    public static final String CHARSET_NAME = "UTF-8";


    private String WEATHER_LAT;
    private String WEATHER_LON;

    private String weather;
    private String API_KEY = "790fc27c50273fcd7df986067dee8ab1";
    private TextView weatherTextView;
    private TextView windTextView;

Button goToWeather;



    public String WEATHER_API = API_URL + "lat=" + WEATHER_LAT + "&lon=" + WEATHER_LON + "&units=metric" + "&appid=" + API_KEY;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherTextView = findViewById(R.id.temperatureTextView);
        windTextView = findViewById(R.id.windTextView);
        goToWeather = findViewById(R.id.goToWeather);
        goToWeather.setOnClickListener(v -> goToWeather());




        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, (float) 0, (LocationListener) this);
        new GetWeatherTask().execute(WEATHER_API);

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //store the location in a variable
        WEATHER_LAT = String.valueOf(location.getLatitude());
        WEATHER_LON = String.valueOf(location.getLongitude());


        WEATHER_API = API_URL + "lat=" + WEATHER_LAT + "&lon=" + WEATHER_LON + "&units=metric" + "&appid=" + API_KEY;
        new GetWeatherTask().execute(WEATHER_API);
        System.out.println(WEATHER_LAT);
        System.out.println(WEATHER_LON);
    }


    private class GetWeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String result = null;

            try {
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream, CHARSET_NAME));

                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                result = buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject mainObject = jsonObject.getJSONObject("main");
                    JSONObject windObject = jsonObject.getJSONObject("wind");
                    JSONObject weatherObject = jsonObject.getJSONArray("weather").getJSONObject(0);
                    String weather = weatherObject.getString("main");
                    double temperature = mainObject.getDouble("temp");
                    double wind = windObject.getDouble("speed");

                    TEMPERATURE = String.valueOf(Math.round(temperature));
                    WIND = String.valueOf(Math.round(wind));
                    WEATHER = weather;
                    if (weather.equals("Rain")) {
                        vibrate();
                    }
                    weatherTextView.setText(TEMPERATURE + TEMPERATURE_SYMBOL + "C");
                    windTextView.setText(WIND + "m/s");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }

        }
        public void goToWeather(){
            Intent i = new Intent(this, WeatherActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("temperature", TEMPERATURE);
            bundle.putString("wind", WIND);
            bundle.putString("weather", WEATHER);
            i.putExtras(bundle);
            //get the weather

            startActivity(i);
        }
        public void vibrate(){
            System.out.println("its raining");
            System.out.println("vibrate");
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));

        }
    }

