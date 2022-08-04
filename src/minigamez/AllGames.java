package minigamez;
/**
 * This interface is used to run resume resized methods in all games
 * This interface is implemented by all the game classes  
 * @author Jedidiah
 *
 *
 */
public interface AllGames {

	public void resume();
	public AllGames resized(Window window, AllGames old_game);
}
