package com.meuret.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.meuret.demo.dao.EntrepriseDAO;
import com.meuret.demo.model.Entreprise;
import com.meuret.demo.view.VueEntreprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EntrepriseController {

    @Autowired
    private EntrepriseDAO entrepriseDAO;

    @GetMapping("/admin/entreprises")
    @JsonView(VueEntreprise.class)
    public List<Entreprise> getListeEntreprise() {
        return entrepriseDAO.findAll();
//        List<Entreprise> entreprises = entrepriseDAO.findAll();
//        return entreprises;
    }

    @GetMapping("/entreprise/{id}")
    @JsonView(VueEntreprise.class)
    public Entreprise getEntreprise(@PathVariable int id) {

        return entrepriseDAO
                .findById(id)
                .orElse(null);
    }

    @DeleteMapping("/entreprise/{id}")
    public boolean supprimeEntreprise(@PathVariable int id){
        entrepriseDAO.deleteById(id);
        return true;
    }

    @PostMapping("/admin/entreprise")
    public Entreprise enregistrerEntreprise(@RequestBody Entreprise entrepriseAenregistrer) {
        entrepriseDAO.save(entrepriseAenregistrer);
        return entrepriseAenregistrer;
    }
}


//    public EntrepriseController() {
//        EntrepriseDAO dao = new EntrepriseDAO(){
//            @Override
//            public int hashCode() {
//                return super.hashCode();
//            }
//        };
//    }

