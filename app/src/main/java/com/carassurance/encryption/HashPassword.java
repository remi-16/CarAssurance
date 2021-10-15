package com.carassurance.encryption;


import org.bouncycastle.util.encoders.Hex;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class HashPassword {


    public String hash(String password){

        String sha256hex;
        MessageDigest digest = null;
        byte[] hash;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algorithm not found");
            System.exit(-1);
        }

        hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        sha256hex = new String(Hex.encode(hash));

        System.out.println("Digest: "  + sha256hex );

        return  sha256hex ;

    }
}
