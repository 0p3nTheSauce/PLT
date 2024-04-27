package library;

import java.io.*;

// Release 1: 2004/06/21
// Release 2: 2008/11/21
// Release 3: 2009/01/27
// Release 4: 2009/07/14
// Release 5: 2011/08/16

public class InFile {
// Provide simple facilities for text input
// P.D. Terry (p.terry@ru.ac.za)

  final static char CR = '\r';
  final static char LF = '\n';

  static boolean okay = false;    // module-wide error flag for simplicity

  char savedChar;
  boolean eof, eol, openFailure, inError, haveCh, noData, printErrors, fromDisk;
  int errorCount;
  String name;

  void init(String fn) {
  // Called by all constructors to initialize hidden fields
    eof = eol = inError = haveCh = noData = openFailure = printErrors = false;
    okay = true;
    savedChar = '\0';
    errorCount = 0;
    name = fn;
    fromDisk = false;
  }

/* Java 1.0 wraps System.in */

  Reader inReader;

  public static final InFile StdIn = new InFile();

  // File opening and closing

  public InFile() {
  // Create an InFile attached to StdIn
    init("StdIn");
    inReader = new InputStreamReader(System.in);
  }

  public InFile(String fileName) {
  // Creates an InFile from named file
  // (reverts to StdIn if it fails)
    if (fileName != null) fileName = fileName.trim();
    if (fileName != null && fileName.equals("")) {
      init("StdIn");
      inReader = new InputStreamReader(System.in);
    }
    else {
      init(fileName); fromDisk = true;
      try {
        inReader = new BufferedReader(new FileReader(fileName));
        probe();
      }
      catch (IOException e) {
        System.err.println("InFile(" + fileName + ") failed - redirected to System.in");
        inReader = new InputStreamReader(System.in);
        name = "StdIn"; openFailure = true; okay = false;
      }
    }
  }

  public InFile(Reader s, boolean fromDisk) {
  // Creates an inFile from extant stream
  // (reverts to StdIn if it fails)
    init("stream");
    if (s == null) {
      System.err.println("InFile(null) directed to System.in");
      inReader = new InputStreamReader(System.in);
      name = "StdIn"; openFailure = true; okay = false;
    } else {
      inReader = s; this.fromDisk = fromDisk;
      if (fromDisk) probe();
    }
  }

  private void probe() {
  // check for empty file
   try {
     int i = inReader.read();
     if (i == -1) {
       eof = true; eol = true; haveCh = false;
     }
     else {
      haveCh = true; savedChar = (char) i;
      eol = i == CR || i == LF;
     }
    }
    catch (IOException e) {
      System.err.println("Error reading " + name); System.exit(1);
      /* fudge compiler fussiness! */
    }
  } // probe

  public void close() {
  // Closes this file
  // Unfortunately there seems no simple way to get the InFile to Close automagically
    try {
      inReader.close();
    }
    catch (IOException e) {
      System.err.println("Failed to close input file " + name);
    }
  }

/* -------- Java 1.0 for reference.  Here we have to use an Input Stream

  InputStream inReader;

  public static InFile StdIn = new InFile();

  // Constructors - choice of three

  public InFile() {
  // Create an InFile attached to StdIn
    init("StdIn");
    inReader = System.in;
  }

  public InFile(String fileName) {
  // Creates an InFile from named file
  // (reverts to StdIn if it fails)
    if (fileName != null && fileName.equals("")) {
      init("StdIn");
      inReader = System.in;
    }
    else {
      init(fileName); fromDisk = true;
      try {
        inReader = new BufferedInputStream(new FileInputStream(fileName));
      }
      catch (IOException e) {
        System.err.println(fileName + " not found");
        inReader = System.in;
        name = "StdIn"; openFailure = true; okay = false;
      }
    }
  }

  public InFile(InputStream s, boolean fromDisk) {
  // Creates an inFile from extant stream
  // (reverts to StdIn if it fails)
    init("stream");
    if (s == null) {
      System.err.println("InFile(null) directed to System.in");
      inReader = System.in;
      name = "StdIn"); openFailure = true; okay = false;
    } else {
      inReader = s; this.fromDisk = fromDisk;
    }
  }

  ------------------ */

  // internal character and token readers

  char nextChar() {
  // Probe and retrieve next character from InFile
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
        int i = inReader.read(); okay = true;
        if (i == CR) { i = inReader.read(); }     // MS-DOS
        ch = (char) i;
        if (i == -1 | i == 26) { ch = '\0'; eof = true; }   // there was no character after all
      }
      savedChar = ch; haveCh = false;
      eol = eof || ch == LF;                      // eof also sets eol
      inError = ! okay;
      if (!okay && printErrors) {
        System.err.println("Attempt to read past eof");
        System.exit(1);
      }
      return ch;
    }
    catch (IOException e) {
      System.err.println("Error reading " + name); System.exit(1);
      /* fudge compiler fussiness! */ return('a');
    }
  }

  private String readToken() {
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

  public char readChar() {
  // Reads and returns a single character
    char ch = nextChar();
    noData = eof;
    if (noData) reportError("readChar failed - no more data");
    return ch;
  }

  public void readAgain() {
  // Allows for a single character probe in code like
  // do ch = I.readChar(); while (notDigit(ch)); I.readAgain(); ...
    okay = true; haveCh = true;
  }

  public void skipSpaces() {
  // Allows for the most elementary of lookahead probes
    char ch;
    do ch = nextChar(); while (ch <= ' ' && !eof);
    haveCh = true;
  }

  public void readLn() {
  // Consumes all character to end of line.
  // Then probe for non-interactive files in case it reached EOF
    if (eof && printErrors) {
      System.err.println("ReadLn attempting to read past eof");
      System.exit(1);
    }
    while (!eol) readChar();
    haveCh = false; eol = false;
    if (fromDisk && !eof) {
      nextChar(); haveCh = true;
    }
  }

  // String based input

  public String readString() {
  // Reads and returns a String.  Incorporates leading white space,
  // and terminates on a control character, which is not
  // incorporated or consumed.
  // Note that this means you might need code like
  //       s = I.readString(); I.readLn();
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

  public String readString(int max) {
  // Reads and returns a String.  Incorporates leading white space,
  // and terminates with a control character, which is not
  // incorporated or consumed, or when max characters have been read
  // (useful for fixed format data).  Note that this means you might
  // need code like s = l.readString(10); l.readLn();
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

  public String readLine() {
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
    if (fromDisk && !eof) {
      nextChar(); haveCh = true;
    }
    return sb.toString();
  }

  public String readWord() {
  // Reads and returns a word - a String delimited at either end
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

  // Numeric input routines.  These always return, even if the data
  // is incorrect.  Users may probe the status of the operation,
  // or use showErrors to get error messages.

  public int readInt(int radix) {
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

  public int readInt() {
  // Reads and returns a single integer (base 10)
    return readInt(10);
  }

  public long readLong(int radix) {
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

  public long readLong() {
  // Reads and returns a single long integer (base 10)
    return readLong(10);
  }

  public short readShort(int radix) {
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

  public short readShort() {
  // Reads and returns a single short integer (base 10)
    return readShort(10);
  }

  public byte readByte(int radix) {
  // Reads a word as a textual representation of abyte (base radix)
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

  public byte readByte() {
  // Reads and returns a single byte (base 10)
    return readByte(10);
  }

  public float readFloat() {
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

  public double readDouble() {
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

  public boolean readBoolean() {
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

  // Utility "getters"

  public boolean eof() {
  // Returns true if the last operation terminated by reaching EOF
    return this.eof;
  }

  public boolean eol() {
  // Returns true if the last operation terminated by reaching EOL
    return this.eol;
  }

  public boolean error() {
  // Returns true if the last operation on this file failed
    return this.inError;
  }

  public boolean noMoreData() {
  // Returns true if the last operation on this file failed because
  // an attempt was made to read past the end of file
    return this.noData;
  }

  public String fileName() {
  // Returns the file name for the file
    return this.name;
  }

  // Error handlers

  public boolean openError() {
  // Returns true if the open operation on this file failed
    return this.openFailure;
  }

  public void reportError(String message) {
  // Possible simple error reporting (disabled by default)
    if (printErrors) System.err.println("--- " + name + " - " + message);
    errorCount++;
  }

  public int errorCount() {
  // Returns number of errors detected on input
    return this.errorCount;
  }

  public static boolean done() {
  // Simple error checking - reports result for last method called
  // regardless of which file was accessed
    return okay;
  }

  public void showErrors() {
  // Allows user to switch error reporting on (off by default)
    printErrors = true;
  }

  public void hideErrors() {
  // Allows user to switch error reporting off (default setting)
    printErrors = false;
  }

}  // InFile

