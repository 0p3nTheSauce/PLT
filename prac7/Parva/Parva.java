package Parva;

/* Driver for compilers and assemblers targetting the PVM: Coco/R for Java.
   P.D. Terry, Rhodes University; modified KL Bradshaw, 2021 */

//  ----------------------- you may need to change the "import" clauses:

import java.io.*;
import java.util.*;
import library.*;

  public class Parva {

    private static String newFileName(String s, String ext) {
      int i = s.lastIndexOf('.');
      if (i < 0) return s + ext; else return s.substring(0, i) + ext;
    }

    public static void main (String[] args) {
      boolean mergeErrors = false;
      String inputName = null;

      // ------------------------- process command line parameters:

      System.out.println("Parva compiler 1.2014");

      for (int i = 0; i < args.length; i++) {
        if (args[i].toLowerCase().equals("-l")) mergeErrors = true;
        else if (args[i].toLowerCase().equals("-d")) Parser.debug = true;
        else if (args[i].toLowerCase().equals("-w")) Parser.warnings = false;
        else inputName = args[i];
      }
      if (inputName == null) {
        System.err.println("No input file specified");
        System.err.println("Usage: Parva [-l] [-d] [-w] source.pav");
        System.err.println("-l directs source listing to listing.txt");
        System.err.println("-d turns on debug mode");
        System.err.println("-w suppresses warnings");
        System.exit(1);
      }

      // ------------------------ parser and scanner initialization

      int pos = inputName.lastIndexOf('/');
      if (pos < 0) pos = inputName.lastIndexOf('\\');
      String dir = inputName.substring(0, pos+1);

      Scanner.Init(inputName);
      Errors.Init(inputName, dir, mergeErrors);
      PVM.init();
      Table.init();

      // ------------------------ compilation

      Parser.Parse();
      Errors.Summarize();

      // ------------------------ interpretation

      boolean assembledOK = Parser.Successful();
      int initSP = CodeGen.getInitSP();
      String codeName = newFileName(inputName, ".cod");
      int codeLength = CodeGen.getCodeLength();
      PVM.listCode(codeName, codeLength);
      if (!assembledOK || codeLength == 0) {
        System.err.println("Unable to execute code");
        System.exit(1);
      }
      else {
        char reply = 'n';
        do {
          System.err.print("\n\nInterpret [y/N]? ");
          reply = (InFile.StdIn.readLine() + " ").toUpperCase().charAt(0);
          if (reply == 'Y') PVM.interpret(codeLength, initSP);
        } while (reply == 'Y');
      }
    } // main

  } // end Parva
