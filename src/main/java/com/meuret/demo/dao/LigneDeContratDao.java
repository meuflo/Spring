package com.meuret.demo.dao;

import com.meuret.demo.model.LigneDeContrat;
import com.meuret.demo.model.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigneDeContratDao extends JpaRepository<LigneDeContrat, LigneDeContrat.IdLigneDeContrat> {

}
