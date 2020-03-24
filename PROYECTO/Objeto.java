//CLASE -> "OBJETO"
//AQUI DIVIDO MI EXPRESION REGULAR EN MULTIPLES UNIDADES QUE PERTENECEN AL MISMO OBJETO
//CADA UNIDAD SERA SUSTITUIDA POR LA CADENA DE SUSTITUCION CADA QUE ENCUENTRE UNA SIMILITUD EN LA CADENA 

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Objeto {
	
	public String expresion; 	  		  //NUESTRA EXPRESION REGULAR 
	public String repetidor;     		 //REPETIDOR ES EN EL CASO QUE TENGA UN ASTERISCO *
	public Objeto repetidorMulti;    //REPETIDOR ES EN EL CASO QUE TENGA UN ASTERISCO * QUE ENGLOBE PARENTESIS ( )*
	public static String reemplazo;
	
	public List<Objeto> solucion=new ArrayList<Objeto>();   //CADA OBJETO TIENE SU LISTA DE SOLUCIONES
	public List<Parentesis> listaParentesis;   //CADA OBJETO TIENE SU LISTA DE PARENTESIS 
	
	public boolean banderaSuma=false;		//SI HAY + QUE REPRESENTA UNA UNION SE PRENDE LA BANDERA Y TRATA LAS SOLUCIONES DE MANERA DIFERENTE
	public boolean banderaConcatenacion=false;	
	public boolean banderaC=false;	
	public boolean banderaCasilla=false;
	public String resultado="";
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Objeto(String expresion,String repetidor) {
		this.expresion=expresion;
		this.repetidor=repetidor;
	}

	public Objeto(Objeto repetidorMulti) {
		this.expresion=null;
		this.repetidorMulti=repetidorMulti;
	}


	public Objeto(String expresion) {
		this.expresion=expresion;
		this.repetidor=null;
		recorrer();
	}

	public void parentesis() {
		Stack<Character> stackLetras = new Stack<Character>();

		char arrayDeParentesis[] =expresion.toCharArray();
		List<Integer> posiciones=new <Integer>ArrayList();
		listaParentesis=new <Parentesis>ArrayList();
		int i;
		for (i = 0; i < arrayDeParentesis.length; i++) {
			if (arrayDeParentesis[i] == '(') {
				stackLetras.push(arrayDeParentesis[i]);
				posiciones.add(i);
			}else if (arrayDeParentesis[i] == ')') {
				if (!stackLetras.empty() && stackLetras.peek() != ')') {
					stackLetras.pop();
					Parentesis p = new Parentesis((int) posiciones.get(posiciones.size()-1),i);
					listaParentesis.add(p);
					posiciones.remove((int)posiciones.size()-1);
				}else{
					stackLetras.push(arrayDeParentesis[i]);  
				}
			}
		}

	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void recorrer() {     //AGREGAR EL COSO SOLITO A 
		String concatenacion="";
		
		for(int x=0;x<expresion.length();x++) {

			if(x==expresion.length()-1) {   //AGREGAMOS ULTIMO
				if(expresion.charAt(x)!='*') {
					concatenacion+=expresion.charAt(x);
					solucion.add(new Objeto(concatenacion,null));
				}
			}
			else if(expresion.charAt(x)!='(') {
				if(expresion.charAt(x)!='+') {
					if((x+1)<expresion.length()&&expresion.charAt(x+1)!='*') {
						concatenacion+=expresion.charAt(x);
					}else {
						if((x+2)<expresion.length()&&expresion.charAt(x+2)!='+') {
							Objeto ob=new Objeto(concatenacion,String.valueOf(expresion.charAt(x)));
							ob.banderaC=true;
							solucion.add(ob);
							x++; //SALTAMOS *
							concatenacion="";
						}else {
						solucion.add(new Objeto(concatenacion,String.valueOf(expresion.charAt(x))));
						x++; //SALTAMOS *
						concatenacion="";
						}
					}
				}else {
					banderaSuma=true;
					if(concatenacion!="") {
						solucion.add(new Objeto(concatenacion,null));
						concatenacion="";
					}
				}
			}else {
				if(concatenacion!="") {
					solucion.add(new Objeto(concatenacion,null));
				}
				parentesis();
				Parentesis temporal=null; 
				for(int y=0;y<listaParentesis.size();y++) {
					if(listaParentesis.get(y).pInicial==x) {
						temporal=listaParentesis.get(y);
					}
				}
				
				if(temporal.pFinal==expresion.length()-1) {
					solucion.add(new Objeto(expresion.substring(x+1,temporal.pFinal)));
					x=temporal.pFinal;
				}else if(expresion.charAt(temporal.pFinal+1)=='+') {
					banderaSuma=true;
					solucion.add(new Objeto(expresion.substring(x+1,temporal.pFinal)));
					x=temporal.pFinal+1;
				}else if(expresion.charAt(temporal.pFinal+1)=='*') {
					Objeto multiple=new Objeto(expresion.substring(x+1,temporal.pFinal));
					solucion.add(new Objeto(multiple));
					x=temporal.pFinal+1;
				}else {
					Objeto objetoConcatenacion=new Objeto(expresion.substring(x+1,temporal.pFinal));
					objetoConcatenacion.banderaConcatenacion=true;
					solucion.add(objetoConcatenacion);
					x=temporal.pFinal;
					banderaConcatenacion=true;
				}
			}
		}
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void metodoNic(String cadena) {
		int apuntador=0;
		int pos=0;
		if(cadena.length()!=0) {
			//TODO ESTO ES PARA SOLUCIONES EN LAS PRIMERAS CASILLAS NO OBJETOS NI ASTERISCOS
			int tamanio=solucion.size();
			while(tamanio>apuntador) { //TODO RECORRO ARREGLO DE SOLUCIONES
				
				if(solucion.get(apuntador).repetidor==null) { //SI NO TIENES REPETIDOR    CASO A AB A+B
					if((pos+solucion.get(apuntador).expresion.length())<=cadena.length()) {  //VALIDACION NO PASAR DE LONGITUD
						String d=cadena.substring(pos, pos+solucion.get(apuntador).expresion.length());
						String a=solucion.get(apuntador).expresion;
						if(a.equals(d)){
							System.out.print(reemplazo);	
							pos=pos+solucion.get(apuntador).expresion.length();
							metodoNic(cadena.substring(pos));
							return;
						}else if(solucion.size()>0){
							apuntador++;
						}
					}else {
						apuntador++;
					}
				}else { //SI TIENES REPETIDOR 
					if(solucion.get(apuntador).expresion.length()==0) {  //CASO A*  la expresion vacia y repetidor no
						String d=cadena.substring(pos, pos+solucion.get(apuntador).repetidor.length());
						String a=solucion.get(apuntador).repetidor;
						if(a.equals(d)) {
							while(a.equals(d)) {
								pos=pos+solucion.get(apuntador).repetidor.length();
								if(pos<cadena.length()) {
									d=cadena.substring(pos,pos+solucion.get(apuntador).repetidor.length());
								}else {
									d="";
								}
							}
							System.out.print(reemplazo);	
							metodoNic(cadena.substring(pos));
							return;
						}else if(solucion.size()>0) {
							apuntador++;
						}else {
							apuntador++;
						}
					}else { //CASO BA*  la expresion B y repetidor A
						if((pos+solucion.get(apuntador).expresion.length()+solucion.get(apuntador).repetidor.length())<=cadena.length()) {  //VALIDACION NO PASAR DE LONGITUD donde hay tanto repetidor como cadena
							String d=cadena.substring(pos, pos+solucion.get(apuntador).expresion.length());
							String c=cadena.substring(pos+solucion.get(apuntador).expresion.length(), pos+solucion.get(apuntador).repetidor.length()+solucion.get(apuntador).expresion.length());
							String e=solucion.get(apuntador).expresion;
							String r=solucion.get(apuntador).repetidor;
							if(e.equals(d)&&r.equals(c)){
								while(r.equals(c)) {
									int indice1=pos+solucion.get(apuntador).expresion.length();
									int indice2=pos+solucion.get(apuntador).repetidor.length()+solucion.get(apuntador).expresion.length();
									pos=pos+solucion.get(apuntador).repetidor.length();
									if(indice2<=cadena.length()) {
										c=cadena.substring(indice1, indice2);
									}else {
										c="";
									}
								}
								System.out.print(reemplazo);	
								metodoNic(cadena.substring(pos+1));
								return;
							}else if(e.equals(d)){
								System.out.print(reemplazo);	
								pos=pos+solucion.get(apuntador).repetidor.length()+solucion.get(apuntador).expresion.length()-1;
								if(solucion.size()>0) {
									apuntador++;
								}
								metodoNic(cadena.substring(pos));
								return;
							}else if(solucion.size()>0){
								apuntador++;
							}
						}else if(pos+solucion.get(apuntador).expresion.length()<=cadena.length()) { //VALIDACION NO PASAR DE LONGITUD donde hay solo cadena el repetidor no
							String d=cadena.substring(pos, pos+solucion.get(apuntador).expresion.length());
							String a=solucion.get(apuntador).expresion;
							if(a.equals(d)){
								System.out.print(reemplazo);	
								pos=pos+solucion.get(apuntador).expresion.length();
								metodoNic(cadena.substring(pos));
								return;
							}else {
								apuntador++;
							}
						}else{
							apuntador++;
						}
					}
				}
			}//TERMINA WHILE
			//EN CASO QUE NO HAYA NADA QUE REEMPLAZAR
			pos++;
			System.out.print(cadena.substring(0, 1));
			metodoNic(cadena.substring(pos));
			return;
		} //YA TERMINASTE DE RECORRER TODA LA CADENA 
	}
	
	

	private String metodoNic2(String cadena, int posicionSi,int ap) {
		int apuntador=ap;
		int pos=0;
		int indice2=0;
		if(cadena.length()!=0) {
			//TODO ESTO ES PARA SOLUCIONES EN LAS PRIMERAS CASILLAS NO OBJETOS NI ASTERISCOS
			int tamanio=solucion.size();
			while(tamanio>apuntador) {
				if(solucion.get(apuntador).repetidor!=null&&solucion.get(apuntador).expresion!="") { //CASO AA*  la expresion B y repetidor A  TIENES REPETIDOR 
					if((pos+solucion.get(apuntador).expresion.length()+solucion.get(apuntador).repetidor.length())<=cadena.length()) {  //VALIDACION NO PASAR DE LONGITUD donde hay tanto repetidor como cadena
						String d=cadena.substring(pos, pos+solucion.get(apuntador).expresion.length());
						String c=cadena.substring(pos+solucion.get(apuntador).expresion.length(), pos+solucion.get(apuntador).repetidor.length()+solucion.get(apuntador).expresion.length());
						String e=solucion.get(apuntador).expresion;
						String r=solucion.get(apuntador).repetidor;
						if(e.equals(d)&&r.equals(c)){
							while(r.equals(c)) {
								pos=pos+solucion.get(apuntador).repetidor.length();
								int indice1=pos+solucion.get(apuntador).expresion.length()-1;
								indice2=pos+solucion.get(apuntador).repetidor.length()+solucion.get(apuntador).expresion.length()-1;
								if(indice2<=cadena.length()) {
									c=cadena.substring(indice1, indice2);
								}else {
									c="";
								}
							}
							return metodoNic2(cadena.substring(indice2-1),indice2,ap+1);
						}else if(e.equals(d)){
							System.out.print(reemplazo);	
							pos=pos+solucion.get(apuntador).repetidor.length()+solucion.get(apuntador).expresion.length()-1;
							return metodoNic2(cadena,pos,ap+1);
						}else if(solucion.size()>0){
							apuntador=apuntador+2;
						}
					}else {
						apuntador=apuntador+2;
					}
					}else if(solucion.get(apuntador).repetidor==null) { //NO TIENES REPETIDOR SOLO LA EXPRESION A
						if((pos+solucion.get(apuntador).expresion.length())<=cadena.length()) {  //VALIDACION NO PASAR DE LONGITUD
							String d=cadena.substring(pos, pos+solucion.get(apuntador).expresion.length());
							String a=solucion.get(apuntador).expresion;
							if(a.equals(d)){	
								pos=pos+solucion.get(apuntador).expresion.length();
								return metodoNic2(cadena.substring(pos),pos,ap+1);
							}else if(solucion.size()>0){
								apuntador=apuntador+2;
								posicionSi=0;
							}
						}else {
							apuntador=apuntador+2;
						}
					}else if(solucion.get(apuntador).expresion.length()==0) {  //CASO A*  la expresion vacia y repetidor no
						String d=cadena.substring(pos, pos+solucion.get(apuntador).repetidor.length());
						String a=solucion.get(apuntador).repetidor;
						if(a.equals(d)) {
							while(a.equals(d)) {
								pos=pos+solucion.get(apuntador).repetidor.length();
								if(pos<cadena.length()) {
									d=cadena.substring(pos,pos+solucion.get(apuntador).repetidor.length());
								}else {
									d=" ";
								}
							}
							return metodoNic2(cadena.substring(pos),pos,ap+1);
						}else if(solucion.size()>0) {
							apuntador=apuntador+2;
						}else {
							apuntador=apuntador+2;
						}
					}
				}
			if(posicionSi==0) {
				System.out.print(cadena.substring(0, 1));
				return metodoNic2(cadena.substring(pos+1),0,0);
			}else {
				System.out.print(reemplazo);
				if(cadena.length()==1) {
					return metodoNic2(cadena,0,0);
				}else {
					return metodoNic2(cadena.substring(posicionSi-1),0,0);
				}
			}
		}
		if(posicionSi!=0) {  //si se cumple un b* al final de la cadena
			System.out.print(reemplazo);
		}
		return cadena;
	}

	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void imprimir() {
		for(int x=0;x<solucion.size();x++) {
			Objeto temporal=solucion.get(x);
			if(temporal.solucion.isEmpty()) {
				if(temporal.repetidorMulti!=null) {
					temporal.repetidorMulti.imprimir();
				}else
				System.out.println("EXPRESION:  " +temporal.expresion+"  REPETIDOR:  "+temporal.repetidor);
			}else {
				System.out.println("\t");
				temporal.imprimir();
			}
		}
	}

}
