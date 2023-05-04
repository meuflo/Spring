package com.meuret.demo.dao;

import com.meuret.demo.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrepriseDAO extends JpaRepository<Entreprise, Integer> {

}
