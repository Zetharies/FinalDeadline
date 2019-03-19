package managers;

public class SettingsManager {
    private static boolean soundChecker = true; //default check sound is on
    private static boolean musicChecker = true; //default check music is on
    private static boolean skipDialogueChecker = false;//default skip dialogue is off
    private static boolean customDialogueChecker = false;//default custom dialogue is off
    public static boolean WASD = false;
    //default set to keys
    public static boolean KEYS = true;
    
    public static boolean getSound(){ //displays sound checker
        return soundChecker;
    }
    public static void setSound(boolean sound){ //allows player to change sound
        soundChecker = sound;
    }
    public static boolean getMusic(){ //displays music checker
        return musicChecker;
    }
    public static boolean getDialogueChecker() { //displays dialogue checker
    	return skipDialogueChecker;
    }
    public static boolean getCustomDialogue() { //displays custom dialogue checker
    	return customDialogueChecker;
    }
    public static void setMusic(boolean music){ //sets the music
        musicChecker = music;
    }
    public static void setDialogueSkipper(boolean skip) { //displays toggle skip dialogue
    	skipDialogueChecker = skip;
    }
    public static void setCustomDialogue(boolean custom) { 
    	customDialogueChecker = custom;
    }
}
