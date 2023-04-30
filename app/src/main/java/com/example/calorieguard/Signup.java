package com.example.calorieguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.calorieguard.R.id;
import com.google.android.gms.common.util.HexDumpUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Objects;

public class Signup extends AppCompatActivity {
    public Button signupbtn;
    public EditText name,age,height,weight,email,pass;
    public RadioGroup RG;
    public RadioButton rb;
    private ProgressBar progressBar2;
    private FirebaseAuth myAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Objects.requireNonNull(getSupportActionBar()).hide();

        signupbtn=(Button)findViewById(R.id.SignupBtn);
        name=(EditText)findViewById(R.id.editTextName);
        age=(EditText)findViewById(id.edittextage);
        height=(EditText)findViewById(id.editTextheight);
        weight=(EditText)findViewById(id.editTextweight);
        email=(EditText)findViewById(id.editText2email);
        pass=(EditText)findViewById(id.editText2password);
        RG=(RadioGroup)findViewById(id.radioGroup);
        progressBar2 = findViewById(id.progressBar2);
        progressBar2.setVisibility(View.GONE);

        myAuth=FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance("https://calorie-guard-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=email.getText().toString();
                String Pass=pass.getText().toString();
                String Height=height.getText().toString();
                String Weight=weight.getText().toString();
                String Age=age.getText().toString();
                String Name=name.getText().toString();
                int ID=RG.getCheckedRadioButtonId();
                if(ID!=-1)
                {rb=findViewById(ID);
                    String sex=rb.getText().toString();
                if(!Email.isEmpty() && !Pass.isEmpty() && !Height.isEmpty() && !Weight.isEmpty() && !Age.isEmpty() && !Name.isEmpty()){
                    progressBar2.setVisibility(View.VISIBLE);
                Fun_signup(Email,Pass);
                SaveData(Name,Age,Height,Weight,Email,sex);}
                else {
                    Toast.makeText(Signup.this, "Enter all the details", Toast.LENGTH_SHORT).show();
                }}
                else {  Toast.makeText(Signup.this, "Enter all the details", Toast.LENGTH_SHORT).show();}
            }
        });

    }

    public  void Fun_signup(String Email,String Pass)
    {
        myAuth.createUserWithEmailAndPassword(Email,Pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar2.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(Signup.this, "Successfully Signed Up", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            String mod_email=Email_modify(Email);
                            intent.putExtra("Email",mod_email);
                            startActivity(intent);
                            name.setText("");
                            age.setText("");
                            height.setText("");
                            weight.setText("");
                            email.setText("");
                            pass.setText("");
                        } else {
                            Toast.makeText(Signup.this, "Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void SaveData(String Name,String Age,String Height,String Weight,String Email,String Sex) {
        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put("name",Name);
        hashMap.put("age",Age);
        hashMap.put("height",Height);
        hashMap.put("weight",Weight);
        hashMap.put("sex",Sex);

        String mod_email=Email_modify(Email);

        mDatabase.child(mod_email).setValue(hashMap)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Signup.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
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
