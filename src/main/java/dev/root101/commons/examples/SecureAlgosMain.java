package dev.root101.clean.core.examples;

import dev.root101.clean.core.utils.SecurityAlgorithms;

public class SecureAlgosMain {

    public static void main(String[] args) throws Exception {
        SecurityAlgorithms secure = new SecurityAlgorithms("aes secret key");

        String textToCipher = "Hiden text";

        byte[] cipherText = secure.cipher(textToCipher);
        
        byte[] decipherText = secure.decipher(cipherText);
        
        System.out.println(textToCipher.equals(new String(decipherText)));
    }
}
