package com.example.root.progressdialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static Activity myActivity;
    //ProgressBar progressBar;
    //TextView visualProgress;
    EditText url;
    DownloadTask downloadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myActivity = this;
        Toast.makeText(myActivity, Environment.getExternalStorageDirectory().toString(), Toast.LENGTH_LONG).show();
    }

    public void buRun(View view) {
        downloadTask = new DownloadTask(myActivity);
        url = (EditText)findViewById(R.id.editTextUrl);
        downloadTask.execute(url.getText().toString());
        /*
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setIndeterminate(false);
        progressBar.setProgress(0);
        progressBar.setMax(40);
        progressBar.setVisibility(View.VISIBLE);
        visualProgress = (TextView)findViewById(R.id.textViewProgress);
        */


        myThread thread = new myThread();
        thread.start();

    }
}
