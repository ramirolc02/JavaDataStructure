package aed.hashtable;

import java.util.NoSuchElementException;

//import com.sun.javafx.css.StyleCacheEntry.Key;

import java.util.Iterator;

import es.upm.aedlib.Entry;
import es.upm.aedlib.EntryImpl;
import es.upm.aedlib.map.HashTableMap;
import es.upm.aedlib.map.Map;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib	.InvalidKeyException;


/**
 * A hash table implementing using open addressing to handle key collisions.
 */
public class HashTable<K,V> implements Map<K,V> {
  Entry<K,V>[] buckets;
  int size;

  public HashTable(int initialSize) {
    this.buckets = createArray(initialSize);
    this.size = 0;
  }

  /**
   * Add here the method necessary to implement the Map api, and
   * any auxilliary methods you deem convient.
   */

  // Examples of auxilliary methods: IT IS NOT REQUIRED TO IMPLEMENT THEM
  
  @SuppressWarnings("unchecked") 
  private Entry<K,V>[] createArray(int size) {
   Entry<K,V>[] buckets = (Entry<K,V>[]) new Entry[size];
   return buckets;
  }

  // Returns the bucket index of an object
  private int index(Object obj) {
	  
	 return Math.abs(obj.hashCode()) % buckets.length;
	 
  }

  // Returns the index where an entry with the key is located,
  // or if no such entry exists, the "next" bucket with no entry,
  // or if all buckets stores an entry, -1 is returned.
  private int search(Object obj) {
	  
	  int indice = index(obj);
	  int n = buckets.length;
	  while (n > 0) {
	    if ( buckets[indice] == null ||  buckets[indice].getKey().equals(obj) ) {
	      return indice;
	    }
	    n--;
	    indice = (indice +1) % (buckets.length);
	  }
	  return -1;
	    
  }

  // Doubles the size of the bucket array, and inserts all entries present
  // in the old bucket array into the new bucket array, in their correct
  // places. Remember that the index of an entry will likely change in
  // the new array, as the size of the array changes.º
  
  private void rehash() {
	  Iterable <Entry<K,V>> entrada = entries();
	  Iterator <Entry<K,V>> it = entrada.iterator();
	  this.buckets=createArray(size*2);
	  this.size=0;

		while (it.hasNext()) {
			Entry<K,V> x = it.next();
			if (x != null) {
				put(x.getKey(),x.getValue());
			}
			
		}
  }

@Override
public Iterator<Entry<K, V>> iterator() {
	// TODO Auto-generated method stub
	 Map<K,V> map = new HashTableMap<K, V>();
	 int i=0;
	 while(i< buckets.length) {
		 
	 map.put(buckets[i].getKey(), buckets[i].getValue());
	 i++;
	 
	 }
	 return map.iterator();
}

@Override
public boolean containsKey(Object arg0) throws InvalidKeyException {
	int indice = index(arg0);
	  int n = buckets.length;
	  while (n > 0) {
		if(buckets[indice]==null) {return false;}
	    if (buckets[indice].getKey().equals(arg0) ) {
	      return true;
	    }
	    n--;
	    indice = (indice +1) % (buckets.length);
	  }
	return false;
}

@Override
public Iterable<Entry<K, V>> entries() {
	// TODO Auto-generated method stub
	
	 PositionList<Entry<K,V>> map = new NodePositionList<Entry<K, V>>();
	 int i=0;
	 
	 while(i < buckets.length) {
     if(buckets[i] == null) {
    	 
     }
     else  {
	 map.addLast(buckets[i]);
     }
	 i++;
	 }
	 
	 return map;
}

@Override
public V get(K arg0) throws InvalidKeyException {
	// TODO Auto-generated method stub
	if (arg0 == null) {
		throw new InvalidKeyException();
	}
	if (!containsKey(arg0)) {
		return null;
	}
	int buscar = search(arg0);
	V res = buckets[buscar].getValue();
	return res;
}

@Override
public boolean isEmpty() {
   	Iterable <Entry<K,V>> entrada = entries();
   	Iterator <Entry<K,V>> it = entrada.iterator();
   	
	while (it.hasNext()) {
		Entry<K,V> x = it.next();
		if (x != null)
			return false;
	}
	return true;
	// TODO Auto-generated method stub
}

@Override
public Iterable<K> keys() {
	// TODO Auto-generated method stub
	 Map<K,V> map = new HashTableMap<K, V>();
	 int i=0;
	 
	 while(i < buckets.length) {
    if(buckets[i] == null) {
   	 
    }
    else  {
	 map.put(buckets[i].getKey(), buckets[i].getValue());
    }
	 i++;
	 }
	 
	 return map.keys();
}

@Override
public V put(K arg0, V arg1) throws InvalidKeyException {
	// TODO Auto-generated method stub
	if (arg0 == null ) {
		throw new InvalidKeyException();
	}
	int a = search(arg0);
	EntryImpl<K,V> res = new EntryImpl<K,V>(arg0, arg1);
	if (containsKey(arg0)) {
		V anterior= buckets[a].getValue();
		buckets[a]=res;
		return anterior;
	}
	if (a == -1) {
		rehash();
		a = search(arg0);
	}
	
	buckets[a] = res; 
	size++;
	
	return null;
}

@Override
public V remove(K arg0) throws InvalidKeyException {
	if (arg0 == null ) {
		throw new InvalidKeyException();
	}
	if(!containsKey(arg0)) {
		return null;
	}
	int indice=search(arg0);
	V resu=buckets[indice].getValue();
	buckets[indice]=null;
	size--;
	int start=indice;
	int i= (start+1) % buckets.length;
	while(i!=start && buckets[i]!=null) {
		int pref=search(buckets[i].getKey());
		if((i!=pref && ((pref<indice && indice<i) || indice>=pref || indice<i ))){
			buckets[indice]=buckets[i];
			buckets[i]=null;
			indice=i;
		}
		i= (i+1) % buckets.length;
	}
	return resu;
}

@Override
public int size() {
	return size;
}
  
}
