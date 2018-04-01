package com.jackchen.essayjoke_day03;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hc.baselibrary.ioc.ExceptionCrashHandler;
import com.hc.baselibrary.ioc.ioc.OnClick;
import com.hc.baselibrary.ioc.ioc.ViewById;
import com.hc.framelibrary.ioc.BaseSkinActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends BaseSkinActivity {


    @ViewById(R.id.btn_test)
    Button btn_test ;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this , "测试 -> " + 2/1 , Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void initData() {


        // 初始化数据
//        int i = 2/0 ;    // TAG: 报异常了

        // 在每次程序一启动时，都在MainActivity中获取上次的异常崩溃信息的文件，上传至服务器
//        // 这个就是崩溃的文件，如果不为空，就上传至服务器
//        File crashFile = ExceptionCrashHandler.getInstance().getCrashFile();
//        if (crashFile != null){
//            try {
//                InputStreamReader fileReader = new InputStreamReader(new FileInputStream(crashFile)) ;
//
//                char[] buffer = new char[1024] ;
//                int len = 0 ;
//                while ((len = fileReader.read(buffer)) != -1){
//                    String str = new String(buffer , 0 , len) ;
//                    Log.e("TAG" , str) ;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }


        // 每次应用启动的时候  请求接口获取服务器中的差分包 fix.apatch  然后修复本地bug
        // 这里用于测试，就直接在本地获取crashFile，如果是真正的项目开发中，是请求接口来获取fix.apatch
        // 如果fix.apatch存在，就去修复，否则就不能修复
        File fixFile = new File(Environment.getExternalStorageDirectory() , "fix.apatch") ;
        if (fixFile != null){
            // 说明有bug，这个时候就去修复bug，立马就会生效，不需要重启，修复成功后就直接执行2/1的方法，就不会执行2/0的方法
            try {
                BaseApplication.mPatchManager.addPatch(fixFile.getAbsolutePath());
                Toast.makeText(MainActivity.this , "修复成功" , Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this , "修复失败" , Toast.LENGTH_SHORT).show();
            }
        }

    }
}
