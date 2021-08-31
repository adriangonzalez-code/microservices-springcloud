package com.smoothiemx.servicioproducto.app.controllers;

import com.smoothiemx.servicioproducto.app.models.Producto;
import com.smoothiemx.servicioproducto.app.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @GetMapping("/listar")
    public List<Producto> listar() {
        return this.productoService.findAll();
    }

    @GetMapping("/ver/{id}")
    public Producto detalle(@PathVariable Long id) {
        return this.productoService.findById(id);
    }
}