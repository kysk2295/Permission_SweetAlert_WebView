package com.kys.lg.a0902;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetAlertActivity extends AppCompatActivity {

    Button btn_default,btn_warning,btn_error,btn_sucess;
    SweetAlertDialog sweetAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweet_alert);

        btn_default=findViewById(R.id.btn_default);
        btn_error=findViewById(R.id.btn_error);
        btn_sucess=findViewById(R.id.btn_sucess);
        btn_warning=findViewById(R.id.btn_warning);

        btn_default.setOnClickListener(click);
        btn_warning.setOnClickListener(click);
        btn_error.setOnClickListener(click);
        btn_sucess.setOnClickListener(click);


    }
    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.btn_default:
                    //사용자가 정의한 이미지로 만듬
                    sweetAlertDialog= new SweetAlertDialog(SweetAlertActivity.this,SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                    sweetAlertDialog.setTitleText("제목");
                    sweetAlertDialog.setContentText("내용");
                    sweetAlertDialog.setCustomImage(R.mipmap.ic_launcher_round);
                    sweetAlertDialog.setConfirmText("확인");
                    sweetAlertDialog.setCancelText("취소");


                    //confrim버튼에 이벤트 감지자 등록
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            Toast.makeText(getApplicationContext(),"확인 클릭",Toast.LENGTH_SHORT).show();

                        }
                    });

                    //cancel버튼에 이벤트감지자 등록
                    sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            sweetAlertDialog.dismiss();//다이얼로그 종료
                        }
                    });

                    sweetAlertDialog.show();
                    break;
                case R.id.btn_error:
                    sweetAlert("등록할 수 없는 카드입니다..","<error>",SweetAlertDialog.ERROR_TYPE,SweetAlertActivity.this);
                    break;
                case R.id.btn_sucess:
                    sweetAlert("등록 성공","<sucess>",SweetAlertDialog.SUCCESS_TYPE,SweetAlertActivity.this);
                    break;
                case R.id.btn_warning:
                    sweetAlert("등록된 사용자가 없습니다.","<warning>",SweetAlertDialog.WARNING_TYPE,SweetAlertActivity.this);
                    break;
            }
        }
    };

    //타입별 다이얼로그 호출메서드
    private void sweetAlert(String msg, String title, int msgType, Context context){
        SweetAlertDialog dialog= new SweetAlertDialog(context,msgType);
        dialog.setTitleText(title);
        dialog.setContentText(msg);
        dialog.setConfirmText("확인");

        //실행중인 다이얼로그가 종료될 때
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(),"dismiss",Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}
