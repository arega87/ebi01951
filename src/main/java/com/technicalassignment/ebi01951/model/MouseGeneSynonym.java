package com.technicalassignment.ebi01951.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mouse_gene_synonym")
public class MouseGeneSynonym {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "mgi_gene_acc_id")
    private String identifier;

    @Column(name = "synonym")
    private String synonym;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getIdentifier() {
        return identifier;
    }


    public void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }


    public String getSynonym() {
        return synonym;
    }


    public void setSynonym(final String synonym) {
        this.synonym = synonym;
    }


    @Override
    public String toString() {
        return "MouseGeneSynonym{" +
                "id=" + id +
                ", identifier='" + identifier + '\'' +
                ", synonym='" + synonym + '\'' +
                '}';
    }
}
