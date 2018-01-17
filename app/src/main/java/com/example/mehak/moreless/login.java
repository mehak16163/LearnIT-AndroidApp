package com.example.mehak.moreless;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    private EditText name , pass;
    private Button login;
    databasehelper helper = new databasehelper(this);
    @Override    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button)findViewById(R.id.button3);
        final Intent search = new Intent(this, search.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = (EditText)findViewById(R.id.name);
                pass = (EditText)findViewById(R.id.pass);
                String namestr = name.getText().toString();
                String passstr = pass.getText().toString();
                String cpass =  helper.searchPass(namestr);
                if (cpass.equals(passstr)){
                    startActivity(search);
                }
                else{
                    Toast t;
                    t = Toast.makeText(login.this, "Password doesn't match!" , Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        }) ;
    }
}
