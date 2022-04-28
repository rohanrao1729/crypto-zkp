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
public class Node{
    Blockchain bc;
    String name;
    Node(){
        bc=new Blockchain();
    }
    Node(String name){
       bc=new Blockchain();
       this.name=name;
    }
    Block proposeBlock(TransactionPool txPool) throws NoSuchAlgorithmException,UnsupportedEncodingException{
        Block b=new Block();
        int cnt=0;
        List<Transaction> curr=new ArrayList<>();
        System.out.println("&&&&&&&&&&&&&&&&&&&"+txPool.trlist.size()+"&&&&&&&&&&");
        while(txPool.dq.size()>0&&cnt<b.CAP){
           cnt++;
           curr.add(txPool.dq.peekFirst());
           txPool.removeTransaction();
        }
        if(cnt<b.CAP){
            curr.add(txPool.dummyTransaction);
        }
        System.out.println("hello-2");
        for(int i=0;i<curr.size();i++){
            curr.get(i).printTransactionData();
            b.addTransaction(curr.get(i));
        }
        System.out.println("hello-1");
        Hash prev=new Hash("0000000");//for genesis block
        if(this.bc.li.size()>0){
            prev =this.bc.li.get(bc.li.size()-1).curr;
        }
        System.out.println("coming-1");
        b.computeHeader(prev);

        return b;
    
    }
    void addBlock(Block b){
        this.bc.li.add(b);

    }
    
    boolean verifyBlock(Block b) throws NoSuchAlgorithmException{
        if(new Hash(b.curr.str+""+b.nonce).str.startsWith("0000")){
            b.confirmations+=1;
            // this.addBlock(b);
            return true;
        }
        return false;

    }
    

}