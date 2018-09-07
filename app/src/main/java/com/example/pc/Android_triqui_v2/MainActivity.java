package com.example.pc.Android_triqui_v2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //private Button[][] buttons = new Button[3][3];
    private Button[]buttons = new Button[9];
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;

    public static final String HUMAN_PLAYER = "X";
    public static final String COMPUTER_PLAYER = "O";

    public enum DifficultyLevel {Easy, Harder, Expert};

    // Current difficulty level
    private DifficultyLevel mDifficultyLevel = DifficultyLevel.Expert;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        buttons[0] = findViewById(R.id.button_0);
        buttons[1] = findViewById(R.id.button_1);
        buttons[2] = findViewById(R.id.button_2);
        buttons[3] = findViewById(R.id.button_3);
        buttons[4] = findViewById(R.id.button_4);
        buttons[5] = findViewById(R.id.button_5);
        buttons[6] = findViewById(R.id.button_6);
        buttons[7] = findViewById(R.id.button_7);
        buttons[8] = findViewById(R.id.button_8);

        buttons[0].setOnClickListener(this);
        buttons[1].setOnClickListener(this);
        buttons[2].setOnClickListener(this);
        buttons[3].setOnClickListener(this);
        buttons[4].setOnClickListener(this);
        buttons[5].setOnClickListener(this);
        buttons[6].setOnClickListener(this);
        buttons[7].setOnClickListener(this);
        buttons[8].setOnClickListener(this);

        /*for (int i=0; i<3;i++){
            for (int j=0;j<3;j++){
                String buttonID = "button_"+i+j;
                int resId = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons [i][j] = findViewById (resId);
                buttons [i][j].setOnClickListener(this);

            }
        }*/

        Button buttonReset = findViewById (R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")){
            return;
        }

        /*if(player1Turn) {

        }
        else{
            ((Button) view).setText(COMPUTER_PLAYER);
        }*/
        ((Button) view).setText(HUMAN_PLAYER);
        String [] field = new String [9];
        for (int i=0; i<9;i++){
            field[i] = buttons[i].getText().toString();

        }
        int winner = this.checkForWin(field);
        if (winner == 0) {
            int move = getComputerMove();
            buttons[move].setText(COMPUTER_PLAYER);
            //setMove(TicTacToeGame.COMPUTER_PLAYER, move);
            //mInfoTextView.setText("It's Android's turn.");
            //player1Turn = !player1Turn;
            //int move = mGame.getComputerMove();
            //setMove(TicTacToeGame.COMPUTER_PLAYER, move);
            //winner = mGame.checkForWinner();
            roundCount=roundCount+2;
            for (int i=0; i<9;i++){
                field[i] = buttons[i].getText().toString();

            }
            winner = this.checkForWin(field);
        }

        //if (winner == 0)
            //player1Turn = !player1Turn;
            //mInfoTextView.setText("It's your turn.");
        if (winner == 1){
            this.draw();
            //mInfoTextView.setText("It's a tie!");
            //mGameOver = true;
        }

        else if (winner == 2){
            this.player1Wins();
            //mInfoTextView.setText("You won!");
            //mGameOver = true;
        }
        else if (winner == 3){
            this.player2Wins();
            //mInfoTextView.setText("Android won!");
            //mGameOver = true;
        }



    }

    private int checkForWin(String [] field){
        /*String [][] field = new String[3][3];
        for (int i=0; i<3;i++){
            for (int j=0;j<3;j++){
                field[i][j] = buttons[i][j].getText().toString();

            }
        }
        for (int i=0; i<3;i++){
            for (int j=0;j<3;j++){
                field[i][j] = buttons[i][j].getText().toString();

            }
        }*/

        /*String [] field = new String [9];
        for (int i=0; i<9;i++){
                field[i] = buttons[i].getText().toString();

        }*/

        for (int i = 0; i <= 6; i += 3)	{
            if (field[i].equals(HUMAN_PLAYER) &&
                    field[i+1].equals(HUMAN_PLAYER)  &&
                    field[i+2].equals(HUMAN_PLAYER) )
                return 2;
            if (field[i].equals(COMPUTER_PLAYER) &&
                    field[i+1].equals(COMPUTER_PLAYER) &&
                    field[i+2].equals(COMPUTER_PLAYER))
                return 3;
        }

        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (field[i].equals(HUMAN_PLAYER) &&
                    field[i+3].equals(HUMAN_PLAYER)&&
                    field[i+6].equals(HUMAN_PLAYER))
                return 2;
            if (field[i].equals(COMPUTER_PLAYER) &&
                    field[i+3].equals(COMPUTER_PLAYER) &&
                    field[i+6].equals(COMPUTER_PLAYER))
                return 3;
        }

        // Check for diagonal wins
        if ((field[0].equals(HUMAN_PLAYER) &&
                field[4].equals(HUMAN_PLAYER) &&
                field[8].equals(HUMAN_PLAYER)) ||
                (field[2].equals(HUMAN_PLAYER) &&
                        field[4].equals(HUMAN_PLAYER) &&
                        field[6].equals(HUMAN_PLAYER)))
            return 2;
        if ((field[0].equals(COMPUTER_PLAYER) &&
                field[4].equals(COMPUTER_PLAYER) &&
                field[8].equals(COMPUTER_PLAYER)) ||
                (field[2].equals(COMPUTER_PLAYER) &&
                        field[4].equals(COMPUTER_PLAYER) &&
                        field[6].equals(COMPUTER_PLAYER)))
            return 3;

        // Check for tie
        for (int i = 0; i < 9; i++) {
            // If we find a number, then no one has won yet
            if ((!field[i].equals(HUMAN_PLAYER)) && (!field[i].equals(COMPUTER_PLAYER)))
                return 0;
        }

        // If we make it through the previous loop, all places are taken, so it's a tie
        return 1;

    }

    private int getComputerMove()
    {
        int move;
        Random mRand = new Random();
        String [] field = new String [9];
        for (int i=0; i<9;i++){
            field[i] = buttons[i].getText().toString();

        }
        // First see if there's a move O can make to win
        for (int i = 0; i < 9; i++) {
            if ((!field[i].equals(HUMAN_PLAYER))  && (!field[i].equals(COMPUTER_PLAYER))) {
                String curr = field[i];
                field[i] = COMPUTER_PLAYER;
                if (this.checkForWin(field) == 3) {
                    field[i] = curr;
                    return i;
                }
                else
                    field[i] = curr;
            }
        }

        // See if there's a move O can make to block X from winning
        for (int i = 0; i < 9; i++) {
            if  ((!field[i].equals(HUMAN_PLAYER))  && (!field[i].equals(COMPUTER_PLAYER))){
                String curr = field[i];   // Save the current number
                field[i] = HUMAN_PLAYER;
                if (this.checkForWin(field) == 2) {
                    field[i] = curr;
                    return i;
                }
                else
                    field[i] = curr;
            }
        }

        // Generate random move
        do
        {
            move = mRand.nextInt(9);
        } while (field[move].equals(HUMAN_PLAYER) || field[move].equals(COMPUTER_PLAYER));

        return move;
    }


    private void player1Wins(){
        player1Points++;
        Toast.makeText(this,"You Won!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void player2Wins(){
        player2Points++;
        Toast.makeText(this,"Android Won!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void draw(){
        Toast.makeText(this,"It's a tie!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText(){
        textViewPlayer1.setText("Player: "+ player1Points);
        textViewPlayer2.setText("Android: "+ player2Points);
    }

    private void resetBoard(){
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
        }
        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
