package com.example.appseriesfirebase2020.models;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String TAG = "SRNOT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
     private void updateUI(FirebaseUser user){
        if(user != null){
            Log.d(TAG, user.toString());
            Toast.makeText(LoginActivity.this, "User: " + user.getEmail(),
                    Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }else{
            Toast.makeText(this, "Invitado", Toast.LENGTH_LONG).show();

            String email = "sancero97@gmail.com";
            String password = "Sr123.";
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed." + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                                updateUI(null);
                            }
                        }
                    });
        }
     }

}