package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private final MemberRepository repository;

    // 외부에서 넣도록 설정
    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    // 회원가입 (중복 이름 불가)
    public Long join(Member member) {
        validateDuplicateMember(member);   // 중복 이름 체크
        repository.save(member);
        return  member.getId();
    }

    // ctrl + alt + shift + T (메소드로 빼는 단축키)
    private void validateDuplicateMember(Member member) {
        repository.findByName(member.getName())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 이름입니다.");
            });
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return repository.findAll();
    }

    // id로 조회
    public Optional<Member> findOne(Long id) {
        return repository.findById(id);
    }
}
