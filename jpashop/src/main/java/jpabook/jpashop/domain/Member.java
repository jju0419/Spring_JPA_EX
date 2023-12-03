package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "mamber_id")
    private long id;

    private String name;

    @Embedded //내장 타입 받는 경우 사용
    private Address address;

    @OneToMany(mappedBy = "member") //일대다 관계 (Order 안에 있는 FK : member에 의해 매핑되어 있음을 알림)
    private List<Order> orders = new ArrayList<>();

}
