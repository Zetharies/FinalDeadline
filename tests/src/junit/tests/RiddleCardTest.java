package junit.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import riddleScreen.RiddleCard;

public class RiddleCardTest {

	private RiddleCard riddlecard;
	SpriteBatch batch;

	@Before
	public void setUp() throws Exception {
		riddlecard= new RiddleCard("riddle", 10, 10);
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testX() {
		assertEquals(10, riddlecard.getX());
	
	}
	@Test
	public void testY() {
		assertEquals(10, riddlecard.getY());
	
	}

}
