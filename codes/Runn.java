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
public class Runn {
    public static void main(String []args) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        //either register user or login user
       int tc=10;
       Users users=new Users();
       User us=new User("default","default");
       TransactionPool txPool=new TransactionPool();
       Nodes nodes=new Nodes();
       Block proposal_block;
       Transaction t;
       Node nd;
       
       txPool.printAll();
      
       Scanner sc=new Scanner(System.in);
        while(tc-->0){
          System.out.println("enter 1 to register (or) 2 to login:");
        
         
          int n=sc.nextInt();
          boolean LoggedIn=false;
          
          if(n==1){
            System.out.println("username:");
            String name=sc.next();
            System.out.println("set password:");
            String pass=sc.next();
              
              us=users.registerUser(name,pass);
              LoggedIn=true;
              
          }else{
            System.out.println("username:");
            String name=sc.next();
            System.out.println("enter password:");
            String pass=sc.next();
            if(users.LoginUser(name, pass)){
                System.out.println("Logged in successfully");
                LoggedIn=true;
                us=users.getUser(name);
            }else{
                System.out.println("invalid username/password");
            }
          }
          if(LoggedIn){
              System.out.println("enter 1 to view any user:");
              System.out.println("enter 2 to list blockchain records stored:");
              System.out.println("enter 3 to add medical records to safe store:");
              System.out.println("enter 4 to logout:");
              System.out.println("enter 5 to print blockchain over a particular node:");
              System.out.println("enter 6 to mine a block over a node:");
              System.out.println("enter 7 to add a node:");
              System.out.println("enter 8 to print data on all nodes:");
              System.out.println("enter 9 to print trxs data on all nodes:");
              int x=sc.nextInt();
              if(x==1){
                //   String 
                  users.viewUser(us);
              }else if(x==2){
                  //print blockchain;
              }else if(x==3){
                  System.out.println("enter no of record willing to add:");
                  int no_of_recs=Integer.parseInt(sc.next());
                  MedicalRecord mr;
                  while(no_of_recs-->0){
                      System.out.println("enter the data to be in record:");
                      String newdata=sc.next();
                      String data="";
                      while(newdata.equals("@@")==false){
                      data=data+" "+newdata;
                      newdata=sc.next();
                      }
                  
                      //need to change here
                      System.out.println("enter name of hospital:");
                      String h_name=sc.next();
                      System.out.println("enter your aadhar_no(not shared with server) we use zkp:");
                      String aadhar_no=sc.next();
                      mr=new MedicalRecord(data,h_name,aadhar_no);  
                      t=new Transaction(us,mr);
                      t.verifyTransaction();
                      // if(t.isValid){
                          
                      // }
                      txPool.addTransaction(t);
                      txPool.printAll();
                      users.addTransaction(us, t);
                      System.out.println(us.name);
                      
                      users.viewUser(us);
                  }
              }else if(x==4){
                  //logout functionality
              }else if(x==5){
                 //get the name of node
                //retrieve the node
                //print the blockchain
                System.out.println("Enter name of the node:");
                String nodeName=sc.next();
                System.out.println(nodes.getNode(nodeName).bc.li.size());
                nodes.getNode(nodeName).bc.printBlockchainHeaders();
              }else if(x==6){
                  System.out.println("Enter name of the node:");
                  String nodeName=sc.next();
                  nd=nodes.getNode(nodeName);
                  proposal_block=nd.proposeBlock(txPool);
                  // proposal_block.printBlockInfo();
                  nodes.broadcastBlock(proposal_block);
                  System.out.println(nodes.li.size());
                  txPool.printAll();
                  if(nodes.broadcastBlock(proposal_block)){
                      System.out.println("added&&&&&&&&&&&&&&&&&&&&&&&&");
                      nodes.addNewBlock(proposal_block);
                      
                  }
                  

              }else if(x==7){
                  System.out.println("give a name to the node to be added:");
                  System.out.println("Enter name of the node:");
                  String nodeName=sc.next();
                  nodes.addNode(nodeName);
              }else if(x==8){
                  nodes.printAllNodes();
              }else if(x==9){
                  nodes.printAllTransactionsNodes();
              }
          }
        }

  }
}
