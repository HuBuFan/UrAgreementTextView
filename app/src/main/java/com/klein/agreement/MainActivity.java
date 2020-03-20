package com.klein.agreement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.hubufan.agreement.OnAgreementClickListener;
import com.hubufan.agreement.UrAgreementTextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UrAgreementTextView mTvAgreement = findViewById(R.id.tv_agreement);
        mTvAgreement.setAgreementClickListener(new OnAgreementClickListener() {
            @Override
            public void clickListener(String tag, String clickText, boolean isCheck) {
                Toast.makeText(MainActivity.this, clickText + isCheck, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
