
public class ExampleHashing {

	public static void main(String[] args) {
		// Prints "Hello, World" in the terminal window.
		System.out.println("Hello, World - See Password Testing");

		String passOne = "Password$1";
		String passTwo = "WhyMonkeysAlwaysMonkeys?";
		String passOneHashed = PasswordUtilities.HashAndSaltPassword(passOne);

		System.out.println("Password: " + passOne);
		System.out.println("Password Hashed: " + passOneHashed);
		System.out.println("Password Match Good: "
				+ PasswordUtilities
						.VerifyPasswordMatchs(passOne, passOneHashed));
		System.out.println("Password Match Bad: "
				+ PasswordUtilities
						.VerifyPasswordMatchs(passTwo, passOneHashed));
	}

}
