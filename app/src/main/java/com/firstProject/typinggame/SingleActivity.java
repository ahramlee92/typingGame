package com.firstProject.typinggame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SingleActivity extends AppCompatActivity {
    TextView txtRead;
    EditText edittext;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        txtRead = (TextView)findViewById(R.id.randomTxt);
        txtRead.setText(readTxt());

        Button backButton = (Button) findViewById(R.id.backBtn);
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
            startActivity(intent);
        });
        edittext=(EditText)findViewById(R.id.editTxtChat);
        textView=(TextView)findViewById(R.id.viewMsg);
        Button sendBtn = (Button) findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String data = edittext.getText().toString();
                // 실시간 디비 관리 객체 읽어오기
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                // 저장시킬 참고 노드객체 가져오기
                DatabaseReference rootRef = database.getReference();
                DatabaseReference childRef = rootRef.push(); // 자식 노드 추가
                childRef.setValue(data);

                // 저장된 값 불러오기
                rootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // 최상위 노드가 데이터를 갖고 있지 않고, 자식노드들이 많기때문에 자식노드에서 가져와야함
                        StringBuffer buffer = new StringBuffer();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String snapData = snapshot.getValue(String.class);
                            buffer.append(snapData+"\n");
                        }
                        textView.setText(buffer.toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
//        sendBtn.setOnClickListener(view -> textView.setText(edittext.getText()));
    }

    private String readTxt() {
        String data = null;
        InputStream inputStream = getResources().openRawResource(R.raw.textfile);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }

            data = new String(byteArrayOutputStream.toByteArray(),"utf-8");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}