# Sucursal CRUD API

_Esta API Rest tiene como finalidad realizar las operaciones de alta, baja, modificaci贸n y consulta de sucursales (branch offices) y puntos de retiro (withdrawal points). Adem谩s informa cual es la sucursal o punto de retiro m谩s cercano a un punto (latitud, longitud) dado._

## Comenzando 馃殌

_Para obtener una copia de la aplicaci贸n y levantarla en tu entorno local necesitaras tener el cliente de git instalado._

_Luego copiar la url del repositorio del branch main e incluyendola en un git clone. De la siguiente manera:_

```
git clone https://github.com/kmlindner/SucursalCRUDChallenge.git
```

_Hecho esto ya tendremos la copia de la app en nuestra computadora._

### Instalaci贸n 馃敡

_Ahora bien, para que funcione y podamos levantarla de forma exitosa, debemos contar con el siguiente entorno de configuracion:_

```
Java 8
Eclipse o IntelliJ
Maven 3.8.1
MySQL 8
```

_Se incluye en la carpeta extra del repo un dump inicial de la base de datos para que se pueda importar su estructura. El nombre de la BD por defecto es fravega. Luego se puede ejecutar el script mencionado anteriormente desde el workbench o DBeaver y ya estar铆a resuelto._

_Una vez tengamos el software mencionado anteriormente, debemos compilar el proyecto con Maven para que se descarguen todas las dependencias necesarias. Para esto debemos ejecutar un clean install en la ruta del directorio de la app o desde los plugins del IDE seleccionado._

_Si optamos por hacerlo desde consola, tipeamos cmd en la ruta del explorador y cuando se abra la consolta ejecutamos:_

```
mvn clean install
```

## Despliegue 馃摝

_La API esta construida sobre SpringBoot asi que para poder ejecutarla debemos levantar/ejecutar el m贸dulo SucursalApplication. Una vez est茅 levantada podemos probar la misma desde la documentaci贸n construida con Swagger (http://localhost:8080/swagger-api.html) o tambi茅n desde Postman (Se incluye la colecci贸n de servicios en la carpeta extra del repo)._

## Documentaci贸n 馃洜锔?

_Una vez levantada la aplicaci贸n ingresar al link debajo para acceder a la informaci贸n sobre los endpoints, requests y responses:_

* [Swagger](http://localhost:8080/swagger-api.html)

## Autores 鉁掞笍

* **Kenneth Lindner** _Software Developer_