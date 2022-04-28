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
public class Blockchain{
    List<Block> li;
    Blockchain(){
       li=new ArrayList<>();
    }
    void addBlock(Block b){
      System.out.println("adding block to blockchain:::::::::::::::");
        li.add(b);
    }
    void printBlockchainHeaders(){
        for(int i=0;i<li.size();i++){
            System.out.println("*****************************");
            System.out.println("Block no:"+i);
            li.get(i).printBlockInfo();
        }
    }
  }