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
public class Hash{
	String str;
	Hash(){

	}
	// Hash(Object o){
	// 	str=String.valueOf(o.hashCode());
	// }
	Hash(String st) throws NoSuchAlgorithmException{
		str=HashAlgo.func(st);
	}
	Hash(Transaction tr) throws NoSuchAlgorithmException{
	   StringBuilder sb=new StringBuilder();
	   sb.append(tr.us.name);
	   sb.append(tr.mr.data);
	   sb.append(tr.mr.hospital_name);
	   // System.out.println(sb);
       str=HashAlgo.func(sb.toString());
	}
	Hash(Block b) throws NoSuchAlgorithmException{
	  StringBuilder sb=new StringBuilder();
	  // System.out.println(b.hsPtr);
    // System.out.println(b.merkelRoot);
    // System.out.println(b.tm);
    // System.out.println(b.prev);
    // System.out.println(b.prev.str);
    // System.out.println(b.merkelRoot);
    
      sb.append(b.prev.str);
    
    sb.append(b.merkelRoot.str);
  

      str=HashAlgo.func(sb.toString());
	}
}
class HashAlgo{
	
	
	HashAlgo(){
       
	}
	

    public static String func(String str) throws NoSuchAlgorithmException{
    	return toHexString(getSHA(str));
    }
	public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    { 
        // Static getInstance method is called with hashing SHA 
        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
  
        // digest() method called 
        // to calculate message digest of an input 
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8)); 
    }
    
    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation 
        BigInteger number = new BigInteger(1, hash); 
  
        // Convert message digest into hex value 
        StringBuilder hexString = new StringBuilder(number.toString(16)); 
  
        // Pad with leading zeros
        while (hexString.length() < 32) 
        { 
            hexString.insert(0, '0'); 
        } 
  
        return hexString.toString(); 
    }

}
