package com.app.ghazi.forumku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ghazi.forumku.api.ApiService;
import com.app.ghazi.forumku.api.RetrofitClient;
import com.app.ghazi.forumku.model.login.Login;
import com.beardedhen.androidbootstrap.BootstrapButton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextView mTextEmail;
    private TextView mTextPassword;
    private BootstrapButton mButtonRegister;

    ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();

        service = RetrofitClient.getClient().create(ApiService.class);

        initEvent();
    }

    public void initUI(){
        mTextEmail = (TextView) findViewById(R.id.email);
        mTextPassword = (TextView) findViewById(R.id.password);
        mButtonRegister = (BootstrapButton) findViewById(R.id.register);
    }

    public void initEvent(){
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    public void register(){

        Login register = new Login();

        register.setEmail(mTextEmail.getText().toString());
        register.setPassword(mTextPassword.getText().toString());

        Call<ResponseBody> call = service.doRegister(register);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Integer responseCode = response.code();

                if(responseCode == 500){
                    Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Thank You for Registering", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
