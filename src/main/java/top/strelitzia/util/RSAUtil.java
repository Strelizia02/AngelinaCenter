package top.strelitzia.util;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.*;


@Component
public class RSAUtil {

    //秘钥大小
    private final int KEY_SIZE = 1024;

    //后续放到常量类中去
    public String PRIVATE_KEY;
    public String PUBLIC_KEY;

    private KeyPair keyPair;

    private final BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();

    public RSAUtil() {
        //生成RSA，并存放
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", bouncyCastleProvider);
            SecureRandom random = new SecureRandom();
            generator.initialize(KEY_SIZE, random);
            keyPair = generator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();

            PUBLIC_KEY = new String(Base64.encodeBase64(publicKey.getEncoded()));

            PrivateKey privateKey = keyPair.getPrivate();

            PRIVATE_KEY = new String(Base64.encodeBase64(privateKey.getEncoded()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 私钥解密(解密前台公钥加密的密文)
     * @param encryptText 公钥加密的数据
     * @return 私钥解密出来的数据
     */
    public String decryptWithPrivate(String encryptText) {
        if (StringUtils.isBlank(encryptText)) {
            return null;
        }
        try {
            byte[] en_byte = Base64.decodeBase64(encryptText.getBytes());
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", bouncyCastleProvider);
            PrivateKey privateKey = keyPair.getPrivate();
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] res = cipher.doFinal(en_byte);
            return new String(res);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取公钥
     * @return 公钥
     */
    public String getPublicKey() {
        return PUBLIC_KEY;
    }

    /**
     * 获取私钥
     * @return 私钥
     */
    public String getPrivateKey() {
        return PRIVATE_KEY;
    }
}
