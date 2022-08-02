package aed.indexedlist;

import es.upm.aedlib.indexedlist.*;

public class Utils {
	public static <E> IndexedList<E> deleteRepeated(IndexedList<E> l) {
		// Hay que modificar este metodo

		IndexedList<E> memoria = new ArrayIndexedList<E>();

		int j = 0;
		memoria.add(j, l.get(j));

		for (int i = 0; i < l.size(); i++) {
			while (j < memoria.size()) {
				if (l.get(i).equals(memoria.get(j))) {
					j = 0;
					break;
				} else {
					j++;
				}
			}
			if (j == memoria.size()) {
				memoria.add(j, l.get(i));
				j = 0;
			}
		}

		return memoria;
	}
}
