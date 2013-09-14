package org.champ

import scala.collection.mutable.HashMap

/** This is a simple implementation of a Burkhard-Keller tree.
  *
  *  BK trees are a data structure that is used for finding all elements in the structure at a distance <= d from a given element.
  *  The only condition is that the distance is a metric.
  *@constructor create a new BKtree from a List of elements.
  *@param elements a list of elements
  *@param dist a distance function for elements
  */
class BKtree[A](elements:List[A])(implicit dist:(A,A)=>Int) {
	val root = new BKnode(elements.head, new HashMap[Int,BKnode])
	elements.tail.foreach(root.addNew(_))
	
	/** An auxiliary class that actually implements the recursive structure.
 	  *
	  */
	class BKnode(val w:A, val subtrees:HashMap[Int, BKnode]){
 
	  def addNew(word:A):Unit={
	    val d = dist(word, w)
	    if(subtrees.contains(d))
	      subtrees(d).addNew(word)
	     else
	       subtrees(d) = new BKnode(word, new HashMap[Int,BKnode])
	  }
 
	  def query(word: A, l: Int): List[A] = {
	      val d = dist(word, w)
	      val distances = (d-l to d+l).filter(subtrees.contains(_))
	      val res:List[A]= distances.flatMap(tmpD=>subtrees(tmpD).query(word, l)).toList
	      if (d <= l) w :: res
	      else res
	    }
	}
	/** 
	  *
	  */
	def query(element: A, d: Int): List[A] = {
		root.query(element, d)
	}


}