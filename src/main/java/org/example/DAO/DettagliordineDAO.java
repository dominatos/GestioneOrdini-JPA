

package org.example.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.example.entities.Dettagliordine;

public class DettagliordineDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectJPA");
    private EntityManager em;

    public DettagliordineDAO() {
        this.em = this.emf.createEntityManager();
    }

    public void saveDettagliordine(Dettagliordine dettagliordine) {
        this.em.getTransaction().begin();
        this.em.persist(dettagliordine);
        this.em.getTransaction().commit();
    }

    public void removeDettagliordine(Dettagliordine dettagliordine) {
        this.em.getTransaction().begin();
        this.em.remove(dettagliordine);
        this.em.getTransaction().commit();
    }

    public Dettagliordine getDettagliordine(Integer id) {
        return (Dettagliordine)this.em.find(Dettagliordine.class, id);
    }

    public List<Dettagliordine> getAllDettagliordine() {
        Query q = this.em.createNamedQuery("Dettagliordine.findAll", Dettagliordine.class);
        return q.getResultList();
    }
}
