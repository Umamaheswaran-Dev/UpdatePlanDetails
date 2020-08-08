package com.cognizant.membership.service;

import com.cognizant.membership.beans.UpdatePlanRequest;
import com.cognizant.membership.beans.UpdatePlanResponse;
import com.cognizant.membership.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class UpdatePlanServiceTest {

    @InjectMocks
    UpdatePlanService updatePlanService;

    @Mock
    private MemberRepository mdRepository;

    @Test
    public void testSubmitClaim() {
        UpdatePlanRequest sc_request = new UpdatePlanRequest();
        sc_request.setUserid("mahes876");
        sc_request.setPassword("password");

        Mockito.when(mdRepository.existsByUserid(Mockito.any())).thenReturn(false);

        UpdatePlanResponse updatePlanResponse = updatePlanService.updatePlan(sc_request);
        Assert.assertNotNull(updatePlanResponse);
        Assert.assertEquals(Integer.valueOf(400), updatePlanResponse.getStatusCode());
    }
}
