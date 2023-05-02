# backend_template

## DB
mysql 세팅
docker 설정 명령어
- docker pull mariadb
- docker run -p 3306:3306 --name lucete -e MARIADB_ROOT_PASSWORD=lucete1347 -d mariadb

mysql docker container에 접속할 때 명령어
- docker exec -it lucete /bin/bash
접속 후
mysql -u root -p
입력 후 비밀번호 lucete1347 입력하면 mysql로 접속


```
create database user;

CREATE TABLE user (name VARCHAR(32) not null, phone VARCHAR(32),
       team VARCHAR(20), year INT not null, active tinyint(1) not null default 1, created DATETIME not null, updated DATETIME not null, primary key (name));
```
이런 식으로 데이터베이스 생성 후 테이블 생성하는 sql 문 미리 작성해둘 것

## backend
프로젝트 구성
- model : mysql의 테이블 칼럼 내용 작성하는 파일
- repository : getter,setter 필수, DB같은 외부 I/O 작업 처리
- controller : rest api 작성하는 파일, 웹 요청과 응답 처리
- service : 내부 자바 로직처리하는 곳
요거 파일 관리할 때 @어노테이션 꼭 잘 해주기! 안그럼 인식 못함
