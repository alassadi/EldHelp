package com.company.eldhelp;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

public class MedicineTest {

    String ExpectedName = "Med";
    String ExpectedTime = "18:16";
    Medicine medicine = new Medicine(ExpectedName, ExpectedTime);

    @Test
    public void getName() {
        Assert.assertEquals(ExpectedName, medicine.getName());
    }

    @Test
    public void setName() {
        String ExpectedName2 = "Med2";
        medicine.setName(ExpectedName2);
        Assert.assertEquals(ExpectedName2, medicine.getName());
    }

    @Test
    public void getTime() {
        Assert.assertEquals(ExpectedTime, medicine.getTime());
    }

    @Test
    public void setTime() {
        String ExpectedTime2 = "Time2";
        medicine.setTime(ExpectedTime2);
        Assert.assertEquals(ExpectedTime2, medicine.getTime());
    }
}