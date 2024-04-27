package library;

import java.io.*;
import java.util.*;

// 2016/10/13

public class IntSet {
// Simple set class
// Non-existent error checking, in the interests of speed
// P.D. Terry (p.terry@ru.ac.za)

  BitSet bits;

  public IntSet() {
  // Empty set constructor
    bits = new BitSet();
  } // IntSet()

  public IntSet(BitSet s) {
  // Construct set from s
    bits = (BitSet) s.clone();
  } // IntSet(s)

  public IntSet(int ... members) {
  // Reasonable approximation to variable args constructor
  // Usage - IntSet(1)  IntSet(a, b, c) etc
    bits = new BitSet();
    for (int i = 0; i < members.length; i++) bits.set(members[i]);
  } // IntSet(a,b,c)

  public Object clone() {
  // Value copy
    return new IntSet(this.bits);
  } // IntSet.clone

  public IntSet copy() {
  // Another value copy (easier to use?)  Cannot call this "public IntSet clone()"
    return new IntSet(this.bits);
  } // IntSet.copy

  public boolean equals(IntSet s) {
  // Value comparison
    return this.bits.equals(s.bits);
  } // IntSet.equals

  public void incl(int i) {
  // Includes i in this set
    this.bits.set(i);
  } // IntSet.incl

  public void excl(int i) {
  // Excludes i from this set
    this.bits.clear(i);
  } // IntSet.excl

  public boolean contains(int i) {
  // Returns true if i is a member of this set
    return this.bits.get(i);
  } // IntSet.contains(i)

  public boolean contains(IntSet that) {
  // Returns true if that is a subset of this set
    return that.isEmpty() || that.difference(this).isEmpty();
  } // IntSet.contians(s)

  public boolean isEmpty() {
  // Returns true if this set is empty
    return this.bits.isEmpty();
  } // IntSet.isEmpty

  public boolean isFull() {
  // Returns true if this set is a universe set 0 ... length-1
    for (int i = 0; i < this.bits.length(); i++)
      if (!this.bits.get(i)) return false;
    return true;
  } // IntSet.isFull

  public boolean isFull(int max) {
  // Returns true if this set is a universe set 0 ... max-1
    for (int i = 0; i < max; i++)
      if (!this.bits.get(i)) return false;
    return true;
  } // IntSet.isFull(max)

  public void fill() {
  // Creates a full universe set for this set 0 ... length-1
    for (int i = 0; i < this.bits.length(); i++) this.bits.set(i);
  } // IntSet.fill

  public void fill(int max) {
  // Creates a full universe set for this set 0 ... max-1
    for (int i = 0; i < max; i++) this.bits.set(i);
  } // IntSet.fill(max)

  public void clear() {
  // Clear this set
    for (int i = 0; i < this.bits.length(); i++) this.bits.clear(i);
  } // IntSet.clear

  public int members() {
  // Returns number of members in this set
    return this.bits.cardinality();
  } // IntSet.fill

  public IntSet union(IntSet that) {
  // Set union
    IntSet D = new IntSet(this.bits);
    D.bits.or(that.bits);
    return D;
  } // IntSet.union

  public IntSet intersection(IntSet that) {
  // Set intersection
    IntSet D = new IntSet(this.bits);
    D.bits.and(that.bits);
    return D;
  } // IntSet.intersection

  public IntSet difference(IntSet that) {
  // Set difference
    IntSet D = new IntSet(this.bits);
    D.bits.andNot(that.bits);
    return D;
  } // IntSet.difference

  public IntSet symDiff(IntSet that) {
  // Set symmetric difference = xor
    IntSet D = new IntSet(this.bits);
    D.bits.xor(that.bits);
    return D;
  } // IntSet.symDiff

  public IntSet xor(IntSet that) {
  // xor = mmetric difference
    IntSet D = new IntSet(this.bits);
    D.bits.xor(that.bits);
    return D;
  } // IntSet.xor

  public void properties () {
  // Simple diagostic for testing
    System.out.println(this.bits.length()
             + "   " + this.bits.cardinality()
             + "   " + this.bits.size()
             + "   " + this.toString());
  } // IntSet.properties

  public void write() {
  // Simple display of this set on StdOut
    System.out.println(bits);
  } // IntSet.write

  public String toString() {
  // Create string representation of this set
    return this.bits.toString();
  } // IntSet.toString

  public String toCharSetString() {
  // Returns string representation of this set as set of chars
    StringBuilder sb = new StringBuilder(1000);
    sb.append('{');
    boolean comma = false;
    for (int i = 0; i < this.bits.length(); i++)
      if (this.bits.get(i)) {
        if (comma) sb.append(", "); comma = true;
        switch (i) {
          case '\"' : sb.append("'\"'");   break;
          case '\\' : sb.append("'\\\\'"); break;
          case '\'' : sb.append("'\\\''"); break;
          case '\b' : sb.append("'\\b'");  break;
          case '\f' : sb.append("'\\f'");  break;
          case '\n' : sb.append("'\\n'");  break;
          case '\r' : sb.append("'\\r'");  break;
          case '\t' : sb.append("'\\t'");  break;
          default   : sb.append("'" + ((char) i) + "'"); break;
        }
      }
    sb.append("}");
    return sb.toString();
  } // IntSet.toCharSetString

/* other possibilities --------------

  public static IntSet union(IntSet a, IntSet b) {
  // Set union
    IntSet D = new IntSet(a.bits);
    D.bits.or(b.bits);
    return D;
  } // IntSet.fill

  public static IntSet intersection(IntSet a, IntSet b) {
  // Set intersection
    IntSet D = new IntSet(a.bits);
    D.bits.and(b.bits);
    return D;
  } // IntSet.fill

  public static IntSet difference(IntSet a, IntSet b) {
  // Set difference
    IntSet D = new IntSet(a.bits);
    D.bits.andNot(b.bits);
    return D;
  } // IntSet.fill

  public static IntSet symDiff(IntSet a, IntSet b) {
  // Set symmetric difference = xor
    IntSet D = new IntSet(a.bits);
    D.bits.xor(b.bits);
    return D;
  } // IntSet.fill

  public static IntSet xor(IntSet a, IntSet b) {
  // xor = Set symmetric difference
    IntSet D = new IntSet(a.bits);
    D.bits.xor(b.bits);
    return D;
  } // IntSet.fill

 ---------------   */

} // IntSet

