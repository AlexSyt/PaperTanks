package com.example.alex.papertanks;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    MainGamePanel mainGamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);

        setContentView(R.layout.main_game_panel);
        mainGamePanel = (MainGamePanel) findViewById(R.id.main_game_panel);
        mainGamePanel.initTanks(displaySize);

        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> mainGamePanel.selectNextTank());

        Button btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(v -> {
            mainGamePanel.stop();
            finish();
        });
    }
}
