package com.example.TrabajoFinalCoder.Servicios;

import com.example.TrabajoFinalCoder.Modelo.Cliente;
import com.example.TrabajoFinalCoder.Modelo.Factura;
import com.example.TrabajoFinalCoder.Modelo.Linea;
import com.example.TrabajoFinalCoder.Modelo.Mapper.MapFactura;
import com.example.TrabajoFinalCoder.Modelo.Mapper.MapLinea;
import com.example.TrabajoFinalCoder.Modelo.Producto;
import com.example.TrabajoFinalCoder.Modelo.dto.FacturaDTO;
import com.example.TrabajoFinalCoder.Modelo.dto.LineaDTO;
import com.example.TrabajoFinalCoder.Repositorios.RepositorioFactura;
import com.example.TrabajoFinalCoder.Repositorios.RepositorioLinea;
import com.example.TrabajoFinalCoder.Repositorios.RepositorioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class ServicioFactura {
    @Autowired
    RepositorioFactura repositorioFactura;

    @Autowired
    RepositorioProducto repositorioProducto;

    @Autowired
    RepositorioLinea repositorioLinea;


    // GET ALL
    public ResponseEntity<?> getAll() {
        Map<String,Object> mensaje = new HashMap<>();
        List<Factura> facturas = repositorioFactura.findAll();
        if(facturas.isEmpty()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje",String.format("La lista esta vacia "));
            return ResponseEntity.badRequest().body(mensaje);
        }
        List<FacturaDTO> facturasDTO = facturas.stream()
                .map(MapFactura::mapperFactura).collect(Collectors.toList());
        mensaje.put("success",Boolean.FALSE);
        mensaje.put("data",facturasDTO);
        return ResponseEntity.ok(mensaje);
    }

    // GET ID
    public ResponseEntity<?> getFacturaById(Integer facturaID) {
        Map<String, Object>mensaje = new HashMap<>();
        Optional<Factura>optionalFactura = repositorioFactura.findById(facturaID);
        if (!optionalFactura.isPresent()){
            mensaje.put("succes",Boolean.FALSE);
            mensaje.put("mensaje",String.format("la factura con la id %d no existe",facturaID));
            return  ResponseEntity.badRequest().body(mensaje);
        }
        Factura factura = optionalFactura.get();
        FacturaDTO FacturaDTO = MapFactura.mapperFactura(factura);
        mensaje.put("success",Boolean.TRUE);
        mensaje.put("data",FacturaDTO);
        return  ResponseEntity.ok(mensaje);
    }



    public ResponseEntity<?> guardarFactura(Factura factura) {
        Map<String,Object> mensaje = new HashMap<>();
        try {
            Cliente cliente = factura.getCliente();
            if (cliente != null) {
                factura.setCliente(cliente);
            }
            for (Linea linea : factura.getLineas()) {
                Producto producto = linea.getProducto();
                if (producto != null) {
                    Optional<Producto> optionalProducto = repositorioProducto.findById(producto.getId_producto());
                    if (optionalProducto.isPresent()) {
                        linea.setProducto(optionalProducto.get());
                    }
                }
                linea.setFactura(factura);
            }
            repositorioFactura.save(factura);

            mensaje.put("success",Boolean.TRUE);
            mensaje.put("mensaje",String.format("Factura correctamente agregada"));
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            mensaje.put("succes",Boolean.FALSE);
            mensaje.put("mensaje",String.format("No se pudo guardar la factura"));
            return ResponseEntity.badRequest().body(mensaje);
        }
    }







    // PUT - ADD Producto a la Factura.
    public ResponseEntity<?> addLineaToFactura(Integer factura_id, Linea linea) {
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Factura> optionalFactura = repositorioFactura.findById(factura_id);
        if (optionalFactura.isPresent()) {
            Factura factura = optionalFactura.get();
            factura.addLinea(linea);
            repositorioFactura.save(factura);
            mensaje.put("success",Boolean.TRUE);
            mensaje.put("data",String.format("Se actualizo correctamente"));
            return ResponseEntity.ok(mensaje);
        } else {
            mensaje.put("succes",Boolean.FALSE);
            mensaje.put("mensaje",String.format("la factura con la id %d no existe",factura_id));
            return  ResponseEntity.badRequest().body(mensaje);

        }
    }

    // GET PRECIO DE Factura
    public ResponseEntity<?> getTotalPrecioID(Integer facturaID) {
        Map<String,Object> mensaje = new HashMap<>();
        double resultado = 0;
        Optional<Factura> optionalFactura = repositorioFactura.findById(facturaID);
        if(optionalFactura.isPresent()){
            Factura factura = optionalFactura.get();
            for(Linea hijo: factura.getLineas()){
                resultado+=hijo.getPrecio();
            }
            mensaje.put("success",Boolean.TRUE);
            mensaje.put("El precio es:",resultado);
            return ResponseEntity.ok(resultado);

        }
        mensaje.put("succes",Boolean.FALSE);
        mensaje.put("mensaje",String.format("la factura con la id %d no existe",facturaID));
        return  ResponseEntity.badRequest().body(mensaje);

    }

    // DELETE
    public ResponseEntity<?> deleteFacturaByID(Integer facturaID) {
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Factura>optionalFactura = repositorioFactura.findById(facturaID);
        if (!optionalFactura.isPresent()){
            mensaje.put("succes",Boolean.FALSE);
            mensaje.put("mensaje",String.format("la factura con la id %d no existe",facturaID));
            return  ResponseEntity.badRequest().body(mensaje);
        }
        repositorioFactura.deleteById(facturaID);
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok(mensaje);

    }

    // GET TODOS LOS PRODUCTOS DE UNA FACTURA

    public ResponseEntity<?> getProductosById(Integer facturaID) {
        Map<String,Object> mensaje = new HashMap<>();
        Optional<Factura> optionalFactura = repositorioFactura.findById(facturaID);
        if(optionalFactura.isPresent()){
            Factura factura = optionalFactura.get();
            Set<Linea> lineas = factura.getLineas();
            List<LineaDTO> lineasDTO = lineas.stream()
                    .map(MapLinea::mapperLinea).collect(Collectors.toList());
            mensaje.put("success",Boolean.TRUE);
            mensaje.put("data",lineasDTO);
            return ResponseEntity.ok(mensaje);
        }
        mensaje.put("succes",Boolean.FALSE);
        mensaje.put("mensaje",String.format("la factura con la id %d no existe",facturaID));
        return  ResponseEntity.badRequest().body(mensaje);
    }


    // DELETE ALL
    public ResponseEntity<?> deleteAllFacturas() {
        Map<String,Object> mensaje = new HashMap<>();
        repositorioFactura.deleteAll();
        mensaje.put("success",Boolean.TRUE);
        return ResponseEntity.ok(mensaje);
    }


}
