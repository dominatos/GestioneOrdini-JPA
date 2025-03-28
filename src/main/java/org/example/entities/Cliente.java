

package org.example.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(
        name = "clienti"
)
@NamedQueries({@NamedQuery(
        name = "Cliente.findAll",
        query = "SELECT c FROM Cliente c"
), @NamedQuery(
        name = "Cliente.count",
        query = "SELECT count(c) FROM Cliente c"
)})
public class Cliente {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id_cliente;
    @Column(
            name = "nome_cliente",
            nullable = false,
            length = 50
    )
    private String nome_cliente;
    @Column(
            nullable = false,
            length = 50
    )
    private String cognome;
    @Column(
            nullable = false,
            unique = true
    )
    private String email;
    @Column(
            nullable = false,
            unique = true
    )
    private String telefono;

    public Cliente(int id_cliente, String nome_cliente, String cognome, String email, String telefono) {
        this.id_cliente = id_cliente;
        this.nome_cliente = nome_cliente;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
    }

    public Cliente(String nome_cliente, String cognome, String email, String telefono) {
        this.nome_cliente = nome_cliente;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
    }

    public Cliente() {
    }

    public int getId_cliente() {
        return this.id_cliente;
    }

    public String getNome_cliente() {
        return this.nome_cliente;
    }

    public void setNome_cliente(String nome) {
        this.nome_cliente = nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefone) {
        this.telefono = telefone;
    }

    public String toString() {
        return "cliente{idcliente=" + this.id_cliente + ", nome='" + this.nome_cliente + "', cognome='" + this.cognome + "', email='" + this.email + "', telefone='" + this.telefono + "'}";
    }
}
