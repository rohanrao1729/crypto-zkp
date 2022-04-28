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

public class Zkp {
    String str;
    //prover has x now need to make verifier believe that it has x without sending x;
    
    int NO_OF_TIMES=1; 
    
    Zkp(String x){
      this.str=x;
    }

    
    boolean performVerificationMultipleTime(){
        for(int i=0;i<NO_OF_TIMES;i++){
            if(verification())return false;
        }
        return true;
    }
    boolean verification(){
        sender snd;
        verifier vr;
        snd=new sender(str);
        vr=new verifier(snd);
        System.out.println(snd.x+""+snd.h+" "+snd.y);
        snd.setVerifier(vr);
        System.out.println("ZERO KNOWLEDGE PROOF");
        System.out.println("*********************______*******************");
        snd.sendyToverifier1();
        System.out.println("data from client to server computed at client");
        System.out.println(snd.x+" "+snd.h+" "+snd.y+" "+snd.r);
        vr.sendToSender1();
        System.out.println("data from server to client computed at server ie random bit");
        System.out.println(snd.b+" "+snd.h);

        snd.sendyToverifier2();
        System.out.println("compute function over secret x");
        System.out.println(snd.s);
        boolean flag=vr.finalverify();
        System.out.println("completed");
        return flag;
        
    }
    
    
    
    
    

}
class sender{

    long y=0;
    long x;
    long g=2;
    long p=11;
    long r;
    long h;
    long b;
    long s;
    verifier vr;
    sender(String aadhaar_no){
        this.x=Long.parseLong(aadhaar_no);
    }
    long pow(long a,long n,long mod){
       int ans=1;
       while(n>0){
           if(n%2==1){ans*=a;ans%=mod;}
           a*=a;
           a%=mod;
           n/=2;

       }
       return ans;
   }
    void setVerifier(verifier vr){
       this.vr=vr;
    }
    long aux(){
       this.r=(long) (Math.random()*(this.p-1));
       long val=pow(g,r,p);
       return val;
   }
    void computey(){
        this.y=pow(g,x,p);
    }
    long computeEmbedSecret(){
       long ans=0;
       
       ans=(this.b*x+this.r)%(p-1);
       this.s=ans;
       return ans;
  }
    
    void sendyToverifier1(){
       computey();
       this.h=aux();
       vr.y=this.y;
       vr.h=this.h;
    }
    void sendyToverifier2(){
       computeEmbedSecret();
       vr.s=this.s;

    }



}
class verifier{
   long h;
   long y;
   sender snd;
   long b;
   long s;
   long g=2;
   long p=11;
   verifier(sender s){
       this.snd=s;
   }
   long choosebitBob(){
       return (int)(Math.random()*2);
   }
   void sendToSender1(){
       choosebitBob();
       snd.b=b;
   }
   long pow(long a,long n,long mod){
       int ans=1;
       while(n>0){
           if(n%2==1){ans*=a;ans%=mod;}
           a*=a;
           a%=mod;
           n/=2;

       }
       return ans;
   }
   boolean finalverify(){
       long comp=pow(g,s,p);
       long sentfromsender=(h*pow(y,b,p))%p;
       // if(comp==)
       System.out.println(comp+" "+sentfromsender);
       if(comp==sentfromsender){return true;}
       return false;
   }


}