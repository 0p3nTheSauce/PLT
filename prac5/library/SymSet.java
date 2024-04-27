package library;

import java.io.*;
import java.util.*;

// 2016/10/13

public class SymSet {
// Simple set class
// Non-existent error checking, in the interests of speed
// P.D. Terry (p.terry@ru.ac.za)

  BitSet bits;

  public SymSet() {
  // Empty set constructor
    bits = new BitSet();
  } // SymSet()

  public SymSet(BitSet s) {
  // Construct set from s
    bits = (BitSet) s.clone();
  } // SymSet(s)

  public SymSet(int[] members) {
  // Reasonable approximation to variable args constructor
  // Usage - SymSet(new int[] {1})  SymSet(new int[] {a, b, c}) etc
    bits = new BitSet();
    for (int i = 0; i < members.length; i++) bits.set(members[i]);
  } // SymSet(a,b,c)

  public Object clone() {
  // Value copy
    return new SymSet(this.bits);
  } // SymSet.clone

  public SymSet copy() {
  // Another value copy (easier to use?)  Cannot call this "public SymSet clone()"
    return new SymSet(this.bits);
  } // SymSet.copy

  public boolean equals(SymSet s) {
  // Value comparison
    return this.bits.equals(s.bits);
  } // SymSet.equals

  public void incl(int i) {
  // Includes i in this set
    this.bits.set(i);
  } // SymSet.incl

  public void excl(int i) {
  // Excludes i from this set
    this.bits.clear(i);
  } // SymSet.excl

  public boolean contains(int i) {
  // Returns true if i is a member of this set
    return this.bits.get(i);
  } // SymSet.contains(i)

  public boolean contains(SymSet that) {
  // Returns true if that is a subset of this set
    return that.isEmpty() || that.difference(this).isEmpty();
  } // SymSet.contains(s)

  public boolean isEmpty() {
  // Returns true if this set is empty
    return this.bits.isEmpty();
  } // SymSet.isEmpty

  public boolean isFull() {
  // Returns true if this set is a universe set 0 ... length-1
    for (int i = 0; i < this.bits.length(); i++)
      if (!this.bits.get(i)) return false;
    return true;
  } // SymSet.isFull

  public boolean isFull(int max) {
  // Returns true if this set is a universe set 0 ... max-1
    for (int i = 0; i < max; i++)
      if (!this.bits.get(i)) return false;
    return true;
  } // SymSet.isFull(max)

  public void fill() {
  // Creates a full universe set for this set 0 ... length-1
    for (int i = 0; i < this.bits.length(); i++) this.bits.set(i);
  } // SymSet.fill

  public void fill(int max) {
  // Creates a full universe set for this set 0 ... max-1
    for (int i = 0; i < max; i++) this.bits.set(i);
  } // SymSet.fill(max)

  public void clear() {
  // Clear this set
    for (int i = 0; i < this.bits.length(); i++) this.bits.clear(i);
  } // SymSet.clear

  public int members() {
  // Returns number of members in this set
    return this.bits.cardinality();
  } // SymSet.fill

  public SymSet union(SymSet that) {
  // Set union
    SymSet D = new SymSet(this.bits);
    D.bits.or(that.bits);
    return D;
  } // SymSet.union

  public SymSet intersection(SymSet that) {
  // Set intersection
    SymSet D = new SymSet(this.bits);
    D.bits.and(that.bits);
    return D;
  } // SymSet.intersection

  public SymSet difference(SymSet that) {
  // Set difference
    SymSet D = new SymSet(this.bits);
    D.bits.andNot(that.bits);
    return D;
  } // SymSet.difference

  public SymSet symDiff(SymSet that) {
  // Set symmetric difference = xor
    SymSet D = new SymSet(this.bits);
    D.bits.xor(that.bits);
    return D;
  } // SymSet.symDiff

  public SymSet xor(SymSet that) {
  // xor = mmetric difference
    SymSet D = new SymSet(this.bits);
    D.bits.xor(that.bits);
    return D;
  } // SymSet.xor

  public void properties () {
  // Simple diagostic for testing
    System.out.println(this.bits.length()
             + "   " + this.bits.cardinality()
             + "   " + this.bits.size()
             + "   " + this.toString());
  } // SymSet.properties

  public void write() {
  // Simple display of this set on StdOut
    System.out.println(bits);
  } // SymSet.write

  public String toString() {
  // Create string representation of this set
    return this.bits.toString();
  } // SymSet.toString

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
  } // SymSet.toCharSetString

/* other possibilities --------------

  public static SymSet union(SymSet a, SymSet b) {
  // Set union
    SymSet D = new SymSet(a.bits);
    D.bits.or(b.bits);
    return D;
  } // SymSet.fill

  public static SymSet intersection(SymSet a, SymSet b) {
  // Set intersection
    SymSet D = new SymSet(a.bits);
    D.bits.and(b.bits);
    return D;
  } // SymSet.fill

  public static SymSet difference(SymSet a, SymSet b) {
  // Set difference
    SymSet D = new SymSet(a.bits);
    D.bits.andNot(b.bits);
    return D;
  } // SymSet.fill

  public static SymSet symDiff(SymSet a, SymSet b) {
  // Set symmetric difference = xor
    SymSet D = new SymSet(a.bits);
    D.bits.xor(b.bits);
    return D;
  } // SymSet.fill

  public static SymSet xor(SymSet a, SymSet b) {
  // xor = Set symmetric difference
    SymSet D = new SymSet(a.bits);
    D.bits.xor(b.bits);
    return D;
  } // SymSet.fill

 ---------------   */

} // SymSet

