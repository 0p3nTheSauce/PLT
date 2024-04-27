package library;

import java.io.*;

// Release 1: 2004/06/21
// Release 2: 2008/11/21
// Release 3: 2009/01/27
// Release 4: 2009/07/14

public class OutFile {
// Provide simple facilities for text output
// P.D. Terry (p.terry@ru.ac.za)

/* Java 1.1 wraps System.out.  Notice that we have autoflush turned on */

  public static final OutFile StdOut = new OutFile();
  public static final OutFile StdErr = new OutFile(new PrintWriter(new OutputStreamWriter(System.err), true));

  boolean openFailure;
  PrintWriter outWriter;
  String fileName;

  class OnExit extends Thread {
    public void run() {
//      IO.writeLine("closing " + fileName);
      outWriter.close();
    }
  }

  // File opening and closing

  public OutFile() {
  // Create an OutFile to StdOut
    openFailure = false;
    outWriter = new PrintWriter(new OutputStreamWriter(System.out), true);
  }

  public OutFile(String fileName) {
  // Creates an OutFile to named disk file
  // (reverts to StdOut if it fails)
    openFailure = false;
    if (fileName != null) fileName = fileName.trim();
    if (fileName != null && fileName.equals(""))
      outWriter = new PrintWriter(new OutputStreamWriter(System.out), true);
    else {
      try {
        outWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName)), true);
        Runtime.getRuntime().addShutdownHook(new OnExit());
        this.fileName = fileName;
      }
      catch (IOException e) {
        System.err.println("OutFile(" + fileName + ") failed - redirected to System.out");
        openFailure = true;
        outWriter = new PrintWriter(new OutputStreamWriter(System.out), true);
      }
    }
  }

  public OutFile(PrintWriter s) {
  // Creates an OutFile from extant stream
  // (reverts to StdOut if it fails)
    openFailure = false;
    if (s == null) {
      System.err.println("OutFile(null) directed to System.out");
      openFailure = true;
      outWriter = new PrintWriter(new OutputStreamWriter(System.out), true);
    } else {
      outWriter = s;
      Runtime.getRuntime().addShutdownHook(new OnExit());
      this.fileName = "output stream";
    }
  }

  public void close() {
  // Closes the file
  // Unfortunately there seems no simple way to get the OutFile to close automagically
  // Well - I was wrong!  see OnExit
    outWriter.close();
  }


/* ------- Java 1.0 ---- for reference.  Here we use a PrintStream

  public static OutFile StdOut = new OutFile();
  public static OutFile StdErr = new OutFile(System.err);

  PrintStream outWriter;

  public OutFile() {
  // Create an OutFile to StdOut
    openFailure = false;
    outWriter = System.out;
  }

  public OutFile(String fileName) {
  // Creates an OutFile to named disk file
  // (reverts to StdOut if it fails)
    openFailure = false;
    if (fileName != null && fileName.equals(""))
      outWriter = System.out;
    else {
      try {
        outWriter = new PrintStream(new BufferedOutputStream(new FileOutputStream(fileName)), true);
      }
      catch (IOException e) {
        System.err.writeLine(fileName + " not created");
        openFailure = true;
        outWriter = System.out;
      }
    }
  }

  public OutFile(PrintStream s) {
  // Creates an OutFile from extant stream
  // (reverts to StdOut if it fails)
    openFailure = false;
    if (s == null) {
      System.err.writeLine("OutFile(null) directed to System.out");
      openFailure = true;
      outWriter = System.out;
    } else outWriter = s;
  }

*/

  // The following methods mostly simply map the operations onto the hidden
  // PrintWriter, and provide the standard functionality of that PrintWriter

  public void write(String o)                 { outWriter.print(o); outWriter.flush(); }
  public void write(Object o)                 { outWriter.print(o); outWriter.flush(); }
  public void write(byte o)                   { write(o, 0); }
  public void write(short o)                  { write(o, 0); }
  public void write(int o)                    { write(o, 0); }
  public void write(long o)                   { write(o, 0); }
  public void write(boolean o)                { write(o, 0); }
  public void write(float o)                  { write(o, 0); }
  public void write(double o)                 { write(o, 0); }
  public void write(char o)                   { outWriter.print(o); outWriter.flush(); }
  public void write(char[] o)                 { outWriter.print(o); outWriter.flush(); }
  public void write(char[] o, int off,
                    int len)                  { outWriter.write(o, off, len); outWriter.flush(); }

  public void writeLine()                     { outWriter.println(); }
  public void writeLine(String o)             { outWriter.println(o); }
  public void writeLine(Object o)             { outWriter.println(o); }
  public void writeLine(byte o)               { writeLine(o, 0); }
  public void writeLine(short o)              { writeLine(o, 0); }
  public void writeLine(int o)                { writeLine(o, 0); }
  public void writeLine(long o)               { writeLine(o, 0); }
  public void writeLine(boolean o)            { writeLine(o, 0); }
  public void writeLine(float o)              { writeLine(o, 0); }
  public void writeLine(double o)             { writeLine(o, 0); }
  public void writeLine(char o)               { outWriter.println(o); }
  public void writeLine(char[] o)             { outWriter.println(o); outWriter.flush(); }
  public void writeLine(char[] o, int off,
                        int len)              { outWriter.write(o, off, len); outWriter.println(); outWriter.flush(); }

  private void putStr(String s, int width) {
    if (width == 0) outWriter.print(" " + s);
    else if (width > 0) {
      for (int i = s.length(); i < width; i++) outWriter.print(' ');
      outWriter.print(s);
    }
    else {
      outWriter.print(s);
      for (int i = s.length(); i < -width; i++) outWriter.print(' ');
    }
    outWriter.flush();
  }

  public void write(String o, int width)      { putStr(o, width); }
  public void write(Object o, int width)      { putStr(String.valueOf(o), width); }
  public void write(byte o, int width)        { putStr(String.valueOf(o), width); }
  public void write(short o, int width)       { putStr(String.valueOf(o), width); }
  public void write(int o, int width)         { putStr(String.valueOf(o), width); }
  public void write(long o, int width)        { putStr(String.valueOf(o), width); }
  public void write(boolean o, int width)     { putStr(String.valueOf(o), width); }
  public void write(float o, int width)       { putStr(String.valueOf(o), width); }
  public void write(double o, int width)      { putStr(String.valueOf(o), width); }
  public void write(char o, int width)        { putStr(String.valueOf(o), width); }

  private void putLine(String s, int width) {
    if (width == 0) outWriter.println(" " + s);
    else if (width > 0) {
      for (int i = s.length(); i < width; i++) outWriter.print(' ');
      outWriter.println(s);
    }
    else {
      outWriter.print(s);
      for (int i = s.length(); i < -width; i++) outWriter.print(' ');
      outWriter.println();
    }
  }

  public void writeLine(String o, int width)  { putLine(o, width); }
  public void writeLine(Object o, int width)  { putLine(String.valueOf(o), width); }
  public void writeLine(byte o, int width)    { putLine(String.valueOf(o), width); }
  public void writeLine(short o, int width)   { putLine(String.valueOf(o), width); }
  public void writeLine(int o, int width)     { putLine(String.valueOf(o), width); }
  public void writeLine(long o, int width)    { putLine(String.valueOf(o), width); }
  public void writeLine(boolean o, int width) { putLine(String.valueOf(o), width); }
  public void writeLine(float o, int width)   { putLine(String.valueOf(o), width); }
  public void writeLine(double o, int width)  { putLine(String.valueOf(o), width); }
  public void writeLine(char o, int width)    { putLine(String.valueOf(o), width); }

  // formatters

  public void writeFormatted(String format, Object... list) {
    outWriter.printf(format, list);
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

  public void writeFixed(double d, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) outWriter.print(" ");
    String fmt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "f";
    outWriter.print(String.format(fmt, d));
  }

  public void writeLineFixed(double d, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) outWriter.print(" ");
    String fmt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "f";
    outWriter.println(String.format(fmt, d));
  }

  public void writeFloating(double d, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) outWriter.print(" ");
    String fmt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "E";
    outWriter.print(String.format(fmt, d));
  }

  public void writeLineFloating(double d, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) outWriter.print(" ");
    String fmt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "E";
    outWriter.print(String.format(fmt, d));
  }

  public void write(double d, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) outWriter.print(" ");
    String fix = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "f";
    String flt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "E";
    String sfx = String.format(fix, d);
    String sfl = String.format(flt, d);
    if (width == 0)
      if (sfx.length() <= sfl.length()) outWriter.print(sfx); else outWriter.print(sfl);
    else
      if (sfx.length() <= Math.abs(width)) outWriter.print(sfx); else outWriter.print(sfl);
  }

  public void writeLine(double d, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) outWriter.print(" ");
    String fix = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "f";
    String flt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "E";
    String sfx = String.format(fix, d);
    String sfl = String.format(flt, d);
    if (width == 0)
      if (sfx.length() <= sfl.length()) outWriter.println(sfx); else outWriter.println(sfl);
    else
      if (sfx.length() <= Math.abs(width)) outWriter.println(sfx); else outWriter.println(sfl);
  }

  public void writeFixed(float f, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) outWriter.print(" ");
    String fmt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "f";
    outWriter.print(String.format(fmt, f));
  }

  public void writeLineFixed(float f, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) outWriter.print(" ");
    String fmt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "f";
    outWriter.println(String.format(fmt, f));
  }

  public void writeFloating(float f, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) outWriter.print(" ");
    String fmt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "E";
    outWriter.print(String.format(fmt, f));
  }

  public void writeLineFloating(float f, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) outWriter.print(" ");
    String fmt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "E";
    outWriter.println(String.format(fmt, f));
  }

  public void write(float f, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) outWriter.print(" ");
    String fix = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "f";
    String flt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "E";
    String sfx = String.format(fix, f);
    String sfl = String.format(flt, f);
    if (width == 0)
      if (sfx.length() <= sfl.length()) outWriter.print(sfx); else outWriter.print(sfl);
    else
      if (sfx.length() <= Math.abs(width)) outWriter.print(sfx); else outWriter.print(sfl);
  }

  public void writeLine(float f, int width, int fractionDigits) {
    fractionDigits = Math.abs(fractionDigits);
    if (width == 0) outWriter.print(" ");
    String fix = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "f";
    String flt = "%" + String.valueOf(width) + "." + String.valueOf(fractionDigits) + "E";
    String sfx = String.format(fix, f);
    String sfl = String.format(flt, f);
    if (width == 0)
      if (sfx.length() <= sfl.length()) outWriter.println(sfx); else outWriter.println(sfl);
    else
      if (sfx.length() <= Math.abs(width)) outWriter.println(sfx); else outWriter.println(sfl);
  }

  // Error handler

  public boolean openError() {
  // Returns true if the open operation on this file failed
    return openFailure;
  }

} // OutFile

