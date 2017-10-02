package com.example.root.progressdialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ResponseCache;
import java.net.URL;

/**
 * Created by root on 10/2/17.
 */

public class DownloadTask extends AsyncTask<String, Integer, String> {
    private Context context;
    private PowerManager.WakeLock wakeLock;
    public static ProgressDialog progressDialog;


    public DownloadTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
        wakeLock.acquire(); // prevent popUp dialog of power management from appearing

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Downloading file . . .");
        progressDialog.setMessage("Downloading in progress");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... Urls){
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(Urls[0]);
            connection = (HttpURLConnection)url.openConnection();
            connection.connect();
            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                return "Server returned" + connection.getResponseCode() + " " + connection.getResponseMessage();
            int fileLength = connection.getContentLength();
            String[] splitedUrl = Urls[0].split("/");
            input = connection.getInputStream();
            output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + splitedUrl[splitedUrl.length - 1]);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1){
                if(isCancelled()){
                    input.close();
                    return null;
                }
                total += count;
                if(fileLength > 0)  // only if total is known
                    publishProgress((int)total * 100 / fileLength);
                output.write(data, 0, count);
            }
        } catch (Exception e){
            return e.toString();
        }
        finally {
                try {
                    if(output != null)
                        output.close();
                    if(input != null)
                        input.close();
                    if(connection != null)
                        connection.disconnect();
                } catch (Exception e) {
                    return e.toString();
                }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        wakeLock.release();
        progressDialog.dismiss();
        if (result != null)
            Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // if we get here, length is known, now set indeterminate to false
        //progressDialog.setIndeterminate(false);

        progressDialog.setProgress(progress[0]);
    }

}
