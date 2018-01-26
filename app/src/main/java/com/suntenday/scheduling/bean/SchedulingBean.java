package com.suntenday.scheduling.bean;

import java.util.List;

/**
 * SchedulingBean
 *
 * @author suntenday
 * @date 2017/9/12 0012.
 */

public class SchedulingBean {

    private String schedulingDate;

    private List<SimpleBean<String>> schedulingEmployees;

    public SchedulingBean(){}

    public SchedulingBean(String schedulingDate, List<SimpleBean<String>> schedulingEmployees){
        this.schedulingDate = schedulingDate;
        this.schedulingEmployees = schedulingEmployees;
    }

    public String getSchedulingDate() {
        return schedulingDate;
    }

    public void setSchedulingDate(String schedulingDate) {
        this.schedulingDate = schedulingDate;
    }

    public List<SimpleBean<String>> getSchedulingEmployees() {
        return schedulingEmployees;
    }

    public void setSchedulingEmployees(List<SimpleBean<String>> schedulingEmployees) {
        this.schedulingEmployees = schedulingEmployees;
    }
}
