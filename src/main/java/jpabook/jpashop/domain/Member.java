package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Member extends BaseEntity {

    //    @Id @GeneratedValue( strategy = GenerationType.AUTO) //default 값 같다.
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    //    @OneToMany(mappedBy = "ORDER_ID") // 이렇게 생각했지만
    @OneToMany(mappedBy = "member")
    private List<Order> Orders = new ArrayList<>();

    private String name;

    @Embedded
    private Address address;


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

    public List<Order> getOrders() {
        return Orders;
    }

    public void setOrders(List<Order> orders) {
        Orders = orders;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
