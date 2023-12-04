package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id; //객체 (엔티티 식별자)는 필드로 구분이 가능하지만 DB에서는 불가능 하기에 테이블 명_id로 해주는게 좋다

    @ManyToOne(fetch = LAZY) //다대일 관계 : fetch = LAZY 를 통해 지연 로딩이 필수이다
    @JoinColumn(name = "member_id") //해당 외례 키로 조인 함
    private Member member;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //FK를 어디에 두든지 상관없지만 많이 액세스하는 곳에 두는게 편함 (Order) : fetch = LAZY 를 통해 지연 로딩이 필수이다
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //order_date <- 스프링에서 자동으로 바꿔줌
    private LocalDateTime orderDate; //주문 시간 : LocalDateTime 현재 시간을 시분까지 넣을수있는 클래스

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [ORDER , CANCEL]

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
