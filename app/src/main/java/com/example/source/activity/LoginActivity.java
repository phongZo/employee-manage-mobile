package com.example.source.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.source.R;
import com.example.source.constant.Constant;
import com.example.source.database.Database;
import com.example.source.model.Account;

public class LoginActivity extends AppCompatActivity {
    EditText loginText, passwordText;
    Button btnLogin;

    private Database database = Database.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginText = (EditText) findViewById(R.id.login);
        passwordText = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.buttonLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                Account account = database.findAccountByUsername(username);
                if(account == null){
                    Toast.makeText(LoginActivity.this, "Username not exist", Toast.LENGTH_SHORT).show();
                }else{
                    if(!account.getPasssword().equals(password)){
                        Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                    }else{
                        if(!account.getKind().equals(Constant.KIND_ADMIN)){
                            Toast.makeText(LoginActivity.this, "This account not have permission", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent(LoginActivity.this,DeliveryActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        });


    }
}