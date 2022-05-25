package jpabook.test;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    // 아무 반응 없음 => transaction 필요
//    public static void main(String[] args) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//
//        EntityManager em = emf.createEntityManager();
//
//        //code
//        Member member = new Member();
//        member.setId(1L);
//        member.setName("HelloA");
//        em.persist(member);
//
//        em.close();
//        emf.close();
//
//
//    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        //Transaction 안에서 실행되어야 한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //code
//            insert(em);
//            find(em);
//            update(em);
//            directlyUseQuery(em);
//            lookOverCache(em);
//            firstCache(em);
//            transactionalWriteBehind(em);//쓰기 지연
//            dirtyChecking(em);//변경 감지
            insertTeam(em);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }

    private static void insertTeam(EntityManager em) {
        Team teamA = new Team();
        teamA.setName("TeamA");
        em.persist(teamA); // PK 값 획득

        Member member = new Member();
        member.setName("MemberA");
        member.setTeam(teamA);
        em.persist(member);

        //<----만약 DB 에서 쿼리 가져오는 것 보고 싶으면
//        em.flush(); // 영속성 컨텍스트에 있는것을 DB에 날려서 동기화 시키고
//        em.clear(); // 초기화 한다.
        //---->

        // 영속성에서 보는 방뻐
        Member findMember = em.find(Member.class, member.getId());
        Team findTeam = findMember.getTeam();
        System.out.println("findTeam.getName() = " + findTeam.getName());



    }

    private static void dirtyChecking(EntityManager em) {
        Member member = em.find(Member.class, 112L);
        member.setName("changeB");

        //em.persist(member); // 요론것들이 있어야 할 것 같은데...
        //em.update(member); //

        System.out.println("============");
    }

    private static void transactionalWriteBehind(EntityManager em) {
        Member memberA = new Member(111L, "A");
        Member memberB = new Member(112L, "B");

        em.persist(memberA);
        em.persist(memberB);

        System.out.println("=================");

    }

    private static void firstCache(EntityManager em) {
        Member findMember = em.find(Member.class, 101L);
        Member findMember2 = em.find(Member.class, 101L);

        System.out.println("result = " + (findMember == findMember2));
    }

    private static void lookOverCache(EntityManager em) {
        //비영속
        Member member = new Member();
//        member.setId(102L);
//        member.setName("HelloJPA2");

        //영속
        System.out.println("-----Before------");
        em.persist(member);
        System.out.println("-----After------");

        Member findMember = em.find(Member.class, 102L);

        System.out.println("findMember.getId() = " + findMember.getId());
        System.out.println("findMember.getName() = " + findMember.getName());

    }

    private static void directlyUseQuery(EntityManager em) {
        List<Member> resultList = em.createQuery("select m from Member as m", Member.class)
                .setFirstResult(0) //paging 처음
                .setMaxResults(3) //paging 마지막
                .getResultList();//조회

        for (Member member: resultList) {
            System.out.println("member.id = " + member.getId());
            System.out.println("member.name = " + member.getName());
        }
    }

    private static void update(EntityManager em) {
        Member findMember = em.find(Member.class, 1L);
        findMember.setName("newName");

        //em.persist(findMember);// 를 써야 할 것 같지만, JPA은 자바 컬렉션을 다루듯이 작업해 준다.
    }

    private static void find(EntityManager em) {
        Member findMember = em.find(Member.class, 1L);
        System.out.println("findMember.id = " + findMember.getId());
        System.out.println("findMember.name = " + findMember.getName());
    }

    private static void insert(EntityManager em) {
        Member member = new Member();
        member.setId(2L);
        member.setName("HelloB");
        em.persist(member);

    }


}
