# MICROSERVICIOS CON SPRING CLOUD

## ¿QUÉ SON LOS MICROSERVICIOS?

Es un enfoque que nos permite desarrollar aplicaciones que están compuestas en servicios, es decir, en vez de tener una gran aplicación monolítica, tenemos varios proyectos, es decir, varios microservicios independientes pero que se comunican entre sí mediante API Rest. Esto hace que las aplicaciones sean mucho más fáciles de escalar y también mucho más rápida de desarrollar.

En pocas palabras, son un conjunto de componentes pequeños y autónomos que colaboran entre sí.

Algunas de las caracterísiticas de los microservicios:

* Autónomos e independientes
* Especializados
* Registro y auto-descubrimiento de servicios
* Escalado flexible
* Confiabilidad y tolerancia a fallos
* Balanceo de cargas
* Configuración centralizada
* Libertad tecnológica
* Agilidad y equipos más pequeños
* Ciclo de desarrollo más cortos
* Código reutilizable

Algunas de las ventajas:

* Nuevas tecnologías y adopción de procesos
* Reducción de costos
* Ciclos de liberación más rápidos
* Equipos de desarrollo más pequeños

## COMPONENTES DEL CURSO

* Spring Boot
* Spring IoC
* Spring Data JPa e Hibernate
* API REST
* Spring Cloud
* Servidor Eureka Netflix
* Eureka Client
* RestTemplate
* Feign
* Ribbon
* Hystrix
* Gateway Zuul
* Spring Cloud Server Config
* Spring Security OAuth2
* JWT
Y mas ...

## HERRAMIENTAS NECESARIAS

* JDK
    > https://www.oracle.com/mx/java/technologies/javase-downloads.html

* IDE
    * STS (Spring Tools Suite)
    * Netbeans
    * Intellij IDEA
    * Eclipse

* Maven
    > https://maven.apache.org/download.cgi

* MySQL y PostgreSQL
    > https://dev.mysql.com/downloads/

    > https://www.postgresql.org/download/


## TRABAJANDO CON MICROSERVICIOS

### APPLICATION NAME

En primera instancia, a cada servicio se le debe definir un nombre en el archivo **application.properties**:

~~~
spring.application.name=my-service-name
~~~

Esto para registrar el servicio en **Eureka** y podamos acceder a través de él directamente por su nombre, no desde su *IP* y *puerto*.

### REST TEMPLATE

**RestTemplate** nos permite comunicarnos y consumir otra API de tipo REST en otro servidor. Es un cliente Http para comunicarnos mediante Http a recursos que están en otros microservicios.

#### CONFIGURAR REST TEMPLATE

Debemos crear un `@Bean` en una clase anotada con `@Configuration`

~~~
@Bean("clienteRest")
public RestTemplate registrarRestTemplate() {
    return new RestTemplate();
}
~~~

### FEIGN

**Feign** es otra forma de implementar un cliente Http como **RestTemplate**, es otra alternativa con el mismo objetivo: comunicarnos mediante Http a otro microservicio.

#### CONFIGURAR FEGIN

Primero debemos agregar la dependencia en el **pom.xml**

~~~
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
~~~

Debemos anotar con

`@EnableFeignClients`

En la clase anotada con **@SpringBootApplication** que es la clase principal de arranque de *SpringBoot*.

**Feign** trabaja con interfaces, por lo que debemos crear una interfaz que anotaremos con `@FeignClient(name = "<my-service-name>", url = "<http://url>")`, en `<my-service-name>` definimos el nombre del servicio a comunicarnos, ese nombre lo definimos en **spring.application.name** del **application.properties** del servicio en question. Dentro de la interfaz especificamos los métodos con los que se realizarán las peticiones Http.

~~~
@FeignClient(name = "<my-service-name>", url = "<http://url>")
public interface MyInterface {
    ...
}
~~~

### BALANCEADOR DE CARGA

Suponiendo que tenemos muchas instancias desplegadas de un mismo servicio, cada una en un puerto diferente, el balanceador de carga **Ribbon** nos conectará con la mejor instancia disponible.

Un tema muy importante, **Ribbon** es compatible con versiones de *Spring Boot* 2.3.X e inferiores. Para versiones de *Spring Boot* 2.4.X y superiores se utiliza **Spring Cloud Load Balancer**.

#### RIBBON

Para trabajar con **Ribbon** en versiones de *Spring Boot* 2.4.X o superiores, debemos cambiar la versión de *Spring Boot* a 2.3.X, para ello, debemos realizar algunos cambios en el **pom.xml**:

1. Cambiar la versión de **Spring Boot** en la etiqueta *parent*

    ~~~
    <version>2.3.12.RELEASE</version>
    ~~~

2. Cambiar la versión de **Spring Cloud** en la etiqueta *properties*

    ~~~
    <spring-cloud.version>Hoxton.SR12</spring-cloud.version>
    ~~~

3. Agregar la dependencia de **Ribbon**

    ~~~
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
    </dependency>
    ~~~

Para habilitar el uso de *Ribbon* en el servicio, en la clase anotada con *@SpringBootApplication* agregamos la siguiente anotación: `@RibbonClient(name = "<my-service-name>")`.

### SERVIDOR DE NOMBRES

Nos permite registrar los servicios a través de sus nombres, para acceder a ellos a través del balanceador de carga.

#### EUREKA

Es un servidor de nombres que trabaja junto con **Ribbon** y con **Spring Cloud Load Balancer** para acceder a los servicios a través de sus nombres.

Para trabajar con Eureka, debemos crearlo como un servicio nuevo, con solamente su dependencia:

~~~
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
~~~

Adicional, si creamos el proyecto con *JDK 9*, debemos agregar la dependencia **JAXB**, ya que en esa se incluye en *Java 8* pero no en *Java 9* y superior.

~~~
<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
</dependency>
~~~

En la clase principal (de arranque de *Spring Boot*), anotada con **@SpringBootApplication**, agregamos la anotación `@EnableEurekaServer`.

En cada servicio, debemos agregar la dependencia en el **pom.xml**

~~~
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
~~~

Adicional, cada servicio debemos habilitarlo como cliente Eureka, anotando con `@EnableEurekaClient` en la clase principal (de arranque de Spring Boot) de cada servicio en question.

**Nota importante**: Eureka incluye balanceador de carga, por lo que **NO** se debe agregar la dependencia **Ribbon** en los servicios cuando se agrega la dependencia **Eureka discovery**, por lo que si en un principio la incluímos (**Ribbon**), debemos eliminarla del **pom.xml** así como las configuraciones (**application.properties** y **@RibbonClient**).

### TOLERANCIA A FALLOS

#### HYSTRIX

Es una herramienta de Spring Cloud que nos permite manejar fallos en las comunicaciones entre los microservicios, añadiendo funcionalidad para tolerancia a fallos, latencia, timeouts, etc, permitiéndonos agregar o programar un camino alternativo.

**Hystrix** al igual que **Ribbon** solamente es compatible con versiones de *Spring Boot* 2.3.X e inferiores.

Para trabajar con Hystrix, debemos realizar las dos primeras configuraciones que realizamos con **Ribbon** y agregamos la dependencia en el **pom.xml**:

~~~
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
~~~

Habilitamos **Hystrix** mediante la anotación `@EnableCircuitBreaker` en la clase principal de Spring Boot (la clase de arranque).

### ENRUTAMIENTO DINÁMICO

Es un servicio que se encarga del acceso a los demás servicios que estén registrados en Eureka, es como la puerta de entrada a todo el ecosistema de microservicios y se le conoce como puerta de enlace (API Gateway). Esta libería se llama **Zuul** para versiones de *Spring Boot 2.3.X* e inferiores, para versiones de *Spring Boot 2.4.X* y superiores la librería se llama **Spring Cloud Gateway**.

Una de las características principales de estas liberías, es que enrutan de manera dinámica cada uno de los servicios registrados en Eureka, permitiendo dar una ruta base a cada microservicio. Esto nos permite centralizar el acceso a todo el ecosistema en un solo punto de entrada. Todas las peticiones que pasan por el enrutamiento dinámico pasan automáticamente por el balanceo de carga, por lo que no es necesario configurar **Ribbon**, además también maneja la tolerancia a fallos, latencia, timeouts, etc.

#### ZUUL

Para trabajar con **Zuul** en versiones de Spring Boot 2.4.X o superiores, debemos realizar los primeros dos puntos explicados en la configruación de **Ribbon**. **Zuul** se debe crear un como servicio adicional que debe incluir la dependencia de Eureka y habilitarla en su clase principal. Adicional, debemos agregar la siguiente dependencia:

~~~
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>
~~~

Y finalmente, habilitar **Zuul** en la clase principal del servicio con la anotación `@EnableZuulProxy`.

##### CICLO DE VIDA DE ENRUTAMIENTO ZUUL

* Pre: Se ejecuta antes de que el request sea enrutado
* Post: Se ejecuta desués de que el request haya sido enrutado
* Route: Se ejecuta durante el enrutado del request, aquí se resuelve la ruta

#### API GATEWAY

**Spring Cloud Gateway** es un servidor de enrutamiento dinámico

Algunas de las características principales del **API Gateway**:

* Zuul Netflix y Spring Cloud Gateway
* Puerta de enlace, acceso centralizado
* Enrutamiento dinámico de los microservicios
* Balanceo de carga
* Maneja filtros propios
* Permite extender funcionalidades

#### RESILIENCE4J

Reemplaza a la librería Hystrix en Spring Boot 2.4.X.

Muchas veces en un ecosistema de microservicios la comunicación puede fallar, puede que tarde demasiado tiempo en responder, o que el servicio arroje alguna excepción o simplemente el servicio no se encuentra disponible. Entonces, ¿qué hacemos?. Una buena práctica para esta situación es implementar el patrón cortocircuito o Circuit Breaker.

El patrón cortocircuito tiene 3 estados:

* Cerrado: cuando todo sale bien. Inicialmente, está en estado cerrado.

* Abierto: cuando la tasa de fallas supera el umbral. En este estado las solicitudes al microservicio con fallas no se realizarán. Cuando haya pasado uin cierto límite de tiempo cambiará a un estado Semiabierto.

* Semiabierto: se ejecutarán varias solicitudes para saber si el microservicio está funcionando con normalidad o no. Si tiene éxito, volverá al estado Cerrado, si aún falla, volverá al estado Abierto.

### SPRING CLOUD CONFIG SERVER

Nos permite centralizar la configuración de los microservicios considerando que podemos tener muchas arquitecturas de microservicios y cada uno de ellos con sus propias configuraciones y con diferentes ambientes.

#### ¿CÓMO FUNCIONA?

En el arranque del servicio, antes de registrarse en Eureka, va a consultar al servidor de configuración todas sus propiedades de configuración, y una vez que las obtenga se va a registrar en Eureka y va a arrancar el microservicio en questión.

Para trabajar con esta herramienta, debemos instalar un Sistema de Control de Versiones: Git, SVN, etc.