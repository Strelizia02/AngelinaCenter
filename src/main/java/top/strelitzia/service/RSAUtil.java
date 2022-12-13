package top.strelitzia.service;

@Component
public class RSAUtil {
    @Autowired
    private GenerateKeyPairConfig KeyPairConfig;

    // region 私有方法

    /**
     * 生成密钥对
     * @return 返回map集合，其中包含publicKey与privateKey
     * @throws NoSuchAlgorithmException
     */
    private Map<String, Key> generateKeyPair() throws NoSuchAlgorithmException {
        /**
         * RSA算法要求有一个可信任的随机数源
         */
        SecureRandom secureRandom = new SecureRandom();

        /**
         * 为RSA算法创建一个KeyPairGenerator对象
         */
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KeyPairConfig.getAlgorithm());

        /**
         * 利用上面的随机数据源初始化这个KeyPairGenerator对象
         */
        keyPairGenerator.initialize(KeyPairConfig.getKeySize(), secureRandom);

        /**
         * 生成密匙对
         */
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        /**
         * 得到公钥
         */
        Key publicKey = keyPair.getPublic();

        /**
         * 得到私钥
         */
        Key privateKey = keyPair.getPrivate();

        Map<String, Key> keyPairMap = new HashMap<>();
        keyPairMap.put("publicKey", publicKey);
        keyPairMap.put("privateKey", privateKey);

        return keyPairMap;
    }

    /**
     * 获取文件中获取密钥对象
     * @param fileName 文件名
     * @return 密钥对象
     */
    private Key getKeyFromFile(String fileName) {
        Key key = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            key = (Key) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return key;
    }

    /**
     * 将密钥对生成到文件中
     */
    private void generateKeyPairToFiles() {
        ObjectOutputStream oosPublicKey = null;
        ObjectOutputStream oosPrivateKey = null;
        try {
            Map<String, Key> keyPairMap = generateKeyPair();
            Key publicKey = keyPairMap.get("publicKey");
            Key privateKey = keyPairMap.get("privateKey");

            oosPublicKey = new ObjectOutputStream(new FileOutputStream(KeyPairConfig.getPublicKeyFile()));
            oosPrivateKey = new ObjectOutputStream(new FileOutputStream(KeyPairConfig.getPrivateKeyFile()));
            oosPublicKey.writeObject(publicKey);
            oosPrivateKey.writeObject(privateKey);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                /**
                 * 清空缓存，关闭文件输出流
                 */
                oosPublicKey.close();
                oosPrivateKey.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // endregion 私有方法

    // region 公有方法

    /**
     * 初始化密钥对文件
     * @return 返回密钥对信息【publicKey（公钥字符串）、、privateKey（私钥字符串）】
     */
    public Map<String, String> initKeyPair() {
        Map<String, String> keyPairMap = new HashMap<>();
        File publicFile = new File(KeyPairConfig.getPublicKeyFile());
        File privateFile = new File(KeyPairConfig.getPrivateKeyFile());

        /**
         * 判断是否存在公钥和私钥文件
         */
        if (!publicFile.exists() || !privateFile.exists()) {
            generateKeyPairToFiles();
        }

        ObjectInputStream oisPublic = null;
        ObjectInputStream oisPrivate = null;
        Key publicKey = null;
        Key privateKey = null;

        try {
            oisPublic = new ObjectInputStream(new FileInputStream(KeyPairConfig.getPublicKeyFile()));
            oisPrivate = new ObjectInputStream(new FileInputStream(KeyPairConfig.getPrivateKeyFile()));
            publicKey = (Key) oisPublic.readObject();
            privateKey = (Key) oisPrivate.readObject();

            byte[] publicKeyBytes = publicKey.getEncoded();
            byte[] privateKeyBytes = privateKey.getEncoded();

            String publicKeyBase64 = new BASE64Encoder().encode(publicKeyBytes);
            String privateKeyBase64 = new BASE64Encoder().encode(privateKeyBytes);

            /**
             * 公钥字符串
             */
            keyPairMap.put("publicKey", publicKeyBase64);
            /**
             * 私钥字符串
             */
            keyPairMap.put("privateKey", privateKeyBase64);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                oisPrivate.close();
                oisPublic.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return keyPairMap;
    }

    /**
     * 加密方法
     * @param source 源数据
     * @return 加密后的字符串
     */
    public String encrypt(String source) {
        Key publicKey = getKeyFromFile(KeyPairConfig.getPublicKeyFile());
        BASE64Encoder encoder = new BASE64Encoder();
        String encryptSource = null;
        try {
            /**
             * 得到Cipher对象来实现对源数据的RSA加密
             */
            Cipher cipher = Cipher.getInstance(KeyPairConfig.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bytes = source.getBytes();

            /**
             * 执行加密操作
             */
            encryptSource = encoder.encode(cipher.doFinal(bytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return encryptSource;
    }

    /**
     * 解密方法
     * @param source 密文
     * @return 解密后的字符串
     */
    public String decrypt(String source) {
        Key privateKey = getKeyFromFile(KeyPairConfig.getPrivateKeyFile());
        BASE64Decoder decoder = new BASE64Decoder();
        String decryptSource = null;
        try {
            /**
             * 得到Cipher对象对已用公钥加密的数据进行RSA解密
             */
            Cipher cipher = Cipher.getInstance(KeyPairConfig.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            /**
             * 执行解密操作
             */
            byte[] bytes = decoder.decodeBuffer(source);
            decryptSource = new String(cipher.doFinal(bytes), "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return decryptSource;
    }
}
