package com.cognizant.membership.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class Plan {

    @JsonProperty("planid")
    private Integer planid;

    @NotNull(message = "planname cannot be null")
    @NotBlank(message = "planname cannot be blank")
    @JsonProperty("planname")
    private String planname;

    @JsonProperty("planamount")
    private Integer planamount;

    @JsonProperty("plan_start_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date plan_start_date;

    @JsonProperty("plan_end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date plan_end_date;
}
