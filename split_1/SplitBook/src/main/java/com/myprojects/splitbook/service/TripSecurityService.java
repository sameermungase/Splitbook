package com.myprojects.splitbook.service;

import com.myprojects.splitbook.dao.TripRepository;
import com.myprojects.splitbook.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripSecurityService {

    @Autowired
    TripRepository tripRepository;

    public boolean isTripOwner(int tripId, int userId)
    {
        Trip trip = tripRepository.getTripById(tripId);
        return trip != null && trip.getOwnerId() == userId;
    }
}
