package network.server;

import java.util.List;
import java.util.UUID;

import data.ChatMessage;
import data.GameTable;
import data.Profile;
import data.State;
import data.TurnState;
import data.User;

/**
 * Interface for server methods
 *
 */
public interface ComServerInterface {

	/**
	 * Sends dice throw result to all the players
	 * @param receivers List of receivers' ID
	 * @param r1 Value of first dice
	 * @param r2 Value of second dice
	 * @param r3 Value of third dice
	 */
	public void sendResult(List<UUID> receivers, int r1, int r2, int r3);

	/**
	 * Sends message to all players
	 * @param receivers List of receivers' ID
	 * @param msg Message to send
	 */
	public void sendMessage(List<UUID> receivers, ChatMessage msg);

	/**
	 * Sends timer to all players
	 * @param receivers List of receivers' ID
	 */
	public void showTimer(List<UUID> receivers); // ajouté List<UUID> au lieu de UUID : corrigé avec le diagramme de sequence "Commencer Partie"

	/**
	 * Adds new table with associated players
	 * @param receiver Receiver's ID
	 * @param receivers List of receivers' ID
	 * @param tableinfo Table's infos
	 */
	public void addNewTable(UUID receiver,List<UUID> receivers, GameTable tableinfo); // ajouté l'UUID du receiver unique

	/**
	 * Sends die selection of a player to all players
	 * @param receivers List of receivers' ID
	 * @param player ID of player selecting the die
	 * @param d1 True if first die is selected
	 * @param d2 True if second die is selected
	 * @param d3 True if third die is selected
	 */
	public void sendSelection(List<UUID> receivers, UUID player, boolean d1, boolean d2, boolean d3);

	/**
	 * Sends quantity of chips won by a player to all the players
	 * @param receivers List of receivers' ID
	 * @param player Player's ID
	 * @param nb Quantity of chips
	 */
	public void updateChips(List<UUID> receivers, UUID player, int nb);

	/**
	 * Sends quantity of chips lost by a player to another's profit
	 * @param receivers List of receivers' ID
	 * @param win Winning player's ID
	 * @param lose Losing player's ID
	 * @param nb Quantity of chips
	 */
	public void updateChips(List<UUID> receivers, UUID win, UUID lose, int nb);

	/**
	 * Sends the winning player's ID to all players
	 * @param receivers List of receivers' ID
	 * @param winner Winning player's ID
	 */
	public void hasWon(List<UUID> receivers, UUID winner);

	/**
	 * Starts a new turn
	 * @param receivers List of receivers' ID
	 * @param player First player's ID
	 * @param isLastLaunch True if last turn
	 */
	public void startTurn(List<UUID> receivers, UUID player, boolean isLastLaunch);

	/**
	 * Sends a player's profile to all players
	 * @param receivers List of receivers' ID
	 * @param userUpdated Profile's user ID
	 * @param data Profile to send
	 */
	public void sendProfileUpdate(List<UUID> receivers, UUID userUpdated, Profile data); // plus necessaire (changement lié au professeur et à "modifier profil")

	/**
	 * Sends a profile to a player
	 * @param receivers Receiver's ID
	 * @param data Profile to send
	 */
	public void sendProfile(UUID receivers, Profile data);

	/**
	 * Kicks a list of players and sends a message to all of them
	 * @param receivers List of receivers' ID
	 * @param msg Message to send to kicked players
	 */
	public void kick(List<UUID> receivers, String msg);

	/**
	 * Sends to all players message asking if they want to stop the game
	 * @param receivers List of receivers' ID
	 */
	public void askStopGameEveryUser(List<UUID> receivers);

	/**
	 * Notifies all players the game has stopped
	 * @param receivers List of receivers' ID
	 */
	public void stopGameAccepted(List<UUID> receivers);

	/**
	 * Sends list of all players to a player
	 * @param user Receiver's ID
	 * @param userList Liste of all players
	 */
	public void refreshUserList(UUID user, List<User> userList);

	/**
	 * Raises an exception
	 * @param user Receiver's ID
	 * @param msg Message of the exception
	 */
	public void raiseException(UUID user, String msg);

	/**
	 * Raises an exception
	 * @param user Receivers's ID
	 * @param msg Message of the exception
	 */
	public void raiseException(List<UUID> receivers, String msg);

	/**
	 * Adds a player to a table
	 * @param receivers List of receivers' ID
	 * @param user New user's profile
	 * @param tableInfo Table info
	 */
	public void newPlayerOnTable(List<UUID> receivers, Profile user, GameTable tableInfo);



	/**
	 * Add a spectator to a table
	 * @param receivers List of receivers' ID
	 * @param user New spectator's ID
	 * @param tableInfo Table info
	 */
	public void newSpectatorOnTable(List<UUID> receivers, Profile user, GameTable tableInfo);

	/**
	 *
	 * @param user
	 * @param receivers List of receivers' ID
	 */
	public void hasAccepted(UUID user,List<UUID> receivers);

	/**
	 *
	 * @param user
	 * @param receivers List of receivers' ID
	 */
	public void hasRefused(UUID user,List<UUID> receivers);

	/**
	 * Adds a user to the game
	 * @param receivers List of receivers' ID
	 * @param user New user's profile
	 */
	public void newUser(List<UUID> receivers, Profile user);

	/**
	 * Sends all tables to all users
	 * @param userList List of all users
	 * @param tableList List of all tables
	 * @param user Receiver's profile
	 */
	public void sendTablesUsers(List<User> userList, List<GameTable> tableList, Profile user);

	/**
	 * Notifies a player has quit a table
	 * @param receivers List of receivers' ID
	 * @param user Quitting user's ID
	 */
	public void playerQuitGame(List<UUID> receivers, UUID user);

	/**
	 * Stops a game
	 * @param receivers List of receivers' ID
	 * @param answer True if players have voted to stop the game
	 */
	public void stopGame(List<UUID> receivers, boolean answer);

	/**
	 * Notifies all players who lost
	 * @param receivers List of receivers' ID
	 * @param winner Losing player's ID
	 */
	public void hasLost(List<UUID> receivers, UUID winner);

	/**
	 * Changes game state
	 * @param receivers List of receivers' ID
	 * @param state State to which the game will be changed
	 */
	public void changeState(List<UUID> receivers, State state);

	/**
	 * Changes turn state
	 * @param receivers List of receivers' ID
	 * @param turnState State to which the turn will be changed
	 */
	public void changeTurnState(List<UUID> receivers, TurnState turnState);

	/**
	 * Sets a player as the game creator
	 * @param receivers List of receivers' ID
	 * @param creator ID of the creator
	 */
	public void setCreator(List<UUID> receivers, UUID creator);
	
	/**
	 * Notify players of an exaequo result
	 * @param receivers List of receivers' ID
	 * @param users Users that are exaequo
	 */
	public void exAequoCase(List<UUID> receivers, List<User> users, boolean win);
}
