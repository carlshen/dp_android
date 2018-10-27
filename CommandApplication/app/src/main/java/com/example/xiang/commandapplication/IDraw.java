package com.example.xiang.commandapplication;

import android.graphics.Canvas;

/**
 * Created by xiang on 2018/10/20.
 */

public interface IDraw {
    void draw(Canvas canvas);
    void undo();
}
