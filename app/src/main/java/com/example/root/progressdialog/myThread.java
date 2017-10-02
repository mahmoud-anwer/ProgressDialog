package com.example.root.progressdialog;

/**
 * Created by root on 10/2/17.
 */

public class myThread extends Thread {
    int progress = 0;
    @Override
    public void run(){
        while (DownloadTask.progressDialog.getProgress() < 100){
            progress += 5 ;
            MainActivity.myActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //progressDialog.incrementProgressBy(progress);
                    //Log.v("****progressbar", progressDialog.getProgress()+"");
                    DownloadTask.progressDialog.setProgress(progress);
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
        MainActivity.myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //DownloadTask.progressDialog.dismiss();
                //progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
