package screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Assets {
	
	static ParticleEffect smokeParticles, interact;
	
	public static void load() {
		smokeParticles = new ParticleEffect();
		smokeParticles.load(Gdx.files.internal("particles/effects/smoke2.p"), Gdx.files.internal("particles/effects"));
		
		interact = new ParticleEffect();
		interact.load(Gdx.files.internal("particles/effects/smoke2.p"), Gdx.files.internal("particles/effects"));
		
	}

}
