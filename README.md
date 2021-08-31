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