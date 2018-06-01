package com.company.eldhelp;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContactTest {

    String expectedName = "name";
    String expectedNumber = "number";
    Contact contact = new Contact(expectedName,expectedNumber);
    @Test
    public void getName() {
        Assert.assertEquals(expectedName,contact.getName());
    }

    @Test
    public void getNumber() {
        Assert.assertEquals(expectedNumber, contact.getNumber());
    }
}