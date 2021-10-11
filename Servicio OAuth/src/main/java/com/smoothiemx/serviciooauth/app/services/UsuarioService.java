package com.smoothiemx.serviciooauth.app.services;

import brave.Tracer;
import com.smoothiemx.commonsusuarios.app.models.Usuario;
import com.smoothiemx.serviciooauth.app.clients.UsuarioFeignCliente;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService, IUsuarioService {

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioFeignCliente client;

    @Autowired
    private Tracer tracer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = null;
        try {
            usuario = this.client.findByUsername(username);
            LOG.info("Username: " + usuario.getUsername());
            List<GrantedAuthority> authorities = usuario.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).peek(authority -> LOG.info("Role: " + authority.getAuthority())).collect(Collectors.toList());

            return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);

        } catch (FeignException e) {
            String error = "Error en el login, no existe el usuario '" + username + "' en el sistema";
            LOG.error(error);
            this.tracer.currentSpan().tag("error.mensaje", error + " : " + e.getMessage());
            throw new UsernameNotFoundException(error);
        }
    }

    @Override
    public Usuario findByUsername(String username) {
        return this.client.findByUsername(username);
    }

    @Override
    public Usuario update(Usuario usuario, Long id) {
        return this.client.update(usuario, id);
    }
}
