package com.cognizant.membership.controller;

import com.cognizant.membership.beans.UpdatePlanRequest;
import com.cognizant.membership.beans.UpdatePlanResponse;
import com.cognizant.membership.service.UpdatePlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/cms")
public class UpdatePlanController {

    Logger logger = LoggerFactory.getLogger(UpdatePlanController.class);

    @Autowired
    private UpdatePlanService service;

    @RequestMapping(path = "/updatePlan", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody
    UpdatePlanResponse updatePlan(@Valid @RequestBody UpdatePlanRequest planRequest) {

        logger.info("UpdatePlanRequest::" + planRequest);

        UpdatePlanResponse planResponse = service.updatePlan(planRequest);

        logger.info("UpdatePlanResponse::" + planResponse);

        return planResponse;
    }

}
