package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne //다대일 관계
    @JoinColumn(name = "member_id") //해당 외례 키로 조인 함
    private Member member;


    @OneToMany(mappedBy = "order")
    private List<OrederItem> orderItems = new ArrayList<>();

    @OneToOne//FK를 어디에 두든지 상관없지만 많이 액세스하는 곳에 두는게 편함 (Order)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문 시간 : LocalDateTime 현재 시간을 시분까지 넣을수있는 클래스

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [ORDER , CANCEL]
}
