package actions;

import com.badlogic.gdx.scenes.scene2d.Action;

/**
 * Fade out action for our game
 * @author Team 2f
 *
 */
public class FadeOutAction extends Action {

	float alpha;
	   float scl;
	   
	   public FadeOutAction(float time) {
	      scl = 1f/time;
	      alpha = 1f;
	   }
	   
	   @Override
	   public boolean act(float delta) {
	      actor.getColor().a = alpha;
	      alpha -= delta * scl;
	      if(alpha <= 0) {
	         run();
	         return true;
	      }
	      return false;
	   }
	   
	   public void run() {}

}
