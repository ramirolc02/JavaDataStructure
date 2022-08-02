package aed.recursion;

import es.upm.aedlib.Node;
import es.upm.aedlib.Pair;
import es.upm.aedlib.Position;
import es.upm.aedlib.indexedlist.*;
import es.upm.aedlib.positionlist.*;


public class Utils {

  public static int multiply(int a, int b) {
	  return multiply_rec(a,b,0,1);
  }

 private static int multiply_rec (int a, int b, int sum, int sign) {
	 if (a<0) {
		 sign = -1;
	 } 
	 if (a ==0) {
		 return sum*sign;
	 }
	 if (a%2 != 0) {
		 sum += b;
	 }
	 return multiply_rec(a/2,b*2,sum,sign);
	 
 }
  public static <E extends Comparable<E>> int findBottom(IndexedList<E> l) {
	  
    return  findBottom_rec(l,0,l.size()-1);
  
  }
	
  private static  <E extends Comparable<E>> int findBottom_rec(IndexedList<E> l, int start,int end) {
	  
	  int medio = (start+end)/2;
	  if (Math.abs(start-end)<2) {
		  if (l.get(start).compareTo(l.get(end))<0) {
			  return start;
		  }
		  return end;
	  }
	   
	  if (l.get(medio).compareTo(l.get((medio)-1)) > 0) {
		  end = medio-1;
	  }
	  else if (l.get(medio).compareTo(l.get((medio)+1)) > 0) {
		  start = medio+1;
	  }
	  else {
		  return medio;
	  }
		  return findBottom_rec(l,start,end);
	  }
	 
  public static <E extends Comparable<E>> NodePositionList<Pair<E,Integer>>
  joinMultiSets(NodePositionList<Pair<E,Integer>> l1,
		  NodePositionList<Pair<E,Integer>> l2) {
	  NodePositionList<Pair<E,Integer>> resul = new NodePositionList<Pair<E,Integer>>();
	  Position<Pair<E,Integer>> cursorl1= l1.first();
	  Position<Pair<E,Integer>> cursorl2= l2.first();
	  
  return joinMultiSetsRec(l1,l2, resul, cursorl1 ,cursorl2);
}
private static <E extends Comparable<E>> NodePositionList<Pair<E,Integer>>
joinMultiSetsRec(NodePositionList<Pair<E, Integer>> l1,
		  NodePositionList<Pair<E, Integer>> l2, NodePositionList<Pair<E, Integer>> resul, 
		   Position<Pair<E, Integer>> cursorl1,
		  Position<Pair<E, Integer>> cursorl2) {
	
	if (cursorl1 == null && cursorl2 == null) {
		return resul;
	}
	else if (cursorl1 == null && cursorl2 != null ) {
		  resul.addLast(cursorl2.element());
		  return joinMultiSetsRec(l1,l2, resul ,cursorl1,l2.next(cursorl2));
	  }
	  else if(cursorl1 != null && cursorl2 == null ){
		  resul.addLast(cursorl1.element());
		  return joinMultiSetsRec(l1,l2, resul ,l1.next(cursorl1),cursorl2);
	  }
	 
	   if (cursorl1.element().getLeft().compareTo(cursorl2.element().getLeft())==0) { 
		  resul.addLast(cursorl1.element());
		  resul.last().element().setRight((cursorl1.element().getRight()+cursorl2.element().getRight()));
		  cursorl1 =l1.next(cursorl1);
		  cursorl2 =l2.next(cursorl2);
	  }
	  else if(cursorl1.element().getLeft().compareTo(cursorl2.element().getLeft()) >0) {
		  resul.addLast(cursorl2.element());
		  cursorl2 =l2.next(cursorl2);
	  }
	  else {
		  resul.addLast(cursorl1.element());
	  		cursorl1 =l1.next(cursorl1);
	  }
	  		
	  return joinMultiSetsRec(l1,l2, resul ,cursorl1,cursorl2);
	  
}

}
