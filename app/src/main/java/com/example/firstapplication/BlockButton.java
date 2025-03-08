package com.example.firstapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

public class BlockButton extends AppCompatButton {

    int x,y;
    boolean mine;
    boolean flag;
    int neighborMines;
    static int flags=0;
    static int blocks=0;
    TextView textView = (TextView)findViewById(R.id.textView2);

    //블록 생성
    public BlockButton(Context context, int x, int y){
        super(context);
        this.x=x;
        this.y=y;
        blocks++;
        TableRow.LayoutParams layoutParams =
                new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f);
        setLayoutParams(layoutParams);
        mine=false;
        flag=false;
        neighborMines=0;
    }

    //깃발 꽃기 메소드
    public void toggleFlag(){
        if(!flag){
            if(flags<10) {
                setText("+");
                flags++;
                flag = true;
            }
        }
        else if(flag) {
            setText("");
            flags--;
            flag=false;
        }
    }

    //블록 열기
    public boolean breakBlock(BlockButton[][] buttons,View view) {
        if (!isClickable()||flag) {
            return false; // 이미 열린 블록이면 종료
        }
        buttons[x][y].setClickable(false);
        if (mine) {

            setText("*");
            return true;
        }
        else {
            setText(String.valueOf(neighborMines));
            setBackgroundColor(Color.WHITE);
            if(neighborMines==0)
            {
                openZeroBlocks(buttons,this.x,this.y);
                buttons[this.x][this.y].setClickable(false);
            }
            blocks--;
            return false;
        }
    }

    //주변 지뢰 수 계산 메소드 1
    public static void calculateNeighborMines(BlockButton[][] buttons) {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!buttons[i][j].mine) {
                    int count = countMines(buttons, i, j);
                    buttons[i][j].neighborMines = count;
                }
            }
        }
    }

    //주변 지뢰 수 계산 메소드 2
    private static int countMines(BlockButton[][] buttons, int x, int y) {
        int count = 0;
            for (int i = Math.max(0,x-1); i <= Math.min(8,x+1); i++) {
                for (int j = Math.max(0,y-1); j <= Math.min(8,y+1); j++) {
                    if (buttons[i][j].mine) {
                        count++;
                    }
                }
            }
            return count;
    }

    //재귀 호출 메소드
    private void openZeroBlocks(BlockButton[][] buttons, int x, int y){
        for (int i = Math.max(0, x - 1); i <= Math.min(8, x + 1); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(8, y + 1); j++) {
                buttons[i][j].breakBlock(buttons,this);
            }
        }
    }
}
