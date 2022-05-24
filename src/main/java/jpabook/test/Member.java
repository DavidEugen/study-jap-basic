package jpabook.test;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity //JPA 가 관리하는 객체라는 것을 알려줌
//@Table(name = "Member") //table 명이 다른 경우 매핑 가능
public class Member {

    @Id // DB의 PK와 매핑
    private Long id;

    //@Column(name ="name") // column명이 다를 경우 매핑 가능
    private String name;

    public Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
