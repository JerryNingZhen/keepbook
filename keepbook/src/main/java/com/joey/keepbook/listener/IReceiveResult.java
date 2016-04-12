package com.joey.keepbook.listener;

import android.content.Intent;

/**
 * Created by joey on 2016/4/12.
 */
public interface IReceiveResult {
    void onReceiveResult(int requestCode, int resultCode, Intent data);
}
