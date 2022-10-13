package com.ckb.labs.e_supervisor.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ckb.labs.e_supervisor.MainActivity;
import com.ckb.labs.e_supervisor.R;
import com.ckb.labs.e_supervisor.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private SignInButton signInButton;
    private GoogleSignInClient googleSignInClient;
    private String TAG="mainTag";
    private FirebaseAuth mAuth;
    private final int RESULT_CODE_SIGN_IN=999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initialization
        signInButton = findViewById(R.id.SignIn_Button);
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this,gso);

        //Attach a onClickListener
        signInButton.setOnClickListener(v -> signInM());
    }

    //when the signIn Button is clicked then start the signIn Intent
    private void signInM() {
        Intent singInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(singInIntent,RESULT_CODE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_CODE_SIGN_IN) {        //just to verify the code
            //create a Task object and use GoogleSignInAccount from Intent and write a separate method to handle singIn Result.

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        //we use try catch block because of Exception.
        try {
            //signInButton.setVisibility(View.INVISIBLE);
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Toast.makeText(LoginActivity.this,"Signed In successfully",Toast.LENGTH_LONG).show();
            //SignIn successful now show authentication
            FirebaseGoogleAuth(account);

        } catch (ApiException e) {
            e.printStackTrace();
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(LoginActivity.this,"SignIn Failed!!!",Toast.LENGTH_LONG).show();
            //FirebaseGoogleAuth(null);
            proceedToMainDashboard(new User());
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(Objects.requireNonNull(account).getIdToken(), null);
        //here we are checking the Authentication Credential and checking the task is successful or not and display the message
        //based on that.
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"successful",Toast.LENGTH_LONG).show();
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    UpdateUI(firebaseUser);
                }
                else {
                    Toast.makeText(LoginActivity.this,"Failed!",Toast.LENGTH_LONG).show();
                    UpdateUI(null);
                }
            }
        });
    }

    //Inside UpdateUI we can get the user information and display it when required
    private void UpdateUI(FirebaseUser fUser) {
        //getLastSignedInAccount returned the account
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account !=null){
            User currentUser = new User();
            currentUser.setEmail(account.getEmail());
            currentUser.setUsername(account.getDisplayName());
            currentUser.setGivenName(account.getGivenName());

            proceedToMainDashboard(currentUser);
            Toast.makeText(LoginActivity.this,currentUser.getUsername() + "  " + currentUser.getEmail(),Toast.LENGTH_LONG).show();
        }
    }

    private void proceedToMainDashboard(User user) {
        Intent dashboardIntent = new Intent(LoginActivity.this, MainActivity.class);
        dashboardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        dashboardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        dashboardIntent.putExtra("User", user);
        startActivity(dashboardIntent);
        finish();
    }
}