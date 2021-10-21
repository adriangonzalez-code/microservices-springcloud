package com.smoothiemx.serviciooauth.app.security.eventos;

import brave.Tracer;
import com.smoothiemx.commonsusuarios.app.models.Usuario;
import com.smoothiemx.serviciooauth.app.services.IUsuarioService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private Tracer tracer;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {

        if (authentication.getDetails() instanceof WebAuthenticationDetails) {
            return;
        }

        /*if (authentication.getName().equalsIgnoreCase("frontendapp")) {
            return;
        }*/

        UserDetails user = (UserDetails) authentication.getPrincipal();
        System.out.println("Success Login: " + user.getUsername());
        LOG.info("Success Login: " + user.getUsername());

        Usuario usuario = this.usuarioService.findByUsername(authentication.getName());

        if (usuario.getIntentos() != null && usuario.getIntentos() > 0) {
            usuario.setIntentos(0);
            this.usuarioService.update(usuario, usuario.getId());
        }
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException e, Authentication authentication) {

        String mensaje = "Error en el Login:" + e.getMessage();
        LOG.error(mensaje);
        System.out.println(mensaje);

        try {
            StringBuilder errors = new StringBuilder();
            errors.append(mensaje);

            Usuario usuario = this.usuarioService.findByUsername(authentication.getName());
            if (usuario.getIntentos() == null) {
                usuario.setIntentos(0);
            }

            LOG.info("Intentos actual es de: " + usuario.getIntentos());
            usuario.setIntentos(usuario.getIntentos() + 1);
            LOG.info("Intentos después es de: " + usuario.getIntentos());

            errors.append(" - Intentos después es de: " + usuario.getIntentos());

            if (usuario.getIntentos() >= 3) {
                String errorMaxIntentos = String.format("El usuario %s deshabilitado por máximo de intentos", usuario.getUsername());
                LOG.error(errorMaxIntentos);
                errors.append(" - " + errorMaxIntentos);
                usuario.setEnabled(false);
            }

            usuarioService.update(usuario, usuario.getId());

            this.tracer.currentSpan().tag("error.mensaje", errors.toString());
        } catch (FeignException ex) {
            LOG.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
        }
    }
}