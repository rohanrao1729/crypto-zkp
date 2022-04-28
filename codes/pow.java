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

public class pow {
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
