package com.example.xiang.commandapplication;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by xiang on 2018/10/20.
 */

public class DrawPath implements IDraw {
    public Path path;
    public Paint paint;

    public DrawPath() {
    }

    public DrawPath(Path pth, Paint pt) {
        path = pth;
        paint = pt;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public void undo() {

    }
}
