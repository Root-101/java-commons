## Clean Core Full

Clean core pretende ser un framework para 

## Table of Contents
- [Clean Core Full](#clean-core)
- [Table of Contents](#table-of-contents)
- [Application](#application)
  - [Modules](#modules)
  - [~~Presenters~~](#presenters)
  - [Repo](#repo)
  - [Application Services](#application-services)
    - [Authentication](#authentication)
    - [Exception](#exception)
    - [Navigation](#navigation)
    - [Notification](#notification)
    - [User Resolver](#user-resolver)
  - [Use cases](#use-cases)
- [Domain](#domain)
  - [Domain Services](#domain-services)
    - [Resource Service](#resource-service)
- [~~UI~~](#ui)
- [Utils](#utils)

## Application
La capa de aplicacion (app de ahora en adelante) está conformada por los elementos internos del sistema, nuestra logica de negocio, y la manera en que esta se integra con otros sistema.

Ejemplo de implementacion y uso de un modulo:

> - [Logica de kanban](http://github.com/JesusHdezWaterloo/) 
> - [Logica de contabilidad](http://github.com/JesusHdezWaterloo/) 

### Modules
Los modulos estan diseñados para estandarizar y facilitarpermitir una facil integracion entre sí.
Los modulos estan diseñados para estandarizar y facilitar hacer el sistema modular y sus integraciones.

### ~~Presenters~~
La capa de presenters es tiene planeada implementar con el cliente

### Repo
La capa de repositorio dentro de app define el estandar de los posibles repositorios que se usan en un sistema. Por el momento se han definido:
- `CRUDRepo`: Repositorio con las operaciones basicas de CRUD(Create, Read, Update, Destroy), ademas del `findAll()`,`count()`. 
  > Ver el [ejemplo de CRUDRepo](http://github.com/JesusHdezWaterloo/) 
- `ReadWriteRepo`: Repositorio con las operaciones basicas de lectura y escritura.
  > Ver el [ejemplo de ReadWriteRepo](http://github.com/JesusHdezWaterloo/) 


### Application Services
Este proyecto tienen entre sus funcionalidades estandarizar algunos servicios que se consideraron importantes en el ciclo de vida de una aplicación, asi como una inversion de control para evitar dependencia entre los diferentes modulos que pueden estar interactuando. Como muestra el siguente diagrama:

![](/readme_resources/Service.png)

 La idea generar de implementacion es tener una interfaz que defina el comportamiento de un servicio, y una clases _handler_ donde la implementacion(es) de dicha interfaz se registrará, y pueda ser accedida estáticamente desde cualquier módulo. En caso de que no se haya registrado ningun servicio en su respectivo _handler_, una excepción sera lanzada

#### Authentication
Uno de los servicios que se predeterminaron fue el servicio de autenticación, diseñado para generalizar y unificar la manera en la que una aplicacion se logea y comienza su flujo de seguridad.
Inicialmente su diseño fue basandose para consumir un servicio de un servidor de autorizacion (oauth2).

Este servicio está confirmado por:

> - [AuthenticationHandler](http://github.com/JesusHdezWaterloo/) 
> - [AuthenticationHandlerService](http://github.com/JesusHdezWaterloo/)

Un ejemlo de su implementación y uso en:

> - [AuthenticationHandler](http://github.com/JesusHdezWaterloo/)

#### Exception
Otro de los servicios que se predeterminaron fue el servicio de control de excepciones, diseñado para generalizar y unificar la manera en la que se procesan las excepciones en el sistema. La idea general es que cada módulo defina las excepciones que va a lanzar y la manera de procesarlas, asi, cualquier excepcion que se lanze, es capturada y procesada acorde, y si no hay ningun procesador para ella, se procesa por defecto, preferiblemente con un cartel de error:
Por defecto todas las excepciones que no se procesen, luego de ser procesadas internamente por el JDK, pasan por este handler

Como procesar una excepcion:
```java
try{
//some code
}catch(Exception e){
    ExceptionHandler.handleException(e);
}
```
Este servicio está confirmado por:

>   - [ExceptionHandler](http://github.com/JesusHdezWaterloo/)
>   - [ExceptionHandlerService](http://github.com/JesusHdezWaterloo/)
>   - [ExceptionHandlerServiceFunctional](http://github.com/JesusHdezWaterloo/)

Una particularidad de este servicio es que el [ExceptionHandlerService](http://github.com/JesusHdezWaterloo/) contiene una implementacion por defecto [ExceptionHandlerServiceFunctional](http://github.com/JesusHdezWaterloo/)

Un ejemlo de su implementación y uso en:

> - [ExceptionHandler](http://github.com/JesusHdezWaterloo/)

#### Navigation
Otro de los servicios que se predeterminaron fue el servicio de navegación, diseñado para estandarizar la manera en que las vistas navegan entre si. La idea general es que cada vista implemente la interfaz y vaya delegando la navegacion a sus componentes internos.

**Este servicio se definió para el trabajo visual y no se ha implementado bien, por lo que puede que se le hagan mejoras en un futuro**

Este servicio está confirmado por:

> - [Navigation](http://github.com/JesusHdezWaterloo/) 
> - [NavigationService](http://github.com/JesusHdezWaterloo/)

#### Notification
Otro de los servicios que se predeterminaron fue el servicio de notificación, diseñado para generalizar y unificar las notificaciones que lanzaría el sistema. La idea general es que cada módulo defina sus notificaciones, las registre y las lance en cualquier momento, así como cualquier otro modulo que requiera usar notificaciones externas.

Este servicio está confirmado por:

> - [Navigation](http://github.com/JesusHdezWaterloo/) 
> - [NavigationService](http://github.com/JesusHdezWaterloo/)

Un ejemlo de su implementación y uso en:

> - [Navigation](http://github.com/JesusHdezWaterloo/)

#### User Resolver
Otro de los servicios que se predeterminaron fue el servicio de usuario, diseñado para generalizar la manera en la que se accede al usuario que se encuentra logeado actualmente en el sistema (cliente), o el usuario que acaba de hacer la peticion actual que se esta procesando (servidor). La idea general es que la implementacion del servicio sea una delegación al framework que se esta usando para la seguridad

Este servicio está confirmado por:

> - [UserResolver](http://github.com/JesusHdezWaterloo/) 
> - [UserResolverService](http://github.com/JesusHdezWaterloo/)

Un ejemlo de su implementación y uso en:

> - [Cliente: UserResolver](http://github.com/JesusHdezWaterloo/)
> - [Server: UserResolver](http://github.com/JesusHdezWaterloo/)


### Use cases
La capa de casos de uso dentro de app define el estandar de los posibles casos de uso que se usan en un sistema. Por el momento se han definido:
- `CRUDUseCase`: Caso de uso con las operaciones basicas de CRUD(Create, Read, Update, Destroy), ademas del `findAll()`,`count()`. Con una implementacion por defecto que delega sus metodos a un `CRUDRepo`
  > Ver el [ejemplo de CRUDUseCase](http://github.com/JesusHdezWaterloo/) 
- `ReadWriteUseCase`: Caso de uso con las operaciones basicas de lectura y escritura. Con una implementacion por defecto que delega sus metodos a un `ReadWriteRepo`
  > Ver el [ejemplo de ReadWriteUseCase](http://github.com/JesusHdezWaterloo/) 

## Domain
La capa de domain esta conformada por los objectos que contienen como tal la informacion, los 'wrapper' de los atributos. Generalemnte un espejo de las entidades de la base de datos en un CRUD o los campos de un JSON en un Read-Write. Por el momento se han definido:
- `EntityDomainObject`: Dominio que pretende hacer de espejo a las entidades generadas por un framework externo con la informacion directa de la Base de Datos (DB de ahora en adelante), o bien el mapeo de un JSON.
  > Ver el [ejemplo de EntityDomainObject](http://github.com/JesusHdezWaterloo/) 
- `VolatileDomainObject`: Dominio que pretende encapsular informacion momentaneamente sin guardarla, objeto de transito.
  > Ver el [ejemplo de VolatileDomainObject](http://github.com/JesusHdezWaterloo/) 

### Domain Services

#### Resource Service
Uno de los servicios que se definió e implementó para esta capa fueron los recursos, que permiten estandarigar y generalizar el uso de recursos (generalemnte texto para internacionalizacion)

Un ejemlo de su implementación y uso en:

> - [Resource Service](http://github.com/JesusHdezWaterloo/)

## ~~UI~~
Deprecated

## Utils
Para hacer que todas estas capas funcionen e interactúen entre si, se diseñó un paquete de utilidades con el implementaciones basicas por defectos de algunos comportamientos estandares.
Entre esto, un sistema de validacion para dominios que permite corregir errores de entrada en los datos mediante `@annotations`.

Ver el [ejemplo de Validaciones](http://github.com/JesusHdezWaterloo/) 


## Releases
- 2.0.1.RELEASE.20210108
### Improvements
    - ExceptionHandler: Agregado `Thread.setDefaultUncaughtExceptionHandler` para que por defecto procese todas las excepciones por aqui
    - Agregados println a los handlers de algunos servicios para ver a nivel de consola el proceso que está siguiendo la aplicación
    - Agregadas Excepciones al lanzar si hay incongruencias en la inicialización de los módulos

- 2.0.2.RELEASE.20210113
### Improvements
    - RuntimeExceptions: Todas las excepciones de los repositorios & casos de usos & Converter cambiado de throws Exception a throws RuntimeException para no tener que estar poniendo los try{}catch(){} ni los throws en los metodos. Y como se agregó en la versión `2.0.1.RELEASE.20210108` el `Thread.setDefaultUncaughtExceptionHandler`, todas las excepciones finalmente se procesan con el ExceptionHandler por defecto.
    - DefaultResourceBundleService: Agrados builders para construir ResourceService rápido
    - ResourceHandler: Agregados registers por defectos para facilitar registrar nuevos servicios

- 2.0.3.RELEASE.20210113
### Improvements
    - Release Notes actualizadas con los cambios de la versión `2.0.2.RELEASE.20210113`