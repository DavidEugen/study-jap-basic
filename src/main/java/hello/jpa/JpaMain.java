package hello.jpa;

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
            Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");
            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();


    }


}
