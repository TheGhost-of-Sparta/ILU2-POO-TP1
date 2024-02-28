package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	private static class Marche{
		private Etal[] etals;
		
		public Marche(int nbEtal) {
			etals = new Etal[nbEtal];
			for (int k =0; k<nbEtal; k++) {
				etals[k]=new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			for (int k =0; k<etals.length; k++) {
				if (!etals[k].isEtalOccupe()) {
					return k;
				}
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
			int nbEtalProduit = 0;
			for (int k =0; k<etals.length; k++) {
				if (etals[k].isEtalOccupe() && etals[k].contientProduit(produit)) {
					nbEtalProduit++;
				}
			}
			Etal[] etal=new Etal[nbEtalProduit];
			int indice=0;
			for (int k =0; k<etals.length; k++) {
				if (etals[k].isEtalOccupe() && etals[k].contientProduit(produit)) {
					etal[indice]=etals[k];
					indice++;
				}
			}
			return etal;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for (int k =0; k<etals.length; k++) {
				if (etals[k].getVendeur()==gaulois) {
					return etals[k];
				}
			}
			return null;
		}
		
		public String afficherMarche() {
			int nbEtalVide=0;
			for (int k =0; k<etals.length; k++) {
				if (etals[k].isEtalOccupe()) {
					etals[k].afficherEtal();
				} else {
					nbEtalVide++;
				}
			}
			return "IL reste " +nbEtalVide +" étals non utilisés dans le marché.\n"
		}
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] etal = marche.trouverEtals(produit);
		StringBuilder chaine = new StringBuilder();
		if (etal.length==0) {
			chaine.append("Il n'y a encore aucun etal qui vend des "+produit+" .\n");
		} else {
			for (int i = 0; i < etal.length; i++) {
				chaine.append("Le vendeur" + etal[i].getVendeur().getNom() + "vend des "+produit+" .\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois gaulois, String produit, int amount){
		StringBuilder chaine = new StringBuilder();
		chaine.append(gaulois.getNom() + "cherche un etal pour vendre des "+produit+" .\n");
		if (marche.trouverEtalLibre()==-1) {
			chaine.append("Il n'y a aucun etal vide.\n");
		} else {
			int indice = marche.trouverEtalLibre();
			marche.utiliserEtal(marche.trouverEtalLibre(), gaulois, produit, amount);
			chaine.append("Le vendeur" + gaulois.getNom() + "vend des "+produit+" a l'etal numéro "+indice+" .\n");
		}
		return chaine.toString();
	}
	
	
	public void afficherMarche() {
		marche.afficherMarche();
	}
	
	public Village(String nom, int nbVillageoisMaximum, int nbetal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbetal);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}