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

import models.Bullet;

import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class BulletTest {

    Bullet bullet;

    public BulletTest() {
        bullet = new Bullet(10, 10);
    }

    @Before
    public void setUp() {
     //   bullet.setPosition(40, 10);
    }

    @After
    public void tearDown() {
    }

   
    /**
     * test bullet speed
     */
   @Test
   public void testSpeed(){
       assertEquals(2.0f,bullet.getSpeed(),0.0);
   }
   
   /**
    * test damage
    */
   @Test
   public void testDamage(){
       assertEquals(0.15f,bullet.getDamage(),0.0);
   }
   
   /**
    * test default shot value
    */
   @Test
   public void testDefaultShot(){
       assertFalse(bullet.getShoot());
   }
    
}
