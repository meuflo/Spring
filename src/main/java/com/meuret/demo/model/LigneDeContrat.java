package com.meuret.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@IdClass(LigneDeContrat.IdLigneDeContrat.class)
public class LigneDeContrat {

    @Id
    @ManyToOne
    @JoinColumn(name = "contrat_id")
    private Contrat contrat;

    @Id
    @ManyToOne
    @JoinColumn(name = "materiel_id")
    private Materiel materiel;

    private LocalDate dateDeRetourAnticipe;

    @Embeddable
    @Getter
    @Setter
    public static class IdLigneDeContrat implements Serializable {
        private int contrat;
        private int materiel;
    }
}

