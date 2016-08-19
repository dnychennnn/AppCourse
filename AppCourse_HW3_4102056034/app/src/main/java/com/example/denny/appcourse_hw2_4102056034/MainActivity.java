package com.example.denny.appcourse_hw2_4102056034;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextcomp, editTextyou, editTextoutcome;
    private Button btnPaper, btnSciz, btnRock;
    private ImageButton btnEnd;
    private int AI;//1=scizzors 2=rock 3=paper
    private AlertDialog.Builder end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewComponent();

        btnRock.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                editTextyou.setText(btnRock.getText());
                Toast.makeText(MainActivity.this, btnRock.getText(), Toast.LENGTH_LONG);
                AI = (int) (Math.random() * 3 + 1);
                Log.d("123", AI + "");
                switch (AI) {
                    case 1:
                        editTextcomp.setText(btnSciz.getText());
                        editTextoutcome.setText("WINNNNNNN!");
                        break;

                    case 2:
                        editTextcomp.setText(btnRock.getText());
                        editTextoutcome.setText("DRAWWWWWW!");
                        break;

                    default:
                        editTextcomp.setText(btnPaper.getText());
                        editTextoutcome.setText("LOSEEEEEE!");
                }

            }
        });
        btnPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextyou.setText(btnPaper.getText());
                Toast.makeText(MainActivity.this, btnPaper.getText(), Toast.LENGTH_LONG);
                AI = (int) (Math.random() * 3 + 1);
                Log.d("123", AI + "");
                switch (AI) {
                    case 1:
                        editTextcomp.setText(btnSciz.getText());
                        editTextoutcome.setText("LOSEEEEEE!");
                        break;
                    case 2:
                        editTextcomp.setText(btnRock.getText());
                        editTextoutcome.setText("WINNNNNNN!");
                        break;
                    case 3:
                        editTextcomp.setText(btnPaper.getText());
                        Log.d("123", editTextcomp.getText() + "");
                        editTextoutcome.setText("DRAWWWWWW!");
                }
            }
        });
        btnSciz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextyou.setText(btnSciz.getText());
                Toast.makeText(MainActivity.this, btnSciz.getText(), Toast.LENGTH_LONG);
                AI = (int) (Math.random() * 3 + 1);
                Log.d("123", AI + "");
                switch (AI) {
                    case 1:
                        editTextcomp.setText(btnSciz.getText());
                        editTextoutcome.setText("DRAWWWWWW!");
                        break;

                    case 2:
                        editTextcomp.setText(btnRock.getText());
                        editTextoutcome.setText("LOSEEEEEE!");
                        break;

                    default:
                        editTextcomp.setText(btnPaper.getText());
                        editTextoutcome.setText("WINNNNNNN!");
                }

            }
        });

        //AlertDialog關掉程式
        final DialogInterface.OnClickListener finish = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        };
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                end = new AlertDialog.Builder(MainActivity.this);
                end.setTitle("結束 終極X剪刀石頭布");
                end.setMessage("你確定要不再玩一場？");
                end.setPositiveButton("Yes", finish);
                end.setNegativeButton("No", null).show();
            }
        });
    }






    private void setupViewComponent(){
        editTextcomp = (EditText)findViewById(R.id.editText_comp);
        editTextyou = (EditText)findViewById(R.id.editText_you);
        editTextoutcome = (EditText)findViewById(R.id.editText_outcome);
        btnPaper = (Button)findViewById(R.id.button_paper);
        btnSciz = (Button)findViewById(R.id.button_scizzors);
        btnRock = (Button)findViewById(R.id.button_rock);
        btnEnd = (ImageButton)findViewById(R.id.button_end);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
