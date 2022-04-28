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
public class MedicalRecord{
    String data;
    Timestamp time;
    String hospital_name="self";
    String aadhaar_no;//the attribute to be used for zkp
    MedicalRecord(){
          
    }
    MedicalRecord(String data,String aadhar_no){
     this.data=data;
     this.aadhaar_no=aadhar_no;
 }
    MedicalRecord(String data,String hospital_name,String aadhar_no){
         this.data=data;
         this.hospital_name=hospital_name;
         this.aadhaar_no=aadhar_no;
         Date d=new Date();
         this.time=new Timestamp(d.getTime());
    }
 
    void printTimeStamp(){
        System.out.println(time);
    }
    void printMedicalData(){
        System.out.println("medical record stored: " +"{"+data+"}");
        System.out.println("data validated by: " +hospital_name);
    }
 
 }