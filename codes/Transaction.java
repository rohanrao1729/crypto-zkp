package codes;
import codes.*;
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


public class Transaction {
        User us;
        MedicalRecord mr;
        boolean isValid;
        Transaction(User us,MedicalRecord mr){
            this.us=us;
            this.mr=mr;
        }
        void printTransactionData(){
            System.out.println("username :"+us.name);
            mr.printTimeStamp();
            mr.printMedicalData();
        }
        boolean verifyTransaction(){
            String str=this.mr.aadhaar_no;
            if(str.equals(null))str="1031";
            // Zkp ob=new Zkp(str);
            boolean f=false;
            // if(ob.performVerificationMultipleTime())f=true;
            this.isValid=f;
            return f;
        }
        
        
    }
    
    

