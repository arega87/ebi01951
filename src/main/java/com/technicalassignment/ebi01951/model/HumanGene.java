package com.technicalassignment.ebi01951.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "human_gene")
public class HumanGene {

    @Id
    Long id;

    @Column(name = "symbol")
    private String symbol;


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


    @Override
    public String toString() {
        return "HumanGene{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
