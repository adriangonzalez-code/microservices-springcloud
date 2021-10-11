package com.smoothiemx.servicioitem.app.services;

import com.smoothiemx.commons.app.models.Producto;
import com.smoothiemx.servicioitem.app.models.Item;

import java.util.List;

public interface ItemService {

    List<Item> findAll();

    Item findById(Long id, Integer cantidad);

    Producto save(Producto producto);

    Producto update(Producto producto, Long id);

    void delete(Long id);
}