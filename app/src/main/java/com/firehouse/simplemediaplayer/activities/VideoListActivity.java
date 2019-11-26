package com.firehouse.simplemediaplayer.activities;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.ProgressDialog;
import android.content.pm.PackageManager;

import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import com.firehouse.simplemediaplayer.R;
import com.firehouse.simplemediaplayer.adapter.VideoAdapter;

import java.io.File;
import java.util.ArrayList;


public class VideoListActivity extends AppCompatActivity {

    RecyclerView rvVideoList;
    VideoAdapter videoAdapter;
    public static int REQUEST_PERMESSION_CODE = 1;
    File directory;
    boolean permission;
    public static ArrayList<File> fileArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        rvVideoList = findViewById(R.id.rvVideoList);
        directory = new File("/mnt");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvVideoList.setLayoutManager(gridLayoutManager);
        permissionForVideo();
    }

    private void permissionForVideo() {
        if (ContextCompat.checkSelfPermission(VideoListActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(VideoListActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(VideoListActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        } else {
            permission = true;
            getFile(directory);
            videoAdapter = new VideoAdapter(this, fileArrayList);
            rvVideoList.setAdapter(videoAdapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMESSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permission = true;
                getFile(directory);
                videoAdapter = new VideoAdapter(getApplicationContext(), fileArrayList);
                rvVideoList.setAdapter(videoAdapter);
            } else
                Toast.makeText(this, "Please Allow permission", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<File> getFile(File directory) {
        File listFile[] = directory.listFiles();
        try {


        if (listFile != null && listFile.length > 0) {

            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    getFile(listFile[i]);
                } else {
                    permission = false;
                    String ext[] = {".3g2", ".3gp", ".asf", ".asx", ".avi", ".flv",
                            ".m2ts", ".mkv", ".mov", ".mp4", ".mpg", ".mpeg",
                            ".rm", ".swf", ".vob", ".wmv"};

                    String extension = listFile[i].getAbsolutePath().substring(listFile[i].getAbsolutePath().lastIndexOf("."));



//                    Log.i("extension",extension);
                    for (int k = 0; k < ext.length; k++) {
                        if (extension.equalsIgnoreCase(ext[k])) {
                            fileArrayList.add(listFile[i]);
                            Log.i("extension",ext[k]);
                            break;
                        }
                    }


                }
            }
        }
        }catch(Exception ex){

        }
        return fileArrayList;

    }

}
