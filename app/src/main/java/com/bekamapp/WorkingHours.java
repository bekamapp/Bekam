package com.bekamapp;

import java.util.ArrayList;
import java.util.List;

public class WorkingHours {
    List<String> workingDays;
    String from, to;

    public List<String> getWorkingDays() {
        return workingDays;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public void setWorkingDays(List<String> workingDays) {
        this.workingDays = new ArrayList<>(workingDays);
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
