# Proyecto con Spring
1. Video guia para este proyecto: [Video](https://www.youtube.com/watch?v=oGhc5Z-WJSw&list=PL12f2ZfD_Eujxj3TJdjDpKFgsZf7CUj3R)
2. Código base: [Código Github](https://github.com/dailycodework/dream-shops)
3. Se creó el proyecto en spring initializr con [Spring Initializr](https://start.spring.io/):
    * Maven, Java 17+, Spring Boot 3.3.3, Packing jar
    * Dependencias: Spring Web, Spring Data JPA, Lombok, MySQLDriver, Validation.
4. Primero, se crearon los modelos de Product, Category, Image. (Se creó un nuevo paquete)
5. La configuración de la bd se encuentra en la ruta: `src/main/resources/application.properties`
6. Para la base de datos podemos usar Docker en lugar de instalar MySQL:
   - Creamos el volumen: `docker volume create mysql-volume`
   - Ejecutamos el contenedor de MySQL: `docker run -d -p 3306:3306 -v mysql-volume:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=admin --name mysql-container mysql:latest`
   - Se puede probar la conexión (Se usa un gestor de BD como MySQL Workbench)
   - No olvidar crear el esquema en la BD: `dream_shops_db`
   - Se ejecuta normal la aplicación y debe crear las tablas con sus relaciones en la instancia
7. Segundo, Se crea el paquete de "service", en donde creo un paquete para cada entidad y después el Service y la interface.
   - En la interface colocamos los métodos que vamos a usar, para obtener info de los productos: Añadir producto, listarlos, etc.
   - Al colocar en la clase de service, que se va a implementar la interface, el IDE le coloca el @Override a todo y declara los métodos automáticamente.
   - En el Service, podemos llamar al Repository y desde ahi mismo crear el nuevo paquete y clase (Create Spring data repository), este sería el Tercer paso.
   - Se editan las clases en el service, recordar que se usa el repository para no llamar el modelo directamente y como esta extiende de JpaRepository, ya tiene métodos determinados.
   - Se pueden crear excepciones ahi mismo, como "ProductNotFoundException", para quedar personalizadas. 
8. Tercero, creamos el paquete de "repository" y las interfaces del repositorio extendiendo de JpaRepository, el modelo que corresponda.
   - Podemos llamar al repository en el service.
9. Cuarto, se crea el paquete de "exceptions" y las clases que usaron en el service, estas extienden de "RuntimeException" 
10. Quinto, se crea el paquete de "request", en la cual tiene las clases para crear y editar, para no hacerlo directamente con el modelo. Generar los constructores en los modelos: Clic derecho, Generate, Constructor.
11. Se hizo el services de Category con su interface y su repository. Además, se completó el Product.
12. Se hizo el services de Image con su interface y su repository.
13. Sexto, para la creación de imagen se creó el nuevo paquete de "dto" y las clases de cada uno.
14. Séptimo, se crea el paquete "response", con la clase ApiResponse (Se usará para devolver información al frontend).
15. Octavo, se crea el paquete "controller", inicialmente con las clases: ProductController, ImageController y CategoryController. Estos llaman a la interfaz del servicio de cada entidad correspondiente.
   - `ResponseEntity:` representa la respuesta HTTP completa: código de estado, cabeceras y cuerpo. Como resultado, podemos utilizarla para configurar completamente la respuesta HTTP.

QUEDE EN EL MINUTO 2:03:00!!!, Formato: ctrl+alt+L