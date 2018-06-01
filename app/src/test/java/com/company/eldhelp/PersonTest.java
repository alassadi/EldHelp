package com.company.eldhelp;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {

    Person person = new Person("Name", "Password");
    String expectedName = "Name";
    String expectedPassword = "Password";
    @Test
    public void getPassword() {
        Assert.assertEquals(person.getPassword(),expectedPassword);
    }

    @Test
    public void getName() {
        Assert.assertEquals(person.getName(), expectedName);
    }
}