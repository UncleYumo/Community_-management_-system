# 基于MVC设计模式和JavaFX的社区管理系统开发手册

> @author uncle_yumo

> @dev-platform IDEA、Scene Builder

> @sql MySQL8.0（位于阿里云ESC）

> @JDK-V 甲骨文JDK21

## 概述

本手册旨在详尽介绍基于MVC（Model-View-Controller）设计模式和JavaFX GUI框架开发的社区管理系统。系统集成了云端MySQL数据库，旨在提供一个高效、用户友好的平台，用于社区管理人员与居民之间的互动。本系统主要实现以下核心功能：

1. **登录验证**：支持社区管理员和普通居民的身份验证。
2. **社区人员管理**：仅供管理员使用，对社区成员信息进行管理。
3. **物业联系模块**：允许普通居民提交需要物业解决的问题或建议。
4. **物业处理模块**：管理员审核并处理居民提交的请求，并能标记事件状态。

## 1. 系统架构设计

### 1.1 MVC架构

- **Model（模型层）**：负责数据管理和业务逻辑处理，包括数据库操作、用户身份验证逻辑、物业请求处理逻辑等。
- **View（视图层）**：采用JavaFX实现，负责用户界面展示，包括登录界面、人员管理界面、物业联系界面、物业处理界面等。
- **Controller（控制层）**：作为Model和View的桥梁，处理用户输入，调用Model执行业务逻辑，并根据逻辑结果更新View。

### 1.2 技术栈

- **前端**：JavaFX
- **后端**：Java + Spring Boot (简化服务端开发，便于集成数据库)
- **数据库**：MySQL
- **通讯**：JDBC (Java Database Connectivity) 或 JPA (Java Persistence API) 进行数据库操作

## 2. 功能模块实现

### 2.1 登录验证

- **界面设计**：使用JavaFX创建登录界面，包含用户名、密码输入框及登录按钮。
- **逻辑处理**：Controller接收输入，调用Model中的验证逻辑，区分管理员与居民角色。
- **数据库交互**：查询用户表验证账号密码及角色。

### 2.2 社区人员管理

- **权限控制**：仅管理员可见此模块。
- **功能实现**：增删改查社区成员信息，包括姓名、住址、联系方式等。
- **界面元素**：表格展示成员列表，提供添加、编辑、删除按钮。

### 2.3 物业联系模块

- **界面设计**：为居民提供表单填写界面，包括问题描述、图片上传等功能。
- **数据提交**：居民提交的信息通过Controller保存至数据库中的“物业请求”表。
- **通知机制**：可选实现即时通知管理员新请求的到达。

### 2.4 物业处理模块

- **界面展示**：管理员界面显示所有未处理的物业请求列表。
- **处理逻辑**：管理员可以查看请求详情，选择处理后更新状态为“已处理”。
- **反馈机制**：可选实现向居民发送处理结果的邮件或消息通知。

## 3. 数据库设计

### 3.1 表结构设计

1. **users**：存储用户基本信息
    - `id` (INT, PK, AUTO_INCREMENT)：用户ID
    - `username` (VARCHAR)：用户名
    - `password` (VARCHAR)：加密后的密码
    - `role` (ENUM('admin', 'resident'))：用户角色

2. **community_members**：社区成员详细信息
    - `member_id` (INT, PK, FK to users.id)：成员ID
    - `name` (VARCHAR)：姓名
    - `address` (VARCHAR)：住址
    - `contact` (VARCHAR)：联系方式

3. **property_requests**：物业请求记录
    - `request_id` (INT, PK, AUTO_INCREMENT)：请求ID
    - `user_id` (INT, FK to users.id)：提交用户ID
    - `description` (TEXT)：问题描述
    - `image_url` (VARCHAR)：图片链接（如有）
    - `status` (ENUM('pending', 'processed'))：处理状态

### 3.2 SQL脚本示例

```sql
CREATE TABLE usertable (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE ,
    password VARCHAR(255),
    role ENUM('admin', 'resident')
);

CREATE TABLE property_requests (
    requestID INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    phone VARCHAR(255) NOT NULL,
    startTime datetime NOT NULL,
    endTime datetime,
    status ENUM('未处理', '已处理') NOT NULL DEFAULT '未处理',
    FOREIGN KEY (username) REFERENCES usertable(username)
);




-- 更多表创建语句依此类推...
```

## 4. 页面与页面间切换逻辑

- 使用JavaFX的Scene和Stage管理页面切换，Controller中定义方法处理不同场景间的跳转逻辑。
- 登录成功后，根据用户角色加载不同的主界面（管理员或居民）。

## 5. 开发步骤指南

1. **环境搭建**：配置Java开发环境，安装JavaFX SDK，设置Spring Boot项目。
2. **数据库准备**：安装MySQL，创建数据库，执行上述SQL脚本初始化表结构。
3. **基础架构搭建**：按照MVC模式划分项目结构，配置Spring Boot与数据库连接。
4. **界面开发**：使用JavaFX设计UI界面，确保响应式设计适应不同屏幕尺寸。
5. **业务逻辑编码**：实现各模块的业务逻辑，包括数据验证、处理逻辑等。
6. **测试与调试**：单元测试、集成测试，确保系统稳定运行无明显bug。
7. **部署与维护**：将应用部署到服务器，持续监控并根据反馈进行迭代优化。

## 6. 结论

本手册提供了基于MVC设计模式和JavaFX技术栈开发社区管理系统的全面指南，从架构设计到具体实现细节，力求为开发者提供清晰、可操作的指导。遵循本手册，团队可以高效协作，开发出功能完善、用户体验良好的社区管理系统。在开发过程中，应持续关注代码质量、安全性和性能优化，确保最终产品满足社区管理的实际需求。