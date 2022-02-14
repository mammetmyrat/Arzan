package afisha.arzan.tm.activity;

import androidx.appcompat.app.AppCompatActivity;

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
import afisha.arzan.tm.request.LoginRequest;
import afisha.arzan.tm.request.RecoverRequest;
import afisha.arzan.tm.response.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetActivity extends AppCompatActivity {

    private ImageView back,visible;
    private MaterialButton send;
    private EditText phone,password;
    private Context context;
    private Dialog loading;
    private boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        context = ForgetActivity.this;
        loading = new Dialog(this);
        back = findViewById(R.id.back_btn);
        visible  =findViewById(R.id.visible);
        send  =findViewById(R.id.send);
        phone  =findViewById(R.id.phone);
        password  =findViewById(R.id.password);

        back.setOnClickListener(this::onClick);
        send.setOnClickListener(this::onClick);
        visible.setOnClickListener(this::onClick);
    }
    private void onClick(View view){
        switch (view.getId()){
            case R.id.back_btn:onBackPressed();break;
            case R.id.send:{
                checkFullData();
            }break;
            case R.id.visible:{
                if (isVisible) {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    visible.setBackgroundResource(R.drawable.ic_visibile);
                } else{
                    password.setTransformationMethod(null);
                    visible.setBackgroundResource(R.drawable.ic_visible_off);
                }
                isVisible = !isVisible;
            }break;
        }
    }
    private void checkFullData(){
        if (TextUtils.isEmpty(phone.getText()) || Objects.requireNonNull(phone.getText()).length() <= 6 || Objects.requireNonNull(password.getText()).length() <= 4) {
            if (TextUtils.isEmpty(phone.getText()) || Objects.requireNonNull(phone.getText()).length() <= 3) {
                phone.setError(getResources().getString(R.string.short_phoneNumber));
                Toast.makeText(context, R.string.short_phoneNumber, Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(password.getText())) {
                password.setError(getResources().getString(R.string.short_password));
                Toast.makeText(ForgetActivity.this, R.string.short_password, Toast.LENGTH_SHORT).show();
            }
        }else {
            String phoneNumber = "+993"+phone.getText().toString();
            RecoverRequest recoverRequest = new RecoverRequest();
            recoverRequest.setPhoneNumber(Objects.requireNonNull(phoneNumber));
            recoverRequest.setPassword(Objects.requireNonNull(password.getText()).toString());
            recoverPassword(recoverRequest);
            SPManager.loading(loading);
        }
    }
    public void recoverPassword(RecoverRequest loginRequest){
        Call<LoginResponse> loginResponseCall = ApiClient.getInstance().getApi().recoverPassword(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    SPManager.getInstance(ForgetActivity.this).setBoolean(SPManager.AUTH,true);
                    SPManager.getInstance(ForgetActivity.this).setData(SPManager.TOKEN,"Bearer "+response.body().getToken());
                    Intent intent =new Intent(ForgetActivity.this, MainActivity.class);
                    Log.v("Tag","token: "+response.body().getToken());
                    startActivity(intent);
                    Toast.makeText(context, R.string.password_changed, Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                    finish();
                }else {
                    loading.dismiss();
                    Log.e("TAG", "onResponse error : "+response.code());
                    Toast.makeText(ForgetActivity.this, R.string.wrong_phone, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loading.dismiss();
                Log.e("Tag","login error "+t.getLocalizedMessage());
                Toast.makeText(ForgetActivity.this, R.string.network_error_toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
}