package it.aulab.springbootweb.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "prodotti")
// devo specificare su che tabella sto lavorando
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nome", length =100, nullable = true)
    // serve per dare un nome alla colonna, una lunghezza massima di caratteri che il singolo dato può contenere e la possibilità di essere null
    private String nome;
    @Column(name="descrizione", length =1000, nullable = true)
    private String descrizione;
    @Column(name="prezzo")
    private Float prezzo;
    @Column(name="netto")
    private Float prezzoNetto;

   @OneToMany(mappedBy= "prodotto")
   List<Variante> varianti;
   @ManyToMany(mappedBy = "prodotti")
   List<Fornitore> fornitori = new ArrayList<Fornitore>();
// nelle relazioni quella principale è dove non c'è il mappedBy
// meglio istanziare una lista vuora nelle relazioni altrimenti la considera null

    public Prodotto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }

    public Float getPrezzoNetto() {
        return prezzoNetto;
    }

    public void setPrezzoNetto(Float prezzoNetto) {
        this.prezzoNetto = prezzoNetto;
    }

    public List<Variante> getVarianti() {
        return varianti;
    }

    public void setVarianti(List<Variante> varianti) {
        this.varianti = varianti;
    }
}


