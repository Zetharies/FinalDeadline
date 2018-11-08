package managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import screens.game.GameScreen;
import screens.intro.SplashScreen;
import screens.menu.MainMenuScreen;

public class ScreenManager {
	
   static Game game = null;
   static Screen splashScreen, mainMenuScreen, gameScreen;
   
   private ScreenManager() {}
   
   public static void initialize(Game game) {
      ScreenManager.game = game;

      splashScreen = new SplashScreen();
      mainMenuScreen = new MainMenuScreen();
      
   }
   
   public static void setSplashScreen() {
      game.setScreen(splashScreen);
   }

   public static void setMainMenuScreen() {
      game.setScreen(mainMenuScreen);
   }
   
   public static void setGameScreen(String character) {
	   gameScreen = new GameScreen(character);
	   game.setScreen(gameScreen);
   }
   
}
