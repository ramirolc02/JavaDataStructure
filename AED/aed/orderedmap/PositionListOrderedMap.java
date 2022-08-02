package aed.orderedmap;

import java.util.Comparator;
import java.util.Map;

import es.upm.aedlib.Entry;
import es.upm.aedlib.Position;
import es.upm.aedlib.indexedlist.ArrayIndexedList;
import es.upm.aedlib.indexedlist.IndexedList;
import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.positionlist.NodePositionList;

public class PositionListOrderedMap<K,V> implements OrderedMap<K,V> {
    private Comparator<K> cmp;
    private PositionList<Entry<K,V>> elements;
  
    /* Acabar de codificar el constructor */
    public PositionListOrderedMap(Comparator<K> cmp) {
	this.cmp = cmp;
	
	 PositionList<Entry<K,V>> elements = new NodePositionList<Entry<K,V>>();
	 
	 this.elements = elements;
	 
    }
    
    

    /* Ejemplo de un posible método auxiliar: */
  
    /* If key is in the map, return the position of the corresponding
     * entry.  Otherwise, return the position of the entry which
     * should follow that of key.  If that entry is not in the map,
     * return null.  Examples: assume key = 2, and l is the list of
     * keys in the map.  For l = [], return null; for l = [1], return
     * null; for l = [2], return a ref. to '2'; for l = [3], return a
     * reference to [3]; for l = [0,1], return null; for l = [2,3],
     * return a reference to '2'; for l = [1,3], return a reference to
     * '3'. */

    private Position<Entry<K,V>> findKeyPlace(K key) {
    	
       
        if (elements.isEmpty()) {
             return null;
        }
        Position<Entry<K,V>> cursor = elements.first();
        
        while(cursor!=null && cmp.compare(key,cursor.element().getKey()) > 0 ) {
             cursor = elements.next(cursor);
         }

        return cursor;
    }
    

    
    /* Podéis añadir más métodos auxiliares */
  
    public boolean containsKey(K key) {
    	
    	if(key==null) throw new IllegalArgumentException();
    	
    	boolean res = false;
    	
    	if (findKeyPlace(key)!= null && findKeyPlace(key).element().getKey().equals(key)) {
    		res = true;
    	}
    	return res;
    }
  
    public V get(K key) {
    	
    	V hola = null;
    	
    	if(key==null) throw new IllegalArgumentException();
    	
    	else if ( findKeyPlace(key)!= null && findKeyPlace(key).element().getKey().equals( key)) {
    		hola=findKeyPlace(key).element().getValue();
    	}
    	
	return hola;
	
    }
  
    public V put(K key, V value) {
    	
    	if(key==null) throw new IllegalArgumentException();
    	
    	V valorviejo = get(key);
    	
    	EntryImpl<K,V> j = new EntryImpl<K,V>(key, value);
    	
    	if (elements.size() == 0) {
    		elements.addFirst(j);
    		
    	}
    	else if (get(key)==null) {
    		if (findKeyPlace(key) == null) {
    		elements.addLast(j);
    		}
    		else {
    			elements.addBefore(findKeyPlace(key), j);
    		}
    	}
    	else {
           elements.set(findKeyPlace(key),j);
    	}
    	
	return valorviejo;
	
    }
  
  



	public V remove(K key) {
		
		 if(key==null) throw new IllegalArgumentException();
		 
	        V valorviejo = get(key);
	     
	        if (get(key)!=null) {
	        elements.remove(findKeyPlace(key));
	         }
	        return valorviejo;
    }
  
    public int size() {
    	return elements.size();
    }
  
    public boolean isEmpty() {
    	
    	boolean hay = false;
    	if (elements.isEmpty()) {
	        hay =  true;
    	}
    	return hay;
    }
  
    public Entry<K,V> floorEntry(K key) {
    	
    	 if(key==null) throw new  IllegalArgumentException();
    	 
    	 else if(elements.size()==0) {
        	 return null;
         }
         else  if(findKeyPlace(key)==null) {
        	 return elements.last().element();
         }
         else if(findKeyPlace(key).element().getKey().equals(key)) {
        	 return findKeyPlace(key).element();
         }
         else if(findKeyPlace(key).equals(elements.first())) {
        	 return null;
         }
         else {
        	 return elements.prev(findKeyPlace(key)).element();
         }	
    }
  
    public Entry<K,V> ceilingEntry(K key) {
    	if(key==null) throw new IllegalArgumentException();
    	
    	else if(elements.size()==0) {
    		return null;
    	}
        if(findKeyPlace(key)==null) {
        	return null;
        }
        else if(findKeyPlace(key).equals(key)) {
        	return findKeyPlace(key).element();
        }
        else {
        	return findKeyPlace(key).element();
        }
    }
     
    public Iterable<K> keys() {
    	
    	PositionList<K> llaves = new NodePositionList<K>();
    	Position<Entry<K,V>> cursorllaves = elements.first();
    
    	while (cursorllaves != null) {
    	     llaves.addLast(cursorllaves.element().getKey());	
    	     cursorllaves = elements.next(cursorllaves);
    	}
   	
    return llaves; 	
    }
  
    public String toString() {
	return elements.toString();
    }
 
  
}
