package com.ckb.labs.e_supervisor.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ckb.labs.e_supervisor.MainActivity;
import com.ckb.labs.e_supervisor.R;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MaterialButton loginBtn = findViewById(R.id.btn_login);
        loginBtn.setOnClickListener( view -> proceedToMainDashboard());
    }

    private void proceedToMainDashboard() {
        Intent dashboardIntent = new Intent(LoginActivity.this, MainActivity.class);
        dashboardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        dashboardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(dashboardIntent);
        finish();
    }
}