package jpabook.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

public class JPAMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member("User1", new Address("Seoul", "Gangnam", "20512"));
            em.persist(member);
//            member.setName("UserNew");

//            Book book = new Book();
//            book.setName("JPA");
//            book.setAuthor("김영한");
//            em.persist(book);
//
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setItem(book);
//
//            em.persist(orderItem);
//
//            Order order = new Order();
//            order.addOrderItem(orderItem);// 으로 끝내는게 아니라
////            orderItem.setOrder(order); //양방향이므로 같이 넣어 줘야 한다.
//
//            em.persist(order);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
