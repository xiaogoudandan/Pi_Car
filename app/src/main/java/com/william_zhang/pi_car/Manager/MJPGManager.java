package com.william_zhang.pi_car.Manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by william_zhang on 2018/2/23.
 */

public class MJPGManager implements Runnable {
    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private String url = "";
    private URL vedioUrl;
    private HttpURLConnection connection;
    private Bitmap bitmap;
    private Canvas canvas;
    private Thread thread;

    public MJPGManager(SurfaceView surfaceView) {
        this.surfaceView = surfaceView;
        holder = surfaceView.getHolder();
        thread = new Thread(this);
    }

    public void start(String url) {
        this.url = url;
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                thread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
    }

    @Override
    public void run() {
        while (true) {
            try {

                InputStream inputStream = null;
                vedioUrl = new URL(url);
                connection = (HttpURLConnection) vedioUrl.openConnection();
                connection.setDoInput(true);
                connection.connect();
                inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                canvas = holder.lockCanvas();
                RectF rectF = new RectF(0, 0, surfaceView.getWidth(), surfaceView.getHeight());
                canvas.drawBitmap(bitmap, null, rectF, null);
                holder.unlockCanvasAndPost(canvas);
                connection.disconnect();
            } catch (Exception e) {

            }
        }
    }
}
