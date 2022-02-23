import java.util.*;
import java.util.Map.Entry;



public class Drassana_Dades implements Runnable{

	private Magatzem_peces_Dades magatzem_peces_dades;

	public static final String nomDrassana = "MCRN Calisto "; 
	
	private LinkedList<Nau_Dades> llistaNausEnDrassana = new LinkedList<Nau_Dades>();		// Cua de tipus FIFO que suporti null's
	
	// La clau serà peça_ID. El valor serà la quantitat de peces que hi ha del propi model.
	private LinkedHashMap<String, Integer> mapaStockPeces = new LinkedHashMap<String, Integer>();
	
	// La clau serà peça_ID. El valor seran els objecte de tipus Peça_electronica_Dades i es diferenciaran pel peça_num_serie.
	private LinkedHashMap<String, LinkedList<Peça_electronica_Dades>> mapaPecesElectronica = new LinkedHashMap<String, LinkedList<Peça_electronica_Dades>>();


	public Drassana_Dades(Magatzem_peces_Dades magatzem_peces_dades) {
		this.magatzem_peces_dades = magatzem_peces_dades;
	}

	public LinkedList<Nau_Dades> getLlistaNausEnDrassana() {
		return llistaNausEnDrassana;
	}

	public void setLlistaNausEnDrassana(LinkedList<Nau_Dades> llistaNausEnDrassana) {
		this.llistaNausEnDrassana = llistaNausEnDrassana;
	}

	public LinkedHashMap<String, Integer> getMapaStockPeces() {
		return mapaStockPeces;
	}

	public void setMapaStockPeces(LinkedHashMap<String, Integer> mapaStockPeces) {
		this.mapaStockPeces = mapaStockPeces;
	}

	public LinkedHashMap<String, LinkedList<Peça_electronica_Dades>> getMapaPecesElectronica() {
		return mapaPecesElectronica;
	}

	public void setMapaPecesElectronica(LinkedHashMap<String, LinkedList<Peça_electronica_Dades>> mapaPecesElectronica) {
		this.mapaPecesElectronica = mapaPecesElectronica;
	}

	public Magatzem_peces_Dades getMagatzem_peces_dades() {
		return magatzem_peces_dades;
	}
	public void veureContingutMapaPecesElectronica() {
		System.out.println("Peces electroniques de la drassana:");
		if(mapaPecesElectronica.entrySet().size()==0) {
			System.out.println("\tmapa buit");}
		else {
			for(Entry<String, LinkedList<Peça_electronica_Dades>> val:mapaPecesElectronica.entrySet()) {
				System.out.println("\t"+val.getKey()+": "+val.getValue().size());
			}
		}
	}


	@Override
	public void run() {

		String str="[";
		System.out.println("--------------------------------------------");
		for(int contador=0; contador<magatzem_peces_dades.getNausAcabades().size();contador++) {
			if(contador==magatzem_peces_dades.getNausAcabades().size()-1) {str+=magatzem_peces_dades.getNausAcabades().get(contador);}
			else {
				str+=magatzem_peces_dades.getNausAcabades().get(contador)+", ";
			}

		}
		str+="]";

		System.out.println(nomDrassana+": INICI "+str);
		System.out.print(nomDrassana+": INICI ");
		veureContingutMapaPecesElectronica();

		System.out.println("-------------------------------------------");
		Set s = mapaPecesElectronica.entrySet();
		Iterator<String> it1 = mapaPecesElectronica.keySet().iterator();
		String llaveString;
		while(it1.hasNext()) {
			llaveString=it1.next();
			for(Peça_electronica_Dades aDades :mapaPecesElectronica.get(llaveString)) {
				magatzem_peces_dades.processarPecesElectronica(aDades);

			}
			it1.remove();
		}

		int f=0;

		while(f!=magatzem_peces_dades.getNausAcabades().size()) {
			String stringbool="[";

			try {Thread.sleep(500);}
			catch (InterruptedException e) {e.printStackTrace();}

			for(boolean b:magatzem_peces_dades.getNausAcabades()) {
				if(b) {
					stringbool+=b+", ";
					f++;
				}
				else {
					stringbool+=b+", ";
					f=0;
				}
			}
			stringbool+="]";

			System.out.println(nomDrassana+": "+stringbool);

		}
		System.out.println();
		System.out.println("----------------------------");
		str="[";
		for(int contador=0; contador<magatzem_peces_dades.getNausAcabades().size();contador++) {
			if(contador==magatzem_peces_dades.getNausAcabades().size()-1) {str+=magatzem_peces_dades.getNausAcabades().get(contador);}
			else {
				str+=magatzem_peces_dades.getNausAcabades().get(contador)+", ";
			}
		}
		str+="]";
		System.out.println(nomDrassana+" FI "+str);
		System.out.print(nomDrassana+" FI: ");
		veureContingutMapaPecesElectronica();
		System.out.println("-------------------------------------");
	}
}
