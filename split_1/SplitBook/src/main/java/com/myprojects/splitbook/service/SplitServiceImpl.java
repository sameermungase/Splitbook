package com.myprojects.splitbook.service;

import com.myprojects.splitbook.dao.TripRepository;
import com.myprojects.splitbook.entity.Contribution;
import com.myprojects.splitbook.entity.Member;
import com.myprojects.splitbook.entity.Trip;
import com.myprojects.splitbook.entity.dto.Amount;
import com.myprojects.splitbook.entity.dto.SettlementsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

@Service
public class SplitServiceImpl {

    @Autowired
    TripRepository tripRepository;

    private static final String OWES_WORD = " owes ";

    public static void initCreditDebit(Contribution contribution)
    {
        Member contributor = contribution.getContributor();
        contributor.setCredit(contributor.getCredit().add(contribution.getAmount()));

        List<Member> beneficiaries = contribution.getBeneficiaryList();
        int n = beneficiaries.size();
        BigDecimal debitAmount = contribution.getAmount()
                                             .divide(BigDecimal.valueOf(n),2, RoundingMode.HALF_DOWN);
        for(Member b : beneficiaries)
        {
            b.setDebit(b.getDebit().add(debitAmount));
        }
    }

    public static void removeCreditDebit(Contribution contribution)
    {
        Member contributor = contribution.getContributor();
        contributor.setCredit(contributor.getCredit().subtract(contribution.getAmount()));

        List<Member> beneficiaries = contribution.getBeneficiaryList();
        int n = beneficiaries.size();
        BigDecimal debitAmount = contribution.getAmount()
                                             .divide(BigDecimal.valueOf(n),2, RoundingMode.HALF_DOWN);
        for(Member b : beneficiaries)
        {
            b.setDebit(b.getDebit().subtract(debitAmount));
        }
    }

    public List<SettlementsDto> initSettlements(Trip trip)          //Actual starting method of the splitting implementation
    {
        if(trip.getContributions().isEmpty())
        {
            return null;
        }
        //Set will be sorted based on intAmount
        TreeSet<Amount> amountsSet = new TreeSet<>();

        generateAmountSet(amountsSet,trip);

        List<SettlementsDto> resList = new ArrayList<>();
        generateSettlements(amountsSet,resList);

        return resList;
    }

    public void generateAmountSet(TreeSet<Amount> amountsSet, Trip trip)
    {
        List<Member> members = trip.getMembers();

        for(Member m : members)
        {
            amountsSet.add(new Amount(m.getId(),m.getCredit().subtract(m.getDebit()).floatValue()));
        }
    }

    public void generateSettlements(TreeSet<Amount> amountsSet, List<SettlementsDto> resList)
    {
        Amount a1 = amountsSet.pollLast();              //Creditor
        Amount a2 = amountsSet.pollFirst();             //Debtor
        if(a1==null||a2==null)
        {
            throw new RuntimeException("Amount class cannot be null");
        }

        float maxCredit = a1.getIntAmount();
        float maxDebit = a2.getIntAmount();

        if(maxCredit==0||maxDebit==0)
        {
            return;
        }

        Member firstPerson = tripRepository.findMemberByMemberId(a2.getId());
        Member secondPerson = tripRepository.findMemberByMemberId(a1.getId());
        String infoString = firstPerson.getName() + OWES_WORD + secondPerson.getName();
        float min = Math.min(maxCredit, -maxDebit);
        resList.add(new SettlementsDto(infoString,min));

        a1.setIntAmount(maxCredit-min);
        a2.setIntAmount(maxDebit+min);
        amountsSet.add(a1);
        amountsSet.add(a2);

        generateSettlements(amountsSet,resList);        //recurse until maxCredit and maxDebit becomes zero

    }

}
