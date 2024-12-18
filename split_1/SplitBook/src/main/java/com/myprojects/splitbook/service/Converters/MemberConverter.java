package com.myprojects.splitbook.service.Converters;

import com.myprojects.splitbook.dao.TripRepository;
import com.myprojects.splitbook.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter implements Converter<String, Member> {

    @Autowired
    TripRepository tripRepository;

    @Override
    public Member convert(String source) {
        return tripRepository.findMemberByMemberId(Integer.parseInt(source));
    }

}
