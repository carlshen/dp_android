package com.example.xiang.commandapplication;

import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawCanvas mCanvas;
    private DrawPath mPath;
    private Paint mPaint;
    private IBrush mBrush;
    private Button btnRedo, btnUndo;
    private Button btnNormal, btnCircle;
    private Button btnRed, btnBlue, btnGreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPaint = new Paint();
        mPaint.setColor(0xffffff);
        mPaint.setStrokeWidth(3);

        mBrush = new NormalBrush();
        mCanvas = (DrawCanvas) findViewById(R.id.ac_draw_canvas);
        mCanvas.setOnTouchListener(new DrawTouchListenser());

        btnRed = (Button) findViewById(R.id.ac_draw_color_red_btn);
        btnRed.setOnClickListener(this);
        btnGreen = (Button) findViewById(R.id.ac_draw_color_green_btn);
        btnGreen.setOnClickListener(this);
        btnBlue = (Button) findViewById(R.id.ac_draw_color_blue_btn);
        btnBlue.setOnClickListener(this);


        btnNormal = (Button) findViewById(R.id.ac_draw_brush_normal_btn);
        btnNormal.setOnClickListener(this);
        btnCircle = (Button) findViewById(R.id.ac_draw_brush_circle_btn);
        btnCircle.setOnClickListener(this);

        btnRedo = (Button) findViewById(R.id.ac_draw_operate_redo_btn);
        btnRedo.setEnabled(false);
        btnRedo.setOnClickListener(this);
        btnUndo = (Button) findViewById(R.id.ac_draw_operate_undo_btn);
        btnUndo.setEnabled(false);
        btnUndo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ac_draw_color_red_btn:
                mPaint = new Paint();
                mPaint.setStrokeWidth(3);
                mPaint.setColor(0xFFFF0000);
                break;
            case R.id.ac_draw_color_green_btn:
                mPaint = new Paint();
                mPaint.setStrokeWidth(3);
                mPaint.setColor(0xFF00FF00);
                break;
            case R.id.ac_draw_color_blue_btn:
                mPaint = new Paint();
                mPaint.setStrokeWidth(3);
                mPaint.setColor(0xFF0000FF);
                break;
            case R.id.ac_draw_operate_undo_btn:
                mCanvas.undo();
                if (!mCanvas.canUndo()) {
                    btnUndo.setEnabled(false);
                }
                btnRedo.setEnabled(true);
                break;
            case R.id.ac_draw_operate_redo_btn:
                mCanvas.redo();
                if (!mCanvas.canRedo()) {
                    btnRedo.setEnabled(false);
                }
                btnUndo.setEnabled(true);
                break;
            case R.id.ac_draw_brush_normal_btn:
                mBrush = new NormalBrush();
                break;
            case R.id.ac_draw_brush_circle_btn:
                mBrush = new CircleBrush();
                break;
        }
    }

    private class DrawTouchListenser implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mPath = new DrawPath();
                mPath.paint = mPaint;
                mPath.path = new Path();
                mBrush.down(mPath.path, event.getX(), event.getY());
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                mBrush.move(mPath.path, event.getX(), event.getY());
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                mBrush.up(mPath.path, event.getX(), event.getY());
                mCanvas.add(mPath);
                mCanvas.isDrawing = true;
                btnUndo.setEnabled(true);
                btnRedo.setEnabled(false);
            }
            return true;
        }
    }
}
