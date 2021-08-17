package com.example.new_community_bird;

import java.io.Serializable;

public class User implements Serializable {
    private String Id;
    private String name;            //用户
    private String age;
    private String high;
    private String weigh;
    private String gender;
    private String password;//密码
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }
    public User(String Id, String name, String password, String age, String high, String weigh, String gender) {
       super();
        this.Id=Id;
        this.name = name;
        this.password = password;
        this.age = age;
        this.high = high;
        this.weigh=weigh;
        this.gender=gender;

    }
    public String getId() {
        return Id;
    }
    public void setId(String name) {
        this.Id = Id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getAge(){return age;}
    public void setAge(String age){this.age=age;}
    public String getHigh(){return high;}
    public void setHigh(String high){this.high=high;}
    public String getWeigh(){return weigh;}
    public void setWeigh(String weigh){this.weigh=weigh;}
    public String getGender(){return gender;}
    public void setGender(String gender){this.gender=gender;}
    @Override
    public String toString() {
        return "User{" +
                "Id='" + Id + '\'' +
                ",name='" + name + '\'' +
                ", password='" + password + '\'' +
                ",age='"+age+'\''+
                ",high='"+high+'\''+
                ",weigh='"+weigh+'\''+
                ",gender='"+gender+'\''+
                '}';
    }
}

