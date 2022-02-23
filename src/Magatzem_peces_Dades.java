import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.Semaphore;


public class Magatzem_peces_Dades {

	private Semaphore semaphorede4 = new Semaphore(4);

	private Semaphore semaphore1 = new Semaphore(1);
	// Aquí s'emmagatzemaran totes les peces electròniques que hi ha en les naus i en la drassana. 
	// Totes les naus i la drassana han d'enviar les seves peces a aquest magatzem.
	
	// És una llista inicialitzada per Drassana que contindrà tants false's com naus hi hagi en la drassana.
	private ArrayList <Boolean> nausAcabades = new ArrayList<Boolean>();
	
	// La clau serà peça_ID. El valor seran els objecte de tipus Peça_electronica_Dades i es diferenciaran pel peça_num_serie.
	private LinkedHashMap<String, LinkedList<Peça_electronica_Dades>> mapaPecesElectronicaOperatives = new LinkedHashMap<String, LinkedList<Peça_electronica_Dades>>();
	
	// La clau serà peça_ID. El valor seran els objecte de tipus Peça_electronica_Dades i es diferenciaran pel peça_num_serie.
	private LinkedHashMap<String, LinkedList<Peça_electronica_Dades>> mapaPecesElectronicaTrencades = new LinkedHashMap<String, LinkedList<Peça_electronica_Dades>>();

	public ArrayList<Boolean> getNausAcabades() {
		return nausAcabades;
	}

	public LinkedHashMap<String, LinkedList<Peça_electronica_Dades>> getMapaPecesElectronicaOperatives() {
		return mapaPecesElectronicaOperatives;
	}

	public void setMapaPecesElectronicaOperatives(LinkedHashMap<String, LinkedList<Peça_electronica_Dades>> mapaPecesElectronica) {
		this.mapaPecesElectronicaOperatives = mapaPecesElectronica;
	}

	public LinkedHashMap<String, LinkedList<Peça_electronica_Dades>> getMapaPecesElectronicaTrencades() {
		return mapaPecesElectronicaTrencades;
	}

	public void setMapaPecesElectronicaTrencades(
			LinkedHashMap<String, LinkedList<Peça_electronica_Dades>> mapaPecesElectronicaTrencades) {
		this.mapaPecesElectronicaTrencades = mapaPecesElectronicaTrencades;
	}

	public Magatzem_peces_Dades() {
		/*this.nausAcabades = nausAcabades;
		this.mapaPecesElectronicaOperatives = mapaPecesElectronicaOperatives;
		this.mapaPecesElectronicaTrencades = mapaPecesElectronicaTrencades;*/
	}

	public void notificaNauAcabada() {
		for(int i =0; i<nausAcabades.size();i++){
			if(!nausAcabades.get(i)){
				nausAcabades.set(i,true);
				break;
			}
		}
	}

	public void processarPecesElectronica(Peça_electronica_Dades peçaRebuda){
		//LinkedHashMap<String, LinkedList<Peça_electronica_Dades>>
		try {
			semaphorede4.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LinkedList<Peça_electronica_Dades> ps=new LinkedList<>();
		ps.add(peçaRebuda);
		if(peçaRebuda.isPeça_trencada()) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(mapaPecesElectronicaTrencades.containsKey(peçaRebuda.getPeça_ID())){
				mapaPecesElectronicaTrencades.get(peçaRebuda.getPeça_ID()).add(peçaRebuda);
			}
			else {
				mapaPecesElectronicaTrencades.put(peçaRebuda.getPeça_ID(), ps);
			}

		}
		else if(!peçaRebuda.isPeça_trencada()) {
			if(mapaPecesElectronicaOperatives.containsKey(peçaRebuda.getPeça_ID())){
				mapaPecesElectronicaOperatives.get(peçaRebuda.getPeça_ID()).add(peçaRebuda);
			}
			else {
				mapaPecesElectronicaOperatives.put(peçaRebuda.getPeça_ID(), ps);
			}
		}
		semaphorede4.release();

	}

	

	public void veureContingutDelMagatzem() {
		try {
			semaphore1.acquire();
		} catch (InterruptedException e) {e.printStackTrace();}

		System.out.println("Contingut del magatzem: ");
		if(mapaPecesElectronicaOperatives.entrySet().size()==0) {System.out.println("\tmapa buit");}
		else {
			for(Entry<String, LinkedList<Peça_electronica_Dades>> a:mapaPecesElectronicaOperatives.entrySet()) {
				System.out.println("\t"+a.getKey()+": "+a.getValue().size());
			}
		}
		if(mapaPecesElectronicaTrencades.entrySet().size()==0) {System.out.println("\t"+"mapa buit");}
		else{
			for(Entry<String, LinkedList<Peça_electronica_Dades>> a:mapaPecesElectronicaTrencades.entrySet()) {
				System.out.println("\t"+a.getKey()+": "+a.getValue().size());
			}
		}
		semaphore1.release();
	}
}

