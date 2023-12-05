package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class) //Junit실행시 스프링과 같이 실행된다는 서술
@SpringBootTest // 스프링을 띄운상태 컨테이너 안에서 테스트를 하겠다는 의미
@Transactional // 테스트 클래스에서 사용 시 테스트가 끝나면 롤백
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("KIM");

        //when
        Long saveId = memberService.join(member);


        //then
        assertEquals(member, memberRepository.findOne(saveId));
     }

     @Test(expected = IllegalStateException.class) //try catch가 아닌 해당에러가 튀어나면 성공
     public void 중복_회원_가입() throws Exception{
         //given
         Member member1 = new Member();
         member1.setName("KIM");

         Member member2 = new Member();
         member2.setName("KIM");
         //when
         memberService.join(member1);
         memberService.join(member2);


         //then
         fail("예외가 발생해야 한다");

      }

}