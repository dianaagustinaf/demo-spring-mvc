package com.example.controllers;

import com.example.entities.Empleado;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class MainController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private DepartamentoService departamentoService;
    
    /*
    @GetMapping("/listar")
    public String listarEmpleado(Model model) {

        model.addAttribute("empleados", empleadoService.findAll());
        return "listar";
    }
    */
    
    @GetMapping("/listar")
    public ModelAndView listarEmpleado(){

        String saludo = "Holis :)";

        ModelAndView maw = new ModelAndView("listar");

        maw.addObject("empleados", empleadoService.findAll());
        maw.addObject("saludo", saludo);

        return maw;
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable(name = "id") Long id, Model model){

        model.addAttribute("empleado", empleadoService.findByIdEmpleado(id));

        return "detalle";
    }

    @GetMapping("/alta")
    public String mostrarFormulario(Model model){
       
        //form vacio vinculado a un new empleado vacío para "completar"
        model.addAttribute("empleado", new Empleado()); 
        // paso new Empleado y lo nombro como "empleado"

        // TAMBIEN MANDAR LISTA DE DEPARTAMENTOS
        
        return "altaEmpleado";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute(name = "empleado") Empleado empleado){
            // recibo por POST un atributo y lo convierte a Empleado
        empleadoService.guardar(empleado);

        return "redirect:/listar";

    }

}
