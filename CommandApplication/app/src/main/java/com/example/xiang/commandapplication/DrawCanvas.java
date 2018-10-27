package com.example.xiang.commandapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by xiang on 2018/10/20.
 */

public class DrawCanvas extends SurfaceView implements SurfaceHolder.Callback {
    public boolean isDrawing = false, isRunning = false;
    private Bitmap mBitmap;
    private DrawInvoker mInvoker;
    private DrawThread mThread;

    public DrawCanvas(Context context, AttributeSet atrrs) {
        super(context, atrrs);

        mInvoker = new DrawInvoker();
        mThread = new DrawThread();
        getHolder().addCallback(this);
    }

    public void add(DrawPath path) {
        mInvoker.add(path);
    }

    public void redo() {
        isDrawing = true;
        mInvoker.redo();
    }

    public void undo() {
        isDrawing = true;
        mInvoker.undo();
    }

    public boolean canUndo() {
        return mInvoker.canUndo();
    }

    public boolean canRedo() {
        return mInvoker.canRedo();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isRunning = true;
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        isRunning = false;
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private class DrawThread extends Thread {
        @Override
        public void run() {
            Canvas canvas = null;
            while (isRunning) {
                if (isDrawing) {
                    try {
                        canvas = getHolder().lockCanvas(null);
                        if (mBitmap == null) {
                            mBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
                        }
                        Canvas c = new Canvas(mBitmap);
                        c.drawColor(0, PorterDuff.Mode.CLEAR);

                        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                        mInvoker.execute(c);
                        canvas.drawBitmap(mBitmap, 0, 0, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        getHolder().unlockCanvasAndPost(canvas);
                    }
                    isDrawing = false;
                }
            }
        }
    }

}
