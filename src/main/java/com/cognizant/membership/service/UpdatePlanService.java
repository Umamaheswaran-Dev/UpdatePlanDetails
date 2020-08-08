package com.cognizant.membership.service;

import com.cognizant.membership.model.Member;
import com.cognizant.membership.model.Plan;
import com.cognizant.membership.beans.UpdatePlanRequest;
import com.cognizant.membership.beans.UpdatePlanResponse;
import com.cognizant.membership.repository.MemberRepository;
import com.cognizant.membership.repository.PlanRepository;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdatePlanService {

    Logger logger = LoggerFactory.getLogger(UpdatePlanService.class);

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlanRepository planRepository;

    public UpdatePlanResponse updatePlan(UpdatePlanRequest planRequest) {
        UpdatePlanResponse response = new UpdatePlanResponse();
        try {
            switch (authenicateUser(planRequest)) {
                case "USER_INVALID":
                    response.setPlanid(0);
                    response.setStatusCode(400);
                    response.setStatusMessage("Invalid User ID");
                    break;

                case "INVALID_PASSWORD":
                    response.setPlanid(0);
                    response.setStatusCode(400);
                    response.setStatusMessage("Invalid password for the user");
                    break;

                case "INVALID_MEMBER_ID":
                    response.setPlanid(0);
                    response.setStatusCode(400);
                    response.setStatusMessage("Invalid Member ID/Plan ID for the user");
                    break;

                case "READY_TO_UPDATE":
                    List<Member> memberDetailsList = memberRepository.findByUserid(planRequest.getUserid());
                    if (!memberDetailsList.isEmpty()) {
                        Plan plan = memberDetailsList.get(0).getPlan();
                        plan.setPlanid(planRequest.getPlan().getPlanid());
                        plan.setPlanamount(planRequest.getPlan().getPlanamount());
                        plan.setPlan_start_date(planRequest.getPlan().getPlan_start_date());
                        plan.setPlan_end_date(planRequest.getPlan().getPlan_end_date());
                        planRepository.save(plan);
                        response.setPlanid(plan.getPlanid());
                        response.setStatusCode(200);
                        response.setStatusMessage("SUCCESS");
                    }
                    break;

                default:
                    response.setPlanid(0);
                    response.setStatusCode(400);
                    response.setStatusMessage("Please cross check your input values");
            }


        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setPlanid(0);
            response.setStatusCode(500);
            response.setStatusMessage(e.getMessage());
        }


        return response;
    }

    private String authenicateUser(UpdatePlanRequest updatePlanRequest) {
        if (memberRepository.existsByUserid(updatePlanRequest.getUserid())) {
            List<Member> memberList = memberRepository.findByUserid(updatePlanRequest.getUserid());
            if (validatePassword(updatePlanRequest, memberList.get(0).getPassword())) {
                if (memberList.get(0).getId() == updatePlanRequest.getMemberid()
                        && memberList.get(0).getPlan().getPlanid() == updatePlanRequest.getPlan().getPlanid()) {
                    return "READY_TO_UPDATE";
                } else {
                    return "INVALID_MEMBER_ID";
                }
            } else {
                return "INVALID_PASSWORD";
            }
        } else {
            return "USER_INVALID";
        }
    }

    private boolean validatePassword(UpdatePlanRequest updatePlanRequest, String dbPassword) {

        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        boolean result = passwordEncryptor.checkPassword(updatePlanRequest.getPassword(), dbPassword);

        return result;
    }
}
