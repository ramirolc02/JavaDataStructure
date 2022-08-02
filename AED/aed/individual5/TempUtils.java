package aed.individual5;


import es.upm.aedlib.Pair;
import es.upm.aedlib.map.*;


public class TempUtils {
  public static Map<String,Integer> maxTemperatures(long startTime,long endTime,TempData[] tempData) {
    
	  Map<String,Integer> map = new HashTableMap<String, Integer>();
	  Integer maximo = -1;
	  int i =0;
	  
	  if (tempData.length == 0 ) {
		  return map;
	  }
	  else {
	      while(i<tempData.length) { 
	    	  if (tempData[i].getTime() >= startTime && tempData[i].getTime() <= endTime) {
	    		  
	    		  if (!map.containsKey(tempData[i].getLocation())) {
				  map.put(tempData[i].getLocation(), tempData[i].getTemperature());
	    		  }
				  
				  else {
					  maximo = map.get(tempData[i].getLocation());
					  
					  if (maximo < tempData[i].getTemperature()) {
					   map.put(tempData[i].getLocation(), tempData[i].getTemperature());
					  }
					  
				  }
			  }
	    	  i++;
		  }
		 
	  }
     
	  return map;
  }


  public static Pair<String,Integer> maxTemperatureInComunidad(long startTime, long endTime,String region,TempData[] tempData,
                                                               Map<String,String> comunidadMap) {
	  Integer maximo = -1;
	  Pair<String,Integer> res = new Pair<String,Integer>("error",maximo);
	  int i =0;
	  
	  if (tempData.length == 0 ) {
		  return null;
	  }
	  else {
	      while(i<tempData.length) { 
	    	  if (tempData[i].getTime() >= startTime && tempData[i].getTime() <= endTime) {
	    		  if (region.equals(comunidadMap.get(tempData[i].getLocation())) && maximo < tempData[i].getTemperature()) {
				  res.setLeft(tempData[i].getLocation());
				  res.setRight(tempData[i].getTemperature());
				  maximo = res.getRight();
				  
	    		  }
				}
	    	  i++;
			  }
		  }
	  if (res.getLeft().equals("error")){
	  return null;
	  }
	  return res;
	  }
  
	  
	  }

     
	  
 



