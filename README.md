# 幣別管理系統

這是一個簡單的幣別管理系統，實現了幣別（Currency）的 CRUD 操作，並且通過 Spring Boot 提供 API，支持新增、查詢、更新和刪除幣別資料。此專案使用了 H2 作為內存型資料庫，並透過 RESTful API 操作幣別資料。

## 功能

- **新增幣別**
- **查詢所有幣別**
- **根據幣別代碼查詢**
- **更新幣別資料**
- **刪除幣別**

## 技術棧

- **後端技術**：
  - Spring Boot
  - H2 Database：用於本地開發和測試的內存型資料庫。
  - JUnit 5：測試框架，用於單元測試和集成測試。
  - MockMvc：用於測試 Spring MVC 控制器。


## 數據庫設計

以下是 SQL 建表語句：

```sql
CREATE TABLE currency (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(100),
    rate DECIMAL(20, 8),
    updated_time VARCHAR(100)
);
```
## Restful API 文檔
### 1. 新增幣別

- **HTTP 方法**: `POST`
- **URL**: `/api/currency`
- **RequestBody**:
    ```json
    {
        "code": "USD",
        "name": "United States Dollar",
        "rate": 97652.9094,
        "updatedTime": "2024-11-21T21:33:17",
        "symbol": "$"
    }
    ```

### 2. 查詢所有講師
- **HTTP 方法**: `GET`
- **URL**: `/api/currency`

### 3. 呼叫coindesk的API
- **HTTP 方法**: `GET`
- **URL**: `/api/currency/update`

### 4. 根據幣別代碼查詢幣別
- **HTTP 方法**: `GET`
- **URL**: `/api/currency/code/{code}`

### 5. 更新幣別資料
- **HTTP 方法**: `PUT`
- **URL**: `/api/currency/update/{id}`
- **RequestBody**:
    ```json
    {
        "code": "USD",
        "name": "Updated United States Dollar",
        "rate": 98000.00,
        "updatedTime": "2024-11-21T21:33:17",
        "symbol": "$"
    }
    ```

### 6. 刪除幣別資料
- **HTTP 方法**: `DELETE`
- **URL**: `/api/currency/delete/{id}`

