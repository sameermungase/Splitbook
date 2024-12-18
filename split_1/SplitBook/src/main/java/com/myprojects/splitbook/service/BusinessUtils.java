package com.myprojects.splitbook.service;

import com.myprojects.splitbook.entity.Contribution;
import com.myprojects.splitbook.entity.Member;
import com.myprojects.splitbook.entity.Trip;
import com.myprojects.splitbook.entity.UserLogin;
import com.myprojects.splitbook.entity.dto.RecordsDto;
import com.myprojects.splitbook.entity.dto.SettlementsDto;
import com.myprojects.splitbook.entity.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessUtils {

    @Autowired
    TripService tripService;
    @Autowired
    UserFriendService userFriendService;
    @Autowired
    SplitServiceImpl splitService;

    private static final String FRIEND_REQUEST_SUFFIX = " wants to be your friend.";

    public static List<String> friendRequestsStringParser(List<String> users)
    {
        List<String> res = users.stream().map(str -> str+FRIEND_REQUEST_SUFFIX).toList();
        return res;
    }

    public void initHomepageAttributes(UserLogin user, Model model)
    {
        model.addAttribute("username",user.getName());
        List<Trip> trips = tripService.getTripsByOwner(user.getId());
        if(trips==null)
        {
            model.addAttribute("notrip",'1');
        }
        else
        {
            model.addAttribute("notrip",'0');
        }
        model.addAttribute("mytrips",trips);
        int requestCount = userFriendService.getFriendRequests(user.getId()).size();
        model.addAttribute("requestcount",requestCount);
    }

    public void initDashboardAttributes(Trip trip, Model model)
    {
        model.addAttribute("trip",trip);
        List<Member> members = tripService.getTripMembers(trip.getId());
        model.addAttribute("mymembers",members);
        model.addAttribute("contribution",new Contribution());
        List<RecordsDto> records = parseTripContributions(trip);
        model.addAttribute("records",records);
        model.addAttribute("memberscount",members.size());
        model.addAttribute("totalexpense",tripService.getTotalExpense(trip.getId()));
        List<SettlementsDto> results = splitService.initSettlements(trip);
        model.addAttribute("results",results);
    }

    public UserDto userDtoMapper(UserLogin user)
    {
        return new UserDto(user.getId(), user.getName(), user.getUsername());
    }

    public List<Member> memberListMapper(int[] ids)
    {
        List<Member> res = new ArrayList<>();
        for(int x : ids)
        {
            Member m = tripService.getMemberById(x);
            res.add(m);
        }

        return res;
    }

    public List<RecordsDto> parseTripContributions(Trip trip)
    {
        List<RecordsDto> records = new ArrayList<>();

        for(Contribution c : trip.getContributions())
        {
            RecordsDto r = new RecordsDto();
            r.setDate(c.getDate());
            r.setDescription(c.getDescription());
            r.setInfo(generateRecordInfo(c));
            r.setCid(c.getId());
            r.setTid(trip.getId());

            records.add(r);
        }

        return records;
    }

    public String generateRecordInfo(Contribution contribution)
    {
        String contributor = contribution.getContributor().getName();
        String amount = contribution.getAmount().toString();
        String beneficiaries = "";
        for(Member b : contribution.getBeneficiaryList())
        {
            beneficiaries += b.getName();
            beneficiaries += ",";
        }
        beneficiaries = beneficiaries.substring(0,beneficiaries.length()-1);

        return contributor + " paid Rs." + amount + " for "+beneficiaries;
    }
}
