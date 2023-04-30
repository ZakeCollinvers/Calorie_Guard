package com.example.calorieguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.List;
import java.util.Objects;

public class Login extends AppCompatActivity {
    public Button lg,sg,skip;
    public EditText email,password;
    public TextView forgotPass;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        Objects.requireNonNull(getSupportActionBar()).hide();

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isSignedIn = sharedPreferences.getBoolean("isSignedIn", false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && isSignedIn) {
            String Memail = Email_modify(Objects.requireNonNull(user.getEmail()));
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("Email",Memail);
            startActivity(intent);
        } else {
            lg = (Button) findViewById(R.id.buttonlogin);
            sg = (Button) findViewById(R.id.buttonsignup);
            skip = (Button) findViewById(R.id.buttonskip);
            email = (EditText) findViewById(R.id.editTextTextEmailAddress);
            forgotPass=(TextView)findViewById(R.id.forgotPass);
            password = (EditText) findViewById(R.id.editTextTextPassword);
            myAuth = FirebaseAuth.getInstance();

            forgotPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Email = email.getText().toString();
                    if(Email.isEmpty())
                    {
                        Toast.makeText(Login.this, "Enter email and click forgot password", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        myAuth.sendPasswordResetEmail(Email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Login.this, "Password Reset link send to "+Email, Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(Login.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            });

            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("Email", "Anonymous");
                    startActivity(intent);
                }
            });

            sg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Signup.class);
                    startActivity(intent);
                }
            });

            lg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Email = email.getText().toString();
                    String pass = password.getText().toString();
                    if (!Email.isEmpty() && !pass.isEmpty()) {
                        progressBar.setVisibility(View.VISIBLE);
                        Fun_login(Email, pass);
                    } else {
                        Toast.makeText(Login.this, "Enter email and password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public  void Fun_login(String Email,String pass)
    {
        myAuth.signInWithEmailAndPassword(Email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            String mod_email=Email_modify(Email);
                            intent.putExtra("Email",mod_email);
                            startActivity(intent);
                            email.setText("");
                            password.setText("");
                            Toast.makeText(Login.this, "Logged in", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isSignedIn", true);
                            editor.apply();
                        } else {
                            Toast.makeText(Login.this, "Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public String Email_modify(String Email)
    {
        String mod_email="";
        for(int i=0;i<Email.length();i++)
        {
            if(Email.charAt(i)=='@' || Email.charAt(i)=='.')
            {
                mod_email+='_';
            }
            else
            {
                mod_email+=Email.charAt(i);
            }
        }
        return mod_email;
    }
}