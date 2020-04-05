import java.util.ArrayList;
import java.util.List;

public class Orquestador {
	public String cadena;
	public String reemplazo;
	public String ER;
	public List<String> palabras=new ArrayList<String>();

	public boolean validaCadena(){   //VALIDACION DE CADENA NO VACIA
		if(cadena.isEmpty()){
			System.out.print("TU CADENA ESTA VACIA!!!! NO HAY NADA CON QUE COMPARAR");
			return true;
		}
		return false;
	}

	public void quitarSalto() {   //CAMBIA LOS SALTOS DE LINEA POR ESPACIOS PARA RESPETAR LA SEPARACION DE PALABRAS
		cadena=cadena.replace("\n"," ");
		ER=ER.replace("\n","");
	}

	public void quitarTab() {		//CAMBIA LOS TABULADORES POR ESPACIOS PARA RESPETAR LA SEPARACION DE PALABRAS
		cadena=cadena.replace("\t"," ");
		ER=ER.replace("\t","");
	}

	public void quitarEspaciosER() {		//ELIMINA LOS ESPACIOS EN LA EXPRESION REGULAR YA QUE NO SON NECESARIOS
		ER=ER.replace(" ","");
	}

	public void cadenaLista() {         //PASO CADENA ARREGLO SEPARANDO POR ESPACIOS, SI HAY MULTIPLES ESPACIOS LOS ELIMINO AL CONVERTIRLO EN LISTA
		String temporal[]=cadena.split(" ");
		for(int x=0;x<temporal.length;x++) {
			if(!temporal[x].equals(" ")||!temporal[x].equals("")) {
				palabras.add(temporal[x]);
			}	
		}
	}
	
	public boolean validar_esp(){
		if(ER.charAt(0)==('*')&&ER.charAt(0)==('+')&&ER.charAt(ER.length()-1)==('+')) {
			System.out.println("HAY UN ERROR EN LOS OPERADORES DE TU EXPRESION REGULAR, FAVOR DE REVISAR POSICIONES INICIALES O FINALES");
			return true;
		}			
		String[] errores= {"()","(*)","(+)","(+","+)","++","+*","**","(*"};
		for(int x=0;x<errores.length;x++){
			if(ER.indexOf(errores[x])!=-1){
				System.out.println("HAY UN ERROR EN LOS OPERADORES DE TU EXPRESION REGULAR, FAVOR DE REVISAR");
				return true;
			}
		}
		return false;
	}
 	
	public void invocador() {
		//cadena="abaa aab aaanbbb";  
		//cadena="abaaaabaaanbbb";
		//cadena="xacccDaeeeDx";
		//cadena="xaDaaeex";
		cadena="xaDaeeex";
		//cadena="CxaDDxsxscxxrrrrr";
		reemplazo="w";
		//reemplazo="ccc";
		//ER="aba*+bbb*";
		ER="ac*Dae*";
		//ER="C+aD*+sc+r*";

		quitarSalto();
		quitarTab();
		quitarEspaciosER();
		cadenaLista();
		Objeto objeto=new Objeto(ER);
		objeto.reemplazo=this.reemplazo;
	
		for(int x=0;x<palabras.size();x++) {
			objeto.metodoNic(palabras.get(x));   //METODO REPLACE
			System.out.println("");
		}
	}

	public static void main(String[] args) {
		Orquestador  coso=new Orquestador();
		coso.invocador();		
	}
}