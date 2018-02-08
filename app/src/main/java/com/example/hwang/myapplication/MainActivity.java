package com.example.hwang.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by hwang on 2018-01-27.
 */

public class MainActivity extends Activity {
    private TextView textView_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        textView_id = (TextView) findViewById(R.id.textView_id);
        textView_id.setText(id);
    }
}
