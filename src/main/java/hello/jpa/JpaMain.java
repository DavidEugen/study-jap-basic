package hello.jpa;

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
            lookOverCache(em);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }

    private static void lookOverCache(EntityManager em) {
        //비영속
        Member member = new Member();
        member.setId(102L);
        member.setName("HelloJPA2");

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
