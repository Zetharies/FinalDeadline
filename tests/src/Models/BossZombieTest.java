/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import models.BossZombie;

import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class BossZombieTest {
    BossZombie boss;
    public BossZombieTest() {
        boss = new BossZombie(10,10);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

   
    
    
    
    /**
     * test rushing player
     */
    @Test
    public void testRushing(){
        float originalSpeed = boss.getSpeed();
        boss.rushPlayer();
        Assert.assertNotEquals(originalSpeed, boss.getSpeed());
    }
    
    /**
     * hp restoring functionality
     */
    @Test 
    public void testHpRestore(){
        boss.setHealth(20);
        boss.resetHealth();
        assertEquals(100,boss.getHealth());
    }
    
    /**
     * test teleport
     */
    @Test
    public void testTeleport(){
    }
   

    
}
