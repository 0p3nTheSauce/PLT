   import library.*;
   import java.util.*;

   class ListDemo2 {
   // Demonstrate simple application of the non-generic ArrayList class (Java 1.4)

     static class Entry {
       public String name;
       public int age;                                     // other fields could be added

       public Entry(String name, int age) {                // constructor
         this.name = name;
         this.age = age;
       }
     } // Entry

     static ArrayList list = new ArrayList();              // global for simplicity here!

     public static int position(String name) {
     // Finds position of entry with search key name in list, or -1 if not found
       int i = list.size() - 1;                            // index of last entry
       while (i >= 0 &&                                    // short-circuit protection
              !name.equals(((Entry)list.get(i)).name))     // must cast before extracting field
         i--;                                              // will reach -1 if no match
       return i;
     }

     public static void main (String[] args) {
     // Build a list of people's names and ages
       IO.writeLine("Supply a list of people's names and ages.  CTRL-Z to terminate");
       do {
         String name = IO.readWord();
         if (IO.eof()) break;
         int age = IO.readInt();
         IO.readLn();
         list.add(new Entry(name, age));                   // add to end of list
       } while (!IO.eof());

       IO.writeLine(list.size() + " items stored");        // report size of list

       Entry myEntry = new Entry("DeeDee", 42);              
       list.set(0, myEntry);                              // insert at position 0

       StringBuffer sb = new StringBuffer();               // demonstrate StringBuffer use
       for (int i = 0; i < list.size(); i++) {             // display each entry
         Entry e = (Entry) list.get(i);                    // retrieve (via a cast) an item at position i
         IO.write(e.name, -16); IO.writeLine(e.age);       // -16 means "left justify"
         sb.append(e.name + " ");                          // add the names to a StringBuffer object
       }
       IO.writeLine();

       int where = position("BooBoo");                      // find the silly fellow!
       if (where < 0) IO.writeLine("BooBoo not found");
       else {
         Entry booEntry  = (Entry) list.get(where);            // retrieve (via a cast) an item at position where
         IO.writeLine("BooBoo found at position " + where + ". He is " + booEntry .age + " years old");
       }

       if (sb.length() > 0) {
         IO.writeLine();
         IO.writeLine("Summary of names:");
         IO.writeLine();
         IO.writeLine(sb.toString());
       }
     }

   } // ListDemo2
