package com.example.TrabajoFinalCoder.Controladores;


import com.example.TrabajoFinalCoder.Modelo.Factura;
import com.example.TrabajoFinalCoder.Modelo.Linea;
import com.example.TrabajoFinalCoder.Modelo.Producto;
import com.example.TrabajoFinalCoder.Repositorios.RepositorioProducto;
import com.example.TrabajoFinalCoder.Servicios.ServicioFactura;
import com.example.TrabajoFinalCoder.Servicios.ServicioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/facturas")
public class ControladorFacturaDTO {


    @Autowired
    ServicioFactura servicioFactura;



    // GET ALL
    @GetMapping()
    public ResponseEntity<?> getFacturas(){
        return servicioFactura.getAll();
    }

    // GET POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getFactura(@PathVariable Integer id) {
        return servicioFactura.getFacturaById(id);
    }

    // POST -
    @PostMapping("/alta")
    public ResponseEntity<?> postFactura(@RequestBody Factura Factura) {
        return servicioFactura.guardarFactura(Factura);
    }


    // PUT - ADD Linea a la Factura
    @PutMapping("/add/{id}")
    public ResponseEntity<?> addLinea(@PathVariable Integer id, @RequestBody Linea linea) {
        return servicioFactura.addLineaToFactura(id, linea);
    }

    // GET PRECIO FACTURA
        @GetMapping("/precio/{id}")
        public ResponseEntity<?> getTotalPriceById(@PathVariable Integer id) {
            return servicioFactura.getTotalPrecioID(id);
        }

    // DELETE
    @DeleteMapping("/baja/{id}")
    public ResponseEntity<?> deleteFactura(@PathVariable Integer id) {
        return servicioFactura.deleteFacturaByID(id);
    }

    // GET TODOS LOS LINEAS DE UNA FACTURA
    @GetMapping("/productos/{id}")
    public ResponseEntity<?> getProductosById(@PathVariable Integer id) {
        return servicioFactura.getProductosById(id);
    }

    //DELETE ALL
    @DeleteMapping("/borrarTodos")
    public ResponseEntity<?> deleteAllFacturas() {
        return servicioFactura.deleteAllFacturas();
    }



}
