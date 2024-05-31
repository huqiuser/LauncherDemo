package com.huqi.launcherdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("LauncherActivity");
        setContentView(textView);
        textView.setOnClickListener(v -> {
            Intent intent = new Intent(this, LauncherActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
