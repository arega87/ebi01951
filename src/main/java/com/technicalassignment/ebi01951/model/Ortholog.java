package com.technicalassignment.ebi01951.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ortholog")
public class Ortholog {

    @Id
    private Long id;


    @Column(name = "support_count")
    private Long supportCount;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mouse_gene_id")
    private MouseGene mouseGene;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "human_gene_id")
    private HumanGene humanGene;


    public Long getId() {
        return id;
    }


    public void setId(final Long id) {
        this.id = id;
    }


    public Long getSupportCount() {
        return supportCount;
    }


    public void setSupportCount(final Long supportCount) {
        this.supportCount = supportCount;
    }


    public MouseGene getMouseGene() {
        return mouseGene;
    }


    public void setMouseGene(final MouseGene mouseGene) {
        this.mouseGene = mouseGene;
    }


    public HumanGene getHumanGene() {
        return humanGene;
    }


    public void setHumanGene(final HumanGene humanGene) {
        this.humanGene = humanGene;
    }


    @Override
    public String toString() {
        return "OrthoLog{" +
                "id=" + id +
                ", supportCount='" + supportCount + '\'' +
                ", mouseGene=" + mouseGene +
                ", humanGene=" + humanGene +
                '}';
    }
}
