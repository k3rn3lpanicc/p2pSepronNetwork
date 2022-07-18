package clientNode.data.hasher;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class SHA3 {
    public static String getSHA3(byte[] inputData) {
        MessageDigest crypt = null;
        try {
            crypt = MessageDigest.getInstance("SHA3-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        crypt.update(inputData);
        byte[] bytes = crypt.digest();
        BigInteger bi = new BigInteger(1, bytes);
        String digest = String.format("%0" + (bytes.length << 1) + "x", bi);
        return digest;
    }
    public static String getSHA3(Object a){
        return getSHA3(a.toString().getBytes());
    }

}
