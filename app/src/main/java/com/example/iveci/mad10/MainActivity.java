package com.example.iveci.mad10;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    MyCanvas myCanvas;
    CheckBox c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("MY CANVAS.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myCanvas = (MyCanvas) findViewById(R.id.canv);
        c = (CheckBox) findViewById(R.id.stamp);
        c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myCanvas.setStamp(isChecked);
            }
        });
        int permissioninfo = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissioninfo == PackageManager.PERMISSION_GRANTED) {
            File file = new File(getFilesDir() + "canvas");
            if (!file.exists()) {
                file.mkdir();
                String msg = "디렉터리 생성";
                if (file.isDirectory() == false) msg = "디렉터리 생성 오류";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Toast.makeText(getApplicationContext(),
                        "SDCard 쓰기 권한이 필요합니다. \n" + "설정에서 수동으로 활성화해주세요.",Toast.LENGTH_SHORT).show();
            }
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"Blurring");
        menu.findItem(1)
                .setCheckable(true);
        menu.add(0,2,1,"Coloring");
        menu.findItem(2)
                .setCheckable(true);
        menu.add(0,3,2,"Pen Width Big");
        menu.findItem(3)
                .setCheckable(true);
        menu.add(0,4,3,"Pen Color BLUE");
        menu.add(0,5,4,"Pen Color GREEN");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == 1){
            if (item.isChecked()){
                myCanvas.setOptType("BLUR");
            }
            else{
                myCanvas.setOptType("");
            }
            item.setChecked(!item.isChecked());
        }
        else if(item.getItemId() == 2){
            if (item.isChecked()){
                myCanvas.setOptType("COLOR");
            }
            else{
                myCanvas.setOptType("");
            }
            item.setChecked(!item.isChecked());
        }
        else if(item.getItemId() == 3){
            if (item.isChecked()){
                myCanvas.setOptType("BIG");
            }
            else{
                myCanvas.setOptType("");
            }
            item.setChecked(!item.isChecked());
        }
        else if(item.getItemId() == 4){
            myCanvas.setOptType("RED");
        }
        else if(item.getItemId() == 5){
            myCanvas.setOptType("BLUE");
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v){
        if(v.getId() == R.id.erase){
            myCanvas.clear();
        }
        else if(v.getId() == R.id.open){

        }
        else if(v.getId() == R.id.save){
            if (myCanvas.Save(getFilesDir() + "canvas/"+
                    "canvas.jpg"))
                Toast.makeText(getApplicationContext(),
                        "저장에 성공했습니다.",Toast.LENGTH_SHORT).show();
            else Toast.makeText(getApplicationContext(),
                    "저장에 실패했습니다.",Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.rotate){
            c.setChecked(true);
            myCanvas.setStamp(true);
            myCanvas.setOptType("rotate");
        }
        else myCanvas.setOptType((String) v.getTag());
    }
}
