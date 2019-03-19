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

import models.Zombie;

import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class ZombieTest {
    
    private Zombie zombie;
    public ZombieTest() {
    }
    
    @Before
    public void setUp() {
        zombie = new Zombie(10,10);
    }
    
    @After
    public void tearDown() {
        zombie = null;
    }

   /**
    * test zombie spawn
    */
    @Test
    public void testZombieX(){
        assertEquals(10,zombie.getX());
    }
    @Test 
    public void testZombieY(){
        assertEquals(10,zombie.getY());
    }
    
    /**
     * test movement timer
     */
    @Test
    public void testMovementTimer(){
        zombie.setTimer(10);
        assertEquals(10,zombie.getTimer());
    }
    
    /**
     * test zombie speed
     */
    @Test 
    public void testSpeed(){
        assertEquals(1.5,zombie.getSpeed(),0.0);
    }
    
    /**
     * check zombie has moved
     */
    @Test
    public void testMoved(){
        zombie.setMoved(false);
        assertEquals(false,zombie.getMoved());
    }
    
    
}
