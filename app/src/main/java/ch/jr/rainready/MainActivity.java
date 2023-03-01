package ch.jr.rainready;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button rainButton = findViewById(R.id.goToWeather);
        rainButton.setOnClickListener(v -> goToWeather());
    }
    //go to weather activity
    public void goToWeather(){
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }
}