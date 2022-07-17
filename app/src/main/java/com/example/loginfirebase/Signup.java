package com.example.loginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {
    EditText name,email,pass,pass_repeat;
    FirebaseAuth firebaseAuth;
    Button cont;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.FullName);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        pass_repeat = findViewById(R.id.password2);
        cont = findViewById(R.id.button);

        firebaseAuth = FirebaseAuth.getInstance();

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail = email.getText().toString().trim();
                String spass = pass.getText().toString().trim();
                String spass_repeat = pass_repeat.getText().toString().trim();

                if(TextUtils.isEmpty(semail))
                {
                    email.setError("Email is Invalid");
                    return;
                }
                if(TextUtils.isEmpty(spass))
                {
                    pass.setError("Enter a password");
                    return;
                }
                if(spass.length()<6)
                {
                    pass.setError("Password Must Be Of min 6 Characters");
                    return;
                }
                if(!spass.equals(spass_repeat))
                {
                    pass.setError("Password do not match");
                    return;
                }

                //Registering user in firebase

                firebaseAuth.createUserWithEmailAndPassword(semail,spass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Signup.this,"Account Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }
                        else
                        {
                            Toast.makeText(Signup.this,""+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}