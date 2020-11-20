package fr.univcotedazur.polytech.si4.fsm.project;

import java.util.ArrayList;
import java.util.List;

public class CarteBancaire {

    private List<Double> prixMoyen;
    private String id;
    private int nbCommande;
    private double reduction;

    public CarteBancaire(String idCarte) {
        this.id = idCarte;
        this.prixMoyen = new ArrayList();
        this.nbCommande = 0;
        this.reduction = 0;
    }

    /**
     * @return true si on doit appliquer une réduction
     */
    public void addCommande(double prix) {
        if (nbCommande < 10 ) {
            this.reduction = 0;
            this.nbCommande += 1;
            this.prixMoyen.add(prix);
        }
        else {
            calculReduction();
            this.nbCommande = 0;
            this.prixMoyen.clear();
        }
    }

    private void calculReduction() {
        for (int i = 0; i < this.prixMoyen.size(); i++) {
            this.reduction += prixMoyen.get(i);
        }
        this.reduction /= 10;
    }

    public double getReduction() {
        return this.reduction;
    }

    public String getId() {
        return this.id;
    }
}
