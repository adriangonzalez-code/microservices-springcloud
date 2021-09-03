package com.smoothiemx.servicioproducto.app.controllers;

import com.smoothiemx.servicioproducto.app.models.Producto;
import com.smoothiemx.servicioproducto.app.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    /*@Value("${server.port}")
    private Integer port;*/

    @Autowired
    private Environment env;

    @GetMapping("/listar")
    public List<Producto> listar() {
        return this.productoService.findAll().stream().peek(producto -> producto.setPort(Integer.parseInt(env.getProperty("local.server.port")))).collect(Collectors.toList());
    }

    @GetMapping("/ver/{id}")
    public Producto detalle(@PathVariable Long id) {
        Producto producto = this.productoService.findById(id);
        producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));

        /*try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        return producto;
    }
}