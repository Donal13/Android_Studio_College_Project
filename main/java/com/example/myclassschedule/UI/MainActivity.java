package com.example.myclassschedule.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myclassschedule.R;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setColorFilter(ContextCompat.getColor(this, R.color.gold1), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    public void onLogin(View view) throws IOException {
        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);
    }

}