/**
 * SDEV 301: Systems Programming
 *
 * @author Kendrick Hang, Tien Han
 * @version 1.0
 */

package inventory;

/**
 * This class represents an inventory of the 26 letters in the English alphabet.
 * A LetterInventory object keeps track of how many a’s, how many b’s, etc.
 * are contained in the inventory. This object stores the counts of the letters
 * in an integer array with a maximum count of Short.MAX_VALUE for each letter
 * For example:
 * the letter inventory for the string “WashingtonState” corresponds to
 * [aaeghinnosstttw] --> String representation of the inventory
 * [2,0,0,0,1,0,1,1,1,0,0,0,0,2,1,0,0,0,2,3,0,0,1,0,0,0] --> inventory count array
 * [a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z] --> corresponding letters
 * The case of the letter is ignored, so ‘s’ and ‘S’ are exactly the same.
 *
 */
public class LetterInventory  {
  //If this is private int[] inventory - it takes up 32 bits * 26 letters => 832 bits of space
  //If this is private short[] inventory - it takes upu 16 bits * 26 letters => 416 bits of space
  //If this is private byte[] inventory - it takes up 8 bits * 26 letters => 208 bits of space
    //Only want to use byte[] if the letter count < 256
    //Because 256 is the biggest value you can store with 8 bits
    //Which is 64 (2^6) + 32  (2^5) + 16 (2^4) + 8 (2^3) + 4 (2^2) + 2 (2^1) + 1 (2^0)
    //127 bytes per spot in the array (example: so we can only support "a" up to 127)
  private short[] inventory; // inventory is null here
  public static final byte ALPHABET_SIZE = 26;

  /**
   * Constructs an integer array for the size of the alphabet.
   * All letter counts are initialized to zero.
   */
  public LetterInventory(){
    inventory = new short[ALPHABET_SIZE];
  }
  /**
   * Constructs an integer array for the size of the alphabet.
   * Each element in the array should hold a 16-bit integer
   * and adds each character in the text to the inventory
   * @param text
   */
  public LetterInventory(String text) {
    //this() will call the constructor LetterInventory() to
    //initialize our inventory short array.
    this();

    for (int i = 0; i < text.length(); i++) {
      char letter = text.charAt(i);
      add(letter);
    }
  }

  /**
   * Identifies the index for the given character within the inventory array , throws an
   * IIegalArgumentException if the character is not in the a-z or A-Z range.
   * For example: if the given character is 'c' or 'C', then the index returned is 2
   *              if the given character is '?', then an IllegalArgumentException is thrown
   *
   * @param c a-z or A-Z character
   * @return index of the character
   */
  public int getIndex(char c) {
    if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
      //Subtract 97 as "a" starts at 97 and we want the index to start at 0
      int index = (int) c - 97;
      //Adjust for capital letters
      if (index < 0) {
        return index + 32;
      }
      return index;
    } else {
      throw new IllegalArgumentException("The given character " + c + " was a non alpha character.");
    }
  }

  /**
   * Increases the count for the given character in the inventory
   * @param c a-z or A-Z otherwise an IllegalArgumentException is thrown
   */
  public void add(char c) {
    int index = getIndex(c);
    int newCount = this.inventory[index] + 1;
    this.inventory[index] = (short) newCount;
  }

  /**
   * Decreases the count for the given character in the inventory
   * @param c a-z or A-Z otherwise an IllegalArgumentException is thrown
   */
  public void subtract(char c) {
    int index = getIndex(c);
    int newCount = this.inventory[index] - 1;
    this.inventory[index] = (short) newCount;
  }

  /**
   * Returns the count for the given character in the inventory
   * @param c a-z or A-Z otherwise an IllegalArgumentException is thrown
   */
  public int get(char c) {
   return this.inventory[getIndex(c)];
  }

  /**
   * Sets the count for the given character in the inventory
   * @param c a-z or A-Z otherwise an IllegalArgumentException is thrown
   * @param count the number of occurrences of the character c; if count < 0
   *              IllegalArgumentException is thrown
   */
  public void set(char c, short count) {
    if (count < 0) {
      throw new IllegalArgumentException("The given count " + count + " is less than 0.");
    }

    this.inventory[getIndex(c)] = count;
  }

  /**
   * Determines if a character's count is in the inventory
   * @param c a-z or A-Z otherwise an IllegalArgumentException is thrown
   * @return true if character is in inventory, false otherwise
   */
  public boolean contains(char c) {
      return this.inventory[getIndex(c)] > 0;
  }

  /**
   * Return the total count of all letters in the inventory
   * @return total count
   */
  public int size() {
    int counter = 0;
    for (short count : this.inventory) {
     counter += count;
    }
    return counter;
  }

  /**
   * Determine if the inventory has zero counts for all letters
   * @return true, if empty, false otherwise
   */
  public boolean isEmpty() {
    for (short count : this.inventory) {
      if (count > 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns a String representation of the inventory with the letters all in
   * lowercase
   * surrounded by square brackets in alphabetical order. The number of
   * occurrences of
   * each letter matches its count in the inventory.
   * For example, an inventory of 4 a’s, 1 b, 1 k and 1 m would be represented as
   * “[aaaabkm]”.
   *
   * @return a bracketed string representation of the letters contained in the
   *         inventory
   */
  public String toString() {
    // If you are concatenating many strings together, StringBuilder class
    // is often more efficient
    StringBuilder toReturn = new StringBuilder("[");

    // for every count in the letters inventory
    for (int i = 0; i < inventory.length; i++) {
      // add each character to the string count times
      for (int count = 0; count < inventory[i]; count++) {
        // ascii math performed here
        // Example:
        // 'a' + 0 = 'a'
        // 'a' + 1 = 'b'
        // 'a' + 2 = 'c'
        // 'a' + 25 = 'z'
        toReturn.append((char) ('a' + i));
      }
    }
    //Join in the closing "]"  and convert the StringBuilder
    //to a String and return it
    return toReturn.append("]").toString();
  }

}
