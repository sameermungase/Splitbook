package com.myprojects.splitbook.dao;

import com.myprojects.splitbook.entity.Contribution;
import com.myprojects.splitbook.entity.Member;
import com.myprojects.splitbook.entity.Trip;
import com.myprojects.splitbook.service.SplitServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class TripRepository {

    @PersistenceContext
    EntityManager entityManager;

    public static final String query_find_members_trip = "SELECT m FROM Member m where m.trip.id = : tripid";

    public boolean insertTrip(Trip trip, Member owner)
    {
        try {
            trip.addNewMember(owner);
            owner.setTrip(trip);
            entityManager.persist(owner);
            entityManager.merge(trip);
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<Trip> getTripsByOwnerId(int id)
    {
        TypedQuery<Trip> query = entityManager.createNamedQuery("query_find_by_owner", Trip.class);
        query.setParameter("ownerid",id);
        List<Trip> resultList = query.getResultList();

        if(resultList.isEmpty())
        {
            return null;
        }
        return resultList;
    }

    public Trip getTripById(int id)
    {
        TypedQuery<Trip> query = entityManager.createNamedQuery("query_find_by_id", Trip.class);
        query.setParameter("id",id);
        List<Trip> resultList = query.getResultList();

        if(resultList.isEmpty())
        {
            return null;
        }
        return resultList.get(0);
    }

    public void insertMember(Trip trip,Member member)
    {
        trip.addNewMember(member);
        member.setTrip(trip);
        try{
            entityManager.persist(member);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public List<Member> findMembersByTripId(int tripId)
    {
        TypedQuery<Member> query = entityManager.createQuery(query_find_members_trip, Member.class);
        query.setParameter("tripid",tripId);
        List<Member> resultList = query.getResultList();

        return resultList;
    }

    public Member findMemberByMemberId(int memberId)
    {
        try {
            TypedQuery<Member> query = entityManager.createNamedQuery("query_find_member_by_id",Member.class);
            query.setParameter("mid",memberId);
            Member member = query.getSingleResult();
            return member;
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    public void insertContribution(Contribution contribution, List<Member> beneficiaries, Member contributor)
    {
        try
        {
            //contribution.setContributor(contributor);    //already been set by modelAttribute
            contributor.addContribution(contribution);
            for(Member b : beneficiaries)
            {
                contribution.addBeneficiary(b);
                b.addBenefit(contribution);
            }
            entityManager.merge(contribution);
            SplitServiceImpl.initCreditDebit(contribution);         //Modify cr,dr for the members

        }
        catch (Exception ex)
        {
            System.out.println("See here -> "+ex.getMessage());
        }
    }

    public Contribution findContributionById(int cid)
    {
        try {
            TypedQuery<Contribution> query = entityManager.createNamedQuery("query_find_contribution_by_id", Contribution.class);
            query.setParameter("cid",cid);
            return query.getSingleResult();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void removeContribution(int cid)         //TODO : TEST ADDING/REMOVING CONTRIBUTIONS
    {
        try {
            Contribution contribution = findContributionById(cid);
            SplitServiceImpl.removeCreditDebit(contribution);       //Reverting cr/dr of the members
            entityManager.remove(contribution);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
