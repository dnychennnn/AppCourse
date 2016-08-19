package com.example.denny.appcourse_4102056034_midterm_;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText input;
    private TextView result;
    private Button resultButton;
    int answer1=0, answer2=0, answer3=0, answer4=0;
    char[] answer;
    int max = 9999, min = 0000;
    Random randNum;
    char[] ans;
    final Context context = this;
    int count;
    int countA = 0;
    int countB = 0;
    String[] input_record, result_record;
    String game_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();

        startgame();

        }



    public void setup(){
        input = (EditText)findViewById(R.id.editText_input);
        result = (TextView)findViewById(R.id.textView_result);
        resultButton = (Button)findViewById(R.id.button_go);
    }

    public void startgame()
    {
        //設定答案
        randNum = new Random();
        answer1 = randNum.nextInt((max - min) + 1) + min;
        answer = String.format("%04d", answer1).toCharArray();
        input_record = new String[100];
        result_record = new String[100];
        count =0;
        game_record+= "\n"+ "#" + " | " + "your game" + " | " + "Hint" + "\n";

            resultButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //answer = String.format("%04d", randNum.nextInt((max-min)+1) + min);
                    if (input.getText().toString().length() != 4) {
                        Toast.makeText(getApplicationContext(), "資料長度不正確", Toast.LENGTH_SHORT).show();
                    } else {
                        count++;
                        countA = 0;
                        countB = 0;

                        ans = input.getText().toString().toCharArray();
                        input_record[count] = input.getText().toString();

                        for (int j = 0; j < 4; j++) {
                            for (int i = 0; i < 4; i++) {
                                if (ans[j] == answer[i]) {
                                    if (i == j) {
                                        countA++;
                                    } else {
                                        countB++;
                                    }
                                }
                            }
                        }
                        result_record[count] = countA + "A" + countB + "B";


                        Toast.makeText(getApplicationContext(), countA + "A" + countB + "B", Toast.LENGTH_SHORT).show();
                        game_record += count + " | " + input_record[count] + " | " + result_record[count] + "\n";

                        result.setText(game_record);

                        if ((countA == 2 && countB == 2)) {
                            new AlertDialog.Builder(context)
                                    .setMessage("答案" + answer1 + "\n" +
                                            "次數" + count + "\n" +
                                            "結果" + "挑戰成功")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            result.setText("");
                                        }
                                    }).show();
                        } else if (count >= 10) {
                            new AlertDialog.Builder(context)
                                    .setMessage("答案" + answer1 + "\n" +
                                            "次數" + 10 + "\n" +
                                            "結果" + "挑戰失敗")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            result.setText("");
                                            startgame();
                                        }
                                    }).show();
                        }
                    }
                }
            });
        }
    }


