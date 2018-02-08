package com.example.hwang.myapplication;



import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hwang.myapplication.db.UserDB;


public class LoginActivity extends AppCompatActivity {
    private EditText idText, pwdText;
    private TextView register_Btn_txt;
    private Button loginBtn;
    private UserDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idText = (EditText) findViewById(R.id.idText);
        pwdText = (EditText) findViewById(R.id.pwdText);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        register_Btn_txt = (TextView) findViewById(R.id.register_btn_txt);


        db = new UserDB(this);
        db.open();
        Log.i("데이터베이스","DB생성");

        //로그인 이벤트
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String id = idText.getText().toString();
                String pwd = pwdText.getText().toString();

                //로그인 유효성 검사 호출
                boolean result = loginValidation(id, pwd);
                if(result){
                    if(id.equalsIgnoreCase("admin")) {
                        //로그인정보 초기화
                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                        startActivity(intent);
                        resetLogin();
                    } else {
                        //성공 -> 페이지 이동
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("로그인")
                                .setMessage("로그인에 성공하였습니다.")
                                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //로그인정보 초기화
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("id",idText.getText().toString());
                                        intent.putExtra("pwd", pwdText.getText().toString());
                                        startActivity(intent);
                                        resetLogin();
                                    }
                                });
                        builder.create();
                        builder.show();

                    }
                } else {
                    //실패
                }
            }
        });

        //회원가입 이벤트
        register_Btn_txt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void resetLogin() {
        idText.setText("");
        pwdText.setText("");
        idText.requestFocus();
    }


    //로그인 유효성 검사
    private boolean loginValidation(String id, String pwd) {
        boolean login_chk = true;

        if(id.equals("") || pwd.equals("")){
            Toast.makeText(LoginActivity.this, "ID 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            login_chk = false;

        } else if (id.equalsIgnoreCase("admin") && pwd.equalsIgnoreCase("admin")){
            //관리자
            Toast.makeText(LoginActivity.this, "관리자 페이지로 이동합니다..", Toast.LENGTH_SHORT).show();
            login_chk = true;
        } else {
            Cursor cursor = db.selectUser(id,pwd);

            if(cursor.getCount() > 0) {
                //Toast.makeText(LoginActivity.this,"로그인 성공",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this,"로그인 실패",Toast.LENGTH_SHORT).show();
                login_chk = false;
            }

        }
        return login_chk;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("종료")
                .setMessage("종료하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //프로세스 종료
                        finish();
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create();
        builder.show();

        //super.onBackPressed();
    }
}
