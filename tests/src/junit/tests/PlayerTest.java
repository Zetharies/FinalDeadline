package junit.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import models.Player;

public class PlayerTest {
	Player player;
	@Before
	public void setUp() throws Exception {
		player = new Player(10, 10);
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testX() {
		assertEquals(10, player.getX());
	}
	@Test
	public void testY() {
		assertEquals(10, player.getY());
	}
	
	@Test
	public void testMovePlayer() {
		player.movePlayer(1, 1);
		assertEquals(11,player.getX());
	}
	

}