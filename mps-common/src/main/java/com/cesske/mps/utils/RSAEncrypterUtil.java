package com.cesske.mps.utils;

import com.cesske.mps.constants.RSAEncrypterConstants;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAEncrypterUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RSAEncrypterUtil.class);
    public static final String KEY_ALGORITHM = "RSA";
    public static final String PUBLIC_KEY = "RSAPublicKey";
    public static final String PRIVATE_KEY = "RSAPrivateKey";
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;
    private static final int RSA_KEY_SIZE = 1024;

    private RSAEncrypterUtil() {
    }

    public static Map<String, Object> genKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
            Map<String, Object> keyMap = new HashMap(2);
            keyMap.put("RSAPublicKey", publicKey);
            keyMap.put("RSAPrivateKey", privateKey);
            return keyMap;
        } catch (Exception var5) {
            LOGGER.error("gen key pair failed", var5);
            return null;
        }
    }

    public static byte[] encryptByPrivateKey(byte[] data, Key privateKey) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(1, privateKey);
            byte[] var5 = encryptBytesByKey(data, out, cipher);
            return var5;
        } catch (Exception var15) {
            LOGGER.error("encrypt by privateKey failed", var15);
        } finally {
            try {
                out.close();
            } catch (IOException var14) {
                LOGGER.error("close ByteArrayOutputStream failed", var14);
            }

        }

        return null;
    }

    public static byte[] decryptByPrivateKey(byte[] encryptedData, Key privateKey) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(2, privateKey);
            byte[] var5 = decrytpBytesByKey(encryptedData, out, cipher);
            return var5;
        } catch (Exception var15) {
            LOGGER.error("decrypt by privateKey failed", var15);
        } finally {
            try {
                out.close();
            } catch (IOException var14) {
                LOGGER.error("close ByteArrayOutputStream failed", var14);
            }

        }

        return null;
    }

    public static byte[] encryptByPublicKey(byte[] data, Key publicKey) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(1, publicKey);
            byte[] var5 = encryptBytesByKey(data, out, cipher);
            return var5;
        } catch (Exception var15) {
            LOGGER.error("encrypt by publicKey failed", var15);
        } finally {
            try {
                out.close();
            } catch (IOException var14) {
                LOGGER.error("close ByteArrayOutputStream failed", var14);
            }

        }

        return null;
    }

    public static byte[] decryptByPublicKey(byte[] encryptedData, Key publicKey) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(2, publicKey);
            byte[] var5 = decrytpBytesByKey(encryptedData, out, cipher);
            return var5;
        } catch (Exception var15) {
            LOGGER.error("decrypt by publicKey failed", var15);
        } finally {
            try {
                out.close();
            } catch (IOException var14) {
                LOGGER.error("close ByteArrayOutputStream failed", var14);
            }

        }

        return null;
    }

    public static String getPrivateKey(Key privateKey) {
        return getString(privateKey);
    }

    public static Key getPrivateKey(String key) {
        byte[] keyBytes = Base64.decodeBase64(key.getBytes(RSAEncrypterConstants.DEFAULT_CHARSET_OBJ));
        return getPrivateKey(keyBytes);
    }

    public static Key getPrivateKey(byte[] key) {
        try {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (Exception var3) {
            LOGGER.error("get privateKey failed", var3);
            return null;
        }
    }

    public static String getPublicKey(Key key) {
        return getString(key);
    }

    public static Key getPublicKey(String key) {
        byte[] keyBytes = Base64.decodeBase64(key.getBytes(RSAEncrypterConstants.DEFAULT_CHARSET_OBJ));
        return getPublicKey(keyBytes);
    }

    public static Key getPublicKey(byte[] publicKey) {
        try {
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(x509KeySpec);
        } catch (Exception var3) {
            LOGGER.error("get publicKey failed", var3);
            return null;
        }
    }

    public static Key getDERPublicKey(String publicKey) {
        byte[] temp = Base64.decodeBase64(publicKey);
        return getDERPublicKey(temp);
    }

    public static Key getDERPublicKey(byte[] publicKey) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
            Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(publicKey));
            return certificate.getPublicKey();
        } catch (Exception var3) {
            LOGGER.error("get publicKey failed", var3);
            return null;
        }
    }

    private static byte[] encryptBytesByKey(byte[] data, ByteArrayOutputStream out, Cipher cipher) throws IllegalBlockSizeException, BadPaddingException {
        int inputLen = data.length;

        for(int offSet = 0; inputLen - offSet > 0; offSet += 117) {
            byte[] cache;
            if (inputLen - offSet > 117) {
                cache = cipher.doFinal(data, offSet, 117);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
        }

        return out.toByteArray();
    }

    private static byte[] decrytpBytesByKey(byte[] encryptedData, ByteArrayOutputStream out, Cipher cipher) throws IllegalBlockSizeException, BadPaddingException {
        int inputLen = encryptedData.length;

        for(int offSet = 0; inputLen - offSet > 0; offSet += 128) {
            byte[] cache;
            if (inputLen - offSet > 128) {
                cache = cipher.doFinal(encryptedData, offSet, 128);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
        }

        return out.toByteArray();
    }

    private static String getString(Key key) {
        return new String(Base64.encodeBase64(key.getEncoded()), RSAEncrypterConstants.DEFAULT_CHARSET_OBJ);
    }
}
