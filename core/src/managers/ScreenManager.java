package managers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import screens.game.GameScreen;
import screens.intro.SplashScreen;
import screens.menu.CharacterSelection;
import screens.menu.MainMenuScreen;

public class ScreenManager {
	
   static Game game = null;
   static Screen splashScreen, mainMenuScreen, gameScreen, characterSelection;
   
   private ScreenManager() {}
   
   public static void initialize(Game game) {
      ScreenManager.game = game;

      splashScreen = new SplashScreen();
      mainMenuScreen = new MainMenuScreen();
      characterSelection = new CharacterSelection();
      
   }
   
   public static void setSplashScreen() {
      game.setScreen(splashScreen);
   }

   public static void setMainMenuScreen() {
      game.setScreen(mainMenuScreen);
   }
   
   public static void setCharacterSelectionScreen() {
	      game.setScreen(characterSelection);
	   }
   
   public static void setGameScreen(String character) {
	   gameScreen = new GameScreen(character);
	   game.setScreen(gameScreen);
   }
   
   public static String setCustomName(String name) {
	return name;
   }
   
}
