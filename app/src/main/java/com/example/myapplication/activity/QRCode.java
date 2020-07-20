package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.util.Constants;
import com.example.myapplication.util.SharedPrefManager;

import org.json.JSONException;

import java.util.Objects;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QRCode extends AppCompatActivity {
    @BindView(R.id.imageView) ImageView imageQR;
    @BindView(R.id.toolbar_back) TextView toolbar_back;
    @BindView(R.id.toolbar_title) TextView toolbar_title;
    @BindView(R.id.introQr) TextView introQr;
    @BindView(R.id.eventQr) TextView eventQr;


    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    String qrString;
    String eventName, eventId, sessionId,sessionName;

    Context mContext;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        ButterKnife.bind(this);
        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);

        Intent myIntent = getIntent();
        eventId = myIntent.getStringExtra(Constants.KEY_EVENTID);
        eventName = myIntent.getStringExtra(Constants.KEY_EVENTNAME);
        sessionId = myIntent.getStringExtra(Constants.KEY_SESSIONID);
        sessionName = myIntent.getStringExtra(Constants.KEY_SESSIONNAME);

        toolbar_title.setText("Qr code");
        eventQr.setText(eventName);
        introQr.setText("The qr code to verify attendances in session "+ sessionName);
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        qrString = eventId + sessionId;
        if (qrString.length() > 0) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(
                    qrString, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            qrgEncoder.setColorBlack(Color.BLACK);
            qrgEncoder.setColorWhite(Color.WHITE);
            try {
                bitmap = qrgEncoder.getBitmap();
                imageQR.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
