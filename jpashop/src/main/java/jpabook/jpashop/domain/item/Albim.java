package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("A") //@DiscriminatorColumn(name = "dtype") dtype == A일때
@Getter
@Setter
public class Albim extends Item{
    private String artist;
    private String etc;
}
