
package managers;

public class SoundManager {
    private static boolean soundChecker = true;//default sound set to true - plays sound
    private static boolean musicChecker = true;//default music set to true - plays music
    
    /**
     * get sound - used to check sound settings before playing audio
     * @return 
     */
    public static boolean getSound(){
        return soundChecker;
    }
    /**
     * called with settings 
     * @param sound 
     */
    public static void setSound(boolean sound){
        soundChecker = sound;
    }
    
    /**
     * get music - used to check music settings before playing music
     * @return 
     */
    public static boolean getMusic(){
        return musicChecker;
    }
    /**
     * called with settings
     * @param music 
     */
    public static void setMusic(boolean music){
        musicChecker = music;
    }
}
