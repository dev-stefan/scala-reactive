package de.tu_darmstadt.stg.impl.macros.reactive

import scala.collection.mutable.HashMap

/**
 * DependencyManager for managing recomputations in dependend elements
 */
object Dependencies {
  
  private val dependencyMap: HashMap[Reactive[_], List[Signal[_]]] = HashMap()
  
  private val signalToReactivesDependencyMap: HashMap[Reactive[_], List[Reactive[_]]] = HashMap()
  
  /**
   * add an element which has implemented Reactive to a Signal
   */
  def add(from: Reactive[_], to: Signal[_]) {
    val entryList = dependencyMap.getOrElse(from, List[Signal[_]]())
    dependencyMap.put(from, entryList :+ to)
    
    val signalToReactivesList = signalToReactivesDependencyMap.getOrElse(to, List[Reactive[_]]())
    signalToReactivesDependencyMap.put(to, signalToReactivesList :+ from)
  }

  /**
   * Set all dependent Reactives to not computed
   * used for the initial start state at the beginning of an update
   * in order to prevent glitches
   */
  def setAllDependentReactivesToNotComputed(reactive: Reactive[_]) {
    println(reactive.getName)
    if (dependencyMap.contains(reactive)) {
	    dependencyMap(reactive).foreach(dependant => {
	      dependant.setComputed(false)
	      setAllDependentReactivesToNotComputed(dependant)
	    })
    }
  }
  
  /**
   * Update existent dependencies
   */
  def update(reactive: Reactive[_]) {
    setAllDependentReactivesToNotComputed(reactive)
  
    updateInternal(reactive)
  }
  
  /**
   * Update the dependencies of a Reactive
   * Only update if dependent is ready for recompute or it is the first
   * and it has not been computed yet
   */
  private def updateInternal(reactive: Reactive[_]) {
    if (!dependencyMap.contains(reactive)) return

    //println("--> updating : " + reactive.getName);
    
    dependencyMap(reactive).foreach(dependant => {
      //println("  --> updating : " + reactive.getName + "[" + reactive.isComputed + "] | " + dependant.getName + "[" + dependant.isComputed + "] | doRecompute?") // - " + ( isReadyForRecompute(dependant) | isFirst));
      //println("  :: dependant : " + dependant.getName)
      
      if ( isReadyForRecompute(dependant) ){
    	//println("    *** " + dependant.getName + " is ready to recompute")
    	  dependant.recompute()
      } else {
        //println("    *** " + dependant.getName + " could not be recomputed")
      }
    	  
      Dependencies.updateInternal(dependant)
    })
    	  
  }
  
  /**
   * get list of dependent signals for a reactive element
   * if nothing is found: return empty list of signals
   */
  def getDependant(reactive: Reactive[_]) : List[Reactive[_]] = {
    val dependant = signalToReactivesDependencyMap.getOrElse(reactive, List[Reactive[_]]())
    dependant
  }
  
  /**
   * Check if the given Reactive is ready for recompute
   */
  def isReadyForRecompute(reactive: Reactive[_]) : Boolean = {
    val listOfSignals = getDependant(reactive)
    //println("  ### isReadyForRecompute?")
    //println("    ** reactive is " + reactive.getName + "[" + reactive() + "]")
    
    var ready = true
    if (!reactive.isInstanceOf[Var[_]]) {
      listOfSignals.foreach((el) => {
	      println("    - " + el.getName + " isComputed = " + el.isComputed);
	      if (!el.isComputed && !el.isInstanceOf[Var[_]]) {
	        ready = false
	      }
	    });
    }
    
    //println("  #-# isReadyForRecompute? = " + ready)
    
    ready
  }
}