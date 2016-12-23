package network.client;

import java.util.UUID;

import data.ChatMessage;
import data.GameTable;
import data.Profile;
import data.Rules;
import data.client.ClientDataEngine;

/**
 * Interface presenting the methods of ComClient
 */
public interface ComClientInterface { 
	
	/**
	 * Sends user profile over the network from the client to the server
	 * @param user
	 */
	public void connection(Profile user);

	/**
	 * Sends a dice throwing request over the network from the client to the server
	 * @param user	the user UUID who asks the throw
	 * @param d1	boolean indicating whether or not the dice 1 should be thrown
	 * @param d2	boolean indicating whether or not the dice 2 should be thrown
	 * @param d3	boolean indicating whether or not the dice 3 should be thrown
	 */
	public void throwDice(UUID user, boolean d1, boolean d2, boolean d3);

	/**
	 * Sends a ChatMessage over the network from the client to the server
	 * @param msg	ChatMessage indicating the message attributes (sender, date...)
	 */
	public void sendMessage(ChatMessage msg);

	/**
	 * Sends a request to start a game over the network from the client to the server
	 * @param user	the user UUID who asks to start a game
	 */
	public void launchGame(UUID user);

	/**
	 * Sends a table creation request over the network from the client to the server
	 * @param user	the user UUID who asks for the table creation
	 * @param name	a new name for the table
	 * @param pwd	a password string needed to access the table
	 * @param min	an int indicating the minimum number of users
	 * @param max	an int indicating the maximum number of users
	 * @param token	an int indicating the number of token on the table
	 * @param withSpec	a boolean indicating whether or not the spectators should be allowed to join the table
	 * @param withChat	a boolean indicating whether or not the chat should be enabled
	 * @param rules		a Rule object indicating the rules of the game
	 */
	public void createNewTable(UUID user, String name, int min, int max, int token, boolean withSpec,
			boolean withChat, Rules rules);

	/**
	 * Sends a dice selection request over the network from the client to the server
	 * @param user	the user UUID who asks the dice selection
	 * @param d1	boolean indicating whether or not the dice 1 should be selected
	 * @param d2	boolean indicating whether or not the dice 2 should be selected
	 * @param d3	boolean indicating whether or not the dice 3 should be selected
	 */
	public void selectDice(UUID user, boolean d1, boolean d2, boolean d3);

	/**
	 * Sends a request to update a user profile over the network from the client to the server
	 * @param user	the user UUID who asks for the update
	 * @param profile	the profile that should be updated
	 */
	public void updateUserProfile(UUID user, Profile profile);

	/**
	 * Sends a request to update the users list over the network from the client to the server
	 * @param user	the user UUID who asks for the update
	 */
	public void updateUsersList(UUID user);

	/**
	 * Sends a request to quit the current table over the network from the client to the server
	 * @param tableId	the UUID of the table that has to be quitted
	 * @param user	the UUID of the user who wants to quit the table
	 */
	public void askQuitTable(UUID tableId, UUID user);

	/**	
	 * Sends a request to update the users list over the network from the client to the server
	 * @param user	the UUID of the user who wants to refresh the user list
	 */
	public void askRefreshUsersList(UUID user);

	/**
	 * Sends a request from a user to join a table over the network from the client to the server
	 * @param user	the UUID of the user who wants to join the table
	 * @param tableId	the UUID of the table to be joined
	 * @param asPlayer	a boolean indicating whether the user wants to join as player or spectator
	 */
	public void askJoinTable(UUID user, UUID tableId, boolean asPlayer);

	/**
	 * Sends an acceptation message about a replay over the network from the client to the server
	 * @param user	the UUID of the user who wants to accept the replay
	 */
	public void acceptReplay(UUID user);

	/**
	 * Sends an refusal message about a replay over the network from the client to the server
	 * @param user	the UUID of the user who wants to refuse the replay
	 */
	public void refuseReplay(UUID user);

	/**
	 * Sends a logout request over the network from the client to the server
	 * @param user	the UUID of the user who wants to log out
	 */
	public void logoutUserRequest(UUID user);

	/**
	 * Sends a profile retrievement request over the network from the client to the server
	 * @param user	the UUID of the user who wants to retieve a user profile
	 * @param sender	the UUID of the user whose profile has to be retrieved
	 */
	public void getProfile(UUID user, UUID sender); // Changement de l'interface pour recuperer le profile getProfile(UUID user) --> getProfile(UUID user, UUID sender)


	/**
	 * Sends an answer to a stop game request over the network from the client to the server
	 * @param tableId	the UUID of the table on which the game was asked to be stopped
	 * @param answer	a boolean indicating the answer
	 * @param user	the UUID of the user who asked to stop the game
	 */
	public void answerStopGame(UUID tableId, boolean answer, UUID user);

	/**
	 * Sends a quitting request (during a game) over the network from the client to the server
	 * @param user	the UUID of the user who wants to quit the table
	 * @param tableId	the UUID of the table the user wants to quit
	 */
	public void quit(UUID user, UUID tableId); // Pendant une partie; quit(user) supprim�, seul quit(user, table) sera utilis�

	/**
	 * Sends the client data over the network from the client to the server
	 * @param clientData
	 */
	public void setClientData(ClientDataEngine clientData);

}
