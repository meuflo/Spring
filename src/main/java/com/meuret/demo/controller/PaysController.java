package com.meuret.demo.controller;

import com.meuret.demo.dao.PaysDao;
import com.meuret.demo.model.Pays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class PaysController {

    @Autowired
    private PaysDao paysDao;

    @GetMapping("/admin/payss")
    public List<Pays> getPays(){

        List<Pays> listPayss = paysDao.findAll();
        return listPayss;
    }

//    @GetMapping("/pays-franck")
//    public Pays getPaysFranck() {
//        return paysDao.findByFirstName("franck");
//    }

//    @GetMapping("/pays/{id}")
//    public Pays getPaysFranck(@PathVariable int id) {
//
////        return paysDao.findByFirstName("Franck");
//
//        return paysDao.findById(id).orElse(null);
//    }

    @GetMapping("/admin/pays/{id}")
    public ResponseEntity<Pays> getPaysFranck(@PathVariable int id) {
        Optional<Pays> optional = paysDao.findById(id);

        if(optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


//  Ajout de pays depuis intellij

//    @PostMapping("/pays")
//    public boolean addPays() {
//        Pays newPays = new Pays();
//        newPays.setId(3);
//        newPays.setName("TUTU");
//        newPays.setFirstName("Tata");
//        paysDao.save(newPays);
//        return true;
//    }

    //Ajouter pays depuis postman
//    @PostMapping("/pays")
//    public boolean addPays(@RequestBody Pays newPays) {
//        paysDao.save(newPays);
//        return true;
//    }



    @PostMapping("/admin/pays")
    public ResponseEntity<Pays> newPays(@RequestBody Pays newPays) {

        //si l'utilisateur fournit possède un id
        if(newPays.getId() != null) {

            Optional<Pays> optional = paysDao.findById(newPays.getId());

            //si c'est un update
            if(optional.isPresent()) {
                paysDao.save(newPays);
                return new ResponseEntity<>(newPays,HttpStatus.OK);
            }

            //si il y a eu une tentative d'insertion d'un utilisateur avec un id qui n'existait pas
            return new ResponseEntity<>(newPays,HttpStatus.BAD_REQUEST);

        }

        paysDao.save(newPays);
        return new ResponseEntity<>(newPays,HttpStatus.CREATED);

    }




    //  Supprimer pays
    @DeleteMapping("/admin/pays/{id}")
    public ResponseEntity<Pays> deletePays(@PathVariable int id) {

        Optional<Pays> deletePays = paysDao.findById(id);

        if(deletePays.isPresent()) {
            paysDao.deleteById(id);
            return new ResponseEntity<>(deletePays.get(), HttpStatus.OK);
        }

       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}