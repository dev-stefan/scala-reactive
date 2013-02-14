package de.tu_darmstadt.stg.impl.macros

import scala.language.experimental.macros
import scala.reflect.macros.Context

package object reactive {
  def Signal[A](expression: A): Signal[A] = macro Signal_impl[A]

  def Signal_impl[A: c.WeakTypeTag](c: Context)(expression: c.Expr[A]): c.Expr[Signal[A]] = {
    import c.universe._

    // extracting sub trees with type Reactive[_]
    val extractedReactives = expression.tree.filter(t => t.tpe <:< typeOf[Reactive[_]])

    // new Signal(() => expression)
    val signal = reify { new Signal(() => expression.splice) }.tree

    // "tmpSignal$"
    val freshName = newTermName(c.fresh("tmpSignal$"))

    // val tmpSignal$ = new Signal(() => expression)
    val createSignal = ValDef(Modifiers(), freshName, TypeTree(weakTypeOf[Signal[A]]), signal)

    // for each dependency: Dependencies.add(dependency, tmpSignal$)
    val addDeps = extractedReactives.map(dependency =>
      reify { Dependencies.add(c.Expr[Reactive[_]](dependency).splice, c.Expr[Signal[A]](Ident(freshName)).splice) }.tree)

    // tmpSignal$
    val returnValue = Ident(freshName)

    // glue everything together
    c.Expr[Signal[A]](Block(createSignal :: addDeps, returnValue))
  }
}