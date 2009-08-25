/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cosmos.acacia.crypto;

import com.cosmos.base64.Base64Decoder;
import com.cosmos.base64.Base64Encoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Properties;
import javax.crypto.Cipher;

/**
 *
 * @author Miro
 */
public class CryptoUtils {

    private static final String CERTIFICATE_ALIAS = "certificate.alias";
    private static final String PRIVATE_KEY_PASSWORD = "private_key.password";
    private static final String KEY_STORE_FILE_NAME = "key_store.file_name";
    private static final String KEY_STORE_TYPE = "key_store.type";
    private static final String KEY_STORE_PASSWORD = "key_store.password";
    private static final String CIPHER_ALGORITHM_NAME = "cipher_algorithm_name";
    //
    private static CryptoUtils instance;
    //
    private String resourceFileName = "META-INF/acacia_crypto.properties";
    private String charsetName = "UTF-8";
    private Properties cryptoProperties;
    private KeyStore keyStore;
    private X509Certificate certificate;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private String cipherAlgorithmName;

    private CryptoUtils() {
    }

    public static CryptoUtils getInstance() {
        if(instance == null) {
            instance = new CryptoUtils();
        }

        return instance;
    }

    public String getCharsetName() {
        return charsetName;
    }

    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }

    public String getResourceFileName() {
        return resourceFileName;
    }

    public void setResourceFileName(String resourceFileName) {
        this.resourceFileName = resourceFileName;
    }

    private Properties getCryptoProperties() {
        if (cryptoProperties == null) {
            cryptoProperties = getProperties(Thread.currentThread().getContextClassLoader().getResourceAsStream(getResourceFileName()));
        }

        return cryptoProperties;
    }

    private Properties getProperties(InputStream inStream) {
        try {
            InputStreamReader reader = new InputStreamReader(inStream, getCharsetName());
            Properties properties = new Properties();
            properties.load(new BufferedReader(reader));
            return properties;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String getCertificateAlias() {
        return getCryptoProperties().getProperty(CERTIFICATE_ALIAS);
    }

    private String getPrivateKeyPassword() {
        return getCryptoProperties().getProperty(PRIVATE_KEY_PASSWORD);
    }

    private String getKeyStoreFileName() {
        return getCryptoProperties().getProperty(KEY_STORE_FILE_NAME);
    }

    private String getKeyStorePassword() {
        return getCryptoProperties().getProperty(KEY_STORE_PASSWORD);
    }

    private InputStream getKeyStoreInputStream() {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(getKeyStoreFileName());
    }

    private String getKeyStoreType() {
        return getCryptoProperties().getProperty(KEY_STORE_TYPE);
    }

    private String getCipherAlgorithmName() {
        if(cipherAlgorithmName == null) {
            cipherAlgorithmName = getCryptoProperties().getProperty(CIPHER_ALGORITHM_NAME);
        }

        return cipherAlgorithmName;
    }

    private KeyStore getKeyStore() {
        if (keyStore == null) {
            try {
                keyStore = KeyStore.getInstance(getKeyStoreType());
                InputStream inStream = getKeyStoreInputStream();
                keyStore.load(inStream, getKeyStorePassword().toCharArray());
                inStream.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        return keyStore;
    }

    public X509Certificate getCertificate() {
        if (certificate == null) {
            try {
                certificate = (X509Certificate) getKeyStore().getCertificate(getCertificateAlias());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return certificate;
    }

    public PublicKey getPublicKey() {
        if (publicKey == null) {
            publicKey = getCertificate().getPublicKey();
        }

        return publicKey;
    }

    private PrivateKey getPrivateKey() {
        if (privateKey == null) {
            try {
                privateKey = (PrivateKey) getKeyStore().getKey(getCertificateAlias(), getPrivateKeyPassword().toCharArray());
            } catch(Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        return privateKey;
    }

    public byte[] encrypt(byte[] bytes) {
        try {
            Cipher cipher = Cipher.getInstance(getCipherAlgorithmName());
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey());
            return cipher.doFinal(bytes);
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public byte[] decrypt(byte[] bytes) {
        try {
            Cipher cipher = Cipher.getInstance(getCipherAlgorithmName());
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
            return cipher.doFinal(bytes);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public String encryptBase64(String source) {
        return encryptBase64(source, false);
    }

    public String encryptBase64(String source, boolean chunked) {
        return encryptBase64(source.getBytes(), chunked);
    }

    public String encryptBase64(byte[] bytes, boolean chunked) {
        try {
            return Base64Encoder.toBase64String(encrypt(bytes), chunked);
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public byte[] decryptBase64(String base64String) {
        try {
            return decrypt(Base64Decoder.toByteArray(base64String));
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public byte[] decryptBase64(InputStream inStream) {
        try {
            return decrypt(Base64Decoder.toByteArray(inStream));
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Properties loadProperties(InputStream inStream) {
        Properties properties = getProperties(inStream);
        for(String key : properties.stringPropertyNames()) {
            String encryptedValue;
            if((encryptedValue = properties.getProperty(key)) != null && encryptedValue.length() > 0) {
                String value = new String(decryptBase64(encryptedValue));
                properties.setProperty(key, value);
            }
        }

        return properties;
    }

    public void storeProperties(Properties properties, OutputStream outStream, String comment) {
        properties = (Properties) properties.clone();
        for(String key : properties.stringPropertyNames()) {
            String value;
            if((value = properties.getProperty(key)) != null && value.length() > 0) {
                String encryptedValue = encryptBase64(value);
                properties.setProperty(key, encryptedValue);
            }
        }
        try {
            OutputStreamWriter writer = new OutputStreamWriter(outStream, getCharsetName());
            properties.store(writer, comment);
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
