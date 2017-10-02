package com.example.root.progressdialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Activity myActivity;
    ProgressDialog progressDialog;
    //ProgressBar progressBar;
    //TextView visualProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myActivity = this;

    }

    public void buRun(View view) {
        /*
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setIndeterminate(false);
        progressBar.setProgress(0);
        progressBar.setMax(40);
        progressBar.setVisibility(View.VISIBLE);
        visualProgress = (TextView)findViewById(R.id.textViewProgress);
        */
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Downloading file . . .");
        progressDialog.setMessage("Downloading in progress");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.setMax(40);
        progressDialog.show();

        myThread thread = new myThread();
        thread.start();

    }

    class myThread extends Thread{

        int progress = 0;
        @Override
        public void run(){
            while (progressDialog.getProgress() < 40){
                progress += 2 ;
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //progressDialog.incrementProgressBy(progress);
                        //Log.v("****progressbar", progressDialog.getProgress()+"");
                        progressDialog.setProgress(progress);
                        //visualProgress.setText(progress+"/"+progressBar.getMax());
                        //progressDialog.setProgress(progress);

                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            myActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    //progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

}
