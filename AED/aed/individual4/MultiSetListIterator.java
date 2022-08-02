package aed.individual4;



import java.util.Iterator;
import java.util.NoSuchElementException;


import es.upm.aedlib.Pair;
import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.PositionList;

public class MultiSetListIterator<E> implements Iterator<E> {
	
  
  PositionList<Pair<E, Integer>> list;
  Position<Pair<E,Integer>> cursor;
  int counter;
  Position<Pair<E,Integer>> prevCursor;

 

  public MultiSetListIterator(PositionList<Pair<E,Integer>> list) {
   
    
	  if (list != null && list.first() != null) {
    
    this.list = list;
    
    cursor = list.first(); 
    prevCursor =list.prev(cursor);
	  }
    
    
    
    
  } 

  public boolean hasNext() {
	  boolean hay = false;
	  
	  if (cursor == null || cursor.element() == null ) {
		  
	  }
	  else if (counter < cursor.element().getRight()) {
	         hay = true;
	  }
	  else if (list.next(cursor) != null) {
		     hay = true;
	  }
	  
   return hay;
  }

  public E next() {
    // hay que modificar
	  
	if (cursor == null || cursor.element() == null ) {
		  throw new NoSuchElementException();
	  }
     else if ( counter < cursor.element().getRight()) {
	    counter ++;
		return cursor.element().getLeft();
	}
	 else if ( list.next(cursor) != null && counter == cursor.element().getRight() ) {
		counter =0;
		counter ++;
	    cursor = list.next(cursor);
	    prevCursor = list.prev(cursor);
	 }
	 else {
			throw new NoSuchElementException();
		}
	    
	    return cursor.element().getLeft();
	}
  
  
  
  public void remove() {
    // opcionalmente se puede modificar para obtener mas puntos
    // NO ES OBLIGATORIO
	  
	  if (cursor.element() == null || counter == 0 ) {
		  throw new IllegalStateException();
	  }
	  else if (counter < cursor.element().getRight()) {
		 cursor.element().setRight(cursor.element().getRight()-1);
		 counter =0;
	  }
	  else if (list.next(cursor) == null && cursor.element().getRight() == counter ) {
		  if (counter ==1) {
		  list.remove(cursor);
		  counter =0;
		  }
		  else {
			  cursor.element().setRight(cursor.element().getRight()-1);
			  counter =0;
		  }
	  }
	  else if ( cursor.element().getRight() == counter ) {
		  if (counter ==1) {
		   cursor = list.next(cursor);
		   prevCursor = list.prev(cursor);
		   list.remove(prevCursor);
		   counter =0;
		  }
		  else {
			  cursor = list.next(cursor);
			  prevCursor = list.prev(cursor);
			  prevCursor.element().setRight(prevCursor.element().getRight()-1);
			  counter =0;
		  }
	  }

  }
}
