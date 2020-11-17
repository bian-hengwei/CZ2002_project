import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Hash {

	public static void main(String[] args) {
		String pwd = "pwd1";
		byte[] salt = getSalt();
		String content = hash(pwd, salt) + "," + new String(salt);
		FileHandler.save("newpwd", content);
	}

	private static String hash(String password, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println("generated password: " + generatedPassword);
        return generatedPassword;
    }

    private static byte[] getSalt() {
        System.out.println("Getting salt...");
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            System.out.println("Byte array: " + new String(salt));
            return salt;
        } catch(NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException");
            e.printStackTrace();
        }
        return null;
    }

}