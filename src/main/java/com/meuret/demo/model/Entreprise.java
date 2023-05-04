package com.meuret.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.meuret.demo.view.VueEntreprise;
import com.meuret.demo.view.VueUtilisateur;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private Integer id;

    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String nom;

    @OneToMany(mappedBy = "entreprise")
    @JsonView(VueEntreprise.class)
    private Set<User> listeEmploye = new HashSet<User>();
}
