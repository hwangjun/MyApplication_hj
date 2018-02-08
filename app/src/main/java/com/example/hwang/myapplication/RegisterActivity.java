package com.example.hwang.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hwang.myapplication.db.UserDB;
import com.example.hwang.myapplication.db.UserDBHelper;

/**
 * Created by hwang on 2018-01-27.
 */
public class RegisterActivity extends Activity {
    private EditText editTextId;
    private EditText editTextName;
    private EditText editTextPwd, editTextPwdRe;
    private Button buttonReg;

    private UserDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextId = (EditText) findViewById(R.id.new_id);
        editTextName = (EditText) findViewById(R.id.new_name);
        editTextPwd = (EditText) findViewById(R.id.new_pw);
        editTextPwdRe = (EditText) findViewById(R.id.new_pw_cnf);
        buttonReg = (Button) findViewById(R.id.new_btn);

        db = new UserDB(this);
        db.open();


        //회원가입버튼 이벤트
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ID 존재 유무 검사
                String id = editTextId.getText().toString();
                //DB에서 조회
                if(!(id.equals(null) || id.equals(""))){
                    try {
                       Cursor cursor = db.selectUserId(id);

                        if (cursor.getCount() > 0) {
                            //ID존재
                            resetEditText(0);
                            Toast.makeText(RegisterActivity.this, "아이디가 존재합니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            //통과
                            //Toast.makeText(RegisterActivity.this, "아이디 확인 완료", Toast.LENGTH_SHORT).show();
                            //유효성 검사 NAMME
                            boolean nameChk = validationName();
                            if(nameChk) {
                                //유효성 검사 PWD
                                validationPwd();
                            }
                        }
                    } catch (Exception e) {
                        Log.i("데이터베이스", "ID조회 실패");
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(RegisterActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public boolean validationName() {
        boolean nameChk = true;
        String name = editTextName.getText().toString();
        if (!(name.equals("") || name.equals(null))) {
            //통과
        } else{
            //실패
            nameChk = false;
            resetEditText(1);
            Toast.makeText(RegisterActivity.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        return nameChk;
    }

    public void validationPwd() {
        String pwd = editTextPwd.getText().toString();
        String pwdRe = editTextPwdRe.getText().toString();
        if (!(pwd.equals("") || pwd.equals(null))&& !(pwdRe.equals("") || pwdRe.equals(null))) {
            if (pwd.equals(pwdRe)) {
                //통과
                boolean registerChk =insert();
                if(registerChk) {
                    //초기화
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            } else {
                //실패
                resetEditText(2);
                Toast.makeText(RegisterActivity.this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            editTextPwd.requestFocus();
        }
    }

    public void resetEditText(int num) {
        //초기화 및 커서 이동
        switch (num) {
            case 0:
                editTextId.setText("");
                editTextName.setText("");
                editTextPwd.setText("");
                editTextPwdRe.setText("");
                editTextId.requestFocus();
                break;
            case 1:
                editTextName.setText("");
                editTextName.requestFocus();
                break;
            case 2:
                editTextPwd.setText("");
                editTextPwdRe.setText("");
                editTextPwd.requestFocus();
                break;
        }
    }

    public boolean insert() {
        String id = editTextId.getText().toString();
        String pwd = editTextPwd.getText().toString();
        String name =editTextName.getText().toString();
        boolean chk = insertoToDatabase(id, name, pwd);
        return chk;
    }

    private boolean insertoToDatabase(String id, String name, String pwd) {
        boolean resultChk = true;
        try {
            db.insertUser(id,name,pwd);
        } catch (Exception e) {
            resultChk = false;
            Log.i("데이터베이스", "INSERT 실패");
        }
        return  resultChk;
    }


}
