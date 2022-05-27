package com.example.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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

        ModelAndView maw = new ModelAndView("listar");

        maw.addObject("empleados", empleadoService.findAll());

        return maw;
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable(name = "id") Long id, Model model){

        model.addAttribute("empleado", empleadoService.findByIdEmpleado(id));
        
        return "detalle";
    }


    @GetMapping("/altaModificacion/{id}")
    public String altaModificacion(Model model,
                                @PathVariable(name = "id", required = false) Long id){
        
        if(id==0){
            //form vacio vinculado a un new empleado vacío para "completar"
            model.addAttribute("empleado", new Empleado());
            // paso new Empleado y lo nombro como "empleado"
        }else{
            model.addAttribute("empleado", empleadoService.findByIdEmpleado(id));
        }
        // TAMBIEN MANDAR LISTA DE DEPARTAMENTOS
        model.addAttribute("departamentos", departamentoService.findAll());

        return "altaModificacion";
    }



    @PostMapping("/guardar")
    public String guardar(@ModelAttribute(name = "empleado") Empleado empleado,
                            @RequestParam(name = "file") MultipartFile imagen){
                        // recibo por POST un atributo y lo convierte a Empleado
        
     
        if (!imagen.isEmpty()){
            //Ruta Relativa a la carpeta static donde por defecto se almacenan los archivos
            Path imageFolder = Paths.get("src/main/resources/static/imagenes");

            //construir la ruta absoluta de raiz
            String rutaAbsoluta = imageFolder.toFile().getAbsolutePath();

            // convertir en un array de bytes
            // sirve para cualquier tipo de archivos 
            try {

                byte[] imagenBytes = imagen.getBytes(); // (imagen x param)

                // guardar este array en la ruta abs
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());

                Files.write(rutaCompleta, imagenBytes);

                // asignar al campo foto
                empleado.setFoto(imagen.getOriginalFilename());

                //persistir el obj Persona en la bd
                empleadoService.guardar(empleado);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return "redirect:/listar";

    }

}
