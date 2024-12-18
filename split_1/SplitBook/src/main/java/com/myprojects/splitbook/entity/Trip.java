package com.myprojects.splitbook.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = "query_find_by_owner", query = "select t from Trip t where t.ownerId = :ownerid"),
        @NamedQuery(name = "query_find_by_id", query = "select t from Trip t where t.id = :id")
})
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "trip", cascade=CascadeType.ALL)
    private List<Member> members = new ArrayList<>();

    @Column(name = "ownerid")       //UserLogin ID
    private int ownerId;

    @Column(name = "totalexpense")
    private BigDecimal totalExpense;

    @OneToMany(mappedBy = "trip")
    @JsonManagedReference
    private List<Contribution> contributions = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void addNewMember(Member member) {
        members.add(member);
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }

    public List<Contribution> getContributions() {
        return contributions;
    }

    public void addContribution(Contribution contribution) {
        contributions.add(contribution);
    }
}
