package com.smoothiemx.servicioproducto.app.services;

import com.smoothiemx.servicioproducto.app.models.Producto;
import com.smoothiemx.servicioproducto.app.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return (List<Producto>) this.productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return this.productoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Producto save(Producto producto) {
        return this.productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.productoRepository.deleteById(id);
    }
}