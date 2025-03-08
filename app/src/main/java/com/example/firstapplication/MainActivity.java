package com.example.firstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout table=(TableLayout) findViewById(R.id.tableLayout);
        ToggleButton toggleButton=(ToggleButton)findViewById(R.id.toggleButton1);
        TextView textView = findViewById(R.id.textView2);

        //블록 9x9 설정
        BlockButton[][] buttons=new BlockButton[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                buttons[i][j] = new BlockButton(this, i, j);
            }
        }

        for(int i=0; i<9;i++){
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);
            for(int j=0;j<9;j++){
                tableRow.addView(buttons[i][j]);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!toggleButton.isChecked()) {
                            if(((BlockButton) view).flag==false) {
                                ((BlockButton) view).breakBlock(buttons,view);
                                if (((BlockButton) view).mine == true) {
                                    Toast.makeText(MainActivity.this, "GAME OVER!", Toast.LENGTH_SHORT).show();
                                    for(int k=0;k<9;k++){
                                        for(int l=0;l<9;l++){
                                            buttons[k][l].setClickable(false);
                                        }
                                    }
                                } else if (((BlockButton) view).blocks ==10 ) {
                                    Toast.makeText(MainActivity.this, "Win!", Toast.LENGTH_SHORT).show();
                                    for(int k=0;k<9;k++){
                                        for(int l=0;l<9;l++){
                                            buttons[k][l].setClickable(false);
                                        }
                                    }
                                }
                            }
                        }
                        else if(toggleButton.isChecked()){
                            ((BlockButton)view).toggleFlag();
                            textView.setText(String.valueOf(10-BlockButton.flags));
                        }
                    }
                });
            }
        }
        //지뢰심기
        Random random=new Random();
        int totalMines=10;
        while(totalMines>0){
            int randomX=random.nextInt(9);
            int randomY=random.nextInt(9);
            if(!buttons[randomX][randomY].mine){
                buttons[randomX][randomY].mine=true;
                totalMines--;
            }
        }
        //주변 지뢰 수 계산
        BlockButton.calculateNeighborMines(buttons);

    }
}