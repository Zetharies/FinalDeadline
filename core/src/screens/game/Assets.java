package screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Assets {
	
	static ParticleEffect chimneySmoke, interact;
	
	public static void load() {
		chimneySmoke = new ParticleEffect();
		chimneySmoke.load(Gdx.files.internal("particles/effects/smoke.p"), Gdx.files.internal("particles/effects"));
		
		interact = new ParticleEffect();
		interact.load(Gdx.files.internal("particles/effects/smoke.p"), Gdx.files.internal("particles/effects"));
		
	}

}