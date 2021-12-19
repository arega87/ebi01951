package com.technicalassignment.ebi01951.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mouse_gene")
public class MouseGene {

    @Id
    Long id;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "mgi_gene_acc_id")
    private String identifier;


    public Long getId() {
        return id;
    }


    public void setId(final Long id) {
        this.id = id;
    }


    public String getSymbol() {
        return symbol;
    }


    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }


    public String getIdentifier() {
        return identifier;
    }


    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }


    @Override
    public String toString() {
        return "MouseGene{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
