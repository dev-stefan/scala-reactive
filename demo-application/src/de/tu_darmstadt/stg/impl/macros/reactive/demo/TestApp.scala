package de.tu_darmstadt.stg.impl.macros.reactive.demo

import de.tu_darmstadt.stg.impl.macros.reactive._

object TestApp {

  def main(argv: Array[String]): Unit = {
    /*val a = new Var(0)
    
    
    val s0 = Signal { println("signal0:"); val x = a() + 10; println("" + x); };
    val s1 = Signal { println("signal1:"); val x = a() + 20; println("" + x); };
    val s2 = Signal { 
      println("signal2:");
      s0()
    };
    s0.setName("s0")
    s1.setName("s1")
    s2.setName("s2")
    
    a is 5*/
    /*
    val a = new Var(0)
    val b = new Var(1)
    a.setName("a[Var]")
    b.setName("b[Var]")
    val txt = new Var("Test")
    a is 3
    b is 2

    println("create Signal s...")
    val s = Signal { println("|| recompute in s"); a() + b() }
    s.setName("s[Signal]")
    
    println("create Signal t...")
    val t = Signal { println("|| recompute in t"); s() + b() }
    t.setName("t[Signal]")
    
    println("create Signal u...")
    val u = Signal { println("|| recompute in u"); s() + t() }
    u.setName("u[Signal]")
    
    
    println()
    println("changing a (there should be a recompute in s)...")
    a is 10
    println("...a changed")
    println()
    println("printing the value of u (there should be no recompute)...")
    println(u())*/
/*
    println()
   // {val tmp = new Signal(() => { println("recompute in t"); s() + b.value }); Dependencies add s tmp; Dependencies add b tmp; tmp}
    println("changing a (there should be a recompute in s and t)...")
    a is 5
    println("...a changed")
    println()
    println("printing the value of t (there should be no recompute)...")
    println(t())
  */
    
/*
    // 4:1 multiplexer (see Harris & Harris, page 81, Figure 2.58a)
    // create a 2 Bit select Signal
    val S0 = new Var(true)
    S0.setName("S0")
    val S1 = new Var(true)
    S1.setName("S1")

    // create four 1 Bit data inputs
    val D0 = new Var(false)
    D0.setName("D0")
    val D1 = new Var(true)
    D1.setName("D1")
    val D2 = new Var(false)
    D2.setName("D2")
    val D3 = new Var(true)
    D3.setName("D3")
    
    // negates of the select bits
    val nS0 = Signal { !S0() }
    val nS1 = Signal { !S1() }

    // compute intermediate results
    val O0 = Signal { D0() & nS1() & nS0() }
    O0.setName("O0")
    val O1 = Signal { D1() & nS1() & S0() }
    O1.setName("O1")
    val O2 = Signal { D2() & S1() & nS0() }
    O2.setName("O2")
    val O3 = Signal { D3() & S1() & S0() }
    O3.setName("O3")

    // determine the output
    val Y = Signal { O0() | O1() | O2() | O3() }
    Y.setName("Y")

    println("The output should be false true false true")
    S0 is false
    S1 is false
    println("******" + Y())
    S0 is true
    println("******" + Y())
    S0 is false
    S1 is true
    println("******" + Y())
    S0 is true
    println("******" + Y())
    
    */
    
    /*
    val a = new Var(0)
    a.setName("a");
    
    val b = Signal { 100 + a() }
    b.setName("b = 100+a");
    
    val c = Signal { 200 + a() }
    c.setName("c = 200+a");
    
    val d = Signal { b() + c() }
    d.setName("d = b+c");
    
    a is 10
    println(c());
    
    println("computation:")
    println("a - " + a.isComputed)
    println("b - " + b.isComputed)
    println("c - " + c.isComputed)
    
  
    a is 1
    println(c());
    */
    
    
    
    val a = new Var(0)
    a.setName("a");
    
    val constSig = Signal { 100 }
    constSig.setName("constSig")
    
    val b = Signal { constSig() + a() }
    b.setName("b = 100+a");
    
    val c = Signal { 200 + a() }
    c.setName("c = 200+a");
    
    val d = Signal { b() + c() }
    d.setName("d = b+c");
    
    a is 10
    println("a is = " + a())
    println("b is = " + b())
    println("c is = " + c())
    println("d is = " + d())
    println("constSig is = " + constSig())
    
    println()
    
  
    a is 1
    println("a is = " + a())
    println("b is = " + b())
    println("c is = " + c())
    println("d is = " + d())
    println("constSig is = " + constSig())
    
  }
}