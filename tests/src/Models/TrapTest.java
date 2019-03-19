/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import models.Trap;

import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class TrapTest {
    Trap trap;
    public TrapTest() {
        trap = new Trap(65, 75);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test x coord of trap 
     */
    @Test
    public void testXPos(){
        assertEquals(65,(int)trap.getTX());
    }
    /**
     * Test x coord of trap 
     */
    @Test
    public void testYPos(){
        assertEquals(75,(int)trap.getTY());
    }
    
    
    
    /**
     * test damage value
     */
    @Test
    public void testDamage(){
        assertEquals(0.15f,trap.getDamage(),0.0);
    }
    
    /**
     * test trap default shot
     */
    @Test 
    public void testDefaultTrap(){
        assertFalse(trap.getShoot());
    }
    
    
}
