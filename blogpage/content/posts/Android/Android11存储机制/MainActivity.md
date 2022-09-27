````
        package com.maniu.sandboxmaniu;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission(this);
    }
    public static boolean checkPermission(
            Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

        }
        return false;
    }
    //    沙箱 影响外置卡   公共目录   沙箱模式
    public void create(View view) {
        File file = new File(Environment.getExternalStorageState(), "demo.txt");
        Log.i("david", "create: "+file.exists());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //external.db  没有办法创建文件
//   文件 文件夹
    public void createSAF(View view) {
        Uri uri =  MediaStore.Files.getContentUri("external");
        ContentResolver contentResolver = getContentResolver();
        ContentValues contentValues = new ContentValues();
        String path = Environment.DIRECTORY_DOCUMENTS + "/Maniu";
//        relative_path      Download/Maniu
        contentValues.put(MediaStore.Downloads.RELATIVE_PATH,path);
        contentValues.put(MediaStore.Downloads.DISPLAY_NAME, path);
        contentValues.put(MediaStore.Downloads.TITLE, path);
        Uri resultUri =contentResolver.insert(uri, contentValues);
        if (resultUri != null) {
            Toast.makeText(this, "添加文件成功", Toast.LENGTH_SHORT).show();
        }
    }

    Uri imageUri;
    public void insertImage(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.die);
        String displayName =  System.currentTimeMillis()+".jpg";
        String mimeType = "image/jpeg";
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, displayName);
        contentValues.put(MediaStore.Images.ImageColumns.MIME_TYPE, mimeType);
        contentValues.put(MediaStore.Images.ImageColumns.
                RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/David/");

        imageUri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        OutputStream outputStream = null;
        try {
            outputStream = getContentResolver().openOutputStream(imageUri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "添加图片成功", Toast.LENGTH_SHORT).show();
    }
    //数据 Android
    public void query(View view) {
//              图片名字   uri
        Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Images.Media.DISPLAY_NAME + "=?";
        String[] arg = new String[]{"修改后的图片.jpg"};
        Cursor cursor =getContentResolver().query(external, null, selection, arg, null);
        if (cursor != null && cursor.moveToFirst()) {
            Uri queryUri = ContentUris.withAppendedId(external, cursor.getLong(0));
            Toast.makeText(this, "查询成功"+queryUri, Toast.LENGTH_SHORT).show();
            cursor.close();
        }
    }
    //通过名字----》uri  ----》 修改    uri是最重要的
    public void updateImage(View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, "码牛学院.jpg" );
        getContentResolver().update(imageUri, contentValues, null, null);
    }


    public void deleteImage(View view) {
        int row=getContentResolver().delete(imageUri, null, null);
        if (row > 0) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        }
    }

    public void downFile(View view) {
        Toast.makeText(this, "开始下载...", Toast.LENGTH_SHORT).show();

        new Thread() {
            @Override
            public void run() {

                try {
                    URL url = new URL("https://down.qq.com/qqweb/QQ_1/android_apk/" +
                            "Android_8.3.3.4515_537063791.apk");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream inputStream = connection.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(inputStream);
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.MediaColumns.DISPLAY_NAME, "test_qq.apk");
                    values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/QQ");
                    final Uri uri=getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);

                    OutputStream outputStream = getContentResolver().openOutputStream(uri);
                    BufferedOutputStream bos =new  BufferedOutputStream(outputStream);
                    byte[] buffer = new byte[1024] ;
                    int bytes = bis.read(buffer);
                    while (bytes >= 0) {
                        bos.write(buffer, 0, bytes);
                        bos.flush();
                        bytes = bis.read(buffer);
                        bos.close();

                    }
                    bos.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            intent.setDataAndType(uri, "application/vnd.android.package-archive");
                            startActivity(intent);
                        }
                    });
                    bis.close();
                } catch (Exception e) {
                }
            }
        }.start();
    }
}
````