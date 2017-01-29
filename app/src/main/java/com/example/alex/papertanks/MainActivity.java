package com.example.alex.papertanks;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MainGamePanel mainGamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        Display display = getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);

        setContentView(R.layout.main_game_panel);
        mainGamePanel = (MainGamePanel) findViewById(R.id.main_game_panel);
        mainGamePanel.initTanks(displaySize);

        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);

        Button btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                mainGamePanel.selectNextTank();
                break;
            case R.id.btnExit:
                finish();
                System.exit(0);
                break;
        }
    }
}
