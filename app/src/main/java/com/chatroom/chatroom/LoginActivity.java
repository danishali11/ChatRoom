package com.chatroom.chatroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    ImageView signinback;
    private customfonts.MyTextView mSignInbtn;
    // TODO: Add member variables here:
     private FirebaseAuth mAuth;
    // UI references.
    private customfonts.MyEditText mEmailView;
    private customfonts.MyEditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signinback = (ImageView)findViewById(R.id.signinback);


        signinback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(it);
            }
        });

        mEmailView = (customfonts.MyEditText) findViewById(R.id.login_email);
        mPasswordView = (customfonts.MyEditText) findViewById(R.id.login_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mSignInbtn=(customfonts.MyTextView)findViewById(R.id.login_register_button);
        mSignInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        // TODO: Grab an instance of FirebaseAuth
        mAuth=FirebaseAuth.getInstance();

    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {
        // TODO: Call attemptLogin() here

        attemptLogin();

    }

    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.chatroom.chatroom.RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        if (email.equals("")|| password.equals("")) return;
        Toast.makeText(this,"Login in progress...",Toast.LENGTH_SHORT).show();


        // TODO: Use FirebaseAuth to sign in with email & password

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("ChatRoom","login onComplete:"+task.isSuccessful());
                if(!task.isSuccessful()){
                    Log.d("ChatRoom","problem signing in :"+task.getException() );
                    showErrorDialog("There was a problem signing in");


                }else {
                    Intent intent=new Intent(LoginActivity.this,MainChatActivity.class);
                    finish();
                    startActivity(intent);
                }

            }
        });





    }

    // TODO: Show error on screen with an alert dialog

    private void showErrorDialog(String message){

        new AlertDialog.Builder(this).setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(android.R.drawable.ic_dialog_alert).
                show();
    }



}