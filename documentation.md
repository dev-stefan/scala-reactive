Documentation
==============

scala-reactive is a framework for reactive programming in Scala with usage of macros.
This project has been inspired by the library scala.react invented by Ingo Maier (https://github.com/ingoem/scala-react).


Usage
--------------
In order to use the macro you first have to compile the macro and after this compile your sourcecode.
The following classes exist:


Var
--------------
A Var is a variable which can be changed during runtime and all dependent signals will be recomputed on change. Just create a new instance of Var and add an initial value in the constructor. For debugging purposes you can set a name to each Var with the setName method.
In order to change the value of a Var use the "is" method.

Example:
```scala
val myVar = new Var(123)
myVar.setName("this is myVar")
...
myVar is 321 // change value to 321
```

Signal
--------------
A Signal acts as an observer for the calculations used in the body of a Signal. If the value of a Var that has been used in the declaration of the Signal changes the Signal's value will be recomputed. Signals are rewritten at compile time by through the macro we developed.

Example:
```scala
val myVar = new Var(123)
myVar.setName("this is myVar")

val mySignal = Signal { println("myVar changed the value"); myVar() }

myVar is 321 // change value to 321, the Signal mySignal will be recomputed
```

Internals
--------------
At the core of our work is an object called "Dependencies" that manages the connections between Vars and Signals at runtime. If a Var's value changes the "Dependencies" object will determine all dependent objects and update them in a glitch free fashion.
To establish these dependencies at runtime our macro will expand Signal definitions at compile time as follows:

Prior to macro expansion:
```scala
val myVar = new Var(123)
val mySignal = Signal { myVar() + 5 }

After macro expansion:
val myVar = new Var(123)
val mySignal = {
  var tmpSignal = new Signal(() => { myVar() + 5 })
	Dependencies.add(myVar, tmpSignal)
	tmpSignal }
```

Notes
--------------
* Multi-Threading
  Our current implementation is not thread safe. This property can probably be reached by marking the Dependencie's update method as synchronized.

* Prevention of recomputation glitches
  In our first implementation there were too many recomputations of the Signals if the dependency graph had more than one possible topological order. Those have been removed by implementing a computation state flag which is used to check if all dependencies of a Signal have already been recomputed prior to recomputing the value of the Signal itself.
