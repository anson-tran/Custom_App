package com.trananson.custom_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    Button b1, b2, b3, b4;
    public Quiz quiz = new Quiz();
    TextView questionView, infoView;
    String[] questions = {"What's your name?", "What's my name?", "Who has really long arms?", "Which one has a criminal record?"};
    String[][] options = {{"Ted", "Martha", "Benny", "Tony"},{"Ted", "Martha", "Benny", "Tony"}, {"Ted", "Martha", "Benny", "Tony"}, {"Ted", "Martha", "Benny", "Tony"}};
    String[] answers = {"Ted", "Benny", "Tony", "Martha"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();
        int questionNumber = quiz.getNumber();
        questionView = findViewById(R.id.questionView);
        questionView.setText(questions[questionNumber]);
        infoView = findViewById(R.id.infoView);
        String infoText = "Question Number " + questionNumber + ", Score: " + quiz.getScore();
        infoView.setText(infoText);
        b1 =findViewById(R.id.button1);
        b2 =findViewById(R.id.button2);
        b3 =findViewById(R.id.button3);
        b4 =findViewById(R.id.button4);
        Button[] buttons = {b1, b2, b3, b4};
        for(int i = 0; i < buttons.length; i++)
        {
            buttons[i].setText(options[questionNumber][i]);
            if(buttons[i].getText() == answers[questionNumber]){
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        quiz.setScore(quiz.getScore()+1);
                        quiz.setNumber(quiz.getNumber() + 1);
                        nextQuestion();
                    }
                });
            }
            else{
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        quiz.setNumber(quiz.getNumber() + 1);
                        nextQuestion();
                    }
                });
            }
        }
    }

    protected void nextQuestion(){
        int questionNumber = quiz.getNumber();
        if(questionNumber == questions.length - 1){
            endQuiz();
        }
        else {
            questionView.setText(questions[questionNumber]);
            String infoText = "Question Number " + questionNumber + ", Score: " + quiz.getScore();
            infoView.setText(infoText);
            Button[] buttons = {b1, b2, b3, b4};
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setText(options[questionNumber][i]);
                if (buttons[i].getText() == answers[questionNumber]) {
                    buttons[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quiz.setScore(quiz.getScore() + 1);
                            quiz.setNumber(quiz.getNumber() + 1);
                            nextQuestion();
                        }
                    });
                } else {
                    buttons[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quiz.setNumber(quiz.getNumber() + 1);
                            nextQuestion();
                        }
                    });
                }
            }
        }
    }

    protected void endQuiz(){
        int questionNumber = quiz.getNumber();
        String infoText = "Question Number " + questionNumber + ", Score: " + quiz.getScore();
        infoView.setText(infoText);
        Button[] buttons = {b1, b2, b3, b4};
        for(Button button: buttons){
            button.setVisibility(View.INVISIBLE);
        }
        int score = quiz.getScore();
        String questionText = "Thank you for taking our quiz! You had a final score of " + score + ".";
        if(quiz.getScore() > sharedPreferences.getInt("highScore", 0)){
            editor.putInt("highScore", score);
            questionText = questionText + " You've set a new high score!";
        }
        questionView.setText(questionText);

    }
}

class Quiz{
    private int score, number;

    public Quiz(){
        score = 0;
        number = 0;
    }

    public int getScore(){
        return score;
    }
    public int getNumber(){
        return number;
    }

    public void setScore(int s){
        score  = s;
    }
    public void setNumber(int n){
        number = n;
    }
        }