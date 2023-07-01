//not commented and variable names dont make sense
//same thing as encryption, just backwards

import java.util.Arrays;
import java.util.Scanner;

public class decryption {
  public static void main(String args[]) {
    Scanner scan = new Scanner(System.in);
    System.out.println("Enter your text to decrypt.");
    String encryptedText = scan.nextLine();
    
    System.out.println("Enter your Code. It has to be 4 numbers.");
    String password = scan.nextLine();
    
    int[] decimalEncrypted = new int[encryptedText.length() / 2];
    duodecimalToDecimal(encryptedText, decimalEncrypted);
    
    int [] binaryEncrypted = new int[decimalEncrypted.length * 7];
    decimalToBinary(decimalEncrypted, binaryEncrypted);
    
    int[] binaryCode = new int[14];
    convertCodeToBinary(password, binaryCode);

    int[] binaryWorkspace = new int[binaryEncrypted.length];
    xorDeCipher(binaryCode, binaryEncrypted, binaryWorkspace);
    
    int[] decWorkspace = new int[binaryWorkspace.length/7];
    convertToDec(binaryWorkspace, decWorkspace);
    
    String output = decToString(decWorkspace);
    System.out.println("Encrypted Text: " + output);
  }
  
  public static void duodecimalToDecimal(String encryptedText, int[] decimalEncrypted) {
    for(int i = 0; i < decimalEncrypted.length; i++) {
      int decimalTemp = 0;
      if(encryptedText.charAt(i*2) == 'a') {
        if(encryptedText.charAt(i*2 + 1) == 'a') {
          decimalTemp = (10 * 12) + (10 * 1);
        }else if(encryptedText.charAt(i*2 + 1) == 'b'){
            decimalTemp = (10 * 12) + (11 * 1);
          }else {
            decimalTemp = (10 * 12) + ((encryptedText.charAt(i*2 + 1) - 48) * 1);
          }
      }else if(encryptedText.charAt(i*2) == 'b') {
          if(encryptedText.charAt(i*2 + 1) == 'a') {
            decimalTemp = (11 * 12) + (10 * 1);
          }else if(encryptedText.charAt(i*2 + 1) == 'b'){
              decimalTemp = (11 * 12) + (11 * 1);
            }else {
              decimalTemp = (11 * 12) + ((encryptedText.charAt(i*2 + 1) - 48) * 1);
            }
        }else if (encryptedText.charAt(i*2 + 1) == 'a') {
            decimalTemp = ((encryptedText.charAt(i*2) - 48) * 12) + (10 * 1);
          }else if (encryptedText.charAt(i*2 + 1) == 'b') {
              decimalTemp = ((encryptedText.charAt(i*2) - 48) * 12) + (11 * 1);
            }else {
              decimalTemp = ((encryptedText.charAt(i*2) - 48) * 12) + ((encryptedText.charAt(i*2 + 1) - 48) * 1);
            }
      decimalEncrypted[i] = decimalTemp;
    }
  }
  
  public static void decimalToBinary(int[] decimalEncrypted, int[] binaryEncrypted) {
    for(int i = 0; i < decimalEncrypted.length; i++) {
      int n = decimalEncrypted[i];
      for(int a = 6; a >= 0; a--) {
        binaryEncrypted[a + (i * 7)] = n % 2;
        n = n / 2;
      }
    }
  }
  
  public static void convertCodeToBinary (String password, int[] binaryCode) {
    int n = Integer.parseInt(password);
    for(int i = 13 ; n > 0; i--) {
      binaryCode[i] = n % 2;
      n = n / 2;
    }
  }
  
  public static void xorDeCipher(int[] binaryCode, int[] binaryWorkspace, int[] encryptedBinary) {
    for(int i = 0; i < binaryWorkspace.length / 14; i++) {
      for(int n=0; n < 14; n++) {
        encryptedBinary[n + (i * 14)] = binaryCode[n] ^ binaryWorkspace[n + (i * 14)];
      }
      codeShuffle(binaryCode);
    }
  }
  
  public static void codeShuffle(int[] binaryCode) {
    int[] temp = new int[binaryCode.length];
    temp[0] = binaryCode[7];
    temp[1] = binaryCode[12];
    temp[2] = binaryCode[6];
    temp[3] = binaryCode[5];
    temp[4] = binaryCode[2];  
    temp[5] = binaryCode[9];
    temp[6] = binaryCode[11];
    temp[7] = binaryCode[13];
    temp[8] = binaryCode[3];
    temp[9] = binaryCode[1];
    temp[10] = binaryCode[8];
    temp[11] = binaryCode[0];
    temp[12] = binaryCode[4];
    temp[13] = binaryCode[10];
    
    for(int i = 0; i < binaryCode.length; i++) {
      binaryCode[i] = temp[i];
    }
  }
  
  public static void convertToDec(int[] binaryWorkspace, int[] decWorkspace){
    for(int i = 0; i < decWorkspace.length; i++) {
      int n = 0;
      for(int a = 0; a < 7; a++) {
        n = n * 2;
        n += binaryWorkspace[a + (i * 7)];
      }
      decWorkspace[i] = n;
    }
  }
  
  public static String decToString(int[] decWorkspace){
    String output = "";
    for (int i: decWorkspace) {
      if (i > 0) {
        output += (char)(i + 31);
      }
    }
    return output; 
  }
}