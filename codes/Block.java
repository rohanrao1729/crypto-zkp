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

public class Block{
	List<Transaction> li;
	Hash prev;
	Hash curr;
	Hash merkelRoot;
	// BlockHeader bh;
	Block hsPtr;
	int confirmations=0;
    String nonce;
	Timestamp tm;
  int height;
	int CAP=3;
	Block(){
        this.li=new ArrayList<>();
	}

	Block(Hash prev,Block prevBl){
		this.prev=prev;
		this.hsPtr=prevBl;
		this.li=new ArrayList<>();
    if(prevBl!=null)this.height=prevBl.height+1;
	}


   int getCapacity(){
    return this.CAP;
   }

	//adds the transaction to the current block if transaction is valid and 
	//current block is not full 
	//if the current block is full return false
	void addTransaction(Transaction t) throws NoSuchAlgorithmException{
            this.li.add(t);
            
	}
	//prints info of current block
    void printBlockInfo(){
    	System.out.println("Block Hash:"+this.curr.str);
    	System.out.println("Block merkelRoot:"+this.merkelRoot);
    	// System.out.println("Block Creation time:"+this.tm);
    	System.out.println("Block confirmations"+this.confirmations);
        System.out.println("POW or nonce value"+this.nonce);

    }
    //pow
    String computeNonce() throws NoSuchAlgorithmException,UnsupportedEncodingException{
        int non=0;
        System.out.println("coming-3");
        String now=prev+""+non;
        System.out.println(now);
        System.out.println(new Hash(now).str);
        Hash h=new Hash(now);
        int cnt=100;
        pow ob=new pow();
        
        this.nonce=ob.solveChallenge(this.prev.str, 4);
        System.out.println("nonce:"+this.nonce);
        
        return this.nonce;
    }
    void computeHeader(Hash prev) throws NoSuchAlgorithmException,UnsupportedEncodingException{
        this.merkelRoot=merkelRootCalc(this.li);
        Date d=new Date();
        this.prev=prev;
        this.tm=new Timestamp(d.getTime());
        System.out.println("coming-2");
        this.computeNonce();
        System.out.println("coming-4");
        this.confirmations+=1;
        this.curr=new Hash(this);
        
    }

    //implementation of merkelRoot calculation in O(nlogn)
	Hash merkelRootCalc(List<Transaction> li) throws NoSuchAlgorithmException{
          List<Hash> arr=new ArrayList<>();
          // to write ie hash of each transaction
          //arr.get(i) --->leaf hash

          if(li.size()==0)return new Hash("abndjkwjkjdwodwow");
          for(int i=0;i<li.size();i++){
          	arr.add(new Hash(li.get(i)));
          }
          while(arr.size()>1){
          	// System.out.println(arr);
          	if(arr.size()%2==1){
                arr.add(arr.get(arr.size()-1));
          	}
          	// System.out.println(arr);
          	// List<Hash> al=new ArrayList<>();
          	for(int i=0;i<arr.size();i+=2){
          		// al.add(hashComp(arr.get(i),arr.get(i+1)));
          		arr.set(i/2,hashComp(arr.get(i),arr.get(i+1)));
          	}
          	int n=arr.size();
          	for(int i=n-1;i>=n/2;i--)arr.remove(arr.size()-1);

          }
          return arr.get(0);
	}
	Hash hashComp(Hash A,Hash B) throws NoSuchAlgorithmException{
        
        StringBuilder sb=new StringBuilder();
        sb.append(A.str);
        sb.append(B.str);
        Hash ans=new Hash(sb.toString());
        return ans;
	}

}
