package com.example.monitoringair;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    EditText mEditTextEmail, mEditTextPassword;
    Button registerButton, loginButton;



    ////Bagian Firebase
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {

            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }


        progressDialog=new ProgressDialog(this);

        mEditTextEmail = findViewById(R.id.editTextEmail);
        mEditTextPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });



    }

    private void signin(){
        final  String Email = mEditTextEmail.getText().toString().trim();
        final  String Password = mEditTextPassword.getText().toString().trim();

        progressDialog.setMessage("Sedang Login" );
        progressDialog.show();


        if(TextUtils.isEmpty(Email)){
            Toast.makeText(this,"Mohon Masukan Email",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        if(TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Mohon Masukan Password", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();

            return;
        }


        progressDialog.setMessage("Please wait" );
        progressDialog.show();



        penentuRoleAuth(Email,Password);

    }


    public void penentuRoleAuth(final String userEmail, final String Password ){


        firebaseAuth.signInWithEmailAndPassword(userEmail,Password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            //Start the profile Activity

                            Toast.makeText(getBaseContext(), "Wellcome", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));


                        } else if (!task.isSuccessful()) {
                            Toast.makeText(getBaseContext(), "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }

                });

    }



}
