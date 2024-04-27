// Skeleton to handle cross reference table for RE productions
// P.D. Terry, Rhodes University; modified 2023  (Java 1.5)

package RE;

import java.util.*;
import library.*;

  class Entry {                      	// Cross reference table entries
    public char name;              		// The identifier itself
    public ArrayList<Integer> refs;  	// Regex numbers where it appears
    public Entry(char name) {
      this.name = name;
      this.refs = new ArrayList<Integer>();
    }
  } // Entry

  class Table {
 
    public static void clearTable() {
    // Clears cross-reference table

    } // clearTable

    public static void addRef(char name, int regnum) {
    // Enters name if not already there, adds another regex reference 
      
    } // addRef

    public static void printTable() {
    // Prints out all references in the table (eliminate duplicate regex numbers)
         
    } // printTable


    public static void printAlphabet(int regnum) {
    // Prints out all terminals in table for particular regex number
  
    } // printAlphabet

  } // Table
