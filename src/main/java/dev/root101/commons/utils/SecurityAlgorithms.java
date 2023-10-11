package dev.root101.commons.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Service;

@Service
public class SecurityAlgorithms {

    private Cipher cipher;

    private String aesSecretKey;

    public SecurityAlgorithms(String aesSecretKey) {
        this.aesSecretKey = aesSecretKey;

        final String ALGO = "AES/CBC/PKCS5Padding";
        try {
            cipher = Cipher.getInstance(ALGO);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.out.println("Error iniciando el Cipher" + e.getMessage());
        }
    }

    public byte[] cipher(String text) throws Exception {
        return cipher(text.getBytes());
    }

    public byte[] cipher(byte[] text) throws Exception {
        byte[] passPadded = hash256(aesSecretKey.getBytes());

        byte sec[] = new byte[16];
        System.arraycopy(passPadded, 0, sec, 0, sec.length);//primeros 16  bytes del sha-256 para la clave secreta
        SecretKey secret = new SecretKeySpec(sec, "AES");

        byte[] iv = new byte[16];
        System.arraycopy(passPadded, 16, iv, 0, iv.length);//ultimos 16  bytes del sha-256 para iv
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, secret, ivSpec);
        return cipher.doFinal(text);
    }

    public byte[] decipher(byte[] text) throws Exception {
        byte[] passPadded = hash256(aesSecretKey.getBytes());

        byte sec[] = new byte[16];
        System.arraycopy(passPadded, 0, sec, 0, sec.length);//primeros 16  bytes del sha-256 para la clave secreta
        SecretKey secret = new SecretKeySpec(sec, "AES");

        byte[] iv = new byte[16];
        System.arraycopy(passPadded, 16, iv, 0, iv.length);//ultimos 16  bytes del sha-256 para iv
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.DECRYPT_MODE, secret, ivSpec);
        return cipher.doFinal(text);
    }

    public static byte[] hash256(byte[] text) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
            return mDigest.digest(text);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
