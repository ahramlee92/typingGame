package com.firstProject.typinggame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{6,}$");

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    // 이메일과 비밀번호
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextNickName;
    private EditText editTxt_cfmPassword;

    private String email = "";
    private String password = "";

    private Button buttonJoin;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText_passWord);
        editTextNickName = (EditText) findViewById(R.id.editText_nickName);
        editTxt_cfmPassword = (EditText) findViewById(R.id.editTxt_cfmPassword);

        backBtn = (Button) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.firstProject.typinggame.RegisterActivity.this, com.firstProject.typinggame.MainActivity.class);
                startActivity(intent);
            }
        });

        buttonJoin = (Button) findViewById(R.id.btn_join);
        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singUp(v);
            }
        });
    }

    public void singUp(View view) {
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();

        if (isValidEmail() && isValidPasswd() && ConfirmPwAndCfmPW()) {
            createUser(email, password);
        }
    }

    // 이메일 유효성 검사
    private boolean isValidEmail() {
        if (email.isEmpty()) {
            // 이메일 공백
            Toast.makeText(com.firstProject.typinggame.RegisterActivity.this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(com.firstProject.typinggame.RegisterActivity.this, "이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show();
            // 이메일 형식 불일치
            return false;
        } else {
            return true;
        }
    }

    // 비밀번호 유효성 검사
    private boolean isValidPasswd() {
        if (password.isEmpty()) {
            // 비밀번호 공백
            Toast.makeText(com.firstProject.typinggame.RegisterActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // 비밀번호 형식 불일치
            Toast.makeText(com.firstProject.typinggame.RegisterActivity.this, "비밀번호는 6자리 이상으로 해주세요", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    // 비밀번호 확인
    private boolean ConfirmPwAndCfmPW() {
        if (!password.equals(editTxt_cfmPassword.getText().toString())) {
            Toast.makeText(com.firstProject.typinggame.RegisterActivity.this, "비밀번호와 비밀번호 확인이 일치하지 않습니다", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    // 회원가입
    private void createUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공
                            Intent intent = new Intent(com.firstProject.typinggame.RegisterActivity.this, com.firstProject.typinggame.MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(com.firstProject.typinggame.RegisterActivity.this, R.string.success_signup, Toast.LENGTH_SHORT).show();
                            // 사용자 인증메일 보내기.
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            // 요청 메일 전송이 완료되면 해당 메소드 실행
                                            Toast.makeText(com.firstProject.typinggame.RegisterActivity.this,
                                                    "메일 발송 완료!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        } else {
                            // 회원가입 실패
                            Toast.makeText(com.firstProject.typinggame.RegisterActivity.this, R.string.failed_signup, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//        firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(
//                this, new OnCompleteListener<ProviderQueryResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "checking to see if user exists in firebase or not");
//                            ProviderQueryResult result = task.getResult();
//                        }
//                    }
//                });

    }
}