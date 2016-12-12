import java.util.Random;

public class PasswordUtilities {

	/**
	 * How long should the randomly generated salt be?
	 */
	private static final int SALT_RANDOM_LENGTH = 20;

	/**
	 * Used to generate random salt;
	 */
	private static final Random RANDOM = new Random();

	/**
	 * Leveraged from stack overflow (could of used this instead of writing this
	 * class, but I'd already done most of the work - so I just leveraged the
	 * hash
	 * 
	 * http://stackoverflow.com/questions/18142745/how-do-i-generate-a-salt-in-
	 * java-for-salted-hash
	 * 
	 * @param userEnteredPass
	 *            how long should be the new string be?
	 * @return A randomly generated salt string
	 */
	public static String GenerateRandomSalt(int saltLength) {
		StringBuilder sb = new StringBuilder(saltLength);
		for (int i = 0; i < saltLength; i++) {
			int c = RANDOM.nextInt(62);
			if (c <= 9) {
				sb.append(String.valueOf(c));
			} else if (c < 36) {
				sb.append((char) ('a' + c - 10));
			} else {
				sb.append((char) ('A' + c - 36));
			}
		}
		return sb.toString();
	}

	/**
	 * Generates a random salt and makes a hash with it
	 * 
	 * @param userEnteredPass
	 *            the password the user wants to hash.
	 * @return new shared password and salt to be stored together
	 */
	public static String HashAndSaltPassword(String userEnteredPass) {
		if (userEnteredPass == null || userEnteredPass.equals("")) {
			throw new IllegalArgumentException(
					"userEnteredPass must have value");
		}
		String salt = GenerateRandomSalt(SALT_RANDOM_LENGTH);
		userEnteredPass = salt + userEnteredPass;
		String sha256hex = org.apache.commons.codec.digest.DigestUtils
				.sha256Hex(userEnteredPass);
		return salt + sha256hex;
	}

	/**
	 * Method takes the password from the DB and the password the user enters
	 * and verifies they are the same.
	 * 
	 * @param userEnteredPass
	 *            password user is trying to login in with currently
	 * @param DBRecoredSaltAndPass
	 *            password stored in the db to compare agaisnt.
	 * @return true if they are, false if they are not
	 */
	public static Boolean VerifyPasswordMatchs(String userEnteredPass,
			String DBRecoredSaltAndPass) {
		if (userEnteredPass == null || userEnteredPass.equals("")) {
			throw new IllegalArgumentException(
					"userEnteredPass must have value");
		}
		if (DBRecoredSaltAndPass == null || DBRecoredSaltAndPass.equals("")) {
			throw new IllegalArgumentException(
					"DBRecoredSaltAndPass must have value");
		}
		if (DBRecoredSaltAndPass.length() <= SALT_RANDOM_LENGTH) {
			throw new IllegalArgumentException(
					"DB Password given is not long enough");
		}

		String salt = DBRecoredSaltAndPass.substring(0, SALT_RANDOM_LENGTH);
		userEnteredPass = salt + userEnteredPass;
		String sha256hex = org.apache.commons.codec.digest.DigestUtils
				.sha256Hex(userEnteredPass);
		return (salt + sha256hex).equals(DBRecoredSaltAndPass);
	}
}
