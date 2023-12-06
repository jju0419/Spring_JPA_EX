package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)//createOrderItem가 아닌 다른 생성자 금지
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id; //객체 (엔티티 식별자)는 필드로 구분이 가능하지만 DB에서는 불가능 하기에 테이블 명_id로 해주는게 좋다

    @ManyToOne(fetch = LAZY) //다대일 관계 : fetch = LAZY 를 통해 지연 로딩이 필수이다
    @JoinColumn(name = "member_id") //해당 외례 키로 조인 함
    private Member member;

    //cascade = CascadeType.ALL : Order클래스가 저장될때 같이 persist해줌 = Repository 필요없단 뜻
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

    //==생성 메서드==//

    //복잡한 생성은 이렇게 생성 메스드를 만들어 두면 다음번 유지보수때 용이
    public static Order createOrder(Member member,Delivery delivery, OrderItem... orderItems)
    {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비지니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw  new IllegalStateException("이미 배송된 상품은 취소가 불가능 합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);//트랜잭션 커밋 시점에 변경 감지(Dirty Checking)이 동작해서 데이터베이스에 UPDATE SQL 실행

        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

    //==조회 로직==//

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
//        int totalPrice = 0;
//        for(OrderItem orderItem : orderItems){
//            totalPrice += orderItem.getTotalPrice();
//        }
//        return totalPrice;
        //위 식을 람다와 스트림으로
    }
}
