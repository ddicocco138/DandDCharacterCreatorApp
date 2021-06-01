package com.example.dicoccoandroidfinaldnd;

import androidx.annotation.NonNull;
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

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText pass, passConfirm;
    private Button regButt;
    private static final String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.etEmailRegister);
        pass = findViewById(R.id.etPasswordRegister);
        passConfirm = findViewById(R.id.etPasswordConfirm);
        regButt = findViewById(R.id.btnRegisterComplete);
    }

    public void createUserWithEmailAndPassword (String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this,"Account created successfully",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(RegisterActivity.this, UserLogin.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void Move_Login(View view) {
        if(email.getText().toString().isEmpty() || pass.getText().toString().isEmpty() || passConfirm.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Field(s) cannot be empty!",
                    Toast.LENGTH_SHORT).show();
        }else if (!pass.getText().toString().equals(passConfirm.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match!",
                    Toast.LENGTH_SHORT).show();
        }else{
            createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString());
        }
    }
}