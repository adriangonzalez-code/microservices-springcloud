package com.smoothiemx.servicioproducto.app.services;

import com.smoothiemx.servicioproducto.app.models.Producto;

import java.util.List;

public interface IProductoService {

    List<Producto> findAll();

    Producto findById(Long id);

    Producto save(Producto producto);

    void deleteById(Long id);
}