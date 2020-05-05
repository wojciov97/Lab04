package com.example.lab04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    private EditText edtAdress, edtSubject, edtContent;
    private Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnSend = (Button) findViewById(R.id.btnSend);
        edtAdress = (EditText) findViewById(R.id.edtAdress);
        edtSubject = (EditText) findViewById(R.id.edtSubject);
        edtContent = (EditText) findViewById(R.id.edtText);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] TO = {edtAdress.getText().toString()};
                Intent sendingEmail  = new Intent(Intent.ACTION_SEND);
                sendingEmail.setData(Uri.parse("mailto:"));
                sendingEmail.setType("text/plain");
                sendingEmail.putExtra(Intent.EXTRA_EMAIL, TO);
                sendingEmail.putExtra(Intent.EXTRA_SUBJECT,edtSubject.getText().toString());
                sendingEmail.putExtra(Intent.EXTRA_TEXT,edtContent.getText().toString());
                startActivity(sendingEmail);
            }
        });


    }
}
