# Review-and-Mileage-Service
트리플 서버개발 사전 과제 2. 트리플 여행자 클럽 마일리지 서비스

## 🏷 프로젝트 개요
트리플 사용자들이 여행 장소에 리뷰를 작성할 때 포인트를 부여하고, 전체/개인에 대한 포인트 부여 히스토리와 개인별 누적 포인트를 관리하는 서비스.  

## 🏷 개발 환경
- **Java 8**
- **Spring Boot 2.5.4**
- **Maven**
- **MyBatis**
- DB: **H2** (Mode: MySQL)

## 🏷 빌드 및 실행 방법
### Maven Command
```
1. mvn package -DskipTests
2. java -jar target/Review-and-Mileage-Service-0.0.1-SNAPSHOT.jar
```
> http port 8080 으로 serve

## 🏷 DB 관련
- SQL 스키마는 `src/main/resources/schema.sql` 참고 [링크](https://github.com/hanwix2/Review-and-Mileage-Service/blob/main/src/main/resources/schema.sql)
- Embedded 모드이므로 서버 재실행시 모든 데이터가 초기화됨
- 서버 실행 후 추가적인 설치과정 없이 H2 콘솔(url 접속)로 DB 확인 가능
  > http://localhost:8080/h2-console
  ```
  <embedded>
  url=jdbc:h2:mem:testdb
  username=sa
  password=
  driver-class-name=org.h2.Driver
  ```
- ERD 설계:
![image](https://user-images.githubusercontent.com/70831990/130379593-73ae6dfc-5c7a-49ed-8978-fbcf9a7cea16.png)
url: https://aquerytool.com/aquerymain/index/?rurl=07823631-9441-4141-bac1-bdd858008936  
pw: k5w87w

- 서버 실행시 기본으로 입력되는 레코드:  
![image](https://user-images.githubusercontent.com/70831990/130379841-afcc93e7-c350-465a-a88c-84f6bb0a9dbb.png)
![image](https://user-images.githubusercontent.com/70831990/130379879-dc20f77f-0a5f-4354-9777-ae8145de756a.png)  

## 🏷 유의 사항
- id 값은 숫자로 대체 (RequestBody(JSON)로 넘기는 값은 String으로 입력)
  > AutoIncrement 적용  
  > String -> int 형변환 오류에 대한 처리 생략  

- 로그인, 회원가입 등 user 관련 api는 생략 
  > 로그인을 하지 않고 전달된 userId로 유저 구분  
  > authentication 기능 생략

- 리뷰 관련 추가, 수정, 삭제 API를 하나로 사용함에 따른 유의점
  - ADD, MOD일 경우 - attachedPhotoId 는 새로운 사진을 등록하는 것이므로 id 값이 아닌 파일명으로 값을 받음
  - 파일(사진) 등록은 테스트의 용이함을 위해 임의의 파일명(String값)을 저장하는 것으로 대체. 그리고 requestBody의 attachedPhotoIds 값은 파일 id 값이 아닌 파일명으로 대체
  - RequestBody에서 api에 따라 필수적이지 않은 요소는 빈문자열 가능
  - 모든 값은 빈문자열("")이 가능하지만 NULL은 불가능

- 리뷰 레코드 수가 많지 않으므로 리뷰 조회시 페이징은 적용하지 않음

## 🏷 API 요청 예시

<br> 

<아래 값만 사용가능>  
**"type": "REVIEW"**  
**"action": "ADD" / "MOD" / "DELETE"**  
**userId 값 범위: 1 ~ 3**  
**placeId 값 범위: 1 ~ 4**  

<br>

### 1. 리뷰 작성(ADD)
- Request: [POST] http://localhost:8080/events
- Body:
```
{
    "type": "REVIEW",
    "action": "ADD",
    "reviewId": "",
    "content": "좋아요!",
    "attachedPhotoIds": ["image01"],
    "userId": "1",
    "placeId": "1"
}
```
- Response: status 200 (OK)

### 2. 리뷰 수정(MOD)
- Request: [POST] http://localhost:8080/events
- Body:
```
{
    "type": "REVIEW",
    "action": "MOD",
    "reviewId": "1",
    "content": "좋아요!좋아요!좋아요!",
    "attachedPhotoIds": ["image02", "image03"],
    "userId": "1",
    "placeId": "1"
}
```
- Response: status 200 (OK)

### 3. 리뷰 삭제(DELETE)
- Request: [POST] http://localhost:8080/events
- Body:
```
{
    "type": "REVIEW",
    "action": "DELETE",
    "reviewId": "1",
    "content": "",
    "attachedPhotoIds": ["image02", "image03"],
    "userId": "1",
    "placeId": "1"
}
```
- Response: status 200 (OK)

### 4. 마일리지 적립 내역 조회
- Request: [GET] http://localhost:8080/mileages?user_id={id값}
- Response(userId 1번의 마일리지 적립 내역):
```
{
    "user_id": 1,
    "mileageRecordings": [
        {
            "id": 1,
            "review_id": 1,
            "content": "리뷰 내용 작성",
            "amount": 1,
            "time": "2021-08-23 11:16:17.664267"
        },
        {
            "id": 2,
            "review_id": 1,
            "content": "리뷰 사진 추가",
            "amount": 1,
            "time": "2021-08-23 11:16:17.664267"
        },
        {
            "id": 3,
            "review_id": 1,
            "content": "첫번째 리뷰 작성",
            "amount": 1,
            "time": "2021-08-23 11:16:17.664267"
        },
        {
            "id": 4,
            "review_id": 1,
            "content": "리뷰 삭제",
            "amount": -3,
            "time": "2021-08-23 11:21:40.94006"
        }
    ],
    "totalMileage": 0
}
```

### 5. 리뷰 조회 1 (장소별 조회)
- Request: [GET] http://localhost:8080/reviews?place_id={id값}
- Response(placeId 2번인 장소의 리뷰):
```
[
    {
        "review_id": 2,
        "place_id": 2,
        "user_id": 1,
        "content": "좋아요!",
        "create_time": "2021-08-23 11:31:02.567284",
        "update_time": "2021-08-23 11:31:02.567284"
    },
    {
        "review_id": 3,
        "place_id": 2,
        "user_id": 2,
        "content": "좋아요!",
        "create_time": "2021-08-23 11:31:06.168433",
        "update_time": "2021-08-23 11:31:06.168433"
    }
]
```

### 6. 리뷰 조회 2 (모든 장소의 리뷰 조회)
- Request: [GET] http://localhost:8080/reviews
- Response:
```
[
    {
        "review_id": 1,
        "place_id": 1,
        "user_id": 1,
        "content": "좋아요!",
        "create_time": "2021-08-23 11:30:54.107592",
        "update_time": "2021-08-23 11:30:54.107592"
    },
    {
        "review_id": 2,
        "place_id": 2,
        "user_id": 1,
        "content": "좋아요!",
        "create_time": "2021-08-23 11:31:02.567284",
        "update_time": "2021-08-23 11:31:02.567284"
    },
    {
        "review_id": 3,
        "place_id": 2,
        "user_id": 2,
        "content": "좋아요!",
        "create_time": "2021-08-23 11:31:06.168433",
        "update_time": "2021-08-23 11:31:06.168433"
    },
    {
        "review_id": 4,
        "place_id": 4,
        "user_id": 3,
        "content": "또 가고싶어요!",
        "create_time": "2021-08-23 11:33:14.792736",
        "update_time": "2021-08-23 11:33:14.792736"
    }
]
```

## 🏷 예외 처리
- `action` 값이 [ADD, MOD, DELETE] 이외의 요청이 올 경우 - **InvalidEventActionException**
- User(id)가 존재하지 않는 경우 - **UserNotFoundException**
- 리뷰 수정 또는 삭제 요청시 RequestBody로 보낸 리뷰 정보가 본인이 적은 리뷰와 상이할 경우 - **ReviewNotFoundException**
- 동일한 유저가 특정 장소에 두개 이상의 리뷰를 작성하려고 하는 경우 - **ReviewDuplicateException**
