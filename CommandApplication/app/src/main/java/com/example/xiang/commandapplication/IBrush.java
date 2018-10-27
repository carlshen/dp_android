package com.example.xiang.commandapplication;

import android.graphics.Path;

/**
 * Created by xiang on 2018/10/20.
 */

public interface IBrush {
    void down(Path path, float x, float y);
    void move(Path path, float x, float y);
    void up(Path path, float x, float y);
}
