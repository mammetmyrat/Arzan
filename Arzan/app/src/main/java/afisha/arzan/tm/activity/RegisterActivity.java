package afisha.arzan.tm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import afisha.arzan.tm.MainActivity;
import afisha.arzan.tm.R;
import afisha.arzan.tm.api.ApiClient;
import afisha.arzan.tm.app.SPManager;
import afisha.arzan.tm.request.RegisterRequest;
import afisha.arzan.tm.request.UserCheckerRequest;
import afisha.arzan.tm.response.LoginResponse;
import afisha.arzan.tm.response.RegisterResponse;
import afisha.arzan.tm.response.UserCheckerResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ImageView back,visible,visible1;
    private MaterialButton send,login;
    private EditText phone,password,password_confirm,username;
    private boolean isVisible = false;
    private Context context;
    private Dialog dialog;
    private UserCheckerRequest userCheckerRequest = new UserCheckerRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = RegisterActivity.this;
        dialog = new Dialog(this);
        back = findViewById(R.id.back_btn);
        visible =findViewById(R.id.visible);
        visible1 =findViewById(R.id.visible1);
        send =findViewById(R.id.register);
        login =findViewById(R.id.login);
        phone =findViewById(R.id.phone);
        username = findViewById(R.id.username);
        password =findViewById(R.id.password);
        password_confirm =findViewById(R.id.password_confirm);
        back.setOnClickListener(this::onClick);
        visible.setOnClickListener(this::onClick);
        send.setOnClickListener(this::onClick);
        login.setOnClickListener(this::onClick);


    }
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back_btn:onBackPressed();break;
            case R.id.visible:{
                if (isVisible) {
                    password_confirm.setTransformationMethod(new PasswordTransformationMethod());
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    visible.setBackgroundResource(R.drawable.ic_visibile);
                    visible1.setBackgroundResource(R.drawable.ic_visibile);

                } else{
                    password_confirm.setTransformationMethod(null);
                    visible.setBackgroundResource(R.drawable.ic_visible_off);
                    password.setTransformationMethod(null);
                    visible1.setBackgroundResource(R.drawable.ic_visible_off);
                }
                isVisible = !isVisible;
            }break;
            case R.id.register:{
                chekFullData();break;
            }
            case R.id.login:{
                startActivity(new Intent(context,LoginActivity.class));break;
            }
        }
    }
    private void chekFullData(){
        if (TextUtils.isEmpty(username.getText()) || Objects.requireNonNull(username.getText()).length() <= 3||Objects.requireNonNull(password.getText()).length() <= 4 || phone.getText().length()<8||TextUtils.isEmpty(phone.getText())|| !password.getText().toString().equals(password_confirm.getText().toString())) {
            if (TextUtils.isEmpty(username.getText()) || Objects.requireNonNull(username.getText()).length() <= 3) {
                username.setError(getResources().getString(R.string.short_name));
                Toast.makeText(RegisterActivity.this, R.string.short_name, Toast.LENGTH_SHORT).show();
            }
            if (!password.getText().toString().equals(password_confirm.getText().toString())){
                password_confirm.setError(getResources().getString(R.string.password_dont));

                Toast.makeText(context, R.string.password_dont, Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(phone.getText()) || Objects.requireNonNull(phone.getText()).length() <8) {
                phone.setError(getResources().getString(R.string.short_phoneNumber));
                Toast.makeText(context, R.string.short_phoneNumber, Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(password.getText())||Objects.requireNonNull(password.getText()).length() <= 4) {
                password.setError(getResources().getString(R.string.short_password));
                Toast.makeText(context, R.string.short_password, Toast.LENGTH_SHORT).show();
            }
        } else {
            String phoneNumner = "+993"+phone.getText().toString();
            userCheckerRequest.setPhoneNumber(phoneNumner);
            userCheckerRequest.setUsername(username.getText().toString());
            userChecker(userCheckerRequest);
            SPManager.loading(dialog);
        }
    }
    private void userChecker(UserCheckerRequest userCheckerRequest) {
        Call<UserCheckerResponse> userCheckerResponseCall = ApiClient.getInstance().getApi().usr_checker(userCheckerRequest);
        userCheckerResponseCall.enqueue(new Callback<UserCheckerResponse>() {
            @Override
            public void onResponse(Call<UserCheckerResponse> call, Response<UserCheckerResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (!response.body().getUsername() || !response.body().getPhone()) {
                        dialog.dismiss();
                        if (!response.body().getPhone()) {
                            Toast.makeText(context, R.string.phone_have, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String phoneNumber ="+993"+phone.getText().toString();
                        RegisterRequest registerRequest = new RegisterRequest();
                        registerRequest.setPhoneNumber(phoneNumber);
                        registerRequest.setPassword(password.getText().toString());
                        registerRequest.setUsername(username.getText().toString());
                        registerUser(registerRequest);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserCheckerResponse> call, Throwable t) {
                Toast.makeText(context, R.string.network_error_toast, Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onFailure: "+t.getLocalizedMessage() );
            }
        });
    }

    public void registerUser(RegisterRequest registerRequest) {
        Call<LoginResponse> registerResponseCall = ApiClient.getInstance().getApi().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    SPManager.getInstance(context).setBoolean(SPManager.AUTH,true);
                    assert response.body() != null;
                    SPManager.getInstance(context).setData(SPManager.TOKEN,"Bearer "+response.body().getToken());
                    SPManager.getInstance(context).setIntData(SPManager.MY_ID,response.body().getUser().getId());
                    Log.v("Tag", "token: " + response.body().getUser().getId());
                    Intent intent = new Intent(context, MainActivity.class);
                    Toast.makeText(context, R.string.successfully_registeres, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                } else {
                    dialog.dismiss();
                    Toast.makeText(context, R.string.network_error_toast, Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "onResponse: "+response.code() );
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message = t.getLocalizedMessage();
                dialog.dismiss();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onFailure: "+t.getLocalizedMessage() );

            }
        });
    }

}