package com.example.services;

import java.util.List;

import com.example.dao.DepartamentoDao;
import com.example.entities.Departamento;

import org.springframework.beans.factory.annotation.Autowired;

public class DepartamentoServiceImpl implements DepartamentoService{

    @Autowired
    private DepartamentoDao departamentoDao;

    @Override
    public List<Departamento> findAll() {
        return this.departamentoDao.findAll();
    }
    
}
