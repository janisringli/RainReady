package ch.jr.rainready;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity {

    Button backToMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        TextView currentWeatherStatus = findViewById(R.id.currentWeatherStatus);


        backToMain = findViewById(R.id.backToMain);
        backToMain.setOnClickListener(v -> backToMain());

        Bundle bundle = getIntent().getExtras();
        currentWeatherStatus.setText(bundle.getString("weather"));
        if (currentWeatherStatus == null) {
            currentWeatherStatus.setText("No weather data available");
        }

    }
    public void backToMain(){
        Intent i = new Intent(this, MainActivity.class);


        startActivity(i);
    }
}
