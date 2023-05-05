package com.meuret.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.meuret.demo.dao.UserDao;
import com.meuret.demo.model.Role;
import com.meuret.demo.model.User;
import com.meuret.demo.security.JwtUtils;
import com.meuret.demo.services.FichierService;
import com.meuret.demo.view.VueUtilisateur;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    FichierService fichierService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/users")
    @JsonView(VueUtilisateur.class)
    public List<User> getUser() {


        List<User> listUsers = userDao.findAll();
        return listUsers;
    }

    @GetMapping("/admin/user-franck")
    public User getUserFranck() {
        return userDao.findByFirstName("franck");
    }

//    @GetMapping("/user/{id}")
//    public User getUserFranck(@PathVariable int id) {
//
////        return userDao.findByFirstName("Franck");
//
//        return userDao.findById(id).orElse(null);
//    }

//    @GetMapping("/user/{id}")
//    @JsonView(VueUtilisateur.class)
//    public ResponseEntity<User> getUserFranck(@PathVariable int id) {
//
//        //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//
//        Optional<User> optional = userDao.findById(id);
//
//        if (optional.isPresent()) {
//            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @GetMapping("user-par-pays/{nomPays}")
    @JsonView(VueUtilisateur.class)
    public List<User> getUser(@PathVariable String monpays) {
        return userDao.trouverutilisateurSelonPays(monpays);
    }

    @GetMapping("/profil")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<User> getProfil(@RequestHeader("Authorization") String bearer) {
        String jwt = bearer.substring(7);
        Claims donnees = jwtUtils.getData(jwt);
        Optional<User> user = userDao.findByEmail(donnees.getSubject());

        if (user.isPresent()) {
            return new ResponseEntity(user.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/image-profil/{idUtilisateur}")
    public ResponseEntity<byte[]> getImageProfil(@PathVariable int idUtilisateur) {
        Optional<User> optional = userDao.findById(idUtilisateur);

        if (optional.isPresent()) {

            String nomImage = optional.get().getNomImageProfil();

            try {
                byte[] image = fichierService.getImageByName(nomImage);

                HttpHeaders enTete = new HttpHeaders();
                String mimeType = Files.probeContentType(new File(nomImage).toPath());
                enTete.setContentType(MediaType.valueOf(mimeType));

                return new ResponseEntity<>(image, enTete, HttpStatus.OK);

            } catch (FileNotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (IOException e) {
                System.out.println("Le test du mimetype a echoué");
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/admin/user")
    public ResponseEntity<User> newUser(
            @RequestPart("utilisateur") User newUser,
            @Nullable @RequestParam("fichier") MultipartFile fichier
    ) {

        //si l'utilisateur fournit possède un id
        if (newUser.getId() != null) {

            Optional<User> optional = userDao.findById(newUser.getId());

            //si c'est un update
            if (optional.isPresent()) {

                User userToUpdate = optional.get();
                userToUpdate.setName(newUser.getName());
                userToUpdate.setFirstName(newUser.getFirstName());
                userToUpdate.setEmail(newUser.getEmail());
                userToUpdate.setPays(newUser.getPays());


                userDao.save(userToUpdate);

                if (fichier != null) {
                    try {
                        fichierService.transfertVersDossierStockage(fichier, "image_profil");
                    } catch (IOException e) {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }

                return new ResponseEntity<>(newUser, HttpStatus.OK);
            }

            //si il y a eu une tentative d'insertion d'un utilisateur avec un id qui n'existait pas
            return new ResponseEntity<>(newUser, HttpStatus.BAD_REQUEST);

        }

        Role role = new Role();
        role.setId(1);
        newUser.setRole(role);

        String passwordHache = passwordEncoder.encode("root");
        newUser.setPassword(passwordHache);


        if (fichier != null) {
            try {

                String nomImage = UUID.randomUUID() + "_" + fichier.getOriginalFilename();
                newUser.setNomImageProfil(nomImage);

                fichierService.transfertVersDossierStockage(fichier, nomImage);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        userDao.save(newUser);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);


    }


    //  Supprimer user
    @DeleteMapping("/admin/user/{id}")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<User> deleteUser(@PathVariable int id) {

        Optional<User> deleteUser = userDao.findById(id);

        if (deleteUser.isPresent()) {
            userDao.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}