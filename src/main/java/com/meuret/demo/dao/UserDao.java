package com.meuret.demo.dao;

import com.meuret.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findByFirstName(String firstName);

    Optional<User> findByEmail(String email);

    @Query("FROM User U JOIN U.pays P WHERE P.name = ?1")
    List<User> trouverutilisateurSelonPays(@Param("pays") String pays);


}
