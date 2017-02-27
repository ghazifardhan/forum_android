package com.app.ghazi.forumku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ghazi.forumku.api.ApiService;
import com.app.ghazi.forumku.api.RetrofitClient;
import com.app.ghazi.forumku.model.TokenResponse;
import com.app.ghazi.forumku.model.login.Login;
import com.beardedhen.androidbootstrap.BootstrapButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextEmail;
    private TextView mTextPassword;
    private BootstrapButton mButtonLogin;
    private BootstrapButton mButtonRegister;

    ApiService service;

    // Shared Preferences
    public static final String myPreferences = "myPref";
    public static final String token = "token";
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        // Shared Preferences
        sp = getSharedPreferences(myPreferences, MODE_PRIVATE);

        service = RetrofitClient.getClient().create(ApiService.class);

        initEvent();
    }

    public void initUI(){
        mTextEmail = (TextView) findViewById(R.id.email);
        mTextPassword = (TextView) findViewById(R.id.password);
        mButtonLogin = (BootstrapButton) findViewById(R.id.login);
        mButtonRegister = (BootstrapButton) findViewById(R.id.register);
    }

    public void initEvent(){
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    public void login(){

        Login login = new Login();

        login.setEmail(mTextEmail.getText().toString());
        login.setPassword(mTextPassword.getText().toString());

        Call<TokenResponse> call = service.doLogin(login);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                Integer responseCode = response.code();
                if(responseCode == 500){
                    Toast.makeText(MainActivity.this, "Login Failed, check your username or password", Toast.LENGTH_LONG).show();
                } else {
                    String myToken = response.body().getToken().toString();
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString(token, myToken);
                    if (ed.commit()) {
                        Intent i = new Intent(getApplicationContext(), ForumActivity.class);
                        Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_LONG).show();
                        startActivity(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Login", Toast.LENGTH_LONG).show();
            }
        });

    }
}
