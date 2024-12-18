package com.myprojects.splitbook.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = "query_find_contribution_by_id", query = "select c from Contribution c where c.id = :cid")
})
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String description;

    @Column
    private LocalDate date;

    @Column
    private BigDecimal amount;

    @ManyToOne
    @JsonBackReference
    private Member contributor;

    @ManyToMany(cascade=CascadeType.PERSIST)
    @JoinTable(name = "ContributionBeneficiary",
                joinColumns = @JoinColumn(name = "contributionid"),
                inverseJoinColumns = @JoinColumn(name = "memberid"))
    @JsonBackReference
    private List<Member> beneficiaryList = new ArrayList<>();

    @ManyToOne
    @JsonBackReference
    private Trip trip;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Member getContributor() {
        return contributor;
    }

    public void setContributor(Member contributor) {
        this.contributor = contributor;
    }

    public List<Member> getBeneficiaryList() {
        return beneficiaryList;
    }

    public void addBeneficiary(Member member) {
        beneficiaryList.add(member);
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
