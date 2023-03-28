package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되지않음(멀티 쓰레드), 실제로는 ConcurrentHashMap, AtomicLong 사용 고려 <--둘다 동기화(sychronize 되어있음)
 */
public class MemberRepository {
    private Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();    //싱글톤

    public static MemberRepository getInstance(){
        return instance;
    }
    private MemberRepository(){
    }
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }
    public Member findById(Long id){
        return store.get(id);
    }
    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }
    public void clearStore(){
        store.clear();
    }

}
