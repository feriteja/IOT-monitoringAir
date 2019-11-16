package com.example.monitoringair;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    //deklarasi objek koding
    private ProgressDialog progressDialog;
    EditText mEditTextEmail, mEditTextPassword ;
    Button registerButton;

    //deklarasi firebase
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth=FirebaseAuth.getInstance();


        progressDialog=new ProgressDialog(this);

        //Link activity dengan xml
        registerButton =findViewById(R.id.buttonRegsterUser);
        mEditTextEmail=findViewById(R.id.editTextEmailRegister);
        mEditTextPassword=findViewById(R.id.editTextPasswordRegister);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    public void register() {
        String email = mEditTextEmail.getText().toString().trim();
        String password = mEditTextPassword.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            //memunculkan peringatan email kosong
            Toast.makeText(this, "please enter email",Toast.LENGTH_SHORT).show();
            // menghentikan peringatan
            return;
        }
        if(TextUtils.isEmpty(password)){
            //memunculkan peringatan password kosong
            Toast.makeText(this, "please enter password",Toast.LENGTH_SHORT).show();
            // menghentikan peringatan
            return;
        }

        progressDialog.setMessage("Registering user.....");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            //register  berhasil
                            Toast.makeText(RegisterActivity.this, "Registered Successfully",Toast.LENGTH_SHORT).show();

                            firebaseAuth.signOut();
                            finish();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));



                        }else{
                            //register  gagal
                            Toast.makeText(RegisterActivity.this, "Registered failed, please try again",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    }
                });

    }
}
