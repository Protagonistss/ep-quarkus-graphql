# GraphQL + Quarkus + MySQL 数据库项目

本项目演示了如何使用 Quarkus 框架构建 GraphQL API，并使用 MySQL 数据库进行数据存储。

## 环境准备

### 开发环境（推荐）

项目已配置 **Quarkus Dev Services**，会自动创建和管理 MySQL 数据库：

1. 确保 Docker 已安装并运行
2. 直接启动项目：`./mvnw quarkus:dev`
3. Quarkus 会自动：
   - 下载 MySQL 8.0 Docker 镜像
   - 启动 MySQL 容器
   - 创建 `graphql_learning` 数据库
   - 配置连接参数

### 生产环境

1. 安装 MySQL 8.0+
2. 创建数据库：
```sql
CREATE DATABASE graphql_learning CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
3. 或者使用 JDBC URL 参数自动创建：
```properties
quarkus.datasource.jdbc.url=jdbc:mysql://localhost:3306/graphql_learning?createDatabaseIfNotExist=true
```

## 项目依赖

### 核心依赖

```xml
<!-- MySQL 数据库 -->
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-jdbc-mysql</artifactId>
</dependency>

<!-- GraphQL -->
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-smallrye-graphql</artifactId>
</dependency>

<!-- Hibernate ORM -->
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-hibernate-orm</artifactId>
</dependency>

<!-- Panache -->
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-hibernate-orm-panache</artifactId>
</dependency>
```

## 数据库配置

### application.properties

```properties
# HTTP Configuration
quarkus.http.port=9090
quarkus.http.cors=true
quarkus.http.cors.origins=*

# Database Configuration
quarkus.datasource.db-kind=mysql
quarkus.datasource.username=root
quarkus.datasource.password=password
quarkus.datasource.jdbc.url=jdbc:mysql://localhost:3306/graphql_learning?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true

# Hibernate ORM Configuration
quarkus.hibernate-orm.database.generation=drop-and-create
quarkus.hibernate-orm.log.sql=true
quarkus.hibernate-orm.log.bind-parameters=true
quarkus.hibernate-orm.dialect=org.hibernate.dialect.MySQLDialect

# MySQL Configuration - 开发环境自动创建数据库
%dev.quarkus.datasource.devservices.enabled=true
%dev.quarkus.datasource.devservices.image-name=mysql:8.0
%dev.quarkus.datasource.devservices.db-name=graphql_learning
%dev.quarkus.datasource.devservices.username=root
%dev.quarkus.datasource.devservices.password=password

# 生产环境配置
%prod.quarkus.datasource.devservices.enabled=false
```

## 数据模型

### Author 实体

```java
@Entity
@Table(name = "authors")
public class Author extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column
    private String biography;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();
    
    // getters and setters...
}
```

### Book 实体

```java
@Entity
@Table(name = "books")
public class Book extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal price;
    
    // getters and setters...
}
```

## 启动应用

### 开发模式（推荐）
```bash
# 确保 Docker 运行
docker --version

# 启动应用（会自动创建数据库）
./mvnw quarkus:dev
```

### 生产模式
```bash
# 确保 MySQL 服务运行
# 手动创建数据库或使用 createDatabaseIfNotExist=true
./mvnw quarkus:dev
```

## 访问 GraphQL

- GraphQL UI: http://localhost:9090/q/graphql-ui/
- GraphQL Schema: http://localhost:9090/q/graphql/schema.graphql
- GraphQL Endpoint: http://localhost:9090/q/graphql

## 示例查询

### 查询所有书籍
```graphql
query {
  allBooks {
    id
    title
    price
    author {
      id
      name
    }
  }
}
```

### 创建作者
```graphql
mutation {
  createAuthor(name: "张三", biography: "著名作家") {
    id
    name
    biography
  }
}
```

### 创建书籍
```graphql
mutation {
  createBook(title: "Spring Boot实战", authorId: 1, price: 89.99) {
    id
    title
    price
    author {
      name
    }
  }
}
```

## 数据库管理

### 开发环境
- Quarkus Dev Services 自动管理
- 容器会在应用停止时自动清理
- 数据在容器重启时会重新初始化

### 生产环境
推荐使用以下工具连接 MySQL：
- MySQL Workbench
- DBeaver
- phpMyAdmin
- IntelliJ IDEA Database Tools

## 故障排除

### 常见问题

1. **Unknown database 'graphql_learning'**
   - 解决方案：已添加 `createDatabaseIfNotExist=true` 参数
   - 或启用 Dev Services 自动管理

2. **Docker 未运行**
   - 确保 Docker Desktop 已启动
   - 检查：`docker --version`

3. **端口冲突**
   - MySQL 默认端口 3306
   - 应用端口 9090

4. **权限问题**
   - 确保 MySQL 用户有创建数据库权限
   - 或使用 root 用户

## 技术栈优势

### 自动化开发环境
- **Dev Services**: 零配置数据库管理
- **Docker 集成**: 隔离的开发环境
- **热重载**: 代码变更自动生效

### 生产就绪
- **MySQL**: 企业级数据库
- **Hibernate ORM**: 成熟的 ORM 框架
- **GraphQL**: 现代 API 设计

这个配置提供了最佳的开发体验，同时保证了生产环境的稳定性。