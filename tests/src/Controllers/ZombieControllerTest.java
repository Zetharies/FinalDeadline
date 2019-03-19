/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controllers.ZombieController;
import models.Zombie;

import static org.junit.Assert.*;


/**
 *
 * @author User
 */
public class ZombieControllerTest {

    ZombieController controller;
    ArrayList<Integer> movementOptions;
    public ZombieControllerTest() {
        controller = new ZombieController(new Zombie(10, 10));
        movementOptions = new ArrayList<Integer>();
    }

    @Before
    public void setUp() {
        controller.setPlayerPosition(5, 5);
        movementOptions.add(1);
        movementOptions.add(2);
        movementOptions.add(3);
        movementOptions.add(4);

    }

    @After
    public void tearDown() {
    }

    /**
     * test controller storing player position
     */
    @Test
    public void testPlayerLocationX() {
        assertEquals(5, controller.getPlayerX());
    }

    @Test
    public void testPlayerLocationY() {
        assertEquals(5, controller.getPlayerY());
    }

    
   
    /**
     * Test detection of player within radius
     */
    @Test 
    public void testDetectPlayer(){
        assertTrue(controller.detect());
    }
    
    
    /**
     * Test movement of zombie to player
     */
    @Test
    public void testMoveToPlayer(){
        
    }
    
    /**
     * test other zombie collision
     */
    @Test
    public void testLeft(){
        controller.setLeft(true);
        assertTrue(controller.getLeft());
    }
    /**
     * test other zombie collision
     */
    @Test
    public void testDown(){
        controller.setDown(true);
        assertTrue(controller.getDown());
    }
    /**
     * test other zombie collision
     */
    @Test
    public void testRight(){
        controller.setRight(true);
        assertTrue(controller.getRight());
    }
    /**
     * test other zombie collision
     */
    @Test
    public void testUp(){
        controller.setUp(true);
        assertTrue(controller.getUp());
    }
}
