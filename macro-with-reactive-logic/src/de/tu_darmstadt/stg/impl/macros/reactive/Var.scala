package de.tu_darmstadt.stg.impl.macros.reactive

class Var[T](protected var v: T) extends Reactive[T] {

  /**
   * Setter for a new value
   */
  def is(x: T) {
    v = x
    Dependencies update this
  }

  override def isComputed(): Boolean = true
  
  override def toString(): String = "Var is " + v
}