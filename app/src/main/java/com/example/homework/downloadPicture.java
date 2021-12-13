package com.example.homework;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;

public class downloadPicture {
        mDownloadUtil = new DownloadUtil();
        mDownloadUtil.downloadFile(PICTURE_URL, new DownloadListener() {
            @Override
            public void onStart() {
                Log.e(TAG, "onStart: ");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fl_circle_progress.setVisibility(View.VISIBLE);
                    }
                });
            }
            @Override
            public void onProgress(final int currentLength) {
                Log.e(TAG, "onLoading: " + currentLength);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        circle_progress.setProgress(currentLength);
                    }
                });
            }
            @Override
            public void onFinish(final String localPath) {
                Log.e(TAG, "onFinish: " + localPath);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fl_circle_progress.setVisibility(View.GONE);
                        Glide.with(mContext).load(localPath).into(iv_picture);
                    }
                });
            }
            @Override
            public void onFailure() {
                Log.e(TAG, "onFailure: ");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fl_circle_progress.setVisibility(View.GONE);
                    }
                });
            }
        });
}
