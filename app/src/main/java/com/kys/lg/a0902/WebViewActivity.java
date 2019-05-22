package com.kys.lg.a0902;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class WebViewActivity extends AppCompatActivity {

    WebView myWebView;
    TextView tv;
    EditText et;
    Button btn_send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        myWebView=findViewById(R.id.myWebView);
        tv=findViewById(R.id.tv);
        et=findViewById(R.id.et);
        btn_send=findViewById(R.id.btn_send);

        //웹뷰가 로드할 페이지
        //myWebView.loadUrl("https://www.naver.com");

        myWebView.loadUrl("file:///android_asset/www/page.html");

        WebSettings ws=myWebView.getSettings();
        ws.setJavaScriptEnabled(true);

        //html에서 보내준 내용을 받는다.
        myWebView.addJavascriptInterface(new Object(){
                @JavascriptInterface
                public void performClick(String id,String pwd){

            Toast.makeText(WebViewActivity.this,id+" / "+pwd,Toast.LENGTH_SHORT).show();

        }

        },"ok");
        //ok라는 클래스(객체)임.


        myWebView.addJavascriptInterface(new Object(){
            @JavascriptInterface
            public void performClick(String course,String subject,String student){

                try{
                    //일반 단일 JSON 타입
                    //{'name':'안드로이드' ...}

                    JSONObject jsonObject= new JSONObject(course);
                    String name="괴목명 : "+jsonObject.getString("name"); //name이라는 키값을 통해서 문자열을 json타입으로 바꿔서 가져온다.
                    String start="시작일 : "+jsonObject.getString("start");
                    String end="종료일 :"+jsonObject.getString("end");

                    //JSON 배열
                    //'sub':['java','android', ...]
                    String s="과목명 : ";
                    JSONArray sub= new JSONObject(subject).getJSONArray("sub");
                    for(int i=0;i<sub.length();i++){
                        s+=sub.get(i)+" / ";
                    }

                    //JSON 다중배열
                    //"{'student':[{'name':'홍길동','age':'20'}, {'name':'r김길동','age':'30'}]}"
                    String s2="학생명 :\n";
                    JSONArray stu=new JSONObject(student).getJSONArray("student");
                    for(int i=0;i<stu.length();i++){

                        jsonObject=stu.getJSONObject(i);
                        s2+=jsonObject.getString("name")+" / "+jsonObject.getString("age")+"\n";
                    }

                    String result=String.format("%s \n%s \n%s \n%s \n%s \n",name,start,end,s,s2);
                    tv.setText(result);

                }catch (Exception e){
                    tv.setText(e.getMessage());
                }

            }

        },"forJson");


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=et.getText().toString();

                //str을 html 의 send()메서드로 전달
                String result=String.format("javascript:send('%s')",str);
                myWebView.loadUrl(result);
                //send라는 메서드를 찾아서 보내버린다.
            }
        });


    }
}
