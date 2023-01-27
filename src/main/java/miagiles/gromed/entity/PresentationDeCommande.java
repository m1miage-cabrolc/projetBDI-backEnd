package miagiles.gromed.entity;

import jakarta.persistence.*;
import miagiles.gromed.entity.key.PresentationCommande;

@Entity(name="pres_de_commande")
@IdClass(PresentationCommande.class)
public class PresentationDeCommande {



    @Column(name="quantite")
    private int quantite;

    @Id
    @JoinTable(name="commande")
    @ManyToOne
    private Commande commande;

    @Id
    @JoinTable(name="pres")
    @ManyToOne
    private Presentation presentation;


    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }



}