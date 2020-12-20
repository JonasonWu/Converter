package project4;

/**
 * This class contains various methods for converting numbers written using different number 
 * systems: binary, decimal, hexadecimal. The decimal numbers are represented using int type. 
 * The binary and hexadecimal numbers are represented using binary and hexadecimal strings.
 * 
 * The valid strings representing binary numbers are formatted as:
 * 		"0bBB...BB" 
 * where BB...BB is a sequence of 1 to 31 binary characters. Binary characters are 0, 1.
 * 
 * The valid strings representing hexadecimal numbers are formatted as: 
 * 		"0xHH...HH" 
 * where HH...HH is a sequence of 1 to 8 hexadecimal characters. Hexadecimal characters are 0, 
 * 1, 2, 3, 4, 5, 6, 7, 8, 9, A, B, C, D, E, F.
 * 
 * @author Jonason Wu
 * @version 11/14/2020
 *
 */
public class Converter {
	/**
	 * Empty constructor for the class
	 */
	Converter() {}
	
	/**
	 * Converts binary strings to hexadecimal strings. More specifically given a valid string 
	 * representing a binary number returns the string representing the hexadecimal number with 
	 * the same value.
	 * 
	 * @param binary - the binary string to be converted
	 * @return the hexadecimal string equal in value to the binary string passed as the 
	 * parameter
	 * @throws IllegalArgumentException - if the parameter is null
	 * @throws NumberFormatException - if the binary string passed to the function is invalid
	 */
	public static String binaryToHex(String binary) throws IllegalArgumentException, 
	NumberFormatException {
		if (binary == null)
			throw new IllegalArgumentException("Null is passed as a parameter.");
		//Partially checks whether binary is valid
		if (!validBinary(binary))
			throw new NumberFormatException("The parameter passed is not a valid binary string");
		
		//Get the binary number to process
		String binarySub = binary.substring(2);
		
		//We want the binary string to process to have a length of a multiple of 4.
		int extra = binarySub.length() % 4; 
		switch (extra) {
		case 0: break;
		case 1: binarySub = "000" + binarySub;
				break;
		case 2: binarySub = "00" + binarySub;
				break;
		case 3: binarySub = "0" + binarySub;
				break;
		}
		
		return "0x" + removeLeadingZeros(binaryToHex1(binarySub));
	}
	
	/**
	 * Takes in a valid binary string that only has 0's and 1's and converts it to hexadecimal 
	 * values. The first 2 characters of the binary string ("0b") is not included for 
	 * {@code binary}. 
	 * Used by {@link Converter#binaryToHex(String)}
	 * 
	 * @param binary - a binary string whose length is a multiple of 4.
	 * @return a Hexadecimal representation of the binary string
	 * @throws NumberFormatException if {@code binary} is not valid.
	 */
	private static String binaryToHex1(String binary) throws NumberFormatException {
		int length = binary.length();
		if (length == 0)
			return "";
		
		//4 binary numbers at a time
		String bin = binary.substring(length-4,length);
		//To store hexadecimal value
		char hex;
		switch (bin) {
		case "0000": hex = '0'; break;
		case "0001": hex = '1'; break;
		case "0010": hex = '2'; break;
		case "0011": hex = '3'; break;
		case "0100": hex = '4'; break;
		case "0101": hex = '5'; break;
		case "0110": hex = '6'; break;
		case "0111": hex = '7'; break;
		case "1000": hex = '8'; break;
		case "1001": hex = '9'; break;
		case "1010": hex = 'A'; break;
		case "1011": hex = 'B'; break;
		case "1100": hex = 'C'; break;
		case "1101": hex = 'D'; break;
		case "1110": hex = 'E'; break;
		case "1111": hex = 'F'; break;
		default: 
			throw new NumberFormatException("The parameter passed is not a valid binary string");
		}
		
		return binaryToHex1(binary.substring(0, length-4)) + hex;
	}
	
	
	/**
	 * Converts hexadecimal strings to binary strings. More specifically given a valid string 
	 * representing a hexadecimal number returns the string representing the binary number with 
	 * the same value.
	 * 
	 * @param hex - the hexadecimal string to be converted
	 * @return the binary string equal in value to the hexadecimal string passed as the parameter
	 * @throws IllegalArgumentException - if the parameter is null
	 * @throws NumberFormatException - if the hexadecimal string passed to the function is 
	 * invalid, or if binary string to return is too large to be considered a valid binary string
	 */
	public static String hexToBinary(String hex) throws IllegalArgumentException,
	NumberFormatException {
		if (hex == null)
			throw new IllegalArgumentException("Null is passed as a parameter.");
		//Partially checks whether hex is valid
		if (!validHex(hex))
			throw new NumberFormatException("The parameter passed is not a valid hexadecimal "
					+ "string");
		
		if (hex.length() == 10) {
			//At maximum length, the character at index 2 of the string should be between 0 and 7
			char tmp = hex.charAt(2);
			if (!(tmp >= '0' && tmp <= '7')) {
				//An exception will be thrown because index 2 of string will cause an invalid 
				//	binary string to be produced. 
				//Check which exception to throw:
				//	NumberFormatException thrown when the hexadecimal string is not valid.
				//	NumberFormatException thrown when hex string can convert to binary, but 
				//		the binary string is too large to be considered a valid binary.
				if (fullValidHex(hex.substring(3)))
					throw new NumberFormatException("Binary string to return is too large "
							+ "to be considered a valid binary string.");
			}
		}
		
		return "0b" + removeLeadingZeros(hexToBinary1(hex.substring(2)));
	}
	

	/**
	 * Takes in a valid hexadecimal string and converts it to binary values. 
	 * The first 2 characters of the hexadecimal string ("0x") is not included for the string. 
	 * Used by {@link Converter#binaryToHex(String)}
	 * 
	 * @param hex - a valid hex string.
	 * @return a binary representation of the {@code hex} string
	 * @throws NumberFormatException if {@code hex} is not valid
	 */
	private static String hexToBinary1(String hex) throws NumberFormatException {
		if (hex.length() == 0)
			return "";
		
		//To store the binary value
		String binary;
		switch (hex.charAt(0)) {
		case '0': binary = "0000"; break;
		case '1': binary = "0001"; break;
		case '2': binary = "0010"; break;
		case '3': binary = "0011"; break;
		case '4': binary = "0100"; break;
		case '5': binary = "0101"; break;
		case '6': binary = "0110"; break;
		case '7': binary = "0111"; break;
		case '8': binary = "1000"; break;
		case '9': binary = "1001"; break;
		case 'A': binary = "1010"; break;
		case 'B': binary = "1011"; break;
		case 'C': binary = "1100"; break;
		case 'D': binary = "1101"; break;
		case 'E': binary = "1110"; break;
		case 'F': binary = "1111"; break;
		default: 
			throw new NumberFormatException("The parameter passed is not a valid hexadecimal "
					+ "string");
		}
		
		return binary + hexToBinary1(hex.substring(1));
	}
	
	/**
	 * Converts decimal numbers to binary strings. More specifically given a non-negative decimal 
	 * integer returns the string representing the binary number with the same value.
	 * 
	 * @param decimal - the decimal number to be converted
	 * @return the binary string equal in value to the decimal number passed as the parameter or 
	 * null if the decimal is negative
	 */
	public static String decimalToBinary(int decimal) {
		if (decimal < 0)
			return null;
		if (decimal == 0)
			return "0b0";
		
		return "0b" + decimalToBinary1(decimal);
	}
	
	/**
	 * Continuation of {@link Converter#decimalToBinary}. The {@code decimal} has been 
	 * determined to be a valid decimal to convert to string.
	 * 
	 * @param decimal - the valid decimal number (positive int) to be converted
	 * @return the binary string equal in value to the decimal number passed as the parameter
	 */
	private static String decimalToBinary1(int decimal) {
		if (decimal == 0)
			return "";

		String bin = "0";
		if (decimal % 2 == 1)
			bin = "1";

		return decimalToBinary1(decimal/2) + bin;
	}

	
	/**
	 * Converts binary strings to decimal numbers. More specifically given a valid string 
	 * representing a binary number returns a non-negative decimal integer with the same value.
	 * 
	 * @param binary - the binary string to be converted
	 * @return the decimal number equal in value to the binary string passed as the parameter
	 * @throws IllegalArgumentException - if the parameter is null
	 * @throws NumberFormatException - if the binary string passed to the function is invalid
	 */
	public static int binaryToDecimal(String binary) throws IllegalArgumentException, 
	NumberFormatException {
		if (binary == null)
			throw new IllegalArgumentException("Null is passed as a parameter.");
		//Partially checks whether binary is valid
		if (!validBinary(binary))
			throw new NumberFormatException("The parameter passed is not a valid binary string");
		
		return binaryToDecimal(binary.substring(2), 1);
	}
	
	
	/**
	 * Continuation of {@link Converter#binaryToDecimal(String)}. Takes in the {@code binary} 
	 * without the ("0b") at the start. 
	 * 
	 * @param binary - a valid binary string to be converted
	 * @param factor - usually starts at 1. The value of the binary digit at the last place of 
	 * the string of the binary.
	 * @return the decimal number equal in value to the binary string passed as the parameter
	 * @throws NumberFormatException if {@code binary} is not valid (has characters other than 
	 * '0' and '1') 
	 */
	private static int binaryToDecimal(String binary, int factor) throws NumberFormatException {
		int length = binary.length();
		if (length == 0)
			return 0;
		
		int value;
		char lastChar = binary.charAt(length-1);
		if (lastChar == '1') {
			//If last char of the string is 1, then value to add would be 1*factor
			value = factor;
		}
		else if (lastChar == '0')
			value = 0;
		else {
			throw new NumberFormatException("The parameter passed is not a valid binary string");
		}
		
		return value + binaryToDecimal(binary.substring(0, length-1), factor*2);
	}
	
	/**
	 * Converts hexadecimal strings to decimal numbers. More specifically given a valid string 
	 * representing a hexadecimal number returns a non-negative decimal integer with the same 
	 * value.
	 * 
	 * @param hex - the hexadecimal string to be converted
	 * @return the decimal number equal in value to the hexadecimal string passed as the parameter
	 * @throws IllegalArgumentException - if the parameter is null
	 * @throws NumberFormatException - if the hexadecimal string passed to the function is invalid
	 * @throws ArithmeticException - when the hexadecimal string parameter is greater than 
	 * 0x7FFFFFFF (the largest value that can be represented as an int)
	 */
	public static int hexToDecimal(String hex) throws IllegalArgumentException, 
	NumberFormatException, ArithmeticException {
		if (hex == null)
			throw new IllegalArgumentException("Null is passed as a parameter.");
		//Partially checks whether hex is valid
		if (!validHex(hex))
			throw new NumberFormatException("The parameter passed is not a valid hexadecimal "
					+ "string");
		if (hex.length() == 10) {
			//At maximum length, the character at index 2 of the string should be between 0 and 7
			char tmp = hex.charAt(2);
			if (!(tmp >= '0' && tmp <= '7')) {
				//An exception will be thrown because index 2 of string is invalid. 
				//Check which exception to throw:
				//	NumberFormatException thrown when the hexadecimal string is not valid.
				//	ArithmeticException is thrown when only index 2 of hex string is invalid.
				if (fullValidHex(hex.substring(3)))
					throw new ArithmeticException("Hexadecimal is too large to be converted "
							+ "to an int.");
			}
		}

		return hexToDecimal(hex.substring(2), 1);
	}

	/**
	 * Continuation of {@link Converter#hexToDecimal(String)}. Takes in the hexadecimal string 
	 * without the ("0x") at the start.
	 * 
	 * @param hex - a hex string without the starting 2 characters to be converted
	 * @param factor - usually would start at 1. The value of hexadecimal '1' in decimal 
	 * at the last place of the string.
	 * @return the decimal number equal in value to the binary string passed as the parameter
	 * @throws NumberFormatException if {@code hex} is not valid
	 */
	private static int hexToDecimal(String hex, int factor) throws NumberFormatException {
		int length = hex.length();
		if (length == 0)
			return 0;
		
		int value;
		//Find value of last character for hex
		char lastChar = hex.charAt(length-1);
		if (lastChar >= '0' && lastChar <= '9')
			value = lastChar - '0';
		else if (lastChar >= 'A' && lastChar <= 'F')
			value = 10 + lastChar - 'A';
		else
			throw new NumberFormatException("The parameter passed is not a valid hexadecimal "
					+ "string");
		
		return value*factor + hexToDecimal(hex.substring(0,length-1), factor*16);
	}
	
	/**
	 * Converts decimal numbers to hexadecimal strings. More specifically given a non-negative 
	 * decimal integer returns the string representing the hexadecimal number with the same 
	 * value.
	 * 
	 * @param decimal - the decimal number to be converted
	 * @return the hexadecimal string equal in value to the decimal number passed as the 
	 * parameter or null if the decimal is negative
	 */
	public static String decimalToHex(int decimal) {
		if (decimal < 0)
			return null;
		if (decimal == 0)
			return "0x0";
		
		return "0x" + decimalToHex1(decimal);
	}
	
	/**
	 * Continuation of {@link Converter#decimalToHex(int)}. The {@code decimal} value has been 
	 * validated to be able to be converted into a hexadecimal
	 * 
	 * @param decimal - the valid decimal number to be converted
	 * @return the hexadecimal string equal in value to the decimal number passed as the 
	 * parameter
	 */
	private static String decimalToHex1(int decimal) {
		if (decimal == 0)
			return "";
		
		char hex;
		int dec = decimal % 16;
		if (dec <= 9)
			hex = (char) (dec + '0');
		else //if (dec <= 15)
			hex = (char) (dec - 10 + 'A');
		return decimalToHex1(decimal/16) + hex;
	}	
	
	/**
	 * Partially validates whether a string passed as parameter is a valid binary.
	 * This function will validate whether the string passed in has these aspects of a binary 
	 * number: the length of the string is between 3 and 33, the first 2 characters of the 
	 * string is "0b".
	 * 
	 * @param binary - a string that is not null
	 * @return true if the {@code binary} passed into the function has aspects of a valid 
	 * binary number, false if otherwise.
	 */
	private static boolean validBinary(String binary) {
		int length = binary.length();
		if (length < 3 || length > 33)
			return false;
		if (!binary.substring(0,2).equals("0b"))
			return false;
		
		return true;
	}
	
	
	/**
	 * Partially validates whether a string passed is a valid hex. This function will 
	 * validate whether the string passed in has these aspects of a hexadecimal number: 
	 * the length of the string is between 3 and 10, the first 2 characters of the string is 
	 * "0x".
	 * 
	 * @param hex - a string that is not null
	 * @return true if the {@code hex} passed into the function has aspects of a valid 
	 * hexadecimal number, false if otherwise.
	 */
	private static boolean validHex(String hex) {
		int length = hex.length();
		if (length < 3 || length > 10)
			return false;
		if (!hex.substring(0,2).equals("0x"))
			return false;
		return true;
	}	
	
	/**
	 * Validates whether a string passed has valid hexadecimal values. To return true, the 
	 * string can only have characters between '1' and '9', or 'A' and 'F' (which are the valid 
	 * values of a hexadecimal). Used by: {@link Converter#hexToDecimal(String)}
	 * 
	 * @param hex - a string that is not null
	 * @return true if the {@code hex} passed into the function is a valid 
	 * hexadecimal number
	 * @throws NumberFormatException when {@code hex} has invalid hexadecimal values
	 */
	private static boolean fullValidHex(String hex) throws NumberFormatException {
		if (hex.length() == 0)
			return true;

		char tmp = hex.charAt(0);
		if (tmp >= '0' && tmp <= '9' || tmp >= 'A' && tmp <= 'F')
			return fullValidHex(hex.substring(1));
		else
			throw new NumberFormatException("The parameter passed is not a valid hexadecimal "
					+ "string");
	}
	
	/**
	 * Remove all leading zeros of the string ({@code bits}) passed in.
	 * 
	 * @param bits - The string to remove all leading zeros
	 * @return A string that does not start with "0"
	 */
	private static String removeLeadingZeros(String bits) {
		if (bits.length() == 0)
			return "0";
		if (bits.charAt(0) == '0')
			return removeLeadingZeros(bits.substring(1));
		return bits;
	}
}
