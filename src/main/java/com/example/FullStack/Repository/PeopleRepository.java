package com.example.FullStack.Repository;

import com.example.FullStack.Entity.PeopleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeopleRepository extends JpaRepository<PeopleEntity, Long> {

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email,Long id);
    Optional<PeopleEntity> findByEmail(String email);
}
