package aed.filter;

import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

import es.upm.aedlib.indexedlist.ArrayIndexedList;
import es.upm.aedlib.indexedlist.IndexedList;



public class Utils {

  public static <E> Iterable<E> filter(Iterable<E> d, Predicate<E> pred) {
	  IndexedList<E> r = new ArrayIndexedList<E>();
	  
	  
	  if (d == null) {
		  throw new IllegalArgumentException();
	  }
	  
		  Iterator <E> recorrerD = d.iterator();
	  while(recorrerD.hasNext()) {
		  E x = recorrerD.next();
		    if (x == null) {
		       }
		    else if  (pred.test(x)) {
				  r.add(r.size(), x);
		     }
		}
	    
	  
	  
	return r;
  }
	  	  
  }


