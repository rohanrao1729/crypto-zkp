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

class pow {
    MessageDigest messageDigest;
	private String challengeText;
	private int timeToSolveMS;
	private String successfulNonce;
	private int successfulNonceInt;
	private String successfulHash;
    public pow() throws NoSuchAlgorithmException {
		// Ready the MessageDigest
		this.messageDigest = MessageDigest.getInstance("SHA-256");
	}
    
	/**
	 * This method will take the challenge text, and brute force find an
	 * appended nonce which will solve a SHA256 hash resulting in n leading
	 * zeros (base 64).
	 * 
	 * @param challengeText
	 *            String. Text used in the challenge.
	 * @param leadingZeros
	 *            Integer. Number of desired leading zeros (base 64). The larger
	 *            the number, the more complex the task. Be careful.
	 * @return String The nonce used to solve the problem.
	 * @throws UnsupportedEncodingException
	 */
	public String solveChallenge(String challengeText, int leadingZeros) throws UnsupportedEncodingException {
		this.challengeText = challengeText;
		String hashPrefixGoal = "";
        for(int i=0;i<leadingZeros;i++){
            hashPrefixGoal+="0";
        }

		// Measure the time to succeed
		long startTime = System.nanoTime();
		NumberFormat formatter = new DecimalFormat();
		formatter = new DecimalFormat("0.#####E0");

		int nonceInteger = 0;
		String currentNonce = getHexNonceFromInteger(nonceInteger);
		String currentHash = hashSHA256(challengeText + currentNonce);
		while (!currentHash.substring(0, leadingZeros).equalsIgnoreCase(hashPrefixGoal)) {
			nonceInteger += 1;
			currentNonce = getHexNonceFromInteger(nonceInteger);
			currentHash = hashSHA256(challengeText + currentNonce);

			if (nonceInteger % 500000 == 0) {
				// For seeing status
				System.out.println(" # of nonces tried: " + formatter.format(nonceInteger));
			}
		}

		long estimatedTime = System.nanoTime() - startTime;

		// Capture as instance vars to avoid being lost.
		this.successfulNonce = currentNonce;
		this.successfulNonceInt = nonceInteger;
		this.successfulHash = currentHash;
		this.timeToSolveMS = (int) Math.floor(estimatedTime / 1000000.0);

		return this.successfulNonce;
	}

	/**
	 * Method used to hash clearText using SHA256. Can be used to verify output
	 * from solveChallenge() since this same method is used.
	 * 
	 * @param clearText
	 *            String. Text to be hashed.
	 * @return String. 64 chars of SHA256 applied to clearText
	 * @throws UnsupportedEncodingException
	 */
	public String hashSHA256(String clearText) throws UnsupportedEncodingException {
		this.messageDigest.update(clearText.getBytes("UTF-8")); // Change
																// this to
																// "UTF-16"
																// if needed
		byte[] digest = this.messageDigest.digest();
		return String.format("%064x", new java.math.BigInteger(1, digest));
	}
	
	private byte[] toBytes(int i) {
		byte[] result = new byte[4];

		result[0] = (byte) (i >> 24);
		result[1] = (byte) (i >> 16);
		result[2] = (byte) (i >> 8);
		result[3] = (byte) (i /* >> 0 */);

		return result;
	}

	private String getHexNonceFromInteger(int nonceInt) {
		return String.format("%x", new java.math.BigInteger(1, toBytes(nonceInt)));
	}

	public String getChallengeText() {
		return this.challengeText;
	}

	public int getTimeToSolveMS() {
		return this.timeToSolveMS;
	}

	public String getSuccessfulNonce() {
		return this.successfulNonce;
	}

	public int getSuccessfulNonceInt() {
		return successfulNonceInt;
	}

	public String getSuccessfulHash() {
		return this.successfulHash;
	}

    
}


public class Runn{
    public static void main(String []args) throws NoSuchAlgorithmException, UnsupportedEncodingException{
          //either register user or login user
         int tc=1000;
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
class Transaction {
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
        Zkp ob=new Zkp(this);
        boolean f=false;
        if(ob.performVerificationMultipleTime())f=true;
        this.isValid=f;
        return f;
    }
    
    
}

class Zkp{
    Transaction t;
    //prover has x now need to make verifier believe that it has x without sending x;
    
    int NO_OF_TIMES=1; 
    
    Zkp(Transaction t){
      this.t=t;
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
    boolean performVerificationMultipleTime(){
        for(int i=0;i<NO_OF_TIMES;i++){
            if(verification())return false;
        }
        return true;
    }
    boolean verification(){
        sender snd;
        verifier vr;
        snd=new sender(this.t);
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
         sender(Transaction t){
             this.x=Long.parseLong(t.mr.aadhaar_no);
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
        boolean finalverify(){
            long comp=pow(g,s,p);
            long sentfromsender=(h*pow(y,b,p))%p;
            // if(comp==)
            System.out.println(comp+" "+sentfromsender);
            if(comp==sentfromsender){return true;}
            return false;
        }


    }

}
class MedicalRecord{
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

class Users{
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
class User{
    String name;
    String password;

    User(){

    }
    User(String name,String password){
        this.name=name;
        this.password=password;

    }
    
    
}
class Block{
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
class Hash{
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
//only verified transactions stay in the transaction pool;

//each verifier would want to compete to create a block and add it to the blockchain
class Blockchain{
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
class Nodes{
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
class Node{
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


