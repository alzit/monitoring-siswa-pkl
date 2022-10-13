package com.example.monitoring.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.monitoring.R;
import com.example.monitoring.api.ApiInterface;
import com.example.monitoring.api.ApiServer;
import com.example.monitoring.model.ResponseLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, confirmpassword;
    String user, pass, confirmpass;
    AppCompatButton btnlogin, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.etusername);
        password = findViewById(R.id.etpassword);
        confirmpassword = findViewById(R.id.etconfirmpassword);
        btnlogin = findViewById(R.id.login);

        user = username.getText().toString();
        pass = password.getText().toString();
        confirmpass = confirmpassword.getText().toString();

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = username.getText().toString();
                pass = password.getText().toString();

                confirmpass = confirmpassword.getText().toString();

                if(confirmpass.equals(pass)){
                    moveToRegister(user, pass);
                }else{
                    password.setError("Password Tidak Sama");
                }
            }
        });

        if (confirmpass.equals(pass)){
            moveToRegister(user, pass);
        }else {
            password.setError("password tidak sama");
        }

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }

    private void moveToRegister(String user,String pass) {
        Log.d("TAG", "moveToRegister: "+user+""+pass);

        ApiInterface apiInterface= ApiServer.getClient().create(ApiInterface.class);
        Call<ResponseLogin> call = apiInterface.register(user, pass);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                Log.d("TAG", "onResponse: "+response.isSuccessful());
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "berhasil mendaftar", Toast.LENGTH_SHORT).toString();
                }else{
                    Toast.makeText(getApplicationContext(), "Gagal Mendaftar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}