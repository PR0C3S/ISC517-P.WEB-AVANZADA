package com.pucmm.practica2.repositorios;

import com.pucmm.practica2.entities.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MockRepository extends JpaRepository<Mock, Long> {

    @Query("select u from Mock u where u.id = ?1")
    Mock consultaMock(Long id);
}