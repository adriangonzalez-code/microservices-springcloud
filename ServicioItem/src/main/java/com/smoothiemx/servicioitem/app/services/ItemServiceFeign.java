package com.smoothiemx.servicioitem.app.services;

import com.smoothiemx.servicioitem.app.clientes.ProductoClienteRest;
import com.smoothiemx.servicioitem.app.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceFeign implements ItemService {

    @Autowired
    private ProductoClienteRest clienteFeign;

    @Override
    public List<Item> findAll() {
        return this.clienteFeign.listar().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        return new Item(this.clienteFeign.detalle(id), cantidad);
    }
}
