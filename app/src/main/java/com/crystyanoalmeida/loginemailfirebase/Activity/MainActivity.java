package com.crystyanoalmeida.loginemailfirebase.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.crystyanoalmeida.loginemailfirebase.R;

public class MainActivity extends AppCompatActivity {

    private Button btnAbrirLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAbrirLogin = (Button) findViewById(R.id.btnFazerLogin);

        btnAbrirLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
