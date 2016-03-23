package com.anhquan.realapp1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int start = 0;
    int redWinCount=0;
    int yellowWinCount=0;
    int tieCount = 0;
    int turn=start; //0 for red, 1 for yellow
    int[] state = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
    int[][] winning = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((LinearLayout)findViewById(R.id.playAgain)).setVisibility(View.INVISIBLE);

    }
    public void dropIn(View view)
    {

        ImageView pic = (ImageView) view;
        int picState= Integer.parseInt(pic.getTag().toString());
        if (state[picState]== -1) {
           if(turn ==0) {
               pic.setImageResource(R.drawable.yellow);
               state[picState] = turn;
               turn =1;
           }
           else
           {
               pic.setImageResource(R.drawable.red);
               state[picState] = turn;
               turn = 0;
           }

            pic.setAlpha(0f);
            pic.animate().alpha(1f).setDuration(400);
        }

        int result = win(state,winning);
        TextView message = (TextView)findViewById(R.id.message);
        String strMessage,temp;
        if(result==0) {

            yellowWinCount++;
            strMessage = "Yellow Wins";
            message.setText(strMessage);

            temp= Integer.toString(yellowWinCount);
            ((TextView) findViewById(R.id.yellowCount)).setText(temp);

            ((LinearLayout)findViewById(R.id.playAgain)).setVisibility(View.VISIBLE);
        }
        else if(result ==1)
        {
            redWinCount++;
            strMessage = "Red Wins";
            message.setText(strMessage);
            temp= Integer.toString(redWinCount);
            ((TextView) findViewById(R.id.redCount)).setText(temp);
            ((LinearLayout)findViewById(R.id.playAgain)).setVisibility(View.VISIBLE);
        }
        else if (result ==2)
        {
            tieCount++;
            strMessage = "It's a tie";
            message.setText(strMessage);
            temp= Integer.toString(tieCount);
            ((TextView) findViewById(R.id.tieCount)).setText(temp);
            ((LinearLayout)findViewById(R.id.playAgain)).setVisibility(View.VISIBLE);
        }
    }

    public void playAgain(View view)
    {
        ((LinearLayout)findViewById(R.id.playAgain)).setVisibility(View.INVISIBLE);

        for(int i=0; i< state.length; i++)
        {
            state[i] = -1;
        }

        if(start == 0)
            start = 1;
        else
            start = 0;

        GridLayout gridLayout = (GridLayout)findViewById(R.id.gridLayout);
        for(int i = 0; i< gridLayout.getChildCount(); i++)
        {
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }




    }

    public int win(int[] state, int[][] winning)
    {
        for(int[] i: winning)
        {
            if(state[i[0]]!=-1 && state[i[0]]== state[i[1]] && state[i[1]]== state[i[2]])
            {
                if(state[i[0]]==0)
                    return 0; // yellow wins
                else
                    return 1; // red wins
            }
        }
        int emptyCount = 0;
        for(int i = 0; i<9; i++)
        {

            if(state[i]!=-1)
                emptyCount++;
        }
        if(emptyCount==9)
            return 2; //tie
        return -1;

    }

}
