package com.example.aditya.scarnesdice;


import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView cs;
    ImageView iv;
    TextView ys;
    TextView win;
    Button roll;
    Button hold;
    Button reset;
    int userscore;
    int comscore;
    int tuserscore;
    int tcompscore;
    Boolean yourturn = true;
    Boolean compturn = false;
    TextView turn;
    final int dice[]=new int[]{
            R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tuserscore = 0;
        tcompscore = 0;
        userscore=0;
        comscore=0;
        cs = (TextView)findViewById(R.id.compscore);
        ys = (TextView)findViewById(R.id.yourscore);
        ys.setText("Your Score: " + tuserscore);
        cs.setText("Computer's Score: " + tcompscore);
        turn=(TextView)findViewById(R.id.turn);
        turn.setText("Your Turn");
    }


    public void roll(final View v) {
        yourturn = true;
        compturn = false;
        turn=(TextView)findViewById(R.id.turn);
        turn.setText("Your Turn");
        Random ran = new Random();
        int num = ran.nextInt(5) + 1;
        updateScore(v, num);

    }


    public void comproll(View v)

    {
        yourturn=false;
        compturn=true;
        turn=(TextView)findViewById(R.id.turn);
        turn.setText("Computer's Turn");
        Random rand = new Random();
        int cnum = rand.nextInt(8) + 1;
        if(comscore==0 && cnum>6)
        {
            cnum=cnum%6+2;
        }
        if(cnum>6) {

            Toast.makeText(this,"Computer held",Toast.LENGTH_SHORT).show();
            comphold(v);
            cnum=1;
        }
        else {
            updateScore(v, cnum);
        }

    }

    void updateScore(final View v, final int num)
    {

        iv=(ImageView)findViewById(R.id.imageView);
        if(yourturn)
        {
            if(num==1)
            {
                iv.setImageResource(dice[num-1]);
                userscore=0;
                totalScore(v,tuserscore);
                turn=(TextView)findViewById(R.id.turn);
                Toast.makeText(this,"You lost your points", Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"Now Computer's Turn", Toast.LENGTH_SHORT).show();
                turn.setText("Computer's Turn");
                roll=(Button)findViewById(R.id.button);
                reset=(Button)findViewById(R.id.button3);
                hold=(Button)findViewById(R.id.button2);
                roll.setEnabled(false);
                reset.setEnabled(false);
                hold.setEnabled(false);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        comproll(v);
                    }
                }, 2000);
            }
            else
            {
                iv.setImageResource(dice[num-1]);
                userscore+=num;
                Toast.makeText(this,"You have "+userscore+" points",Toast.LENGTH_SHORT).show();
            }
        }
      if(compturn)
        {
            if(num==1)
            {
                iv.setImageResource(dice[num-1]);
                comscore=0;
                totalScore(v,tcompscore);


                yourturn=true;
                compturn=false;
                turn=(TextView)findViewById(R.id.turn);
                turn.setText("Your Turn");
               // Toast.makeText(this,"Your turn",Toast.LENGTH_SHORT).show();
                roll=(Button)findViewById(R.id.button);
                reset=(Button)findViewById(R.id.button3);
                hold=(Button)findViewById(R.id.button2);
                roll.setEnabled(true);
                reset.setEnabled(true);
                hold.setEnabled(true);

            }

            else
            {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        iv.setImageResource(dice[num-1]);
                        comscore+=num;

                       // cs.setText("Computer's Score: "+ comscore );

                        iv.setImageResource(dice[num-1]);
                        comproll(v);
                    }
                }, 2000);
            }
        }
    }

    public void totalScore(View v,int score)
    {
        if(yourturn)
        {
            ys.setText("Your Score: "+ score );
        }
        if(compturn)
        {
            cs.setText("Computer's Score: "+ score);
        }
    }

    public void hold(final View v)

    {
        if(yourturn) {
            tuserscore += userscore;
            if (tuserscore >= 50)
            {
                totalScore(v, tuserscore);
                win = (TextView) findViewById(R.id.win);
                win.setText("Congrats!! You Won");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        reset(v);
                    }
                }, 3000);

            }

            else
            {
                userscore = 0;
                totalScore(v, tuserscore);
                turn = (TextView) findViewById(R.id.turn);
                turn.setText("Computer's Turn");
                //   Toast.makeText(this,"Now Computer's Turn", Toast.LENGTH_SHORT).show();
                roll = (Button) findViewById(R.id.button);
                reset = (Button) findViewById(R.id.button3);
                hold = (Button) findViewById(R.id.button2);
                roll.setEnabled(false);
                reset.setEnabled(false);
                hold.setEnabled(false);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        comproll(v);
                    }
                }, 2000);

            }
        }

    }

    public void comphold(final View v)
    {
        tcompscore+=comscore;
        comscore=0;
        if (tcompscore >= 50)
        {
            totalScore(v,tcompscore);
            win = (TextView) findViewById(R.id.win);
            win.setText("Computer Won");
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    reset(v);
                }
            }, 3000);

        }
        else {

            totalScore(v,tcompscore);
            turn = (TextView) findViewById(R.id.turn);
            turn.setText("Your Turn");
            // Toast.makeText(this,"Your turn",Toast.LENGTH_SHORT).show();
            roll = (Button) findViewById(R.id.button);
            reset = (Button) findViewById(R.id.button3);
            hold = (Button) findViewById(R.id.button2);
            roll.setEnabled(true);
            reset.setEnabled(true);
            hold.setEnabled(true);
        }

    }

    public void reset(View v)
    {
        userscore=0;
        comscore=0;
        tcompscore=0;
        tuserscore=0;
        ys.setText("Your Score: 0");
        cs.setText("Computer's Score: 0" );
        win = (TextView) findViewById(R.id.win);
        win.setText("");
        iv=(ImageView)findViewById(R.id.imageView);
        iv.setImageResource(dice[0]);
        yourturn = true;
        compturn = false;
        turn.setText("Your Turn");
        roll = (Button) findViewById(R.id.button);
        reset = (Button) findViewById(R.id.button3);
        hold = (Button) findViewById(R.id.button2);
        roll.setEnabled(true);
        reset.setEnabled(true);
        hold.setEnabled(true);
    }

}
