package com.smoothiemx.servicioproducto.app.repositories;

import com.smoothiemx.servicioproducto.app.models.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductoRepository extends CrudRepository<Producto, Long> {
}