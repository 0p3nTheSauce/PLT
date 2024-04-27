package library;

import java.io.*;

// Release 1; 2003/03/10
// Release 2: 2008/11/21
// Release 3: 2009/01/27
// Release 4: 2009/07/14
// Release 5: 2011/08/16

public class IO {
// Provide simple facilities for text I/O (standard input and output streams)
// P.D. Terry (p.terry@ru.ac.za)

  static public void write(String o)                 { System.out.print(o); System.out.flush(); }
  static public void write(Object o)                 { System.out.print(o); System.out.flush(); }
  static public void write(byte o)                   { write(o, 0); }
  static public void write(short o)                  { write(o, 0); }
  static public void write(int o)                    { write(o, 0); }
  static public void write(long o)                   { write(o, 0); }
  static public void write(boolean o)                { write(o, 0); }
  static public void write(float o)                  { write(o, 0); }
  static public void write(double o)                 { write(o, 0); }
  static public void write(char o)                   { System.out.print(o); System.out.flush(); }
  static public void write(char[] o)                 { System.out.print(o); System.out.flush(); }
  static public void write(char[] o, int off,
                           int len)                  { for (int i = 0; i < len; i++)
                                                         System.out.write(o[off+i]);
                                                       System.out.flush(); }

  static public void writeLine()                     { System.out.println(); }
  static public void writeLine(String o)             { System.out.println(o); }
  static public void writeLine(Object o)             { System.out.println(o); }
  static public void writeLine(byte o)               { writeLine(o, 0); }
  static public void writeLine(short o)              { writeLine(o, 0); }
  static public void writeLine(int o)                { writeLine(o, 0); }
  static public void writeLine(long o)               { writeLine(o, 0); }
  static public void writeLine(boolean o)            { writeLine(o, 0); }
  static public void writeLine(float o)              { writeLine(o, 0); }
  static public void writeLine(double o)             { writeLine(o, 0); }
  static public void writeLine(char o)               { System.out.println(o); }
  static public void writeLine(char[] o)             { System.out.println(o); }
  static public void writeLine(char[] o, int off,
                           int len)                  { for (int i = 0; i < len; i++)
                                                         System.out.write(o[off+i]);
                                                       System.out.println(); }

  static private void putStr(String s, int width) {
    if (width == 0) System.out.print(" " + s);
    else if (width > 0) {
      for (int i = s.length(); i < width; i++) System.out.print(' ');
      System.out.print(s);
    }
    else {
      System.out.print(s);
      for (int i = s.length(); i < -width; i++) System.out.print(' ');
    }
    System.out.flush();
  }

  static public void write(String o,  int width)     { putStr(o, width); }
  static public void write(Object o,  int width)     { putStr(String.valueOf(o), width); }
  static public void write(byte o,    int width)     { putStr(String.valueOf(o), width); }
  static public void write(short o,   int width)     { putStr(String.valueOf(o), width); }
  static public void write(int o,     int width)     { putStr(String.valueOf(o), width); }
  static public void write(long o,    int width)     { putStr(String.valueOf(o), width); }
  static public void write(boolean o, int width)     { putStr(String.valueOf(o), width); }
  static public void write(float o,   int width)     { putStr(String.valueOf(o), width); }
  static public void write(double o,  int width)     { putStr(String.valueOf(o), width); }
  static public void write(char o,    int width)     { putStr(String.valueOf(o), width); }

  static private void putLine(String s, int width) {
    if (width == 0) System.out.println(" " + s);
    else if (width > 0) {
      for (int i = s.length(); i < width; i++) System.out.print(' ');
      System.out.println(s);
    }
    else {
      System.out.print(s);
      for (int i = s.length(); i < -width; i++) System.out.print(' ');
      System.out.println();
    }
  }

  static public void writeLine(String o,  int width) { putLine(o, width); }
  static public void writeLine(Object o,  int width) { putLine(String.valueOf(o), width); }
  static public void writeLine(byte o,    int width) { putLine(String.valueOf(o), width); }
  static public void writeLine(short o,   int width) { putLine(String.valueOf(o), width); }
  static public void writeLine(int o,     int width) { putLine(String.valueOf(o), width); }
  static public void writeLine(long o,    int width) { putLine(String.valueOf(o), width); }
  static public void writeLine(boolean o, int width) { putLine(String.valueOf(o), width); }
  static public void writeLine(float o,   int width) { putLine(String.valueOf(o), width); }
  static public void writeLine(double o,  int width) { putLine(String.valueOf(o), width); }
  static public void writeLine(char o,    int width) { putLine(String.valueOf(o), width); }

  // formatters

  static public void writeFormatted(String format, Object... list) {
    System.out.printf(format, list);
  }

  public static String fixedRep(double d, int fractionDigits) {
    String fmt = "%1." + String.valueOf(Math.abs(fractionDigits)) + "f";
    return String.format(fmt, d);
  }

  public static String fixedRep(float f, int fractionDigits) {
    String fmt = "%1." + String.valueOf(Math.abs(fractionDigits)) + "f";
    return String.format(fmt, f);
  }

  public static String floatingRep(double d, int fractionDigits) {
    String fmt = "%1." + String.valueOf(Math.abs(fractionDigits)) + "E";
    return String.format(fmt, d);
  }

  public static String floatingRep(float f, int fractionDigits) {
    String fmt = "%1." + String.valueOf(Math.abs(fractionDigits)) + "E";
    return String.format(fmt, f);
  }

  static public void writeFixed(double d, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) System.out.print(" ");
    String fmt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "f";
    System.out.print(String.format(fmt, d));
  }

  static public void writeLineFixed(double d, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) System.out.print(" ");
    String fmt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "f";
    System.out.println(String.format(fmt, d));
  }

  static public void writeFloating(double d, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) System.out.print(" ");
    String fmt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "E";
    System.out.print(String.format(fmt, d));
  }

  static public void writeLineFloating(double d, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) System.out.print(" ");
    String fmt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "E";
    System.out.println(String.format(fmt, d));
  }

  static public void write(double d, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) System.out.print(" ");
    String fix = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "f";
    String flt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "E";
    String sfx = String.format(fix, d);
    String sfl = String.format(flt, d);
    if (width == 0)
      if (sfx.length() <= sfl.length()) System.out.print(sfx); else System.out.print(sfl);
    else
      if (sfx.length() <= Math.abs(width)) System.out.print(sfx); else System.out.print(sfl);
  }

  static public void writeLine(double d, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) System.out.print(" ");
    String fix = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "f";
    String flt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "E";
    String sfx = String.format(fix, d);
    String sfl = String.format(flt, d);
    if (width == 0)
      if (sfx.length() <= sfl.length()) System.out.println(sfx); else System.out.println(sfl);
    else
      if (sfx.length() <= Math.abs(width)) System.out.println(sfx); else System.out.println(sfl);
  }

  static public void writeFixed(float f, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) System.out.print(" ");
    String fmt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "f";
    System.out.print(String.format(fmt, f));
  }

  static public void writeLineFixed(float f, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) System.out.print(" ");
    String fmt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "f";
    System.out.println(String.format(fmt, f));
  }

  static public void writeFloating(float f, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) System.out.print(" ");
    String fmt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "E";
    System.out.print(String.format(fmt, f));
  }

  static public void writeLineFloating(float f, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) System.out.print(" ");
    String fmt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "E";
    System.out.println(String.format(fmt, f));
  }

  static public void write(float f, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) System.out.print(" ");
    String fix = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "f";
    String flt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "E";
    String sfx = String.format(fix, f);
    String sfl = String.format(flt, f);
    if (width == 0)
      if (sfx.length() <= sfl.length()) System.out.print(sfx); else System.out.print(sfl);
    else
      if (sfx.length() <= Math.abs(width)) System.out.print(sfx); else System.out.print(sfl);
  }

  static public void writeLine(float f, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) System.out.print(" ");
    String fix = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "f";
    String flt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "E";
    String sfx = String.format(fix, f);
    String sfl = String.format(flt, f);
    if (width == 0)
      if (sfx.length() <= sfl.length()) System.out.println(sfx); else System.out.println(sfl);
    else
      if (sfx.length() <= Math.abs(width)) System.out.println(sfx); else System.out.println(sfl);
  }

  static public void prompt(String str) {
    System.err.print(str + " ");
  }

  static public void writeErrorMessage(String str) {
    System.err.println(str);
  }

// -- input routines

  final static char CR = '\r';
  final static char LF = '\n';

  static boolean okay = false;    // module-wide error flag for simplicity

  static char savedChar;
  static boolean eof, eol, openFailure, inError, haveCh, noData, printErrors, fromDisk;
  static int errorCount;

  // internal character and token readers

  static char nextChar() {
  // Probe and retrieve next character from input
  // Note that CR+LF is mapped into LF only, and '\0' is returned if the end of
  // file is reached.  There is a one character buffer, to allow readAgain
  // to work, and to allow easy reading of strings and tokens, which have to
  // one character past their end.
  // It is an error to attempt to go past the end of file if it has already
  // been detected!
    try {
      char ch;
      if (haveCh) { ch = savedChar; okay = ch != '\0'; }
      else if (eof) { ch = '\0'; okay = false; }  // been there already
      else {                                      // get on with it
        int i = System.in.read(); okay = true;
        if (i == CR) { i = System.in.read(); }    // MS-DOS
        ch = (char) i;
        if (i == -1 | i == 26) { ch = '\0'; eof = true; }   // there was no character after all
       }
      savedChar = ch; haveCh = false;
      eol = eof || ch == LF;                      // eof also sets eol
      inError = ! okay;
      if (!okay) { System.err.println("Attempt to read past eof"); System.exit(1); }
      return ch;
    }
    catch (IOException e) {
      System.err.println("Error in nextChar"); System.exit(1);
      /* fudge compiler fussiness! */ return('a');
    }
  }

  static private String readToken() {
  // Internal version of readWord with no error reporting
    StringBuffer sb = new StringBuffer();
    char ch;
    skipSpaces();
    if (eof) {
      noData = true; okay = false; inError = true;
      return " ";
    }
    ch = nextChar();
    while (ch > ' ') { sb.append(ch); ch = nextChar(); }
    haveCh = true;
    return sb.toString();
  }

  // Character based input

  static public char readChar() {
  // Reads and returns a single character
    char ch = nextChar();
    noData = eof;
    if (noData) reportError("readChar failed - no more data");
    return ch;
  }

  static public char readChar(String prompt) {
  // Prompts for, reads and returns a single character
    System.err.print(prompt);
    return readChar();
  }

  static public void readAgain() {
  // Allows for a single character probe in code like
  // do ch = IO.readChar(); while(notDigit(ch)); IO.readAgain(); ...
    okay = true; haveCh = true;
  }

  static public void skipSpaces() {
  // Allows for the most elementary of lookahead probes
    char ch;
    do ch = nextChar(); while (ch <= ' ' && !eof);
    haveCh = true;
  }

  static public void readLn() {
  // Consumes all characters to end of line.
    while (!eol) readChar();
    haveCh = false; eol = false;
  }

  // String based input

  static public String readString() {
  // Reads and returns a String.  Incorporates leading white space,
  // and terminates on a control character, which is not
  // incorporated or consumed.
  // Note that this means you might need code like
  //    s = IO.readString(); IO.readLn();
  // (Alternatively use readLine() below )
    StringBuffer sb = new StringBuffer();
    char ch;
    if (eof) {
      noData = true; okay = false; inError = true;
      reportError("readString failed - no more data");
      return "";
    }
    ch = nextChar();
    while (ch >= ' ') { sb.append(ch); ch = nextChar(); }
    haveCh = true;
    return sb.toString();
  }

  static public String readString(String prompt) {
  // Prompts for, reads and returns a single string
    System.err.print(prompt);
    return readString();
  }

  static public String readString(int max) {
  // Reads and returns a string.  Incorporates leading white space,
  // and terminates on a control character, which is not
  // incorporated or consumed, or when max characters have been read
  // (useful for fixed format data).  Note that this means you might
  // need code like s = IO.readString(10); IO.readLn();
    StringBuffer sb = new StringBuffer();
    char ch;
    if (eof) {
      noData = true; okay = false; inError = true;
      reportError("readString failed - no more data");
      return "";
    }
    ch = nextChar();
    while (ch >= ' ' && max > 0) { sb.append(ch); ch = nextChar(); max--; }
    haveCh = true;
    return sb.toString();
  }

  static public String readString(String prompt, int max) {
  // Prompts for, reads and returns a single string limited to max characters
    System.err.print(prompt);
    return readString(max);
  }

  static public String readLine() {
  // Reads and returns a String.  Incorporates leading white space,
  // and terminates when EOL or EOF is reached.  The EOL character
  // is not incorporated, but is consumed.
    StringBuffer sb = new StringBuffer();
    char ch;
    if (eof) {
      noData = true; okay = false; inError = true;
      reportError("readLine failed - No more data");
      return "";
    }
    ch = nextChar();
    while (!eol) { sb.append(ch); ch = nextChar(); }
    haveCh = false;
    return sb.toString();
  }

  static public String readLine(String prompt) {
  // Prompts for, reads and returns a single line
    System.err.print(prompt);
    return readLine();
  }

  static public String readWord() {
  // Reads and returns a word - a string delimited at either end
  // by a control character or space (typically the latter).
  // Leading spaces are discarded, and the terminating character
  // is not consumed.
    StringBuffer sb = new StringBuffer();
    char ch;
    skipSpaces();
    if (eof) {
      noData = true; okay = false; inError = true;
      reportError("readWord failed - no more data");
      return "";
    }
    ch = nextChar();
    while (ch > ' ') { sb.append(ch); ch = nextChar(); }
    haveCh = true;
    return sb.toString();
  }

  static public String readWord(String prompt) {
  // Prompts for, reads and returns a single word
    System.err.print(prompt);
    return readWord();
  }

  // Numeric input routines.  These always return, even if the data
  // is incorrect.  Users may probe the status of the operation,
  // or use showErrors() to get error messages.

  static public int readInt(int radix) {
  // Reads a word as a textual representation of an integer (base radix)
  // and returns the corresponding value.
  // Errors may be reported or quietly ignored (when 0 is returned).
    String s = readToken();
    int n = 0;
    if (okay) {
      try {
        n = Integer.parseInt(s, radix);
      }
      catch (NumberFormatException e) {
        okay = false; n = 0; inError = true;
        reportError("Bad Integer Format");
      }
    }
    else reportError("readInt failed - no more data");
    return n;
  }

  static public int readInt(String prompt, int radix) {
  // Prompts for, reads and returns a single integer (base radix)
    System.err.print(prompt);
    return readInt(radix);
  }

  static public int readInt() {
  // Reads and returns a single integer (base 10)
    return readInt(10);
  }

  static public int readInt(String prompt) {
  // Prompts for, reads and returns a single integer (base 10)
    System.err.print(prompt);
    return readInt(10);
  }

  static public long readLong(int radix) {
  // Reads a word as a textual representation of a long integer (base radix)
  // and returns the corresponding value.
  // Errors may be reported or quietly ignored (when 0 is returned).
    String s = readToken();
    long n = 0;
    if (okay) {
      try {
        n = Long.parseLong(s, radix);
      }
      catch (NumberFormatException e) {
        okay = false; n = 0; inError = true;
        reportError("Bad Long Integer Format");
      }
    }
    else reportError("readLong failed - no more data");
    return n;
  }

  static public long readLong(String prompt, int radix) {
  // Prompts for, reads and returns a single long integer (base radix)
    System.err.print(prompt);
    return readLong(radix);
  }

  static public long readLong() {
  // Reads and returns a single long integer (base 10)
    return readLong(10);
  }

  static public long readLong(String prompt) {
  // Prompts for, reads and returns a single long integer (base 10)
    System.err.print(prompt);
    return readLong(10);
  }

  static public short readShort(int radix) {
  // Reads a word as a textual representation of a short integer (base radix)
  // and returns the corresponding value.
  // Errors may be reported or quietly ignored (when 0 is returned).
    String s = readToken();
    short n = 0;
    if (okay) {
      try {
        n = Short.parseShort(s, radix);
      }
      catch (NumberFormatException e) {
        okay = false; n = 0; inError = true;
        reportError("Bad Short Integer Format");
      }
    }
    else reportError("readShort failed - no more data");
    return n;
  }

  static public short readShort(String prompt, int radix) {
  // Prompts for, reads and returns a single short integer (base radix)
    System.err.print(prompt);
    return readShort(radix);
  }

  static public short readShort() {
  // Reads and returns a single short integer (base 10)
    return readShort(10);
  }

  static public short readShort(String prompt) {
  // Prompts for, reads and returns a single short integer (base 10)
    System.err.print(prompt);
    return readShort(10);
  }

  static public byte readByte(int radix) {
  // Reads a word as a textual representation of an integer (base radix)
  // and returns the corresponding value.
  // Errors may be reported or quietly ignored (when 0 is returned).
    String s = readToken();
    byte n = 0;
    if (okay) {
      try {
        n = Byte.parseByte(s, radix);
      }
      catch (NumberFormatException e) {
        okay = false; n = 0; inError = true;
        reportError("Bad Byte Format");
      }
    }
    else reportError("readByte failed - no more data");
    return n;
  }

  static public byte readByte(String prompt, int radix) {
  // Prompts for, reads and returns a single byte (base radix)
    System.err.print(prompt);
    return readByte(radix);
  }

  static public byte readByte() {
  // Reads and returns a single byte (base 10)
    return readByte(10);
  }

  static public byte readByte(String prompt) {
  // Prompts for, reads and returns a single byte (base 10)
    System.err.print(prompt);
    return readByte(10);
  }

  static public float readFloat() {
  // Reads a word as a textual representation of a float
  // and returns the corresponding value.
  // Errors may be reported or quietly ignored (when 0 is returned).
    String s = readToken();
    float n = 0;
    if (okay) {
      try {
        n = Float.parseFloat(s);
      }
      catch (NumberFormatException e) {
        okay = false; n = 0; inError = true;
        reportError("Bad Float Format");
      }
    }
    else reportError("readFloat failed - no more data");
    return n;
  }

  static public float readFloat(String prompt) {
  // Prompts for, reads and returns a single float
    System.err.print(prompt);
    return readFloat();
  }

  static public double readDouble() {
  // Reads a word as a textual representation of a double
  // and returns the corresponding value.
  // Errors may be reported or quietly ignored (when 0 is returned).
    String s = readToken();
    double n = 0;
    if (okay) {
      try {
        n = Double.parseDouble(s);
      }
      catch (NumberFormatException e) {
        okay = false; n = 0; inError = true;
        reportError("Bad Double Format");
      }
    }
    else reportError("readDouble failed - no more data");
    return n;
  }

  static public double readDouble(String prompt) {
  // Prompts for, reads and returns a single double
    System.err.print(prompt);
    return readDouble();
  }

  static public boolean readBoolean() {
  // Reads a word and returns a Boolean value, based on the
  // first letter.  Typically the word would be T(rue) or Y(es)
  // or F(alse) or N(o).
    String s = readToken();
    boolean b = false;
    if (okay) {
      switch (Character.toUpperCase(s.charAt(0))) {
        case 'Y' : case 'T' : b = true; break;
        case 'N' : case 'F' : b = false; break;
        default  : okay = false; b = false; inError = true;
                   reportError("Bad Boolean Format"); break;
      }
    }
    else reportError("readBoolean failed - no more data");
    return b;
  }

  static public boolean readBoolean(String prompt) {
  // Prompts for, reads and returns a single boolean
    System.err.print(prompt);
    return readBoolean();
  }

  // Utility "getters"

  static public boolean eof() {
  // Returns true if the last operation terminated by reaching EOF
    return eof;
  }

  static public boolean eol() {
  // Returns true if the last operation terminated by reaching EOL
    return eol;
  }

  static public boolean error() {
  // Returns true if the last operation on this file failed
    return inError;
  }

  static public boolean noMoreData() {
  // Returns true if the last operation on this file failed because
  // an attempt was made to read past the end of file
    return noData;
  }

  // Error handlers

  static public void reportError(String message) {
  // Possible simple error reporting (disabled by default)
    if (printErrors) {
      System.err.println("--- " + message);
      System.exit(1);
    }
    errorCount++;
  }

  static public int errorCount() {
  // Returns number of errors detected on input
    return errorCount;
  }

  static public boolean done() {
  // Simple error checking - Reports result for last method called
    return okay;
  }

  static public void showErrors() {
  // Allows user to switch error reporting on (off by default)
    printErrors = true;
  }

  static public void hideErrors() {
  // Allows user to switch error reporting off (default setting)
    printErrors = false;
  }

} // IO

