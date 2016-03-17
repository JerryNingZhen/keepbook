package com.joey.keepbook.listener;

import android.content.Intent;

/**
 * Created by Joey on 2016/3/10.
 */
public interface ActivityListener {
    void onReceiveActivityResult(int requestCode, int resultCode, Intent data);
}
