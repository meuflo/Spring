package com.meuret.demo.dao;

import com.meuret.demo.model.Emploi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploiDao extends JpaRepository<Emploi, Integer> {

}
