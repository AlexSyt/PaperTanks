package com.example.alex.papertanks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(MainGamePanel.displaySize);
        setContentView(R.layout.main_game_panel);

        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainGamePanel.selectNextTank();
            }
        });

        Button exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }
}
