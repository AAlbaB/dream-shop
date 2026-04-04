# e-Commerce: Dream Shop

## Descripción

Este proyecto es una aplicación de comercio electrónico desarrollada con Spring Boot, que permite a los usuarios navegar
por productos, realizar compras y gestionar sus cuentas. La aplicación utiliza MySQL como base de datos para almacenar
la información de los productos, usuarios y pedidos.

## Tecnologías Utilizadas

- Java 17
- Spring Boot 3.5.11
- Spring Data JPA
- MySQL

## Configuración de MySQL con Docker

Para configurar MySQL utilizando Docker, sigue los siguientes pasos

- Asegúrate de tener Docker instalado en tu máquina.
- Creamos el volumen:

```bash
  docker volume create mysql-volume
  ```

- Ejecutamos el contenedor de MySQL:

```bash
  docker run -d -p 3306:3306 -v mysql-volume:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=admin --name mysql-container mysql:latest
  ```

- Se puede probar la conexión (Se usa un gestor de BD como MySQL Workbench)
- No olvidar crear el esquema en la BD: `dream_shops_db`
- Configurar el archivo `application.properties` con las siguientes propiedades:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/dream_shops_db
spring.datasource.username=root
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=create
```

- Esta propiedad índica a Hibernate que cree las tablas automáticamente al iniciar la aplicación. Puedes cambiarla a
  `update` si deseas que Hibernate actualice las tablas existentes sin eliminarlas.
- Con esta configuración, la aplicación se conectará a la base de datos MySQL en el contenedor Docker y creará las
  tablas necesarias al iniciar la aplicación.
