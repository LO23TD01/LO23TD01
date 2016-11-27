package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Rules implements Serializable {
	private boolean isRampoAmeliore;
	private boolean isForcedToFollowFirst;
	private boolean isNenetteFlat;
	

	//TOREVIEW ajouter le reste dice regles
	//TODO les implementer ensuite

	
	
	public Rules(boolean isRampoAmeliore, boolean isForcedToFollowFirst, boolean isNenetteFlat) {
		super();
		this.isRampoAmeliore = isRampoAmeliore;
		this.isForcedToFollowFirst = isForcedToFollowFirst;
		this.isNenetteFlat = isNenetteFlat;
	}


	

	private static class DiceThrow
	implements Comparable<DiceThrow> {

		public User user;
		public int[] dice;

		public DiceThrow(User user, int[] dice) {
			this.user = user;
			this.dice = dice;
			Arrays.sort(this.dice);
		}

		public boolean isTriple() {
			if (this.dice[0] == this.dice[1] && this.dice[0] == this.dice[2]) {
				return true;
			}
			return false;
		}

		public boolean isPaireAs() {
			if (this.dice[0] == this.dice[1] && this.dice[0] == 1) {
				return true;
			}
			return false;
		}

		public boolean isSuite() {
			if (this.dice[0] == (this.dice[1] - 1) && this.dice[0] == (this.dice[2] - 2)) {
				return true;
			}
			return false;
		}

		public int valeur() {
			if (this.dice[2] == 4 && this.dice[1] == 2 && this.dice[0] == 1) //421
			{
				return 10;
			}
			if (isPaireAs()) // double as
			{
				if (this.dice[2] == 1) {
					return 7;
				}
				return this.dice[2];
			}
			if (isTriple()) 
				return this.dice[2];

			if (isSuite()) 
				return 2;

			return 1;
		}

		public int compareTo(DiceThrow lancerDes2) {
			if (this.valeur() > lancerDes2.valeur()) {
				return 1;
			} else if (this.valeur() < lancerDes2.valeur()) {
				return -1;
			} else if (this.isPaireAs() && !lancerDes2.isPaireAs()) {
				return 1;
			} else if (!this.isPaireAs() && lancerDes2.isPaireAs()) {
				return -1;
			} else if (!this.isSuite() && lancerDes2.isSuite()) {
				return 1;
			} else if (this.isSuite() && !lancerDes2.isSuite()) {
				return -1;
			} else if (this.isSuite() && lancerDes2.isSuite() && this.dice[0] > this.dice[1]) {
				return 1;
			} else if (this.isSuite() && lancerDes2.isSuite() && this.dice[0] < this.dice[1]) {
				return -1;
			} else {
				if (this.dice[2] > lancerDes2.dice[2]) {
					return 1;
				}
				if (this.dice[2] < lancerDes2.dice[2]) {
					return -1;
				}
				if (this.dice[1] > lancerDes2.dice[1]) {
					return 1;
				}
				if (this.dice[1] < lancerDes2.dice[1]) {
					return -1;
				}
				if (this.dice[0] > lancerDes2.dice[0]) {
					return 1;
				}
				if (this.dice[0] < lancerDes2.dice[0]) {
					return -1;
				}
				return 0;
			}
		}
	}




	public List<User> getWinner(List<PlayerData> l)
	{
		List<DiceThrow> listDice = new ArrayList<DiceThrow>();
		for(PlayerData p : l)
			listDice.add(new DiceThrow(p.getPlayer(),p.getDices()));
		Collections.sort(listDice);
		
		List<User> listWinners = new ArrayList<User>();
		for(DiceThrow d : listDice)
		{
			if(d.compareTo(listDice.get(0))==0)
				listWinners.add(d.user);
		}
		return listWinners;
	}
	
	
	public List<User> getLoser(List<PlayerData> l)
	{
		List<DiceThrow> listDice = new ArrayList<DiceThrow>();
		for(PlayerData p : l)
			listDice.add(new DiceThrow(p.getPlayer(),p.getDices()));
		Collections.sort(listDice);
		
		List<User> listLosers = new ArrayList<User>();
		for(DiceThrow d : listDice)
		{
			if(d.compareTo(listDice.get(listDice.size()-1))==0)
				listLosers.add(d.user);
		}
		return listLosers;
	}
	
	public int getChip(List<PlayerData> l)
	{
		List<DiceThrow> listDice = new ArrayList<DiceThrow>();
		for(PlayerData p : l)
			listDice.add(new DiceThrow(p.getPlayer(),p.getDices()));
		Collections.sort(listDice);
		
		return listDice.get(0).valeur(); // TODO rajouter nenette;
	}
	
	public boolean canReroll(List<PlayerData> l, User actualPlayer, User firstPlayer) throws Exception
	{
		PlayerData actualPlayerData = null; 
		PlayerData firstPlayerData = null; 
		for(PlayerData p : l)
		{
			if(p.getPlayer().isSame(actualPlayer))
				actualPlayerData=p;
			if(p.getPlayer().isSame(firstPlayer))
				firstPlayerData=p;
		}
		if(actualPlayerData==null || firstPlayerData==null)
			throw new Exception("Player not found.");
		if(actualPlayerData.getRerollCount()==3 || actualPlayerData.getRerollCount()==firstPlayerData.getRerollCount()) //TODO implement ruleset
			return false;
		return true;
	}
	
	public boolean hasToReroll(List<PlayerData> l, User actualPlayer, User firstPlayer)
	{
		//TODO implement ruleset
		return false; //par rapport au regle de d�faut que l'on a d�fini, �a suffit.
	}
	
	
	
	public boolean isRampoAmeliore() {
		return isRampoAmeliore;
	}


	public void setRampoAmeliore(boolean isRampoAmeliore) {
		this.isRampoAmeliore = isRampoAmeliore;
	}


	public boolean isForcedToFollowFirst() {
		return isForcedToFollowFirst;
	}


	public void setForcedToFollowFirst(boolean isForcedToFollowFirst) {
		this.isForcedToFollowFirst = isForcedToFollowFirst;
	}


	public boolean isNenetteFlat() {
		return isNenetteFlat;
	}


	public void setNenetteFlat(boolean isNenetteFlat) {
		this.isNenetteFlat = isNenetteFlat;
	}

	
}
