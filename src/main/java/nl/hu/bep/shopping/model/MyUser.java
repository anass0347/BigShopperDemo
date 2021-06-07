package nl.hu.bep.shopping.model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class MyUser implements Principal {
    private String username;
    private String password;
    private String role;

    private static List<MyUser> allUsers;

    public MyUser(String un, String ps, String rl){
        username = un;
        password = ps;
        role = rl;
        allUsers = new ArrayList<>();
    }

    public static String validateLogin(String username, String password){
        for(MyUser user : allUsers){
            if(user.username.equals(username) && user.password.equals(password)){
                return user.role;
            }
        }
        return null;
    }

    public void addUser(MyUser user){
        allUsers.add(user);
    }

    public String getRole(){
        return role;
    }

    public static MyUser getUserByName(String naam){
        for(MyUser user :allUsers){
            if(user.username.equals(naam)){
                return user;
            }
        }
        return null;
    }

    public String getName(){
        return username;
    }


}
