
# 게시물 관리 API (Hexagonal Architecture)

이 프로젝트는 **헥사고날 아키텍처**를 기반으로 설계된 게시물 관리 API입니다. 해당 아키텍처는 유연성과 확장성을 제공하며, 도메인 중심의 설계를 중점으로 합니다.
## common-lib 프로젝트 필수!!

---

## 📐 아키텍처 개요

### 1. **어플리케이션 헥사곤**
- **포트 (Port)**
  - **Input Ports**: 애플리케이션의 유스케이스를 구현 (Service 계층).
  - **Output Ports**: 외부 시스템 연동 (e.g., JPA Repository, 외부 API 호출 등)을 위한 인터페이스.
- **Usecase**
  - Input Adapter (Controller)에서 호출될 비즈니스 로직을 정의하는 인터페이스.

### 2. **도메인 헥사곤**
- 비즈니스 로직을 처리하는 핵심 영역.
- 모든 도메인 로직은 이 레이어에서 처리되며, 주요 비즈니스 엔티티가 포함됩니다.

### 3. **프레임워크 헥사곤**
- 외부 시스템과의 통합 및 프레임워크 의존성이 포함된 영역.
- 예: 
  - Controller
  - 데이터베이스 연동 (H2)
  - 분산 스트리밍 플랫폼 (Kafka)
  - 캐시 시스템 (Redis)
---

## 고민 내용

### Viewcount 업데이트 시점
1. **즉시 업데이트의 문제점**:
   - Redis 분산 락(lock)을 통한 즉시 업데이트는 **대규모 동시 접속 환경**에서 다음과 같은 문제가 발생할 수 있습니다:
     1. 트랜잭션 대기시간 초과로 인한 예외 발생.
     2. Redis 서버에 과도한 락 요청 발생 (e.g., 1억 번의 요청).
2. **해결 방안**:
   - 조회수는 Redis `increment` 명령을 통해 적립되고, **1분마다 스케줄러**가 데이터를 업데이트하는 방식으로 처리됩니다.
3. **추가 고려 사항**:
   - Redis 데이터 손실 방지를 위해 동기화된 **백업 서버**를 사용하여 데이터를 관리합니다.

---

## 📋 API 명세

### 1. **관리자용 API**
- 게시물 조회 시 `PREFIX(BOARD_VIEWCOUNT:) + 게시물 PK` 형식으로 Redis에 등록됩니다.
- 조회수는 **1분 단위 스케줄러**를 통해 업데이트되며, Redis 키 TTL은 **1일**로 설정됩니다.

- **Base Path**: `/boards`
- **엔드포인트**:
  - **게시물 생성**: `POST /board//boards`
  - **게시물 조회**: `GET /board//boards/{boardId}`
  - **게시물 수정**: `PUT /board//boards/{boardId}`
  - **게시물 삭제**: `DELETE /board//boards/{boardId}`
  - **사용자 게시물 전체 삭제**: `POST /board/boards/user/{userId}`
  - **게시물 목록 조회**: `GET /board/boards/list`

### 2. **사용자용 API**
- 게시물 조회 시 `PREFIX(BOARD_VIEWCOUNT:) + 게시물 PK` 형식으로 Redis에 등록됩니다.
- 조회수는 **1분 단위 스케줄러**를 통해 업데이트되며, Redis 키 TTL은 **1일**로 설정됩니다.
- **Base Path**: `/board/v1/boards`
- **엔드포인트**:
  - **게시물 생성**: `POST /board/v1/boards`
  - **게시물 조회**: `GET /board/v1/boards/{boardId}`
  - **게시물 수정**: `PUT /board/v1/boards/{boardId}`
  - **게시물 삭제**: `DELETE /board/v1/boards/{boardId}`
  - **사용자 게시물 전체 삭제**: `POST /board/v1/boards/user`
  - **게시물 목록 조회**: `GET /board/v1/boards/list`

---

## 📋 DTO 정의

### 1. **BoardCreate**
- **필드**:
  - `title`: 게시물 제목.
  - `content`: 게시물 내용.

### 2. **BoardDto**
- **필드**:
  - `id`: 게시물 ID.
  - `title`: 게시물 제목.
  - `content`: 게시물 내용.
  - `userId`: 작성자 ID.
  - `viewCount`: 조회수.

### 3. **BoardResponse**
- **필드**:
  - `id`: 게시물 ID.
  - `title`: 게시물 제목.
  - `content`: 게시물 내용.
  - `userId`: 작성자 ID.
  - `viewCount`: 조회수.

### 4. **BoardSearchDto (Request Param)**
- **필드**:
  - `title`: 제목.
  - `content`: 본문 내용 (LIKE 검색).
  - `userId`: 사용자 아이디.
  - `pageNo` (기본값: 1): 페이지 번호.
  - `pageSize` (기본값: 10): 페이지 크기.

### 5. **BoardUpdate**
- **필드**:
  - `title`: 게시물 제목.
  - `content`: 게시물 내용.

---

## 🛠️ 설치 및 실행

### 1. 의존성 설치
```bash
./gradlew build
```

### 2. 애플리케이션 실행
```bash
./gradlew bootRun
```

---

## 🧑‍💻 필수 설치 사항
1. **Redis**:
   - Redis를 설치하고 `localhost:6379`에서 실행 중이어야 합니다.
   - 설치 방법:
     ```bash
     # Ubuntu
     sudo apt update
     sudo apt install redis
     sudo systemctl start redis

     # macOS (Homebrew)
     brew install redis
     brew services start redis
     ```

2. **H2 데이터베이스**:
   - 애플리케이션 실행 시 자동으로 설정됩니다.

---

## 📚 기술 스택
- **언어**: Java 21 (Temurin JDK 21)
- **프레임워크**: Spring Boot
- **데이터베이스**: H2 (인메모리 데이터베이스)
- **인증 방식**: JWT
- **캐싱 시스템**: Redis (localhost:6379)
