package com.smoothiemx.servicioitem.app.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.smoothiemx.servicioitem.app.models.Item;
import com.smoothiemx.servicioitem.app.models.Producto;
import com.smoothiemx.servicioitem.app.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    @Qualifier("itemServiceFeign")
    private ItemService itemService;

    @GetMapping("/listar")
    public List<Item> listar() {
        return this.itemService.findAll();
    }

    @HystrixCommand(fallbackMethod = "metodoAlternativo")
    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
        return this.itemService.findById(id, cantidad);
    }

    private Item metodoAlternativo(Long id, Integer cantidad) {
        Item item = new Item();
        Producto producto = new Producto();

        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("Cámara Sony");
        producto.setPrecio(500.00);
        item.setProducto(producto);

        return item;
    }
}