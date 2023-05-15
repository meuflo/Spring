package com.meuret.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate dateDeRetour;
    private LocalDate dateDeCreation;
    @OneToMany(mappedBy = "contrat")
    List<LigneDeContrat> listeLigneDeContrat;


}
