package screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Assets {
	
	static ParticleEffect smokeParticles, interact;  //particle variables
	
	public static void load() {
		smokeParticles = new ParticleEffect(); //creates new particle effect
		smokeParticles.load(Gdx.files.internal("particles/effects/smoke2.p"), Gdx.files.internal("particles/effects"));
		
		interact = new ParticleEffect();//allows particle to be interactive
		interact.load(Gdx.files.internal("particles/effects/smoke2.p"), Gdx.files.internal("particles/effects"));
		
	}

}
