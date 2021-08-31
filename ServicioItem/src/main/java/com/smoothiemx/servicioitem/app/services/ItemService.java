package com.smoothiemx.servicioitem.app.services;

import com.smoothiemx.servicioitem.app.models.Item;

import java.util.List;

public interface ItemService {

    List<Item> findAll();

    Item findById(Long id, Integer cantidad);
}