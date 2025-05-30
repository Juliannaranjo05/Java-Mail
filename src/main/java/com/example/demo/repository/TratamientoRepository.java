package com.example.demo.repository;

import com.example.demo.model.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    List<Tratamiento> findByActivoTrue();
}
