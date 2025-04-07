# cineBDBManagement-API
# cineBDBManagement-API

## Descripción

`cineBDBManagement-API` es una API para la gestión de un cine. Proporciona endpoints para gestionar películas, reservas, gestionar salas del cine.

## Requisitos previos

Antes de ejecutar la aplicación, asegúrate de tener instalados los siguientes componentes:

- [Java 21](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) o superior
- [Maven 3.6.3](https://maven.apache.org/download.cgi) o superior
- [MySQL](https://dev.mysql.com/downloads/installer/) (o cualquier otra base de datos compatible)

## Configuración

1. Clona el repositorio:

    ```bash
    git clone https://github.com/MALGOJ/cineBDBManagement-API.git
    cd cineBDBManagement-API
    ```

2. Configura la base de datos:

    - Crea una base de datos en MySQL llamada `cineBDB_db`.
    - Actualiza las credenciales de la base de datos en el archivo `src/main/resources/application-dev.properties`:

    ```ini
    spring.datasource.url=jdbc:mysql://localhost:3306/cineBDB_db?createDatabaseIfNotExist=true
    spring.datasource.username=tuUsuario
    spring.datasource.password=tuContraseña
    ```

3. Configura las credenciales de AWS en el archivo `src/main/resources/application-dev.properties`:

    ```ini
    aws.accessKey=tuAccessKey
    aws.secretKey=tuSecretKey
    aws.region=tuRegion
    aws.ses.source-email=tuEmail
    ```

## Compilación y ejecución

1. Compila el proyecto usando Maven:

    ```bash
    mvn clean install
    ```

2. Ejecuta la aplicación:

    ```bash
    mvn spring-boot:run
    ```

3. La aplicación estará disponible en `http://localhost:8081`.

## Documentación de la API

La documentación de la API está disponible en Swagger. Una vez que la aplicación esté en ejecución, puedes acceder a la documentación en: