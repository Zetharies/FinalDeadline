package screens.intro;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SplashScreen extends AbstractScreen {

    private TextureRegion textureRegion;
    private Texture splashImage;
    private Stage stage;
 
    public SplashScreen() {
    }
 
    @Override
    public void render(float delta) {
    	Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        
        stage.act(Gdx.graphics.getDeltaTime());
    	stage.draw();
    }
 
    @Override
    public void hide() { }
 
    @Override
    public void pause() { }
 
    @Override
    public void resume() { }
    
    @Override
    public void show() { 
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	stage = new Stage();
    	splashImage = new Texture(Gdx.files.internal("images/aston_resized.jpg")); // Intro Image, feel free to change. Remember to add it to assets
    	splashImage.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	textureRegion = new TextureRegion(splashImage);
    	Image actorImage = new Image(textureRegion);
    	actorImage.getColor().a = 0;
    	actorImage.setScale(660, 660); // Scale of image
    	actorImage.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight()); // Set size of image to the screen size
    	actorImage.setPosition(Gdx.graphics.getWidth()/2 - actorImage.getWidth()/2, Gdx.graphics.getHeight()/2 - actorImage.getHeight()/2); // Position of image
    	actorImage.setScale(1); // Set scale of image to original scale
    	
    	SequenceAction actions = new SequenceAction(Actions.sequence(Actions.fadeIn(1f), Actions.delay(1.5f), Actions.fadeOut(2.5f), Actions.run(new Runnable() {

			@Override
			public void run() {
				System.out.println("Finished Intro, loading Game Menu");
			}
    		
    	})));
    	actorImage.addAction(actions);
    	stage.addActor(actorImage);
    }
    
    @Override
    public void resize(int width, int height) { }
 
    @Override
    public void dispose() {
       // ttrSplash.dispose();
        splashImage.dispose();
    }

}
