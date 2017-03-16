package com.android.skyward.mycamera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.skyward.mycamera.camera2.CameraActivity;

public class MainActivity extends AppCompatActivity {




    private static final int REQUEST_CODE = 100;
    private int width;
    private int height;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_main);
        Button myCamera = (Button) findViewById(R.id.myCamera2);
        image = (ImageView) findViewById(R.id.image);
        //定义DisplayMetrics 对象;
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口的宽度
        int screenWidth = dm.widthPixels;
        //窗口高度
        int screenHeight = dm.heightPixels;

        width = (int) (screenWidth * 0.8);//获取屏幕的百分之八的比例宽度(宽和高是根据业务需求可以自己自行设定的)
        height = (int) (screenHeight * 0.7);//获取屏幕的百分之7的比例高度
        //Log.i("myLog","width "+ width);
        //Log.i("myLog","height "+ height);

        myCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), CameraActivity.class);
                intent2.putExtra("width",width);
                intent2.putExtra("height",height);
                intent2.putExtra("url","");//图片上传的url
                intent2.putExtra("angle",0);//0度为正拍，90为横拍（其实这个角度是显示图片裁剪后，那个取消或者上传按钮的方向不一样而已）
                intent2.putExtra("type","0");//这个类型是根据业务需求而用到的，可以忽略
                startActivityForResult(intent2,1000);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(requestCode ==1000 && resultCode ==1001){
                image.setImageURI(data.getData());
            }
        }
    }
}
