# Admin API 전체 Phase 테스트 가이드

## 🚀 사전 준비

### 1. 서버 실행 확인
- Spring Boot 애플리케이션이 `http://localhost:8080`에서 실행 중이어야 합니다.

### 2. Redis 실행 확인
```bash
docker ps --filter "name=redis-local"
```
Redis가 실행 중이 아니면:
```bash
docker start redis-local
# 또는
docker run --name redis-local -p 6379:6379 -d redis:latest
```

### 3. MySQL 실행 확인
- MySQL이 실행 중이어야 합니다.
- 데이터베이스에 테스트 데이터가 있어야 합니다.

---

## 🔐 인증 설정 (필수)

### 1. Google OAuth 로그인
브라우저에서 다음 URL 접속:
```
http://localhost:8080/oauth2/authorization/google
```
Google 계정으로 로그인 후 리다이렉트됩니다.

### 2. JWT 토큰 발급
1. Swagger UI 접속: `http://localhost:8080/`
2. `POST /api/v1/auth/token` 실행
3. 응답에서 `token` 값 복사

### 3. ADMIN 권한 확인
**중요**: Admin API를 사용하려면 ADMIN 권한이 필요합니다.

MySQL에서 확인:
```sql
SELECT * FROM member WHERE email = 'your-email@gmail.com';
```

권한이 ADMIN이 아니면:
```sql
UPDATE member SET role = 'ADMIN' WHERE email = 'your-email@gmail.com';
```
그 후 다시 OAuth 로그인 → 토큰 발급

### 4. Swagger UI에 토큰 설정
1. Swagger UI 우측 상단 **"Authorize"** 버튼 클릭
2. `Bearer {token}` 형식으로 입력
3. **"Authorize"** 클릭

---

## ✅ Phase 1: Member 관리 API 테스트

### 1.1 가입 사용자 목록 조회
```
GET /api/v1/admin/users
```

**예상 응답:**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "email": "user@example.com",
      "role": "PENDING",
      "socialType": "GOOGLE",
      "createdAt": "2024-01-01T00:00:00",
      "updatedAt": "2024-01-01T00:00:00"
    }
  ],
  "error": null
}
```

### 1.2 멤버 승인
```
PATCH /api/v1/admin/users/{userId}/approve
```

**파라미터:**
- `userId`: 승인할 회원 ID (예: 1)

**확인:**
- 승인 전: `role = "PENDING"`
- 승인 후: `role = "MEMBER"`

### 1.3 멤버 권한 수정
```
PATCH /api/v1/admin/users/{userId}
```

**Request Body:**
```json
{
  "role": "CORE"
}
```

**가능한 role 값:**
- `ADMIN`, `LEAD`, `CORE`, `MEMBER`, `PENDING`

---

## ✅ Phase 2: Project 관리 API 테스트

### 2.1 프로젝트 등록
```
POST /api/v1/admin/projects
```

**Request Body:**
```json
{
  "projectName": "GDGoC 웹사이트",
  "generation": 1,
  "description": "GDGoC 공식 웹사이트 개발 프로젝트입니다.",
  "imageUrl": "https://example.com/image.jpg",
  "activityId": 1
}
```

**주의:** `activityId`는 데이터베이스에 존재하는 Activity ID여야 합니다.

**예상 응답:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "projectName": "GDGoC 웹사이트",
    "generation": 1,
    "description": "GDGoC 공식 웹사이트 개발 프로젝트입니다.",
    "imageUrl": "https://example.com/image.jpg",
    "activityId": 1,
    "createdAt": "2024-01-01T00:00:00",
    "updatedAt": "2024-01-01T00:00:00"
  },
  "error": null
}
```

### 2.2 프로젝트 수정
```
PATCH /api/v1/admin/projects/{projectId}
```

**Request Body (모든 필드 선택):**
```json
{
  "projectName": "GDGoC 웹사이트 v2",
  "generation": 2,
  "description": "업데이트된 설명",
  "imageUrl": "https://example.com/new-image.jpg",
  "activityId": 1
}
```

**Request Body (일부 필드만 수정):**
```json
{
  "projectName": "새로운 프로젝트명"
}
```

### 2.3 프로젝트 삭제
```
DELETE /api/v1/admin/projects/{projectId}
```

**파라미터:**
- `projectId`: 삭제할 프로젝트 ID

**확인:**
- 삭제 후 `GET /api/v1/admin/projects`로 목록에서 제거되었는지 확인

---

## ✅ Phase 3: Part 관리 API 테스트

### 3.1 파트 목록 조회
```
GET /api/v1/admin/parts
```

**예상 응답:**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "partName": "FE",
      "description": "프론트엔드 개발 파트입니다.",
      "createdAt": "2024-01-01T00:00:00",
      "updatedAt": "2024-01-01T00:00:00"
    }
  ],
  "error": null
}
```

### 3.2 파트 수정
```
PATCH /api/v1/admin/parts/{partId}
```

**Request Body (모든 필드 선택):**
```json
{
  "partName": "Frontend",
  "description": "프론트엔드 개발 파트입니다. React, Vue 등을 사용합니다."
}
```

**Request Body (일부 필드만 수정):**
```json
{
  "description": "업데이트된 설명"
}
```

---

## ✅ Phase 4: Discord 알림 로그 조회 API 테스트

### 4.1 Discord 알림 로그 조회
```
GET /api/v1/admin/notifications/discord
```

**예상 응답:**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "eventType": "MEMBER_APPROVED",
      "isSuccess": true,
      "createdAt": "2024-01-01T00:00:00",
      "memberId": 1,
      "memberEmail": "user@example.com"
    }
  ],
  "error": null
}
```

**확인 사항:**
- 로그가 최신순으로 정렬되어 있는지 확인
- `isSuccess` 필드가 올바르게 표시되는지 확인

---

## 🔍 에러 확인 및 해결

### 권한 없음 (403 Forbidden)
**원인:**
- JWT 토큰이 없거나
- ADMIN 권한이 아닌 경우

**해결:**
- ADMIN 권한을 가진 사용자로 로그인
- 올바른 JWT 토큰 사용

### 회원/프로젝트/파트를 찾을 수 없음 (404 Not Found)
**원인:**
- 존재하지 않는 ID 사용

**해결:**
- 올바른 ID 사용
- 먼저 목록 조회 API로 존재하는 ID 확인

### 활동을 찾을 수 없음 (404 Not Found)
**원인:**
- 프로젝트 등록 시 존재하지 않는 `activityId` 사용

**해결:**
- MySQL에서 Activity 테이블 확인:
  ```sql
  SELECT * FROM activity;
  ```
- 존재하는 `activityId` 사용

### 승인 대기 상태가 아님
**원인:**
- `PENDING` 상태가 아닌 회원을 승인하려고 할 때

**해결:**
- `PENDING` 상태인 회원만 승인 가능

---

## 📋 전체 테스트 체크리스트

### Phase 1: Member 관리
- [ ] `GET /api/v1/admin/users` - 목록 조회 성공
- [ ] `PATCH /api/v1/admin/users/{userId}/approve` - 승인 성공
- [ ] `PATCH /api/v1/admin/users/{userId}` - 권한 수정 성공

### Phase 2: Project 관리
- [ ] `POST /api/v1/admin/projects` - 프로젝트 등록 성공
- [ ] `PATCH /api/v1/admin/projects/{projectId}` - 프로젝트 수정 성공
- [ ] `DELETE /api/v1/admin/projects/{projectId}` - 프로젝트 삭제 성공

### Phase 3: Part 관리
- [ ] `GET /api/v1/admin/parts` - 파트 목록 조회 성공
- [ ] `PATCH /api/v1/admin/parts/{partId}` - 파트 수정 성공

### Phase 4: Discord 알림 로그
- [ ] `GET /api/v1/admin/notifications/discord` - 로그 조회 성공
- [ ] 최신순 정렬 확인

### 보안 테스트
- [ ] ADMIN이 아닌 사용자로 접근 시 403 에러 확인
- [ ] 토큰 없이 접근 시 401 에러 확인

---

## 💡 테스트 데이터 준비 (MySQL)

```sql
-- Activity 생성 (프로젝트 등록 테스트용)
INSERT INTO activity (activity_name, created_at, updated_at)
VALUES ('2024년 활동', NOW(), NOW());

-- PENDING 상태 회원 생성 (승인 테스트용)
INSERT INTO member (role, social_type, social_id, email, created_at, updated_at)
VALUES ('PENDING', 'GOOGLE', 'test123', 'test@example.com', NOW(), NOW());

-- ADMIN 권한 회원 확인
SELECT * FROM member WHERE role = 'ADMIN';
```

---

## 🎯 테스트 순서 권장사항

1. **인증 설정** (OAuth 로그인 → 토큰 발급 → Swagger UI에 설정)
2. **Phase 1 테스트** (Member 관리)
3. **Phase 2 테스트** (Project 관리 - Activity 데이터 필요)
4. **Phase 3 테스트** (Part 관리)
5. **Phase 4 테스트** (Discord 알림 로그)
6. **보안 테스트** (권한 체크)


