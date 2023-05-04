package com.meuret.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.meuret.demo.view.VueEntreprise;
import com.meuret.demo.view.VueUtilisateur;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
//@Table(name = "")  si le model et la bdd n'ont pas le meme nom
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private Integer id;

    //    @Column(name = "")
    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String name;

    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    @Column(length = 80, nullable = false)
    private String firstName;

    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String email;

    @JsonView(VueUtilisateur.class)
    private String nomImageProfil;

    private String password;

    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    @ManyToOne
    private Role role;

    @JsonView(VueUtilisateur.class)
    private int age;

    @ManyToOne
    @JsonView(VueUtilisateur.class)
    private Pays pays;

    @ManyToOne
    @JsonView(VueUtilisateur.class)
    private Entreprise entreprise;

    @ManyToMany
    @JsonView(VueUtilisateur.class)
    @JoinTable(
            name = "recherche_emploi_utilisateur",
            inverseJoinColumns = @JoinColumn(name = "emploi_id")
    )
    private Set<Emploi> emploirecherche = new HashSet<>();

    @CreationTimestamp
    @JsonView(VueUtilisateur.class)
    private LocalDate createdAt;

    @UpdateTimestamp
    @JsonView(VueUtilisateur.class)
    private LocalDate updatedAt;





}
