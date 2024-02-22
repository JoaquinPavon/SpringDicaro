package com.example.TrabajoFinalCoder.Controladores;


import com.example.TrabajoFinalCoder.Modelo.Linea;
import com.example.TrabajoFinalCoder.Modelo.Mapper.MapLinea;
import com.example.TrabajoFinalCoder.Modelo.dto.LineaDTO;
import com.example.TrabajoFinalCoder.Servicios.ServicioLinea;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lineas")
public class ControladorLineaDTO {



    @Autowired
    private ServicioLinea servicioLinea;

    // TRAER TODAS LAS LINEAS
    @GetMapping
    public ResponseEntity<?> getTodos(){
        return servicioLinea.getAll();}

    // TRAER UNA LINEA
    @GetMapping("/{id}")
    public  ResponseEntity<?> getLineaId(@PathVariable Integer id){
        return servicioLinea.getLineaid(id);
    }


    // ELIMINAR UNA LINEA
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteByID(@PathVariable Integer id){
        return servicioLinea.deleteById(id);
}


    // MODIFICAR UNA LINEA
    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificar(@PathVariable Integer id, @RequestBody Linea linea){
    return servicioLinea.modificarLinea(id,linea);}

    // AGREGAR UNA LINEA
    @PostMapping("/alta")
    public ResponseEntity<?> postLinea(@RequestBody Linea linea){
    return servicioLinea.agregarLinea(linea);
}

    // PRECIO DE UNA LINEA
    @GetMapping("/precio/{id}")
    public ResponseEntity<?>getPrecio(@PathVariable Integer id){
        return servicioLinea.getPrecioLinea(id);

    }
}

