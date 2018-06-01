package com.company.eldhelp;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

public class EventTest {
    String expectedName = "name";
    String expectedDate = "date";
    String expectedTime = "time";

    Event event = new Event(expectedName,expectedDate,expectedTime);

    @Test
    public void getName() {
        Assert.assertEquals(expectedName,event.getName());
    }

    @Test
    public void setName() {
        String expectedName2 = "name2";
        event.setName(expectedName2);
        Assert.assertEquals(expectedName2,event.getName());
    }


    @Test
    public void getDate() {
        Assert.assertEquals(expectedDate,event.getDate());
    }

    @Test
    public void setDate() {
        String expectedDate2 = "date2";
        event.setName(expectedDate2);
        Assert.assertEquals(expectedDate2,event.getDate());
    }

    @Test
    public void getTime() {
        Assert.assertEquals(expectedTime,event.getTime());
    }

    @Test
    public void setTime() {
        String expectedTime2 = "time2";
        event.setName(expectedTime2);
        Assert.assertEquals(expectedTime2,event.getTime());
    }
}