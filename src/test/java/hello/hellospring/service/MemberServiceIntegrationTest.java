package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    MemberService service;
    @Autowired
    MemberRepository repository;

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("spring100");

        // when
        Long saveId = service.join(member);

        // then
        Member findMember = repository.findById(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    // 중복 예외 플로우
    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        service.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> service.join(member2));
        // assertThrows: 예외가 발생하는지 확인하는 메소드

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 이름입니다.");
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}