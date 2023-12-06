package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional //데이터 변경의 경우 꼭 트랜잭션 안에 있어야 함
@RequiredArgsConstructor//LOMBOK final이 붙은 클래스의 생성자를 만들어서 자동 주입 시켜줌
public class MemberService {

    private final MemberRepository memberRepository; //한번 주입되면 바뀌지 않음으로 final

    //@Autowired 최신 스프링 부턴 없어도 생성자가 하나라면 알아서 주입시켜줌
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //회원 가입

    @Transactional //데이터 변경의 경우 꼭 트랜잭션 안에 있어야 함
    public Long join(Member member){

        validateDuplicateMember(member);//중복 회원 검사
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPION
        //멀티 스레드의 경우 문제가 될수 있음으로 DB멤버 네임을 유니크 제약 조건으로 잡는게 정답이다
        List<Member> findMemmbers = memberRepository.findByName(member.getName());
        if(!findMemmbers.isEmpty()){
            throw  new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회

    @Transactional(readOnly = true) //(readOnly = true)의 경우 (출력만 하는 경우) Jpa가 최적화 해줌
    public  List<Member> findMembers(){
        return  memberRepository.findAll();
    }


    @Transactional(readOnly = true)
    public  Member findOne(Long memberid){
        return  memberRepository.findOne(memberid);
    }




}
