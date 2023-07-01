import java.util.Arrays;
import java.util.Scanner;

public class encryption {
  public static void main(String[] args) {
    //Ask for text and code to encrypt, any ascii128 char
    Scanner scan = new Scanner(System.in);
    System.out.println("Enter your text to encrypt.");
    String text = scan.nextLine();
    System.out.println("Enter your Code. It has to be 4 numbers.");
    String password = scan.nextLine();
    
    //convert text into int array (decimal)
    int[] workspace = new int[(int) (Math.ceil((float)(text.length()) / 2) * 2)]; 
    convertToWorkspace(text, workspace);
    //convert int array to binary
    int[] binaryWorkspace = new int[workspace.length * 7];
    convertToBinary(workspace, binaryWorkspace);
    //convert code to binary
    int[] binaryCode = new int [14];
    convertCodeToBinary(password, binaryCode);
    //encrypt by repeatedly pairing up blocks of the binary text with the code using xor
    int[] encryptedBinary = new int [binaryWorkspace.length];
    xorCipher(binaryCode, binaryWorkspace, encryptedBinary);
    //convert encrypted binary to decimal
    int[] encryptedDec = new int[encryptedBinary.length / 7];
    binaryToDec(encryptedBinary, encryptedDec);
    //convert every decimal to 2 digit duodecimal (max duodecimal is bb = 143)
    char[] encryptedChar = new char[encryptedDec.length * 2];
    for(int i = 0; i < encryptedDec.length * 2; i++) {
      encryptedChar[i] = '0';
    }
    workspaceToChar(encryptedDec, encryptedChar);
    //print encrypted bits in duodecimal
    System.out.println("The encrypted text is: " + String.valueOf(encryptedChar));
  }
    
  public static void convertToWorkspace(String text, int[] workspace) {
    for(int i = 0; i < workspace.length; i++) {
      
      if(i >= text.length()) {
        workspace[i] = 0;
      }else {
        workspace[i] = (int)(text.charAt(i) - 31);
        if (workspace[i] > 127) {
          workspace[i] = 0;
        }
      }
    }
  }
  
  public static void convertToBinary(int[] workspace, int[] binaryWorkspace) {
    
    for(int i = 0; i < workspace.length; i++) {
      int n = workspace[i];
      for(int a = 6; a >= 0; a--) {
        binaryWorkspace[a + (i * 7)] = n % 2;
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
  
  public static void xorCipher(int[] binaryCode, int[] binaryWorkspace, int[] encryptedBinary) {
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
  
  public static void binaryToDec(int[] encryptedBinary, int[] encryptedDec) {
    for(int i = 0; i < encryptedDec.length; i++) {
      int res = encryptedBinary[i * 7];
      for(int j = 1; j < 7; j++) {
        res = 2*res + encryptedBinary[i*7 + j];
      }
      encryptedDec[i] = res;
    }
  }
  
  public static void workspaceToChar(int[] encryptedDec, char[] encryptedChar) {
    for(int i = 0; i < encryptedDec.length; i++) {
      int n = encryptedDec[i];
      for(int j = 1; n > 0; j--) {
        if(n % 12 > 9) {
          if(n % 12 == 10) {
            encryptedChar[i*2  + j] = 'a';
          }else {
            encryptedChar[i*2  + j] = 'b';
          }
        }else {
          encryptedChar[i*2  + j] = (char)(n % 12  + 48);
        }
        n = n / 12;
      }
    }
  }
}
