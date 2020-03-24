//CLASE QUE CONTIENE EL MAIN
//REALIZA LAS PRIMERAS VALIDACIONES PARA DEJAR LIMPIA TANTO LA CADENA COMO LA EXPRESION ORIGINAL Y ASI EVITAR ERRORES
//MANDA A LLAMAR A LA CLASE OBJETO

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Orquestador {
	//3 VARIABLES BASICAS
	public String cadena;
	public String reemplazo;
	public String ER;
	
	
	//LISTA PARA MANIPULAR NUESTRA CADENA
	public List<String> palabras=new ArrayList<String>();


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// VALIDACIONES GENERALES
	// VALIDACIONES CON EL PROPOSITO DE EVITAR QUE NUESTRO CODIGO TRUENE POR "ERRORES" DE ESCRITURA

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

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//VALIDAR PARENTESIS      
	//VALIDACION REALIZADA PARA CHECAR QUE LOS PARENTESIS ESTEN COLOCADOS DE MANERA CORRECTA Y EVITAR QUE TRUENE EL PROGRAMA 
	public boolean validarMalParentesis() {

		Stack<Character> stackLetras = new Stack<Character>();  //USO UN STACK PARA GUARDAR LOS PARENTESIS
		char arrayDeParentesis[] = ER.toCharArray();  //CONVIERTO MI ER EN UN ARREGLO
		
		int i;
		for (i = 0; i < arrayDeParentesis.length; i++) {
			if (arrayDeParentesis[i] == '(') {
				stackLetras.push(arrayDeParentesis[i]);  //CADA QUE ENCUENTRA UN PARENTESIS QUE ABRE LO GUARDA EN EL STACK
			}else if (arrayDeParentesis[i] == ')') { 
				if (!stackLetras.empty() && stackLetras.peek() != ')') {
					stackLetras.pop();  //CADA QUE ENCUENTRA UN PARENTESIS QUE CIERRA Y EL STACK DE PARENTESIS NO ESTA VACIO SACA EL PARENTESIS QUE ABRE
				}else{
					stackLetras.push(arrayDeParentesis[i]);  
				}
			}
		}										 //SI EL STACK ESTA VACIO SIGNIFICA QUE HAY EL MISMO NUMERO DE PARENTESIS QUE ABRE QUE LOS QUE CIERRA
		return stackLetras.empty();  //POR LO TANTO REGRESA TRUE SI ESTA BIEN, DE LO CONTRARIO FALSE

	}


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//VALIDAR EXPRESION REGULAR -> NO INICIE CON +/* O QUE TERMINE CON + O QUE DESPUES DE UNA + HAYA UN *
	//VALIDACION PARA EVITAR "ERRORES" DE ESCRITURA INICIALES EN NUESTRA EXPRESION REGULAR 
	
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
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METODO INVOCADOR LA PRIMERA VEZ 
	//		->CONTIENE LOS VALORES INICIALES 
	//		->LLEVA A CABO TODAS LAS VALIDACIONES INICIALES
	//		->MANDA A LLAMAR A MI CLASE OBJETO 
	
	public void invocador() {
		//cadena="abaa aab aaanbbb";  //xAx   //hey
		cadena="xaccx";
		reemplazo="w";
		//ER="aaba*+bbb*";
		ER="a+ccb*";

		if(validar_esp()||validarMalParentesis()==false||validaCadena()) {   //SI HAY ALGUN ERROR NO LO EJECUTA
			if(validarMalParentesis()==false) {
				System.out.println("HAY UN ERROR EN TUS PARENTESIS");
			}
			return;
		}else {
			quitarSalto();
			quitarTab();
			quitarEspaciosER();
			cadenaLista();
			Objeto objeto=new Objeto(ER);
			objeto.reemplazo=this.reemplazo;
			//objeto.imprimir();
			for(int x=0;x<palabras.size();x++) {
				objeto.metodoNic(palabras.get(x));   //METODO REPLACE
				//System.out.print(palabras.get(x));
				System.out.println("");
			}
		}
	}
	

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METODO MAIN
	//MANDO A LLAMAR A MI METODO INVOCADOR 
	public static void main(String[] args) {
		Orquestador  coso=new Orquestador();
		coso.invocador();		
	}
	
}
