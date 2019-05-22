package com.kys.lg.a0902;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class PermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        //전화 걸기 권한 수락여부 확인
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE )
                != PackageManager.PERMISSION_GRANTED){

            setPermission();
            return;
        }
        //주소부 접그 권한 수락여부 확인
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED){

            setPermission();
            return;
        }

    }

    PermissionListener permissionListener= new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            //앱 권한 설정이 되어 있을 때
            //위에서 return 을 써서 onCreate()밑에서 아무 작동이 안됨 그래서 인텐트로 다시 들어옴.

            Intent i = new Intent(PermissionActivity.this, PermissionActivity.class);
            startActivity(i);
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            //앱 권한 설정이 안되어 있을 때

            finish();

        }
    };

    private void setPermission(){
        TedPermission.with(this).setPermissionListener(permissionListener).setDeniedMessage("이 앱에서 요구하는 두개의 권한이 있습니다.\n[설정]" +
                "->[권한]에서 해당 권한을 활성화 해 주세요").setPermissions(Manifest.permission.CALL_PHONE,Manifest.permission.READ_CONTACTS)
                .check();
    }

}
