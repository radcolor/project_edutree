package com.littlegiants.android.edutree;

/**
 * Created by Android Developer on 06-Jun-17.
 */

public class UserInformation {

    private String UserID;
    private String Name;
    private String Email;
    private String Class;
    private String School;

    public UserInformation(){

    }

    public UserInformation(String user_id, String name, String email, String user_class, String school){
        this.UserID = user_id;
        this.Name = name;
        this.Email = email;
        this.Class = user_class;
        this.School = school;
    }

    public String getUserID(){
        return UserID;
    }

    public void setUserID(String user_id){
        this.UserID = user_id;
    }


    public String getName(){
        return Name;
    }

    public void setName(String name){
        this.Name = name;
    }


    public String getEmail(){
        return Email;
    }

    public void setEmail(String email){
        this.Email = email;
    }


    public String getclass(){
        return Class;
    }

    public void setclass(String user_class){
        this.Class = user_class;
    }


    public String getSchool(){
        return School;
    }

    public void setSchool(String school){
        this.School = school;
    }

}
