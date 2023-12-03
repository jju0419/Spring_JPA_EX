package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
//Inheritance : 상속관계 매핑 ->객체의 상속 구조와 DB의 슈퍼타입 서브타입 관계를 매핑
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//SINGLE_TABLE전략 부모테이블에 자식 테이블을 합침
@DiscriminatorColumn(name = "dtype") //dtype에 따라 사용하는 자식 테이블 컬럼이 달라진다
@Getter @Setter
public abstract class Item {//영화 책 앨범으로 나누어야 하기때문에 추상 클래스로 지정

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private long id;

    private String name;
    private int price;
    private int stockQuantity;
}
