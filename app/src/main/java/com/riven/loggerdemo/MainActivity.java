package com.riven.loggerdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import static com.riven.loggerdemo.FileUtil.readOriginalData;

public class MainActivity extends AppCompatActivity {

    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 000);
            }
        }

        show = (TextView) findViewById(R.id.tv_show);

        findViewById(R.id.btn_create_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileUtil.createFile();
            }
        });

        findViewById(R.id.btn_write_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileUtil.write("test1111111111111111");
                FileUtil.write("test2         test2   ");
                FileUtil.write("   test3         ");
                FileUtil.write("  test4------------");
            }
        });

        findViewById(R.id.btn_read_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = "";
                for (String str : readOriginalData()) {
                    s = s + str + "\n";
                }
                show.setText(s);
            }
        });

        findViewById(R.id.btn_delete_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileUtil.deleteFile();
            }
        });
    }
}
