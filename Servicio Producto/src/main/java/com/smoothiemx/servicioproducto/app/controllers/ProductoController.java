package com.smoothiemx.servicioproducto.app.controllers;

import com.smoothiemx.commons.app.models.Producto;
import com.smoothiemx.servicioproducto.app.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
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
    public Producto detalle(@PathVariable Long id) throws InterruptedException {

        if (id.equals(10L)) {
            throw new IllegalStateException("Producto no encontrado");
        }

        if (id.equals(7L)) {
            TimeUnit.SECONDS.sleep(5L);
        }
        Producto producto = this.productoService.findById(id);
        producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));

        return producto;
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@RequestBody Producto producto) {
        return this.productoService.save(producto);
    }

    @PutMapping("/editar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {
        Producto productoDb = this.productoService.findById(id);

        productoDb.setNombre(producto.getNombre());
        productoDb.setPrecio(producto.getPrecio());

        return this.productoService.save(productoDb);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        this.productoService.deleteById(id);
    }
}