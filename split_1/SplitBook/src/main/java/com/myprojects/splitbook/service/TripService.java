package com.myprojects.splitbook.service;

import com.myprojects.splitbook.dao.TripRepository;
import com.myprojects.splitbook.entity.*;
import com.myprojects.splitbook.entity.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TripService {

    @Autowired
    TripRepository tripRepository;

    @Autowired
    TripSecurityService tripSecurityService;

    public String addNewTrip(String name,UserLogin user)
    {
        Trip trip = new Trip();
        trip.setName(name);
        trip.setOwnerId(user.getId());

        Member newMember = new Member();
        newMember.setName(user.getName());
        newMember.setOwner(true);
        newMember.setUserId(user.getId());
        newMember.setRole(UserRole.ADMIN);
        newMember.setCredit(BigDecimal.ZERO);
        newMember.setDebit(BigDecimal.ZERO);

        if(tripRepository.insertTrip(trip,newMember))
        {
            return "Your trip was added!";
        }
        return "Error adding trip!";
    }

    public List<Trip> getTripsByOwner(int ownerid)
    {

        List<Trip> trips = tripRepository.getTripsByOwnerId(ownerid);
        return trips;
    }

    @PreAuthorize("@tripSecurityService.isTripOwner(#tripId,#userId)")          // Using TripSecurityService to check authority for the request
    public Trip getTripById(int tripId, int userId)
    {
        return tripRepository.getTripById(tripId);
    }

    public String addMemberToTrip(UserDto member, int tripId)
    {
        Trip trip = tripRepository.getTripById(tripId);
        List<Member> members = trip.getMembers();
        for(Member x : members)
        {
            if(x.getUserId()==member.getUid())
            {
                return "Member already added";
            }
        }
        Member newMember = new Member();
        newMember.setName(member.getName());
        newMember.setUserId(member.getUid());
        newMember.setOwner(false);
        newMember.setRole(UserRole.USER);
        newMember.setCredit(BigDecimal.ZERO);
        newMember.setDebit(BigDecimal.ZERO);
        tripRepository.insertMember(trip,newMember);

        return "Member added.";
    }

    public List<Member> getTripMembers(int tripId)
    {
        return tripRepository.findMembersByTripId(tripId);
    }

    public void addContribution(Contribution contribution, List<Member> beneficiaries)
    {
        tripRepository.insertContribution(contribution,beneficiaries, contribution.getContributor());
    }

    public Member getMemberById(int id)
    {
        return tripRepository.findMemberByMemberId(id);
    }

    public void deleteContribution(int cid)
    {
        tripRepository.removeContribution(cid);
    }

    public float getTotalExpense(int tripId)
    {
        Trip trip = tripRepository.getTripById(tripId);
        List<Contribution> contributions = trip.getContributions();

        float res = 0.00F;
        for(Contribution c : contributions)
        {
            res += c.getAmount().floatValue();
        }

        return res;
    }
}
