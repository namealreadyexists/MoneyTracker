package com.someasshole.my.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 0;
    private static final String TAG = AuthActivity.class.getSimpleName();
    private GoogleSignInClient mGoogleSignInClient;
    private Api mApi;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auth);
        mApi = ((App) getApplication()).getApi();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        ImageButton button = findViewById(R.id.sign_in_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            updateUI(account);
        }
    }

    private void signIn(){
        Intent signinIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signinIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        }catch (ApiException e){
            Log.w(TAG, "signInResult: failed code= "+e.getStatusCode() );
            updateUI(null);
        }
    }
    private void updateUI(GoogleSignInAccount account){
        if (account==null){
            showError("Account is null");
            return;
        }
        String id = account.getId();

        mApi.auth(id).enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Call<AuthResult> call, Response<AuthResult> response) {
                AuthResult result = response.body();
                ((App) getApplication()).saveAuthToken(result.token);
                finish();
            }

            @Override
            public void onFailure(Call<AuthResult> call, Throwable t) {
                showError("Auth failed " + t.getMessage());
            }
        });
    }

    private void showSuccess(){
        Toast.makeText(this, "Account successfull obtainedy", Toast.LENGTH_SHORT).show();
    }
    private void showError(String error){
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
    }
}
