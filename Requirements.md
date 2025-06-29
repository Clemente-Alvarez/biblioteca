# Sistemas Orientados a Servicios – 2024- 25

# Práctica: Diseño e implementación de una API Rest

```
Se trata de diseñar e implementar una API REST y un prototipo funcional de un sencillo
servicio de gestión de inventario para la biblioteca de la Escuela. Donde los usuarios de
esta aplicación puedan realizar distintas operaciones sobre la gestión de inventario
(añadir, borrar libros) y préstamo de libros (préstamo o devoluciones de libros). Se
asume que la autenticación y autorización de la herramienta está realizada y por
tanto no hay que implementarla.
```
```
En este servicio se podrán realizar acciones como añadir, borrar, editar libros del
inventario, tomar prestados libros para un período de tiempo o realizar las
devoluciones de los libros prestados. Para ello, se debe tener en cuenta que los
libros deben estar representados por el título del libro, autor/es, edición, ISBN y
editorial.
```
```
El servicio debe implementar a través de la API las siguientes operaciones:
```
- Añadir un usuario nuevo a la aplicación. Los datos básicos de un usuario son:
    nombre de usuario, matrícula, fecha de nacimiento y correo electrónico.
- Ver los datos básicos del usuario.
- Cambiar datos básicos de un perfil de usuario.^
- Borrar un perfil de usuario.
- Obtener una lista de todos los usuarios en la aplicación.
- Añadir libros a la biblioteca. Además, puede modificar y/o dar de baja libros
    concretos.
- Los usuarios pueden listar los libros de la biblioteca. Esta lista debe permitir ser
    filtrada por patrón de título (eg. Buscar todos los libros que contengan “Rest” en
    su título, “ Principios Restful”, “Rest recetas” ...etc.).^1
- Se debe permitir filtrar este listado de libros para obtener únicamente los libros
    que estén disponibles.
- Los usuarios pueden tomar prestados libros durante un período de dos semanas.
- Los usuarios pueden devolver libros prestados. Si los devuelven fuera del plazo de
    préstamo, se aplicará una sanción y no podrán hacer prestamos en una semana.
- Los usuarios pueden ampliar el préstamo de un libro, siempre que no se haya
    terminado el plazo de préstamo anterior.
- Se puede obtener la lista de todos los préstamos actuales de un usuario. Además,
    ese listado debe permitir la opción de ser filtrada por fechas.
- Se debe permitir también consultar el histórico de libros que ha tenido prestados
    un usuario.
- Se debe facilitar consultar _rápidamente_ toda la información de actividad
    relacionada con un usuario. Esta respuesta debe contener toda la información
    relativa al usuario, así como; la lista de libros que tiene actualmente prestados
    ordenada por fecha de préstamos, el historial de préstamos con los últimos 5
    libros que ya ha devuelto.

(^1) Tanto esta lista como todas las demás, deben cumplir con los criterios de paginación y
navegabilidad vistos en clase (principios HATEOAS). Los resultados deben estar paginados y deben
mostrar las URIs de cada recurso.


**Se pide:**
Teniendo en cuenta estos aspectos, esta práctica se plantea atendiendo a dos objetivos
fundamentales:

1. Que el alumno realice un diseño RESTful de un servicio sencillo, aplicando las
mejores prácticas y las recomendaciones explicadas en las clases de la asignatura.
2. Que el alumno implemente este servicio usando la implementación de
referencia de la _API Spring Boot_. Y posteriormente verifique su correcto
funcionamiento con un cliente REST (Postman) y desarrolle su propio cliente en Java.

```
Para ello se divide el trabajo a realizar en 3 categorías:
```
```
a) Diseño del servicio RESTful. Se recomienda seguir estos pasos:
```
- Identifique primero todos los recursos en un modelo de recursos para el
    servicio, incluyendo recursos de información, colecciones, recursos
    compuestos, fábricas, controladores, etc. Diseñe los tipos de documentos JSON
    necesarios para el servicio. Diseñe los esquemas JSON.
- Diseñe los identificadores URI ( _endpoint_ ) de todos los recursos en el modelo,
    siguiendo las convenciones vistas en clase.
- Diseñe para cada recurso, el subconjunto del interfaz uniforme de HTTP que
    ofrece. Incluya para cada verbo empleado por un recurso una breve
    descripción de su uso.
- Resuma el diseño del servicio utilizando la siguiente para cada recurso,
    método y representación.

URI Patrón de URI del recurso
Método GET / POST / PUT / DELETE

Cadena de consulta
(sólo GET)

```
param1 = Descripción del parámetro y del conjunto de valores posibles
```
paramN= Etc.
Cuerpo de la petición
(sólo PUT ó POST) Application/JSON + referencia al JSON Schema^

Devuelve

```
200 OK + Application/JSON + referencia al JSON Schema
401 Unauhtorized
Etc. Etc.
```
```
b) Implementación de la API REST del servicio utilizando la implementación de
referencia del estándar para REST Spring Boot vista en clase.
```
```
El prototipo deberá mantener los datos de manera persistente utilizando
una base de datos SQL (PostgreSQL). Detallar el diagrama de entidad-relación del
modelo realizado.
```
```
c) Implementación de un cliente Java que pruebe ese servicio y que llame a todas las
operaciones del servicio y verifique el correcto funcionamiento de los diferentes
códigos HTTP. No es necesario realizar un cliente con interfaz gráfico o web.
```

La entrega se realizará en Moodle y contendrá al menos estos ficheros:

1. Memoria de la práctica (PDF). La memoria debe incluir al menos la siguiente
    información:
       1. Resumen del diseño del servicio como se define en el apartado (a)
          incluyendo el diseño de la persistencia de los datos escogida (Ej,
          Diagrama entidad-relación de la BBDD), descripción de los recursos
          utilizados (JSON Schema), tablas resumen de las operaciones.
       2. Capturas de la ejecución de las operaciones desde un cliente REST
          (tipo Postman ó http Rest Client) en las que **pueda verse tanto los**
          **detalles de la llamada a la operación como los detalles del resultado de**
          **esa llamada**. Se deberán de comprobar todos los códigos HTTP indicados
          en las tablas de diseño. Se deben detallar en la memoria.
       3. Capturas de la ejecución del cliente de prueba para las operaciones
          anteriormente referidas.
2. **JAR** generado c **on** Maven (mvn clean package).
3. Fichero ZIP comprimido con la carpeta src y los ficheros pom.xml, mvnw y
    mvnw.cmd.
4. Proyecto con el código del cliente (comprimido ZIP).

