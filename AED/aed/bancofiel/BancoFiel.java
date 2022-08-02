package aed.bancofiel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import aed.bancofiel.TesterLab1.CuentasListOk;
import es.upm.aedlib.indexedlist.IndexedList;
import es.upm.aedlib.indexedlist.ArrayIndexedList;


/**
 * Implements the code for the bank application.
 * Implements the client and the "gestor" interfaces.
 */
public class BancoFiel implements ClienteBanco, GestorBanco {

  // NOTAD. No se deberia cambiar esta declaracion.
  public IndexedList<Cuenta> cuentas;

  // NOTAD. No se deberia cambiar esta constructor.
  public BancoFiel() {
    this.cuentas = new ArrayIndexedList<Cuenta>();
  }

  // ----------------------------------------------------------------------
  // Anadir metodos aqui ..
   
  private int buscarCuenta (String id) {
	  
	  int i =0;
	  int primero = 0;
	  int ultimo = cuentas.size()-1;
	  int medio = ultimo/2;
	   while(primero <= ultimo) {
		  if ( cuentas.get(medio).getId().compareTo(id) < 0) {
			  primero = medio+1;
		  }
		  else if (cuentas.get(medio).getId().compareTo(id) == 0){
			  i = cuentas.indexOf(cuentas.get(medio));
			  break;
		  }
		  else {
			  ultimo = medio-1;
		  }
		  medio = (primero + ultimo)/2;
	  }
	   if (primero > ultimo) {
			  i = -1;
		  }
	   return i;
  }

  @Override
  public IndexedList<Cuenta> getCuentasOrdenadas(Comparator<Cuenta> cmp) {
  
	  IndexedList<Cuenta> list = new ArrayIndexedList<Cuenta>();
	 
	  
	  int j = 0;
	  list.add(0, cuentas.get(0));
	  
	  for (int i = 1; i<cuentas.size();i++) {
		  for (j = 0; j<list.size();j++) {
			  if (cmp.compare(cuentas.get(i), list.get(j))<0) {
				  break;
			  }
		  }
			  list.add(j,cuentas.get(i));   
	  }

	 return list;	  	
  }
  

  
  @Override
  public String crearCuenta(String dni, int saldoIncial) {
	  
	    int i=0;
	  Cuenta CuentaNueva =  new Cuenta(dni, saldoIncial); 
	  if (cuentas.size() == 0) {
	         cuentas.add(0, CuentaNueva);
	  }
	  else {
		  for (i=0; i<cuentas.size();i++) {
			  if (cuentas.get(i).getId().compareTo(CuentaNueva.getId()) > 0) {
				 cuentas.add(i, CuentaNueva);
				 break;
			  }
			  
		  }
			  if (i == cuentas.size()) {
				  cuentas.add(i, CuentaNueva);
			  }
			  
		  }
	         return CuentaNueva.getId();
  }

  @Override
  public void borrarCuenta(String id) throws CuentaNoExisteExc, CuentaNoVaciaExc {
	  
	  if (buscarCuenta(id) == -1) {
		  throw new CuentaNoExisteExc();
	  }
	  else if (cuentas.get(buscarCuenta(id)).getSaldo() != 0 ) {
		  throw new CuentaNoVaciaExc();
	  }
	  else {
		  cuentas.removeElementAt(buscarCuenta(id));
	  }
  }

  @Override
  public int ingresarDinero(String id, int cantidad) throws CuentaNoExisteExc {
	  
	  if (buscarCuenta(id) == -1) {
		  throw new CuentaNoExisteExc();
	  }
	  else {
		  cuentas.get(buscarCuenta(id)).ingresar(cantidad);
	  }
	  return cuentas.get(buscarCuenta(id)).getSaldo();
  	 
  }

  @Override
  public int retirarDinero(String id, int cantidad) throws CuentaNoExisteExc, InsuficienteSaldoExc {

	  if (buscarCuenta(id) == -1) {
		  throw new CuentaNoExisteExc();
	  }
	  else if (cantidad > cuentas.get(buscarCuenta(id)).getSaldo()) {
		  throw new InsuficienteSaldoExc();
	  }
	  else {
		  cuentas.get(buscarCuenta(id)).retirar(cantidad);
	  }
	  return cuentas.get(buscarCuenta(id)).getSaldo();
  }

  @Override
  public int consultarSaldo(String id) throws CuentaNoExisteExc {
  	// TODO Auto-generated method stub
	  if (buscarCuenta(id) == -1) {
		  throw new CuentaNoExisteExc();
	  }
	  return cuentas.get(buscarCuenta(id)).getSaldo();
	  
  }

  @Override
  public void hacerTransferencia(String idFrom, String idTo, int cantidad)
  		throws CuentaNoExisteExc, InsuficienteSaldoExc {
  	// TODO Auto-generated method stub
	  if (buscarCuenta(idFrom) == -1 || buscarCuenta(idTo) == -1) {
		  throw new CuentaNoExisteExc();
	  }
	  else if (cantidad > cuentas.get(buscarCuenta(idFrom)).getSaldo()) {
		  throw new InsuficienteSaldoExc();
	  }
	  else {
		  retirarDinero(idFrom,cantidad);
		  ingresarDinero(idTo,cantidad);
		  
	  }
	  
  }

  @Override
  public IndexedList<String> getIdCuentas(String dni) {
	  
	  IndexedList<String> listaDeIds = new ArrayIndexedList<String>();
	  
	 for (int i=0; i < cuentas.size();i++) {
		 
		 if(cuentas.get(i).getDNI().equals(dni)) {
			 
			 listaDeIds.add(0,cuentas.get(i).getId() );
		 }
		 
	 }
  return listaDeIds;
  }

  @Override
  public int getSaldoCuentas(String dni) {
	  
	  int sumaTotal=0;
	  for (int i=0; i < cuentas.size();i++) {
		   if(cuentas.get(i).getDNI().equals(dni)) {
			   sumaTotal += cuentas.get(i).getSaldo();
		  }
	  }
	  return sumaTotal;

  }


  // ----------------------------------------------------------------------
  // NOTAD. No se deberia cambiar este metodo.
  public String toString() {
    return "banco";
  }


  
}
