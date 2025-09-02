
# 🛠️ Proyecto SpringClientesAPI

Este proyecto consiste en una **API REST** desarrollada en **Java** con **Spring Boot**, que permite gestionar una base de datos relacional MySQL con **clientes** y **pedidos**.
Se han seguido buenas prácticas como arquitectura en capas, uso de **JPA** con **Hibernate**, uso de **DTOs** con **MapStruct**, pruebas unitarias/integración y documentación **OpenAPI**.

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
| 🔄 MapStruct         | Conversión entre entidades y DTOs      |
| 📝 DTOs             | Objetos de transferencia de datos entre API y capas internas |

---

## 📁 Estructura del proyecto
```text
src/
├── main/
│   └── java/
│       └── com/raulpar/springclientesapi/
│           ├── controller/      → Controladores REST (API pública)
│           │   ├── ClienteController.java
│           │   └── PedidoController.java
│           │
│           ├── dto/             → Data Transfer Objects (DTOs)
│           │   ├── ClienteInputDto.java
│           │   ├── ClienteOutputDto.java
│           │   ├── ClienteOutputDetailDto.java
│           │   ├── PedidoCreateDto.java
│           │   └── PedidoDto.java
│           │
│           ├── mapper/          → Clases para convertir entre entidades y DTOs (MapStruct)
│           │   ├── ClienteMapper.java
│           │   └── PedidoMapper.java
│           │
│           ├── service/         → Lógica de negocio
│           │   ├── ClienteService.java
│           │   └── PedidoService.java
│           │
│           ├── repository/      → Acceso a datos con Spring Data JPA
│           │   ├── ClienteRepository.java
│           │   └── PedidoRepository.java
│           │
│           ├── model/           → Entidades JPA que representan la base de datos
│           │   ├── Cliente.java
│           │   └── Pedido.java
│           │
│           └── SpringClientesApiApplication.java
│
└── test/
    └── java/
        └── com/raulpar/springclientesapi/
            ├── controller/             → Tests de controladores (MockMvc)
            │   ├── ClienteControllerTest.java
            │   └── PedidoControllerTest.java
            │
            └── service/
                ├── unit/               → Tests unitarios de servicios
                │   ├── ClienteServiceUnitTest.java
                │   └── PedidoServiceUnitTest.java
                │
                └── integration/        → Tests de integración de servicios
                    ├── ClienteServiceIntegrationTest.java
                    └── PedidoServiceIntegrationTest.java

```
---

## 🧪 Testing

- ✔️ **Service:** Tests unitarios (Mockito) y de integración (SpringBootTest)
- ✔️ **Controller:** Tests aislados con `@WebMvcTest` + `MockMvc`
- ✨ Cobertura parcial usando escenarios típicos de uso

---

## 🌐 Acceder a la API REST

Puedes probar los endpoints usando tu navegador, Swagger o Postman. A continuación los principales:

| Método | URL                                                        | Descripción                       |
| ------ | --------------------------------------------------------   | --------------------------------  |
| GET    | `http://localhost:8080/api/clientes`                       | Obtener todos los clientes        |
| GET    | `http://localhost:8080/api/clientes/{id}`                  | Obtener cliente por su ID         |
| GET    | `http://localhost:8080/api/clientes/dni/{dni}`             | Buscar cliente por DNI            |
| PUT    | `http://localhost:8080/api/clientes/{id}`                  | Actualizar cliente por su ID      |
| POST   | `http://localhost:8080/api/clientes`                       | Crear un nuevo cliente            |
| DELETE | `http://localhost:8080/api/clientes/{id}`                  | Borrar cliente por su ID          |
| GET    | `http://localhost:8080/api/pedidos`                        | Obtener todos los pedidos         |
| GET    | `http://localhost:8080/api/pedidos/{id}`                   | Obtener pedido por su ID          |
| POST   | `http://localhost:8080/api/pedidos`                        | Crear un nuevo pedido             |
| DELETE | `http://localhost:8080/api/pedidos/{id}`                   | Borrar pedido por su ID           |
| GET    | `http://localhost:8080/api/pedidos/fecha?fecha=YYYY-MM-DD` | Consultar pedidos por fecha       |

---

## 📦 Uso de DTOs (Data Transfer Objects) y MapStruct

Para separar la **entidad de la base de datos** de los **datos expuestos a la API**, se utilizan DTOs junto con **MapStruct** para la conversión automática:

- **ClienteInputDto** → usado para crear un cliente.
- **ClienteOutputDto** → usado para mostrar información básica de un cliente.
- **ClienteOutputDetailDto** → usado para mostrar información detallada de un cliente.
- **PedidoCreateDto** → usado para crear un pedido.
- **PedidoDto** → usado para mostrar información de un pedido.

**MapStruct** se encarga de:

- ✅ Convertir entre entidades JPA y DTOs automáticamente
- ✅ Reducir la duplicación de código en los mappers
- ✅ Mantener consistencia y facilidad de mantenimiento al cambiar la estructura interna de las entidades

Esto permite:

- ✅ Validar los datos entrantes (`@NotNull`, `@Size`, etc.)
- ✅ Evitar exponer información sensible
- ✅ Mantener flexibilidad al cambiar la estructura interna de las entidades

---

## 📌 Detalles técnicos del proyecto

### 1️⃣ Entidades y persistencia
- **`@CreationTimestamp`** en JPA/Hibernate:
    - Marca automáticamente la fecha de creación de un registro (por ejemplo en `Pedido`).
- **Relaciones entre entidades:**
    - `Cliente` y `Pedido` con `@OneToMany` / `@ManyToOne` para mantener integridad referencial.
- **`Optional`** en servicios y repositorios:
    - Evita `NullPointerException` al buscar entidades que podrían no existir.

### 2️⃣ Control de serialización
- **`@JsonIgnore`** en entidades:
    - Evita exponer campos sensibles o generar bucles infinitos al serializar relaciones.
- DTOs separados (`InputDto` y `OutputDto`) para controlar qué datos se reciben y se envían por la API.

### 3️⃣ Conversión y mapeo
- **MapStruct** se usa para convertir automáticamente entre **entidades y DTOs**, reduciendo código repetitivo y errores.

### 4️⃣ Validación de datos
- Anotaciones como `@NotNull`, `@Size`, `@Email` en DTOs:
    - Garantizan que los datos entrantes cumplan reglas mínimas antes de persistirlos.

### 5️⃣ Pruebas unitarias e integración
- **Mockito + JUnit 5** para tests unitarios de servicios.
- **SpringBootTest** para tests de integración, verificando el flujo completo con la base de datos.
- **`@WebMvcTest` + `MockMvc`** para probar controladores y endpoints REST de forma aislada.

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
