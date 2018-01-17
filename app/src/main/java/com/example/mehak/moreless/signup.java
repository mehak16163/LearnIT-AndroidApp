package com.example.mehak.moreless;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signup extends AppCompatActivity {
    private EditText name,email ,pass,cpass;
    databasehelper helper = new databasehelper(this);
    private Button bt1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        bt1 = (Button)findViewById(R.id.sign);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = (EditText)findViewById(R.id.name);
                email = (EditText)findViewById(R.id.email);
                pass = (EditText)findViewById(R.id.pass);
                cpass = (EditText)findViewById(R.id.cpass);
                String namestr = name.getText().toString();
                String emailstr = email.getText().toString();
                String passstr = pass.getText().toString();
                String cpassstr = cpass.getText().toString();
                if (!passstr.equals(cpassstr)){
                    Toast passt = Toast.makeText(signup.this, "Passwords don't match!",Toast.LENGTH_SHORT);
                    passt.show();
                }
                else{
                    contact c = new contact();
                    c.setName(namestr);
                    c.setEmail(emailstr);
                    c.setPass(passstr);
                    helper.insertContact(c);
                    Toast passt = Toast.makeText(signup.this, "New Account Created",Toast.LENGTH_SHORT);
                    passt.show();
                    Intent i = new Intent(signup.this , MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }) ;
    }


}
