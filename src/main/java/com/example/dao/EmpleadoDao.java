package com.example.dao;

import java.util.List;

import com.example.entities.Empleado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoDao extends JpaRepository<Empleado,Long> {
    
    @Query(value = "select * from Empleado fetch :salario", nativeQuery = true)
    public List<Empleado> findBySalario(Double salario);

}
