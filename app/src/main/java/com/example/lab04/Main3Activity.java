package com.example.lab04;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    private Button btnSendData;
    private TextView txtStudent;
    String dane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btnSendData = (Button) findViewById(R.id.btnSendData);
        txtStudent = (TextView) findViewById(R.id.txtStudent);
        Intent myIntent = getIntent();
        Bundle bundle = myIntent.getExtras();
        final String indeks = bundle.getString("indeks");
//    Toast.makeText(getApplicationContext(),indeks,Toast.LENGTH_LONG).show();
        if (indeks.equals("123123")){
            dane = "Jan Kowalski";
        }else if (indeks.equals("235196")){
            dane = "Wojciech Nowak";
        }else {
            dane= "Unknow student";
        }
        txtStudent.setText(dane);

        bundle.putString("dane", dane);
        bundle.putString("indeks", indeks);
        myIntent.putExtras(bundle);

        setResult(Activity.RESULT_OK,myIntent);
        btnSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
}
