package com.smoothiemx.servicioitem.app.controllers;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.smoothiemx.servicioitem.app.models.Item;
import com.smoothiemx.servicioitem.app.models.Producto;
import com.smoothiemx.servicioitem.app.services.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RefreshScope
@RestController
public class ItemController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Value("${configuracion.texto}")
    private String texto;

    @Autowired
    private Environment env;

    @Autowired
    private CircuitBreakerFactory cbFactory;

    @Autowired
    @Qualifier("itemServiceFeign")
    private ItemService itemService;

    @GetMapping("/listar")
    public List<Item> listar(@RequestParam (name = "nombre", required = false) String nombre, @RequestHeader(name = "token-request", required = false) String token) {
        System.out.println("nombre = " + nombre);
        System.out.println("token = " + token);
        return this.itemService.findAll();
    }

    /*@HystrixCommand(fallbackMethod = "metodoAlternativo")*/
    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
        return this.cbFactory.create("items")
                .run(() -> this.itemService.findById(id, cantidad), e -> metodoAlternativo(id, cantidad, e));
    }

    @CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo")
    @GetMapping("/ver2/{id}/cantidad/{cantidad}")
    public Item detalle2(@PathVariable Long id, @PathVariable Integer cantidad) {
        return this.itemService.findById(id, cantidad);
    }

    @CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo2")
    @TimeLimiter(name = "items")
    @GetMapping("/ver3/{id}/cantidad/{cantidad}")
    public CompletableFuture<Item> detalle3(@PathVariable Long id, @PathVariable Integer cantidad) {
        return CompletableFuture.supplyAsync(() -> this.itemService.findById(id, cantidad));
    }

    private Item metodoAlternativo(Long id, Integer cantidad, Throwable e) {

        LOG.info(e.getMessage());
        Item item = new Item();
        Producto producto = new Producto();

        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("Cámara Sony");
        producto.setPrecio(500.00);
        item.setProducto(producto);

        return item;
    }

    private CompletableFuture<Item> metodoAlternativo2(Long id, Integer cantidad, Throwable e) {

        LOG.info(e.getMessage());
        Item item = new Item();
        Producto producto = new Producto();

        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("Cámara Sony");
        producto.setPrecio(500.00);
        item.setProducto(producto);

        return CompletableFuture.supplyAsync(() -> item);
    }

    @GetMapping("/obtener-config")
    public ResponseEntity<?> obtenerConfig(@Value("${server.port}") String puerto) {

        LOG.info(texto);

        Map<String, String> json = new HashMap<>();
        json.put("texto", texto);
        json.put("puerto", puerto);

        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equalsIgnoreCase("dev")) {
            json.put("autor.nombre", env.getProperty("configuracion.nombre"));
            json.put("autor.email", env.getProperty("configuracion.email"));
        }

        return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
    }
}