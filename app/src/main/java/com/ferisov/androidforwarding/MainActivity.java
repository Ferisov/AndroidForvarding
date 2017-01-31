package com.ferisov.androidforwarding;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.app.PendingIntent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG = "myLogs";
    private static final String FILENAME = "FileSD";
    private static final String DIR_SD = "MyFile";
    Button btnSend;

    EditText txtMessage;
    File sdPath;
    File sdFile;
    BufferedReader br;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //btnRead = (Button) findViewById(R.id.btnRead);
        btnSend = (Button) findViewById(R.id.btnSend);
        txtMessage = (EditText) findViewById(R.id.editText);
        btnSend.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {

                String massage = txtMessage.getText().toString();
                sendSms(massage);

    }



    public void sendSms(String message) {

      try {
           //Получаем доступ к SDcard

            sdPath = Environment.getExternalStorageDirectory();
          //Получаем доступ к папке на SDcard.
            sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
            sdFile = new File(sdPath, FILENAME);
           // открываем поток для чтения
            br = new BufferedReader(new FileReader(sdFile));
            String str = "";
            // читаем содержимое
            while ((str =br.readLine()) != null) {
                PendingIntent pi= PendingIntent.getActivity(this,0,new Intent(this, MainActivity.class),0);
                SmsManager sms= SmsManager.getDefault();
                sms.sendTextMessage(str, null, message, pi, null);

            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
