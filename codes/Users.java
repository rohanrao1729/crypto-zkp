package codes;
import java.io.*;
import java.lang.*;
import java.util.*;
import java.security.*;
import java.sql.Timestamp;
import java.math.BigInteger; 
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Users{
    List<User> al;
    HashMap<String,String> hm;
    HashMap<User,List<Transaction>> db;
    HashMap<String,User> mapping;
    Users(){
        al=new ArrayList<>();
        hm =new HashMap<>();
        db=new HashMap<>();
        mapping=new HashMap<>();
    }
    User getUser(String s){
        return mapping.get(s);
    }
    User registerUser(String name,String password){
       User u=new User(name,password);
       al.add(u);
       hm.put(name,password);
       mapping.put(name,u);
    //    System.out.println("hello"+" "+hm.get(name));
       db.put(u,new ArrayList<>());
       return u;
    }
    boolean LoginUser(String name,String oldpass){
        System.out.println(name+" "+oldpass+" "+hm.get(name));
        if(hm.containsKey(name)==false)return false;
        if(hm.get(name).equals(oldpass))return true;
        return false;
    }
    void changeLoginPassword(String name,String oldpass,String newpass){
        if(hm.get(name).equals(oldpass)){
           hm.put(name,newpass);
           System.out.println("password updated");
        }else{
            System.out.println("error wrong password entered");
        }
    }
    void addTransaction(User u,Transaction t){
        //before adding always verify transaction using zkp
        db.get(u).add(t);
    }
    void viewUser(User u){
        if(db.containsKey(u)){
            for(int i=0;i<db.get(u).size();i++){
                db.get(u).get(i).printTransactionData();
            }
        }else{
            System.out.println("no such user exists/invalid data");
        }
    }

}