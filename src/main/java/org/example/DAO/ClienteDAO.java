
package org.example.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.example.entities.Cliente;

public class ClienteDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectJPA");
    private EntityManager em;

    public ClienteDAO() {
        this.em = this.emf.createEntityManager();
    }

    public void saveCliente(Cliente cliente) {
        this.em.getTransaction().begin();
        this.em.persist(cliente);
        this.em.getTransaction().commit();
    }

    public void removeCliente(Cliente cliente) {
        this.em.getTransaction().begin();
        this.em.remove(cliente);
        this.em.getTransaction().commit();
    }

    public Cliente getCliente(Integer id) {
        return (Cliente)this.em.find(Cliente.class, id);
    }

    public List<Cliente> getAllCliente() {
        Query q = this.em.createNamedQuery("Cliente.findAll", Cliente.class);
        return q.getResultList();
    }

    public int countAllCliente() {
        Query q = this.em.createNamedQuery("Cliente.count", Long.class);
        return ((Number)q.getSingleResult()).intValue();
    }
}
