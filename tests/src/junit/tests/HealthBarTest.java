package junit.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import models.HealthBar;

public class HealthBarTest {
	HealthBar health;
	double healthValue = 1.0f;
	@Before
	public void setUp() throws Exception {
		health = new HealthBar();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void test() {
		double delta = 0;
		assertEquals(healthValue, health.getHealth(), delta);
	}
	
}
