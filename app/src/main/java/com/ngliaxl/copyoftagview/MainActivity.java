package com.ngliaxl.copyoftagview;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final PictureTagLayout layout = findViewById(R.id.tag_layout);


        findViewById(R.id.clear_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.removeTagView(layout.getValidTagViewList().size());
            }
        });
        findViewById(R.id.clear_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.removeAllTagViews();
            }
        });
    }
}
