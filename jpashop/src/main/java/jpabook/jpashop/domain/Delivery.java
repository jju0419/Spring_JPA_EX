package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private long id;

    @OneToOne(mappedBy = "delivery")
    private  Order order;

    @Embedded
    private Address address;

    //EnumType.ORDINAL : enum 순서 값을 DB에 저장, EnumType.STRING : enum 이름을 DB에 저장
    @Enumerated(EnumType.STRING)
    public DeliveryStatus status;//READY , COMP
}
