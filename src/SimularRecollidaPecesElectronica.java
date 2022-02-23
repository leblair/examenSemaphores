public class SimularRecollidaPecesElectronica {

	public static void main(String[] args) {
		System.out.println("MAIN: INICI");
		Magatzem_peces_Dades magatzem_peces_dades = new Magatzem_peces_Dades();
		Drassana_Dades drassana_dades = new Drassana_Dades(magatzem_peces_dades);
		Thread  threadDrassana = new Thread(drassana_dades);
		Drassana.inicialitzarNaus(drassana_dades, magatzem_peces_dades);
		Drassana.inicialitzarPecesNaus(drassana_dades);
		Drassana.inicialitzarPecesDrassana(drassana_dades);
		Drassana.inicialitzarNausAcabades(drassana_dades);
		threadDrassana.start();
		for(Nau_Dades nau : drassana_dades.getLlistaNausEnDrassana()) {
			Thread naveThread=new Thread(nau, nau.getNau_nom());
			naveThread.start();
		}
		try {
			threadDrassana.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		magatzem_peces_dades.veureContingutDelMagatzem();
		System.out.println("MAIN: FI");
	}

}
