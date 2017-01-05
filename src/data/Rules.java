package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Rules {

	//Regles :
	//Charge Sec
	//perdant commence
	//Nenette gagne contre les coup de valeur 1;
	//le perdant est le dernier avec des jetons.
	//le gagnant est le premier ayant des jetons à ne plus en avoir.
	//Rampo joués sec avec pour enjeu le montant avant rampo

	//Variantes :
	//1 Décharge à nombre fixe de coup.
	//2 Décharge obligatoirement le nombre du premier joueur.
	//3 Décharge au plus le nombre du premier joueur.

	private final ObjectProperty<Variant> variant;
	private final IntegerProperty throwMax;

	public Rules(Variant variant, int throwMax) {
		this.variant = new SimpleObjectProperty<Variant>(variant);
		this.throwMax = new SimpleIntegerProperty(throwMax);
	}

	private static class DiceThrow implements Comparable<DiceThrow> {

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

		public boolean isNenette() {
			if (this.dice[0] == 2 && this.dice[0] ==2 && this.dice[0] ==1) {
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
			if((this.dice[0] == lancerDes2.dice[0]) &&
					(this.dice[1] == lancerDes2.dice[1]) &&
					(this.dice[2] == lancerDes2.dice[2]))
				return 0;
			else if (this.valeur() > lancerDes2.valeur()) {
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
			} else if (this.isSuite() && lancerDes2.isSuite() && this.dice[0] > lancerDes2.dice[0]) {
				return 1;
			} else if (this.isSuite() && lancerDes2.isSuite() && this.dice[0] < lancerDes2.dice[0]) {
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
				return 0; //devrait ne pas être atteint
			}
		}
	}


	public List<User> getWinner(List<PlayerData> l)
	{
		List<DiceThrow> listDice = new ArrayList<DiceThrow>();
		for(PlayerData p : l)
			listDice.add(new DiceThrow(p.getPlayer(),p.getDices()));
		Collections.sort(listDice);

		if(listDice.get(0).valeur()==1 && listDice.get(listDice.size()-1).isNenette())
			Collections.reverse(listDice);

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
		if(l.size()==0)
			return null;
		List<DiceThrow> listDice = new ArrayList<DiceThrow>();
		for(PlayerData p : l)
			listDice.add(new DiceThrow(p.getPlayer(),p.getDices()));
		Collections.sort(listDice);

		if(listDice.get(0).valeur()==1 && listDice.get(listDice.size()-1).isNenette())
			Collections.reverse(listDice);

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

		if(listDice.get(0).valeur()==1 && listDice.get(listDice.size()-1).isNenette())
			Collections.reverse(listDice);

		if(listDice.get(0).isNenette())
			return 2;
		return listDice.get(0).valeur();
	}

	public boolean canReroll(List<PlayerData> l, User actualPlayer, User firstPlayer, boolean isDischarge)
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
		{
//			throw new Exception("Player not found.");
			System.out.println("Failed to find Data : Rules.java");
			return false;
		}
		else if(isDischarge)
		{
			switch(this.variant.get()){
			case FREE_DISCHARGE:
				if (actualPlayerData.getRerollCount() == this.throwMax.get()
						|| (actualPlayerData.getRerollCount() == firstPlayerData.getRerollCount() && !actualPlayer.isSame(firstPlayer)))
					return false;
				return true;
			case FIXED_DISCHARGE:
				if (actualPlayerData.getRerollCount() == this.throwMax.get())
					return false;
				return true;
			case CONSTRAINED_DISCHARGE:
				if (actualPlayerData.getRerollCount() == this.throwMax.get()
						|| (actualPlayerData.getRerollCount() == firstPlayerData.getRerollCount() && !actualPlayer.isSame(firstPlayer)))
					return false;
				return true;
			default:
				// throw new Exception("Etat Incoherent : Rules.java");
				return false;
			}
		}
		else
		{
			return actualPlayerData.getRerollCount() ==0;
		}



	}

	public boolean hasToReroll(List<PlayerData> l, User actualPlayer, User firstPlayer, boolean isDischarge) {
		switch (this.variant.get()) {
		case FREE_DISCHARGE:
			return false;
		case FIXED_DISCHARGE:
			return true;
		case CONSTRAINED_DISCHARGE:
			return this.canReroll(l, actualPlayer, firstPlayer, isDischarge);
		default:
			// throw new Exception("Etat Incoherent : Rules.java");
			return false;
		}
	}


	public final ObjectProperty<Variant> variantProperty() {
		return this.variant;
	}

	public final Variant getVariant() {
		return this.variantProperty().get();
	}

	public final void setVariant(final Variant variant) {
		this.variantProperty().set(variant);
	}

	public final IntegerProperty throwMaxProperty() {
		return this.throwMax;
	}

	public final int getThrowMax() {
		return this.throwMaxProperty().get();
	}

	public final void setThrowMax(final int throwMax) {
		this.throwMaxProperty().set(throwMax);
	}

}
