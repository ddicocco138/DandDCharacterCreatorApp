package com.example.dicoccoandroidfinaldnd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLogin extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button sendPasswordbtn, Cancelbtn;
    private FirebaseAuth mAuth;
    private EditText email, pass, getEmailReset;
    private static final String TAG = "UserLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.etEmail);
        pass = findViewById(R.id.etPassword);

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(UserLogin.this,"Logged In",Toast.LENGTH_LONG).show();
                            Intent i = new Intent (UserLogin.this, MainMenu.class);
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(UserLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void Move_MainMenu(View view) {
        if(email.getText().toString().isEmpty() || pass.getText().toString().isEmpty()) {
            Toast.makeText(UserLogin.this, "Field(s) cannot be empty!",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            signIn(email.getText().toString(), pass.getText().toString());
        }
    }

    public void Move_Register(View view) {
        Intent i = new Intent (this, RegisterActivity.class);
        startActivity(i);
    }

    public void Email_Window(View view) {
        Toast.makeText(UserLogin.this, "clicked",
                Toast.LENGTH_SHORT).show();
        createNewForgottenPasswordDialog();
    }

    public void createNewForgottenPasswordDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View PopupView = getLayoutInflater().inflate(R.layout.popup, null);

        getEmailReset = PopupView.findViewById(R.id.editTextEmail);
        sendPasswordbtn = PopupView.findViewById(R.id.btnEmailPswd);
        Cancelbtn = PopupView.findViewById(R.id.btnCancel);

        dialogBuilder.setView(PopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        sendPasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

        Cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public void sendEmail(){
        String loginEmail = getEmailReset.getText().toString().trim();

        if(loginEmail.isEmpty()){
            getEmailReset.setError("Email is Required");
            getEmailReset.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(loginEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UserLogin.this, "Password Reset Sent to Your Email!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UserLogin.this, "Could Not Send Reset Email, Please Try Again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}