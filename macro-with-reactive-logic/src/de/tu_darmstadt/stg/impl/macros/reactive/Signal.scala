package de.tu_darmstadt.stg.impl.macros.reactive

class Signal[T](protected val f: () => T) extends Reactive[T] {
  protected var v: T = f()

  def recompute(): Unit = {
    v = f()
    setComputed(true)
  }
  
  override def toString() : String = "Signal [" + f.toString + "]"
}