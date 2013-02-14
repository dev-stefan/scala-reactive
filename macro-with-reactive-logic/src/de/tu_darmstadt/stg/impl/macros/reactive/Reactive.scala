package de.tu_darmstadt.stg.impl.macros.reactive

/**
 * Base for Var and Signal
 */
trait Reactive[T] {
  protected var v: T
  
/**
 * State of computation. If true then this element has been
 * already computed
 */
  private var computed: Boolean = true
  
  /**
   * Name of the element which has implemented Reactive
   */
  protected var name: String = "Reactive"
  
  def apply(): T = v
  
  def setComputed(value : Boolean) = {
    computed = value
  }
  
  def isComputed(): Boolean = computed
  
  def setName(n : String) = {
    name = n
  }
  
  def getName(): String = name
  
}