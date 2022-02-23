import java.text.Collator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;


public class Nau_Dades implements Comparable<Nau_Dades>,Runnable{
	private String nau_ID;
	private String nau_nom;
	private ArrayList<Peça_electronica_Dades> llistaPecesElectronica = new ArrayList<Peça_electronica_Dades>();
	private int num_tripulants;
	private LocalDateTime data_fabricacio;

	private Magatzem_peces_Dades magatzem_peces_dades;

	public Nau_Dades(Magatzem_peces_Dades magatzem_peces_dades,String nau_ID, String nau_nom, int num_tripulants, LocalDateTime data_fabricacio) {
		this.nau_ID = nau_ID;
		this.nau_nom = nau_nom;
		this.num_tripulants = num_tripulants;
		this.data_fabricacio = data_fabricacio;
		this.magatzem_peces_dades = magatzem_peces_dades;
	}


	public String getNau_ID() {
		return nau_ID;
	}

	public void setNau_ID(String nau_ID) {
		this.nau_ID = nau_ID;
	}

	public String getNau_nom() {
		return nau_nom;
	}

	public void setNau_nom(String nau_nom) {
		this.nau_nom = nau_nom;
	}

	public ArrayList<Peça_electronica_Dades> getLlistaPecesElectronica() {
		return llistaPecesElectronica;
	}

	public void setLlistaPecesElectronica(ArrayList<Peça_electronica_Dades> llistaPecesElectronica) {
		this.llistaPecesElectronica = llistaPecesElectronica;
	}

	public int getNum_tripulants() {
		return num_tripulants;
	}


	public void setNum_tripulants(int num_tripulants) {
		this.num_tripulants = num_tripulants;
	}


	public LocalDateTime getData_fabricacio() {
		return data_fabricacio;
	}


	public void setData_fabricacio(LocalDateTime data_fabricacio) {
		this.data_fabricacio = data_fabricacio;
	}


	@Override
	public int compareTo(Nau_Dades o) {
		int result = 0;
		
		//Anem a ordenar alfabèticament independenment de si hi ha majúscules o accents.
		Collator tertiaryCollator = Collator.getInstance(new Locale("es"));
        tertiaryCollator.setStrength(Collator.TERTIARY);
        result = tertiaryCollator.compare(this.getNau_nom(), o.getNau_nom());
        
        return result;
	}

	@Override
	public String toString() {
		StringBuilder dades = new StringBuilder("");
		
		dades.append("ID: " + nau_ID);
		dades.append(System.getProperty("line.separator"));
		dades.append("NOM: " + nau_nom);
		dades.append(System.getProperty("line.separator"));
		
		dades.append("PECES D'ELECTRÒNICA: ");
		dades.append(System.getProperty("line.separator"));
		for (Peça_electronica_Dades peçaElecTmp : this.getLlistaPecesElectronica()) {
			dades.append("    " + peçaElecTmp);
			dades.append(System.getProperty("line.separator"));
		}
		
		dades.append("Nº DE TRIPULANTS: " + num_tripulants);
		dades.append(System.getProperty("line.separator"));
		dades.append("DATA DE CONSTRUCCIÓ: " + data_fabricacio);
		dades.append(System.getProperty("line.separator"));
		
		return dades.toString();
	}

	@Override
	public void run() {

		try {
			Thread.sleep(1000); //1 segon
		} catch (InterruptedException e) {e.printStackTrace();}

		System.out.println(Thread.currentThread().getName()+": INICI (n peces electronica = "+llistaPecesElectronica.size()+")");

		Iterator<Peça_electronica_Dades> it=llistaPecesElectronica.iterator();
		Peça_electronica_Dades t;
		while(it.hasNext()) {
			t=it.next();

			magatzem_peces_dades.processarPecesElectronica(t);
			it.remove();
		}
		magatzem_peces_dades.notificaNauAcabada();
		System.out.println(Thread.currentThread().getName()+" FI (n. peces electroniques = "+llistaPecesElectronica.size()+")");

	}
}
