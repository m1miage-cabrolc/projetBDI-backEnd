package miagiles.gromed.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinTable;

import java.io.Serializable;

@Embeddable
public class MedicamentGroupeGenerique implements Serializable {

    @Column(name="codeCIS")
    @JoinTable(name="med")
    private long medicament;

    @Column(name="id")
    @JoinTable(name="groupe_generique")
    private long groupeGenerique;

    public long getGroupeGenerique() {
        return groupeGenerique;
    }

    public void setGroupeGenerique(long groupeGenerique) {
        this.groupeGenerique = groupeGenerique;
    }

    public long getMedicament() {
        return medicament;
    }

    public void setMedicament(long medicament) {
        this.medicament = medicament;
    }
}
