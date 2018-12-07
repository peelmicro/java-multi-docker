package com.peelmicro.server.repositories;

import com.peelmicro.server.models.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValuesRepository extends JpaRepository<Value, Integer> {
}
