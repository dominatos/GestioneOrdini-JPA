

package org.example.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(
        name = "Dettagliordine.findAll",
        query = "SELECT d FROM Dettagliordine d"
)
public class Dettagliordine {
    @Column(
            nullable = false
    )
    private int quantita;
    @Column(
            nullable = false,
            scale = 2
    )
    private double prezzo_unitario;
    @ManyToOne
    @JoinColumn(
            name = "id_ordine",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "ordinidetagli_ordini_fk"
            )
    )
    private Ordine ordine;
    @ManyToOne
    @JoinColumn(
            name = "id_prodotto",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "ordinidetagli_prodotto_fk"
            )
    )
    private Prodotto prodotto;
    @Id
    @GeneratedValue
    private Integer id;

    public Dettagliordine(int quantita, double prezzo_unitario, Ordine ordine, Prodotto prodotto) {
        this.quantita = quantita;
        this.prezzo_unitario = prezzo_unitario;
        this.prodotto = prodotto;
        this.ordine = ordine;
    }

    public Dettagliordine() {
    }

    public Dettagliordine(int id_dettagli_ordini, int quantita, double prezzo_unitario, Ordine ordine, Prodotto prodotto) {
        this.quantita = quantita;
        this.prezzo_unitario = prezzo_unitario;
        this.ordine = ordine;
        this.prodotto = prodotto;
    }

    public int getQuantita() {
        return this.quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getPrezzo_unitario() {
        return this.prezzo_unitario;
    }

    public void setPrezzo_unitario(double prezzo_unitario) {
        this.prezzo_unitario = prezzo_unitario;
    }

    public Ordine getOrdine() {
        return this.ordine;
    }

    public void setOrdine(Ordine ordine) {
        this.ordine = ordine;
    }

    public Prodotto getProdotto() {
        return this.prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }
}
