package com.meuret.demo.dao;

import com.meuret.demo.model.Pays;
import com.meuret.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaysDao extends JpaRepository<Pays, Integer> {

}
