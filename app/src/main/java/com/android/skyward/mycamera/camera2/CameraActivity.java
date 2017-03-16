/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.skyward.mycamera.camera2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;



import com.android.skyward.mycamera.R;

public class CameraActivity extends Activity implements Camera2BasicFragment.CallBackValue{
    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Intent intent = getIntent();
        int width = intent.getExtras().getInt("width");
        int height = intent.getExtras().getInt("height");
        String url = intent.getExtras().getString("url");
        int angle = intent.getExtras().getInt("angle");
        String type = intent.getExtras().getString("type");
        FragmentManager manager = getFragmentManager();
        Camera2BasicFragment fragment = new Camera2BasicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("width", width);
        bundle.putInt("height", height);
        bundle.putString("url", url);
        bundle.putInt("angle", angle);
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.commit();
    }

    //回调fragment返回的数据
    @Override
    public void SendMessageValue(Bundle bundle) {
        String filepath = bundle.getString("filepath");
        Uri uri = Uri.parse(filepath);
        Intent intent = new Intent();
        intent.setData(uri);
        setResult(1001,intent);
        finish();
        //Log.i("myLog","msg " + msg);
    }
}
