

package org.example.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.example.entities.Prodotto;

public class ProdottoDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectJPA");
    private EntityManager em;

    public ProdottoDAO() {
        this.em = this.emf.createEntityManager();
    }

    public void saveProdotto(Prodotto prodotto) {
        this.em.getTransaction().begin();
        this.em.persist(prodotto);
        this.em.getTransaction().commit();
    }

    public void removeProdotto(Prodotto prodotto) {
        this.em.getTransaction().begin();
        this.em.remove(prodotto);
        this.em.getTransaction().commit();
    }

    public Prodotto getProdotto(Integer id) {
        return (Prodotto)this.em.find(Prodotto.class, id);
    }

    public List<Prodotto> getAllProdotto() {
        Query q = this.em.createNamedQuery("Prodotto.findAll", Prodotto.class);
        return q.getResultList();
    }

    public int countAllProdotti() {
        Query q = this.em.createNamedQuery("Prodotto.count", Long.class);
        return ((Number)q.getSingleResult()).intValue();
    }
}
