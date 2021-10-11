package com.smoothiemx.serviciooauth.app.services;

import com.smoothiemx.commonsusuarios.app.models.Usuario;

public interface IUsuarioService {

    Usuario findByUsername(String username);

    Usuario update(Usuario usuario, Long id);
}