package com.example.mehak.moreless;

/**
 * Created by mehak on 16/1/18.
 */

public class contact {
    int id;
    String name , email  ,pass;
    public void setId(int i){
        id = i;
    }
    public void setName(String i){
        name = i;
    }
    public void setEmail(String i){
        email = i;
    }
    public void setPass(String i){
        pass = i;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPass(){
        return pass ;
    }
}
