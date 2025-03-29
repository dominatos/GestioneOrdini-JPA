

package org.example;

import com.github.javafaker.Faker;
import java.io.PrintStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.example.DAO.ClienteDAO;
import org.example.DAO.DettagliordineDAO;
import org.example.DAO.OrdineDAO;
import org.example.DAO.ProdottoDAO;
import org.example.entities.Cliente;
import org.example.entities.Dettagliordine;
import org.example.entities.Ordine;
import org.example.entities.Prodotto;
import org.example.exceptions.ClienteNonTrovateException;
import org.example.exceptions.ProdottoNonTrovateException;

public class Main {
    private static Faker faker = new Faker(new Locale("it-IT"));
    private static DBinizialize db;

    static {
        try {
            db = new DBinizialize(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Scanner sc;

        private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("projectJPA");
        private static EntityManager em = emf.createEntityManager();
        private static Faker fake = new Faker(new Locale("it-IT"));
        private static ClienteDAO clienteDAO= new ClienteDAO();
        private static DettagliordineDAO dettagliordineDAO=new DettagliordineDAO();;
        public static OrdineDAO ordineDAO= new OrdineDAO();
        public static ProdottoDAO prodottoDAO =new ProdottoDAO();;


    public static void main(String[] args) throws SQLException {
        generateDB(100);
        generateOrders(10);


        ordineDAO.getAllOrdine().forEach(Ordine::stampa);
    }

    public static void generateOrders(int n) {
        int clientCount = clienteDAO.countAllCliente();
        if (clientCount == 0) {
            throw new ClienteNonTrovateException("Non ci sono clienti disponibili.");
        } else {
            int productCount = prodottoDAO.countAllProdotti();
            if (productCount == 0) {
                throw new ProdottoNonTrovateException("Non ci sono clienti disponibili.");
            } else {
                for(int i = 1; i <= n; ++i) {
                    Cliente randomClient = clienteDAO.getCliente((new Random()).nextInt(clientCount) + 1);
                    int randProdN = (new Random()).nextInt(3) + 1;
                    List<Prodotto> listProdotti = new ArrayList();
                    Ordine ordine = new Ordine();
                    ordine.setCliente(randomClient);
                    ordine.setData_ordine(LocalDateTime.now());
                    ordineDAO.saveOrdine(ordine);

                    for(int j = 0; j < randProdN; ++j) {
                        Dettagliordine dettagliordine = new Dettagliordine();
                        Prodotto randomProdotto = prodottoDAO.getProdotto((new Random()).nextInt(productCount) + 1);
                        int availableQuantity = randomProdotto.getQuantita_disponibile();
                        if (availableQuantity != 0) {
                            if (availableQuantity <= 0) {
                                throw new IllegalArgumentException("non e abbastanza prodotto quantita disponibile.");
                            }

                            int randomProdQuant = 1 + (new Random()).nextInt(availableQuantity);
                            randomProdotto.setQuantita_perordine(randomProdQuant);
                            dettagliordine.setProdotto(randomProdotto);
                            dettagliordine.setPrezzo_unitario(randomProdotto.getPrezzo());
                            dettagliordine.setQuantita(randomProdotto.getQuantita_perordine());
                            dettagliordine.setOrdine(ordine);
                            dettagliordineDAO.saveDettagliordine(dettagliordine);
                            listProdotti.add(randomProdotto);
                            ordine.setListaProdotti(listProdotti);
                        }
                    }

                    if (listProdotti.isEmpty()) {
                        Dettagliordine dettagliordine = new Dettagliordine();
                        Prodotto fallbackProdotto = null;
                        int retries = 0;

                        while(fallbackProdotto == null || fallbackProdotto.getQuantita_disponibile() == 0) {
                            fallbackProdotto = prodottoDAO.getProdotto((new Random()).nextInt(productCount) + 1);
                            ++retries;
                            if (retries > productCount) {
                                throw new ProdottoNonTrovateException("Non ci sono prodotti disponibili da aggiungere all'ordine.");
                            }
                        }

                        fallbackProdotto.setQuantita_perordine(1);
                        dettagliordine.setProdotto(fallbackProdotto);
                        dettagliordine.setPrezzo_unitario(fallbackProdotto.getPrezzo());
                        dettagliordine.setQuantita(fallbackProdotto.getQuantita_perordine());
                        dettagliordine.setOrdine(ordine);
                        ordine.setListaProdotti(listProdotti);
                        dettagliordineDAO.saveDettagliordine(dettagliordine);
                    }
                }

            }
        }
    }

    public static void generateDB(int n) {
        for(int i = 1; i <= n; ++i) {
            String nome = faker.name().firstName().replace("\"", "'");
            String cognome = faker.name().lastName().replace("\"", "'");
            String telefono = faker.phoneNumber().cellPhone();
            int addX = (new Random()).nextInt(100);
            String email = faker.internet().emailAddress().replace("\"", "'");
             email = email + addX;
            Cliente u = new Cliente(nome, cognome, email, telefono);
            clienteDAO.saveCliente(u);


            //System.out.println(u.getNome_cliente()+ u.getCognome() + " e creato.");
        }

        for(int i = 1; i <= n; ++i) {
            String nome = faker.name().firstName() + faker.beer().name().replace("\"", "'");
            String descrizione = faker.book().title();
            double prezzo = faker.random().nextDouble() * (double)10.0F;
            int quantita = faker.number().numberBetween(50, 2000);
            Prodotto u = new Prodotto(nome, descrizione, prezzo, quantita);
            prodottoDAO.saveProdotto(u);
            //System.out.println("Prodotto " + u.getNome() + " e creato. Quantita: " + quantita + " e prezzo: " + prezzo);
        }

    }


}
