package miagiles.gromed.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Entity(name="prescription")
public class Prescription {
    @Id
    @Column(name="codeDP")
    private int codeDP;

    @Column(name="libelle")
    private String libelle;

    @ManyToMany
    private List<Medicament> medicaments;

    public int getCodeDP() {
        return codeDP;
    }

    public void setCodeDP(int codeDP) {
        this.codeDP = codeDP;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
