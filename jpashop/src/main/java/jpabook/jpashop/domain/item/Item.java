package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
//Inheritance : 상속관계 매핑 ->객체의 상속 구조와 DB의 슈퍼타입 서브타입 관계를 매핑
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//SINGLE_TABLE전략 부모테이블에 자식 테이블을 합침
@DiscriminatorColumn(name = "dtype") //dtype에 따라 사용하는 자식 테이블 컬럼이 달라진다
@Getter @Setter
public abstract class Item {//영화 책 앨범으로 나누어야 하기때문에 추상 클래스로 지정

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;


    @ManyToMany(mappedBy = "items") //ManyToMany 실무에선 추천되지 않음
    private List<Category> categories = new ArrayList<>();

    //==비지니스 로직==//
    //stockQuantity를 가지고있는 이곳에서 핵심 비지니스 로직을 사용하는것이 객체지향적으로 좋다
    //stockQuantity를 직접 컨트롤 해야 함으로
    /**
     * Stock 증가
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**
     * Stock 감소
     */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock"); //Exception을 직접 작성
        }
        this.stockQuantity =restStock;
    }


}
