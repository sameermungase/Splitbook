package com.myprojects.splitbook.controller;

import com.myprojects.splitbook.dao.TripRepository;
import com.myprojects.splitbook.entity.Member;
import com.myprojects.splitbook.entity.Trip;
import com.myprojects.splitbook.entity.dto.RecordsDto;
import com.myprojects.splitbook.service.BusinessUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/testservice")
public class TestRestController {

    @Autowired
    TripRepository tripRepository;
    @Autowired
    BusinessUtils utils;


    @GetMapping("/welcome")
    public String welcome()
    {
        return "Hello!";
    }

    @GetMapping("/getmember/{id}")
    public Member getTripMembers(@PathVariable int id)
    {
        try
        {
            Member res= tripRepository.findMemberByMemberId(id);
            return res;
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @GetMapping("/getcontributionstrip/{tripid}")
    public List<RecordsDto> getContributionStringsOfTrip(@PathVariable int tripid)
    {
        Trip trip = tripRepository.getTripById(tripid);
        return utils.parseTripContributions(trip);
    }
}
