package com.example.lab04;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton minBright,maxBright, manualSet;
    private SeekBar Brightness;
    private int brightness;
    private EditText edtNum, edtBody,edtFind;
    private Button btnSend,btnSendEmail, btnFindById;
    private TextView txtData;
    boolean success = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        minBright = (RadioButton) findViewById(R.id.checkMinBright);
        maxBright = (RadioButton) findViewById(R.id.checkMaxBright);
        manualSet = (RadioButton) findViewById(R.id.checkSet);
        Brightness = (SeekBar) findViewById(R.id.seekBarBrigthness);
        edtBody  = (EditText) findViewById(R.id.smsBody);
        edtNum = (EditText) findViewById(R.id.edtNumber);
        btnSend = (Button) findViewById(R.id.btnSendSMS);
        btnSendEmail= (Button) findViewById(R.id.btnSendEmail);
        btnFindById = (Button) findViewById(R.id.btnFind);
        edtFind = (EditText) findViewById(R.id.EdtID);
        txtData  = (TextView) findViewById(R.id.TXTData) ;
        Brightness.setMax(255);
        Brightness.setProgress(GetBrightness());
        GetPermisson();
        Brightness.setEnabled(false);



            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (radioGroup.getCheckedRadioButtonId()){
                        case R.id.checkMinBright:
                            SetBrightness(0);
                            Brightness.setEnabled(false);
                            break;
                        case R.id.checkMaxBright:
                            Brightness.setEnabled(false);
                            SetBrightness(255);
                            break;
                        case R.id.checkSet:
                            Brightness.setEnabled(true);
                            Brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                @Override
                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                    if (fromUser && success){
                                        SetBrightness(progress);
                                    }
                                }

                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {

                                }

                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                }
                            });
                            break;
                        default:
                            break;
                    }
                }
            });

            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendingSMS = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+edtNum.getText().toString()));
                    sendingSMS.putExtra("sms_body",edtBody.getText().toString());
                    startActivity(sendingSMS);
                }
            });

            btnSendEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendingEmail = new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(sendingEmail);
                }
            });


            btnFindById.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,Main3Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("indeks",edtFind.getText().toString());
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 301);
                }
            });








    }
    private void SetBrightness (int brightness){
        if (brightness<0){
            brightness = 0;
        }else if (brightness >255){
            brightness = 255;
        }
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        Settings.System.putInt(contentResolver,Settings.System.SCREEN_BRIGHTNESS,brightness);
    }

    private int GetBrightness(){
        int brightness = 100;
        try {
            ContentResolver contentResolver = getApplicationContext().getContentResolver();
            brightness = Settings.System.getInt(contentResolver,Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return brightness;
    }

    private void GetPermisson(){
        boolean value;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            value = Settings.System.canWrite(getApplicationContext());
            if (value){
                success = true;
            }else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:"+getApplicationContext().getPackageName()));
                startActivityForResult(intent,1000);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000){
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                boolean value = Settings.System.canWrite(getApplicationContext());
                if (value){
                    success = true;
                } else{
                    Toast.makeText(getApplicationContext(),"Permission not granted!", Toast.LENGTH_LONG).show();
                }
            }
        }else if (requestCode == 301){
            Bundle mybundle = data.getExtras();
            String result = mybundle.getString("dane");
            String id  = mybundle.getString("indeks");
            String str = "Student with id: "+id+" is: "+result;
            txtData.setText(str);

        }
    }
}
