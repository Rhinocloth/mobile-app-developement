package edu.dtcc.janemone.stepcounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SetupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        Button cont = findViewById(R.id.continueBt);
        final TextView userHeight = findViewById(R.id.editText);

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userHeight.getText().toString().isEmpty())
                {
                    int height = Integer.parseInt(userHeight.getText().toString());
                    Intent intent = getIntent();
                    intent.putExtra("height", height);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else
                {
                    Toast heightToast = Toast.makeText(getApplicationContext(), "Please enter height to continue", Toast.LENGTH_LONG);
                    heightToast.show();
                }
            }
        });
    }
}
