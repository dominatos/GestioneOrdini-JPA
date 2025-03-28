

package org.example.DAO;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.example.Main;
import org.example.entities.Dettagliordine;
import org.example.entities.Ordine;
import org.example.entities.Prodotto;

public class OrdineDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectJPA");
    private EntityManager em;
    private DettagliordineDAO dettagliordineDAO;

    public OrdineDAO() {
        this.em = this.emf.createEntityManager();
        this.dettagliordineDAO = new DettagliordineDAO();
    }

    public void saveOrdine(Ordine ordine) {
        this.em.getTransaction().begin();
        this.em.persist(ordine);
        this.em.getTransaction().commit();
    }

    public void removeOrdine(Ordine ordine) {
        this.em.getTransaction().begin();
        this.em.createQuery("DELETE FROM Dettagliordine d WHERE d.ordine = :ordine").setParameter("ordine", ordine).executeUpdate();
        this.em.remove(this.em.contains(ordine) ? ordine : this.em.merge(ordine));
        this.em.getTransaction().commit();
        System.out.println("Ordine n." + ordine.getId_ordine() + " eliminato dal DB!");
    }

    public Ordine getOrdineNodata(Integer id) {
        return (Ordine)this.em.find(Ordine.class, id);
    }

    public Ordine getOrdineByidwithDATA(Integer id) {
        Ordine ordine = this.getOrdineNodata(id);
        List<Prodotto> listprod = new ArrayList();
        List<Dettagliordine> detord = this.dettagliordineDAO.getAllDettagliordine().stream().filter((dettagliordine1) -> dettagliordine1.getOrdine().getId_ordine() == id).toList();
        detord.stream().forEach((dettagliordine1) -> {
            Prodotto prod = Main.prodottoDAO.getProdotto(dettagliordine1.getProdotto().getId_prodotto());
            prod.setQuantita_perordine(dettagliordine1.getQuantita());
            listprod.add(prod);
        });
        ordine.setListaProdotti(listprod);
        return ordine;
    }

    public List<Ordine> getAllOrdine() {
        Query q = this.em.createNamedQuery("Ordine.findAll", Ordine.class);
        List<Ordine> orList = q.getResultList();
        orList.stream().forEach((ordine) -> ordine.setListaProdotti(this.getOrdineByidwithDATA(ordine.getId_ordine()).getListaProdotti()));
        return q.getResultList();
    }
}
