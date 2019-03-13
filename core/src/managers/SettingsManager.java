package managers;

public class SettingsManager {
    private static boolean soundChecker = true;
    private static boolean musicChecker = true;
    private static boolean skipDialogueChecker = false;
    public static boolean WASD = false;
    //default set to keys
    public static boolean KEYS = true;
    
    
    public static boolean getSound(){
        return soundChecker;
    }
    public static void setSound(boolean sound){
        soundChecker = sound;
    }
    
    public static boolean getMusic(){
        return musicChecker;
    }
    
    public static boolean getDialogueChecker() {
    	return skipDialogueChecker;
    }
    public static void setMusic(boolean music){
        musicChecker = music;
    }
    public static void setDialogueSkipper(boolean skip) {
    	skipDialogueChecker = skip;
    }
}
