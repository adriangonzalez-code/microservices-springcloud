package com.smoothiemx.servicioproducto.app.repositories;

import com.smoothiemx.commons.app.models.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductoRepository extends CrudRepository<Producto, Long> {

    List<Producto> findAll();
}