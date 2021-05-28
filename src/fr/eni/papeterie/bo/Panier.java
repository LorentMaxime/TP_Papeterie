package fr.eni.papeterie.bo;

import java.util.ArrayList;
import java.util.List;

public class Panier {
    // Attributs d'instance
    private float montant;
    private Ligne ligne;
    private List<Ligne>lignePanier = new ArrayList<>();


    public Panier() {
    }

    public float getMontant() {
        return montant;
    }

    public Ligne getLigne(int index) {
        return lignePanier.get(index);
    }

    public List<Ligne> getLignePanier() {
        return lignePanier;
    }

    public void addLigne(Article article, int qte){
        lignePanier.add(new Ligne(article,qte));
    }

    public void updateLigne(int index, int newQte){
        this.getLigne(index).setQte(newQte);
    }

    public void removeLigne(int index){
        lignePanier.remove(index);
    }

    @Override
    public String toString() {
        return "Panier{" +
                "montant=" + montant +
                ", ligne=" + ligne +
                ", lignePanier=" + lignePanier +
                '}';
    }
}
