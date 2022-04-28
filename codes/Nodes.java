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

public class Nodes{
    List<Node> li;
    HashMap<String,Node> mp;
    Nodes(){
      li=new ArrayList<>();
      mp=new HashMap<>();
    }
    Node getNode(String name){
        return mp.get(name);
    }
    Node addNode(String name){
        Node n=new Node(name);
        
        n.bc=this.copyBlockchain();
        li.add(n);
        mp.put(name,n);
        return n;
    }
    boolean broadcastBlock(Block proposal_block) throws NoSuchAlgorithmException{
     for(Node n:this.li){
         n.verifyBlock(proposal_block);
     }
     if(proposal_block.confirmations>=3||proposal_block.confirmations>=this.li.size()/2){
         return true;
     }
     return false;
    }
    void addNewBlock(Block proposal_block){
        for(int i=0;i<li.size();i++){
            System.out.println("adding block to node-"+(i+1));
            this.li.get(i).bc.addBlock(proposal_block);
        }
    }
    Blockchain copyBlockchain(){
       //using longest chain rule;
     
       if(li.size()==0)return new Blockchain();
       Blockchain bck=li.get(0).bc;
       for(int i=0;i<li.size();i++){
         if(li.get(i).bc.li.size()>bck.li.size()){
             bck=li.get(i).bc;
         }
     }
     return bck;
    }
    void printAllNodes(){
        for(int i=0;i<this.li.size();i++){
            System.out.println(this.li.get(i).name);
            Blockchain bc_curr=this.li.get(i).bc;
            for(int j=0;j<bc_curr.li.size();j++){
                bc_curr.li.get(j).printBlockInfo();
            }
        }
    }
    void printAllTransactionsNodes(){
     for(int i=0;i<this.li.size();i++){
         System.out.println(this.li.get(i).name);
         Blockchain bc_curr=this.li.get(i).bc;
         for(int j=0;j<bc_curr.li.size();j++){
             System.out.println("Block No: "+j);
             for(int k=0;k<bc_curr.li.get(j).li.size();k++){
                 bc_curr.li.get(j).li.get(k).printTransactionData();
             }
         }
     }
 }
 
 }
 class TransactionPool{
     Deque<Transaction> dq;
     HashSet<Transaction> hs;
     List<Transaction> trlist;
     Transaction dummyTransaction;
     TransactionPool(){
         dq=new LinkedList<>();
         hs=new HashSet<>();
         trlist=new ArrayList<>();
         dummyTransaction=new Transaction(new User("hello","hello"),new MedicalRecord("hello","0123"));
     }
     void addTransaction(Transaction t){
         //  t.verifyTransaction();
         //  if(t.isValid){
              
         //  }
         dq.push(t);
         hs.add(t);
         trlist.add(t);
     }
     void removeTransaction(){
         hs.remove(dq.peekFirst());
         dq.removeFirst();
         
     }
     void printAll(){
         System.out.println(trlist.size()+"********************");
         for(Transaction t:trlist){
             t.printTransactionData();
         }
     }
     
 }