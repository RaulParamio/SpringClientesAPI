# 🛠️ Proyecto SpringClientesAPI

Este proyecto consiste en una **API REST** desarrollada en **Java** con **Spring Boot**, que permite gestionar una base de datos relacional MySQL con **clientes** y **pedidos** mediante operaciones CRUD.
Se han seguido buenas prácticas como arquitectura en capas, uso de **JPA** con **Hibernate**, pruebas unitarias/integración y documentación **OpenAPI**.

---

## 🚀 Funcionalidades principales

- 📋 CRUD completo de clientes
- 📦 CRUD completo de pedidos
- 🔍 Búsqueda de cliente por DNI
- 📅 Consulta de pedidos por fecha
- 🧪 Testing con **JUnit 5**, **Mockito** y **MockMvc**
- 🧾 Documentación automática con **Swagger/OpenAPI 3**

---

## 🧰 Tecnologías utilizadas

| Herramienta       | Uso principal                          |
|-------------------|----------------------------------------|
| ☕ Java 17         | Lenguaje principal                     |
| 🌱 Spring Boot     | Framework principal                    |
| 🛢️ Hibernate (JPA)| Persistencia ORM                       |
| 🐬 MySQL           | Base de datos relacional               |
| 🧪 JUnit + Mockito | Pruebas unitarias e integración        |
| 🔧 Maven           | Gestión de dependencias                |
| 📄 Swagger UI      | Documentación de API REST              |

---

## 📁 Estructura del proyecto
```text
src/
└── main/
    └── java/
        └── com/
            └── raulpar/
                └── springclientesapi/
                    ├── controller/      → Controladores REST (API pública)
                    │   ├── ClienteController.java
                    │   └── PedidoController.java
                    │
                    ├── service/         → Lógica de negocio
                    │   ├── ClienteService.java
                    │   └── PedidoService.java
                    │
                    ├── repository/      → Acceso a datos con Spring Data JPA
                    │   ├── ClienteRepository.java
                    │   └── PedidoRepository.java
                    │
                    ├── model/           → Entidades JPA que representan la base de datos
                    │   ├── Cliente.java
                    │   └── Pedido.java
                    │
                    └── SpringClientesApiApplication.java
```
---

## 🧪 Testing

- ✔️ **Service:** Tests unitarios (Mockito) y de integración (SpringBootTest)
- ✔️ **Controller:** Tests aislados con `@WebMvcTest` + `MockMvc`
- ✨ Cobertura parcial usando escenarios típicos de uso

---

## ▶️ Cómo ejecutar el proyecto

 1. Clona el repositorio:
   ```bash
   git clone https://github.com/raulparamio/SpringClientesAPI.git
   ```  
2. Ajusta tu conexión en src/main/resources/application.yml
3. Levanta la aplicación con Maven:
  ```bash
  mvn clean spring-boot:run
  ```
4. Accede a la API en http://localhost:8080 o a Swagger UI en
http://localhost:8080/swagger-ui/index.html

## 🐳 Docker

Puedes levantar la aplicación y la base de datos MySQL con Docker Compose de forma sencilla.

### Requisitos

- Docker
- Docker Compose

### Pasos

1. Desde la raíz del proyecto, ejecuta:
   ```bash
   docker-compose up --build
   
2. Esto iniciará dos contenedores:

- app: tu API Spring Boot en http://localhost:8080
- mysql: base de datos MySQL en el puerto 3306

3. Para detenerlos y eliminar los contenedores:

```bash
docker-compose down
```
---
## 🌐 Acceder a la API REST

Puedes probar los endpoints usando tu navegador, Swagger o Postman. A continuación los principales:

| Método | URL                                        | Descripción                          |
| ------ | ------------------------------------------ | ------------------------------------ |
| GET    | `http://localhost:8080/api/clientes`       | Obtener todos los clientes           |
| GET    | `http://localhost:8080/api/clientes/{id}`  | Obtener cliente por su ID            |
| POST   | `http://localhost:8080/api/clientes`       | Crear un nuevo cliente               |
| DELETE | `http://localhost:8080/api/clientes/{id}`  | Borrar cliente por su ID             |
| GET    | `http://localhost:8080/api/clientes/dni/{dni}` | Buscar cliente por DNI            |
| GET    | `http://localhost:8080/api/pedido`         | Obtener todos los pedidos            |
| GET    | `http://localhost:8080/api/pedido/{id}`    | Obtener pedido por su ID             |
| POST   | `http://localhost:8080/api/pedido`         | Crear un nuevo pedido                |
| DELETE | `http://localhost:8080/api/pedido/{id}`    | Borrar pedido por su ID              |
| GET    | `http://localhost:8080/api/pedido/fecha?fecha=YYYY-MM-DD` | Consultar pedidos por fecha |

