# 分布式医疗信息化系统 - 技术文档

## 一、系统概述

基于 SpringBoot 3.2.0 + Spring Cloud 2023.0.0 构建的分布式医疗信息化系统，涵盖患者管理、医生管理、科室管理、预约管理、病历管理和处方管理等核心业务模块。

## 二、系统架构

```
                    ┌─────────────────┐
                    │   API Gateway   │  (8080)
                    │ Spring Cloud    │
                    │   Gateway       │
                    └───────┬─────────┘
                            │
              ┌─────────────┼─────────────┐
              │             │             │
    ┌─────────▼──┐  ┌──────▼──────┐ ┌────▼─────────┐
    │   Auth     │  │  Patient    │ │   Doctor     │
    │  Service   │  │  Service    │ │   Service    │
    │  (8081)    │  │  (8082)     │ │   (8083)     │
    └────────────┘  └──────┬──────┘ └──────┬───────┘
              │             │ Redis        │ Redis
              │             └──────────────┘
    ┌─────────▼──┐  ┌──────▼──────┐ ┌────▼─────────┐
    │Appointment │  │   Record    │ │   Config     │
    │  Service   │  │  Service    │ │   Server     │
    │  (8084)    │  │  (8085)     │ │   (8888)     │
    │  Feign调用  │  │   Redis     │ └──────────────┘
    └────────────┘  └─────────────┘
           │
    ┌──────▼──────┐
    │   Eureka    │
    │  Registry   │
    │   (8761)    │
    └─────────────┘
```

## 三、API接口清单(62个)

### 认证服务 (3个)
| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /api/auth/login | 用户登录 |
| POST | /api/auth/register | 用户注册 |
| GET  | /api/auth/public/health | 健康检查 |

### 患者管理 (11个)
| 方法 | 路径 | 说明 |
|------|------|------|
| POST   | /api/patients | 新增患者 |
| PUT    | /api/patients/{id} | 修改患者 |
| DELETE | /api/patients/{id} | 删除患者 |
| GET    | /api/patients/{id} | 查询患者(Redis缓存) |
| GET    | /api/patients | 查询所有(Redis缓存) |
| GET    | /api/patients/search?name= | 按姓名搜索 |
| GET    | /api/patients/by-idcard/{idCard} | 按身份证查询 |
| GET    | /api/patients/filter/gender?gender= | 按性别筛选 |
| GET    | /api/patients/filter/age?minAge=&maxAge= | 按年龄范围筛选 |
| GET    | /api/patients/count | 统计数量 |
| POST   | /api/patients/batch | 批量新增 |

### 医生管理 (10个)
| 方法 | 路径 | 说明 |
|------|------|------|
| POST   | /api/doctors | 新增医生 |
| PUT    | /api/doctors/{id} | 修改医生 |
| DELETE | /api/doctors/{id} | 删除医生 |
| GET    | /api/doctors/{id} | 查询医生(Redis缓存) |
| GET    | /api/doctors | 查询所有(Redis缓存) |
| GET    | /api/doctors/search?name= | 按姓名搜索 |
| GET    | /api/doctors/by-department/{deptId} | 按科室查询 |
| GET    | /api/doctors/filter/title?title= | 按职称筛选 |
| GET    | /api/doctors/filter/specialty?specialty= | 按专业筛选 |
| GET    | /api/doctors/count | 统计数量 |

### 科室管理 (7个)
| 方法 | 路径 | 说明 |
|------|------|------|
| POST   | /api/departments | 新增科室 |
| PUT    | /api/departments/{id} | 修改科室 |
| DELETE | /api/departments/{id} | 删除科室 |
| GET    | /api/departments/{id} | 查询科室 |
| GET    | /api/departments | 查询所有 |
| GET    | /api/departments/search?name= | 按名称搜索 |
| GET    | /api/departments/count | 统计数量 |

### 预约管理 (12个)
| 方法 | 路径 | 说明 |
|------|------|------|
| POST   | /api/appointments | 创建预约(Feign调用) |
| PUT    | /api/appointments/{id} | 修改预约 |
| DELETE | /api/appointments/{id} | 删除预约 |
| GET    | /api/appointments/{id} | 查询预约 |
| GET    | /api/appointments | 查询所有 |
| GET    | /api/appointments/by-patient/{patientId} | 按患者查询 |
| GET    | /api/appointments/by-doctor/{doctorId} | 按医生查询 |
| GET    | /api/appointments/filter/status?status= | 按状态筛选 |
| PUT    | /api/appointments/{id}/confirm | 确认预约 |
| PUT    | /api/appointments/{id}/cancel | 取消预约 |
| PUT    | /api/appointments/{id}/complete | 完成预约 |
| GET    | /api/appointments/count | 统计数量 |

### 病历管理 (10个)
| 方法 | 路径 | 说明 |
|------|------|------|
| POST   | /api/records | 新增病历 |
| PUT    | /api/records/{id} | 修改病历 |
| DELETE | /api/records/{id} | 删除病历 |
| GET    | /api/records/{id} | 查询病历(Redis缓存) |
| GET    | /api/records | 查询所有(Redis缓存) |
| GET    | /api/records/by-patient/{patientId} | 按患者查询 |
| GET    | /api/records/by-doctor/{doctorId} | 按医生查询 |
| GET    | /api/records/by-appointment/{appointmentId} | 按预约查询 |
| PUT    | /api/records/{id}/finish | 完成病历 |
| GET    | /api/records/count | 统计数量 |

### 处方管理 (9个)
| 方法 | 路径 | 说明 |
|------|------|------|
| POST   | /api/prescriptions | 新增处方 |
| PUT    | /api/prescriptions/{id} | 修改处方 |
| DELETE | /api/prescriptions/{id} | 删除处方 |
| GET    | /api/prescriptions/{id} | 查询处方 |
| GET    | /api/prescriptions | 查询所有 |
| GET    | /api/prescriptions/by-record/{recordId} | 按病历查询 |
| GET    | /api/prescriptions/by-patient/{patientId} | 按患者查询 |
| PUT    | /api/prescriptions/{id}/dispense | 发药 |
| GET    | /api/prescriptions/count | 统计数量 |

总计: 3+11+10+7+12+10+9 = **62个API接口**

## 四、单元测试结果

```
[INFO] Tests run: 18, Failures: 0, Errors: 0, Skipped: 0

Auth Service (5 tests):
  ✓ contextLoads - 应用上下文加载
  ✓ testLoginSuccess - 登录成功
  ✓ testLoginFailure - 登录失败(403)
  ✓ testRegister - 用户注册
  ✓ testHealthEndpoint - 健康检查

Patient Service (9 tests):
  ✓ contextLoads - 应用上下文加载
  ✓ testCreatePatient - 创建患者
  ✓ testGetPatientById - 查询患者
  ✓ testUpdatePatient - 更新患者
  ✓ testDeletePatient - 删除患者
  ✓ testListAllPatients - 查询所有
  ✓ testSearchByName - 按姓名搜索
  ✓ testFilterByGender - 按性别筛选
  ✓ testCount - 统计数量

Doctor Service (4 tests):
  ✓ testCreateDoctor - 创建医生
  ✓ testCreateAndGetDepartment - 创建科室
  ✓ testFilterByTitle - 按职称筛选
  ✓ testDoctorCount - 统计数量
```

## 五、开发难点与解决方案

### 难点1: Lombok与JDK 25不兼容
**问题描述:** 系统运行环境为JDK 25，而Lombok 1.18.30/1.18.36均不支持JDK 25的内部javac API。编译时Lombok注解(@Data, @Getter, @Setter等)完全失效，导致所有实体类和DTO的getter/setter方法无法生成。

**解决过程:**
1. 首先尝试升级Lombok到最新版本1.18.36，仍然不兼容
2. 检查确认JDK 25为最新LTS版本(2026年1月发布)，Lombok社区尚未适配
3. 决定移除所有Lombok依赖，手工编写所有getter/setter/constructor方法
4. 涉及修改: 8个实体类(BaseEntity, Patient, Doctor, Department, Appointment, MedicalRecord, Prescription, User)、3个DTO类(R, LoginRequest, LoginResponse)
5. 编译验证通过，所有18个单元测试通过

**经验教训:** 在项目初期应锁定JDK版本为LTS版本(如JDK 21)，避免使用尚未被生态完全支持的最新JDK。

### 难点2: Redis缓存与测试环境冲突
**问题描述:** 患者/医生/病历服务在非测试环境需要Redis缓存支持，但单元测试环境中没有Redis服务，导致Spring容器启动失败(No qualifying bean of type 'RedisConnectionFactory').

**解决过程:**
1. 分析错误: RedisConfig类无条件创建RedisCacheManager Bean,测试环境无Redis
2. 方案一: 使用Embedded Redis — 增加依赖复杂度
3. 方案二: 条件化配置 — 选择使用`@Profile("!test")`排除测试环境
4. 同时需要处理`@EnableCaching`注解: 从Application主类移除，只在RedisConfig中保留
5. 测试环境使用Spring默认的SimpleCacheManager(无需显式配置)

### 难点3: Feign远程调用中的数据一致性
**问题描述:** 预约服务创建预约时需要调用患者服务和医生服务获取信息。如果远程调用失败，需要保证数据一致性。

**解决过程:**
1. 在AppointmentService.create()中进行两次Feign调用获取患者和医生信息
2. 添加明确的异常处理: 远程调用返回null或失败时抛出RuntimeException
3. 使用@Transactional确保本地数据库操作的事务性
4. 在Controller层捕获异常并返回友好的错误信息

### 难点4: 多模块Maven项目的依赖管理
**问题描述:** 9个模块之间存在复杂的依赖关系，需要确保正确的构建顺序和依赖传递。

**解决过程:**
1. 使用父POM统一管理版本(Spring Boot, Spring Cloud, JWT等)
2. 将公共代码抽取到med-common模块
3. 业务服务通过`mvn install`将med-common安装到本地仓库后引用
4. 使用`mvn clean install -DskipTests -T 4`并行构建提高效率

### 难点5: Spring Security与JWT集成
**问题描述:** 需要在Spring Security 6.x(Spring Boot 3.x)中集成JWT认证，同时暴露公开接口(登录/注册/文档)。

**解决过程:**
1. 使用OncePerRequestFilter实现JWT过滤器
2. 配置SecurityFilterChain: 放行/auth/login和/auth/register
3. 使用BCryptPasswordEncoder加密密码
4. 登录成功后返回JWT令牌(有效期24小时)

## 六、启动流程

```bash
# 1. 启动Redis (如无可跳过，服务降级运行)
redis-server

# 2. 编译并启动所有服务
cd med-platform
mvn clean install -DskipTests

# 3. 按顺序启动各服务 (每个在新终端)
# 3a. 注册中心
mvn spring-boot:run -pl med-registry

# 3b. 配置中心
mvn spring-boot:run -pl med-config

# 3c. API网关
mvn spring-boot:run -pl med-gateway

# 3d. 认证服务
mvn spring-boot:run -pl med-auth-service

# 3e. 患者服务
mvn spring-boot:run -pl med-patient-service

# 3f. 医生服务
mvn spring-boot:run -pl med-doctor-service

# 3g. 预约服务
mvn spring-boot:run -pl med-appointment-service

# 3h. 病历服务
mvn spring-boot:run -pl med-record-service

# 4. 访问
# Eureka Dashboard: http://localhost:8761
# API Gateway: http://localhost:8080
# Swagger (直连): http://localhost:8081/doc.html (auth)
```

## 七、演示要点对照

| 序号 | 要求 | 实现方式 |
|------|------|----------|
| 1 | 完善的接口文档 | Knife4j(OpenAPI 3.0)自动生成，62个API |
| 2 | Redis中间件 | 患者/医生/病历服务使用Redis缓存(@Cacheable) |
| 3 | 单元测试 | 18个测试用例，全部通过 |
| 4 | Git提交推送 | git init + commit，文档化push步骤 |
| 5 | 登录认证 | Spring Security + JWT + BCrypt |
| 6 | 注册中心 | Eureka Server (8761端口) |
| 7 | 远程调用 | Feign (AppointmentService → Patient/Doctor) |
| 8 | API网关 | Spring Cloud Gateway (8080端口) |
| 9 | 配置中心 | Spring Cloud Config (8888端口) |
| 10 | 难点与解决 | 5个核心难点详细分析 |
