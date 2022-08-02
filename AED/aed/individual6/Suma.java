package aed.individual6;

import es.upm.aedlib.graph.Edge;
import es.upm.aedlib.graph.Vertex;

import java.util.Iterator;

import aed.hashtable.HashTable;
import es.upm.aedlib.graph.DirectedGraph;
import es.upm.aedlib.map.Map;
import es.upm.aedlib.map.HashTableMap;


public class Suma {
  public static <E> Map<Vertex<Integer>,Integer> sumVertices(DirectedGraph<Integer,E> g) {
	  
	  Map<Vertex<Integer>,Integer> res = new HashTableMap<Vertex<Integer>,Integer>();
	  Iterator<Vertex<Integer>> it = g.vertices().iterator();
	  
	  
	  while(it.hasNext()) {
		  Map<Vertex<Integer>,Integer> visitados = new HashTableMap <Vertex<Integer>,Integer>();
		  Vertex<Integer> Nodo = it.next();
		  Integer suma =0;
	      res.put(Nodo,sumar(g,Nodo,res,visitados,it,suma));
	  }
	  return res;
	  
  		}
  
  public static <E> Integer sumar(DirectedGraph<Integer,E> g,
		  Vertex<Integer> vertex,Map<Vertex<Integer>,Integer> res,
		  Map<Vertex<Integer>,Integer> visitados,
		  Iterator<Vertex<Integer>> it,Integer suma) {
	  
	     visitados.put(vertex,vertex.element());
	  for(Edge<E> e: g.outgoingEdges(vertex)) {
		  Vertex<Integer> alcanzable = g.endVertex(e);
		  if(!alcanzable.equals(vertex) && !visitados.containsKey(alcanzable)) {
			  suma = sumar(g,alcanzable,res,visitados,it,suma);
		  }
	  }
		  suma += vertex.element(); 
	      return suma;
 }
}
  

