# 요구사항

- [x] 회원은 일반회원과 관리자로 구분해야 한다.
- [x] 회원 가입일과 수정일이 있어야 한다.
- [x] 회원을 설명할 수 있는 필드가 있어야 한다. 이필드는 길이제한이 없다.

- [X] 회웥과 팀이 있다.
- [X] 회원은 하나의 팀에만 소속될 수 있다.
- [X] 회원과 팀은 다대일 관계이다.


## 매핑 어노테이션

### @Column
컬럼 매핑

#### name
테이블 컬럼명 ( 객체 필드 이름 )

#### insertable, updatable

등록,변경 가능 여부 (TRUE)

#### nullable(DDL)
false 설정 => DDL 생성시 not null 제약 조건 붙는다.

#### unique(DDL)
@Table의 uniqueConstraints와 같음. 한 컬럼에 간단히 유니크 제 약조건을 걸 때 사용

#### columnDefinition(DDL)
DB 컬럼 정보를 직접 줄 수 있다. (Dialect 정보 사용해서)

#### length(DDL)
문자 길이 제약조건, String 타입에만 사용한다.(255)

#### precision, scale(DDL)
BigDecimal 타입에서 사용. precision은 소수점을 포함한 전체 자릿수. scale은 소수의 자릿수
 


### @Temporal
날짜 매핑

- java.util.Date
- java.util.Calendar 사용시


LocalDate, LocalDateTime을 사용할 때는 생략 가능



### @Enumerated 
enum 타입 매핑

#### value

EnumType.ORDINAL - enum 순서 를 저장 (기본값)

EnumType.STRING - enum 이름을 저장 (권장사항)

_EnumType.ORDINAL 로 설정시 enum 순서 바뀌면 다 꼬임_

### @lob 
LOB 데이터 매핑

### Transient 

매핑하지 않음

DB 저장, 조회 X

주로 메모리상에서만 임시로 어떤 값을 보관하고 싶을 때 사용


# 기본 키 매핑 방법

## 직접 할당 
@Id만 사용

## 자동 생성 ( @GeneratedValue )
ex)
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

### IDENTITY
데이터베이스에 위임, MYSQL

보통 트랜잭션 커밋 시점에 INSERT SQL 실행하나 IDENTITY 전략은 EntityManager.persist() 시점에 즉시 INSERT SQL 실행 하고 DB에서 식별자를 조회.
따라서 모아서 날리는 것은 안됨.

### SEQUENCE
데이터베이스 시퀀스 오브젝트 사용, ORACLE

ex)오라클 시퀀스

```java
@Entity
@SequenceGenerator(name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 1)
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

```

_@SequenceGenerator 필요_

#### name
식별자 생성기 이름

#### sequenceName
DB 등록되어 있는 시퀀스 이름

#### initialValue
DDL 생성 시에만 사용됨, 시퀀스 DDL을 생성할 때 처음 1 시작하는 수를 지정한다.

#### allocationSize
시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용됨)

#### catalog, schema
DB catalog, schema 이름




### TABLE
키 생성용 테이블 사용, 모든 DB에서 사용

장점 : 모든 DB에 적용 가능

단점 : 성능

```java
@Entity
@TableGenerator(name = "MEMBER_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR")
private Long id;
```

_@TableGenerator 필요_

#### name
식별자 생성기 이름(필수)

#### table
키생성 테이블명(hibernate_sequences)

#### pkColumnName
시퀀스 컬럼명(sequence_name)

#### valueColumnName
시퀀스 값 컬럼명(next_val)

#### pkColumnValue
초기 값, 마지막으로 생성된 값이 기준이다(0)

#### allocationSize
시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용) (50)

#### catalog, schema
db catalog, schema 이름

#### uniqueConstraint s(DDL)
유니크 제약 조건을 지정


### AUTO
기본 값. 방언에 따라 자동 지정



## 권장하는 식별자 전략
- 기본 키 제약 조건 : null 아님, 유일, 변하면 안됨
- 미래 끝까지 만족하는 자연키 찾기 어려움. 대리키를 사용
- 권장 : Long형 + 대체키 + 키 생성 전략 사용