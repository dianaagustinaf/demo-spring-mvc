package com.example.services;

import java.util.List;

import com.example.dao.EmpleadoDao;
import com.example.entities.Empleado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    //1ra variante de inyeccion de dependencia
    @Autowired
    private EmpleadoDao empleadoDao;

    //2da variante por constructor
    /*
    private EmpleadoDao empleadoDao;

    public EmpleadoServiceImpl(EmpleadoDao empleadoDao){
        this.empleadoDao = ...
    }
    */

    @Override
    public List<Empleado> findAll() {

        return this.empleadoDao.findAll();
    }

    @Override
    public Empleado findByIdEmpleado(Long id) {

        // findById returns an OPTIONAL  == .get() convert to Empleado
        return this.empleadoDao.findById(id).get();
    }

    @Override
    public void guardar(Empleado empleado) {

        empleadoDao.save(empleado);
        //el save se da cuenta si tiene ID = MODIFICA
                         // si no tiene ID =  CREA
    }
    
}
