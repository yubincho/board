


Trouble Shooting

userRepository.findByEmailAndFromSocial 메소드 테스트 시 오류 발생
오류 내용 : failed to lazily initialize a collection of role:
com.example.board1.entity.User.roleSet: could not initialize proxy - no Session

원인 : failed to lazily initialize a collection of role 에러는
Hibernate에서 Lazy Initialization Exception이라고 불리는 문제다.
이는 JPA 엔티티의 LAZY로딩된 컬렉션이 세션 밖에서 초기화되려고 할 때 발생한다.

해결방법
1. FetchType.EAGER로 변경 -> 성능에 영향있을 수 있음
2. 테스트 시 세션을 유지
3. Fetch Join 사용


@ElementCollection(fetch = FetchType.EAGER)
@Builder.Default
private Set<UserRole> roleSet = new HashSet<>();
-> 해결은 됐으나 사용 안함

findByEmailAndFromSocial 메소드에 fetch join으로 시도했으나 해결 안됨
또, 프로젝트 실행시 추가 에러 발생하였음
( @Query("SELECT u FROM User u LEFT JOIN FETCH u.roleSet
WHERE u.email = :email AND u.fromSocial = :fromSocial")

결국, 테스트 시 세션을 유지하는 방법으로 해결함
    @Transactional // 테스트 시 세션을 유지
    @Test
    void 이메일과소셜로_찾기() { ... }



