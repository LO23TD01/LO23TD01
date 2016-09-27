/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23proto421;

import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

/**
 *
 * @author Proprietaire
 */
public class Lo23proto421 {

    /**
     * @param args the command line arguments
     */
    public static int lancerDe1() {
        return (int) (Math.random() * 6) + 1;
    }

    public static int[] lancerDe3() {
        return new int[]{lancerDe1(), lancerDe1(), lancerDe1()};
    }

    public static class LancerDes
            implements Comparable<LancerDes> {

        public int proprietaire = 0;
        public int[] des;

        public LancerDes(int proprietaire, int[] des) {
            this.proprietaire = proprietaire;
            this.des = des;
            Arrays.sort(this.des);
        }

        public LancerDes(int proprietaire) {
            this.proprietaire = proprietaire;
            this.des = lancerDe3();
            Arrays.sort(this.des);
        }

        public boolean isTriple() {
            if (this.des[0] == this.des[1] && this.des[0] == this.des[2]) {
                return true;
            }
            return false;
        }

        public boolean isPaireAs() {
            if (this.des[0] == this.des[1] && this.des[0] == 1) {
                return true;
            }
            return false;
        }

        public boolean isSuite() {
            if (this.des[0] == (this.des[1] - 1) && this.des[0] == (this.des[2] - 2)) {
                return true;
            }
            return false;
        }

        public boolean relancerDes(boolean de0, boolean de1, boolean de2) {
            if (de0) {
                this.des[0] = lancerDe1();
            }
            if (de1) {
                this.des[1] = lancerDe1();
            }
            if (de2) {
                this.des[2] = lancerDe1();
            }
            Arrays.sort(this.des);
            return de0 || de1 || de2;
        }

        public int valeur() {
            if (this.des[2] == 4 && this.des[1] == 2 && this.des[0] == 1) //421
            {
                return 10;
            }
            if (this.des[1] == 1 && this.des[0] == 1) // double as
            {
                if (this.des[2] == 1) {
                    return 7;
                }
                return this.des[2];
            }
            for (int i = 2; i < 7; i++) // triples
            {
                if (this.des[2] == i && this.des[1] == i && this.des[0] == i) {
                    return i;
                }
            }
            for (int i = 1; i < 4; i++) // suites
            {
                if (this.des[2] == (i + 2) && this.des[1] == (i + 1) && this.des[0] == i) {
                    return 2;
                }
            }

            return 1;
        }

        public int compareTo(LancerDes lancerDes2) {
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
            } else if (this.isSuite() && lancerDes2.isSuite() && this.des[0] > this.des[1]) {
                return 1;
            } else if (this.isSuite() && lancerDes2.isSuite() && this.des[0] < this.des[1]) {
                return -1;
            } else {
                if (this.des[2] > lancerDes2.des[2]) {
                    return 1;
                }
                if (this.des[2] < lancerDes2.des[2]) {
                    return -1;
                }
                if (this.des[1] > lancerDes2.des[1]) {
                    return 1;
                }
                if (this.des[1] < lancerDes2.des[1]) {
                    return -1;
                }
                if (this.des[0] > lancerDes2.des[0]) {
                    return 1;
                }
                if (this.des[0] < lancerDes2.des[0]) {
                    return -1;
                }
                return 0;
            }
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here

        Scanner sc = new Scanner(System.in);
        System.out.format("SALUT\nCombien de Joueurs ?\n");
        int joueursNb = sc.nextInt();
        System.out.format("%d Joueurs \n", joueursNb);
        System.out.format("Combien de jetons ?\n");
        int jetonUnused = sc.nextInt(); //int jetonUnused = 21; 
        System.out.format("%d jetons \n", jetonUnused);

        //recherche du premier Joueur
        int joueurActuel = 0;
        ArrayList<LancerDes> desLances = new ArrayList<LancerDes>();
        while (joueurActuel < joueursNb) {
            System.out.format("Joueur %d : Lancer les dés !", joueurActuel);
            sc.next();
            LancerDes lancerActuel = new LancerDes(joueurActuel);
            System.out.format("Dés : %d %d %d\n", lancerActuel.des[0], lancerActuel.des[1], lancerActuel.des[2]);
            desLances.add(lancerActuel);
            joueurActuel++;
        }
        Collections.sort(desLances);
        joueurActuel = desLances.get(0).proprietaire; // le plus bas devient le premier à commencer;
        desLances.clear();

        System.out.format("Joueur %d : Perd !\n", joueurActuel);

        //Charge
        System.out.format("CHARGE\n\n\n");

        int[] jetonJoueurs = new int[joueursNb];
        while (jetonUnused != 0) {
            System.out.format("Joueur %d : Lancer les dés !", joueurActuel);
            sc.next();
            LancerDes lancerActuel = new LancerDes(joueurActuel);
            System.out.format("Dés : %d %d %d\n", lancerActuel.des[0], lancerActuel.des[1], lancerActuel.des[2]);
            int valeur = lancerActuel.valeur();
            System.out.format("Valeur : %d \n", valeur);
            if (valeur > jetonUnused) {
                valeur = jetonUnused;
            }
            jetonJoueurs[joueurActuel] += valeur;
            jetonUnused -= valeur;
            joueurActuel++;
            if (joueurActuel == joueursNb) {
                joueurActuel = 0;
            }
        }
        System.out.format("Jetons :\n");
        for (int i = 0; i < joueursNb; i++) {
            System.out.format("Joueur %d : %d\n", i, jetonJoueurs[i]);
        }

        //decharge
        System.out.format("DECHARGE\n\n\n");

        boolean partieFinie = false;
        while (!partieFinie) {
            //recherche du premier joueur
            //todo
            int premierJoueur = joueurActuel;
            System.out.format("Joueur %d : Lancer les dés !", joueurActuel);
            sc.next();
            int inputInt;
            LancerDes lancerActuel = new LancerDes(joueurActuel);
            System.out.format("Dés : %d %d %d\n", lancerActuel.des[0], lancerActuel.des[1], lancerActuel.des[2]);
            int nbLancerPremier = 1;
            for (nbLancerPremier = 1; nbLancerPremier < 3; nbLancerPremier++) {
                boolean relancerDe0, relancerDe1, relancerDe2;
                System.out.format("Relancer de n°0 ? (0/1) : ");
                inputInt = sc.nextInt();
                if (inputInt == 1) {
                    relancerDe0 = true;
                } else {
                    relancerDe0 = false;
                }
                System.out.format("Relancer de n°1 ? (0/1) : ");
                inputInt = sc.nextInt();
                if (inputInt == 1) {
                    relancerDe1 = true;
                } else {
                    relancerDe1 = false;
                }
                System.out.format("Relancer de n°2 ? (0/1) : ");
                inputInt = sc.nextInt();
                if (inputInt == 1) {
                    relancerDe2 = true;
                } else {
                    relancerDe2 = false;
                }
                lancerActuel.relancerDes(relancerDe0, relancerDe1, relancerDe2);
                if (!(relancerDe0 || relancerDe1 || relancerDe2)) {
                    break;
                }
                System.out.format("Dés : %d %d %d\n", lancerActuel.des[0], lancerActuel.des[1], lancerActuel.des[2]);
            }
            desLances.add(lancerActuel);

            joueurActuel++;
            if (joueurActuel == joueursNb) {
                joueurActuel = 0;
            }

            while (joueurActuel != premierJoueur) {
                System.out.format("Joueur %d : Lancer les dés !", joueurActuel);
                sc.next();
                lancerActuel = new LancerDes(joueurActuel);
                System.out.format("Dés : %d %d %d\n", lancerActuel.des[0], lancerActuel.des[1], lancerActuel.des[2]);
                for (int i = 1; i < nbLancerPremier; i++) {
                    boolean relancerDe0, relancerDe1, relancerDe2;
                    System.out.format("Relancer de n°0 ? (0/1) : ");
                    inputInt = sc.nextInt();
                    if (inputInt == 1) {
                        relancerDe0 = true;
                    } else {
                        relancerDe0 = false;
                    }
                    System.out.format("Relancer de n°1 ? (0/1) : ");
                    inputInt = sc.nextInt();
                    if (inputInt == 1) {
                        relancerDe1 = true;
                    } else {
                        relancerDe1 = false;
                    }
                    System.out.format("Relancer de n°2 ? (0/1) : ");
                    inputInt = sc.nextInt();
                    if (inputInt == 1) {
                        relancerDe2 = true;
                    } else {
                        relancerDe2 = false;
                    }
                    lancerActuel.relancerDes(relancerDe0, relancerDe1, relancerDe2);
                    if (!(relancerDe0 || relancerDe1 || relancerDe2)) {
                        break;
                    }
                    System.out.format("Dés : %d %d %d\n", lancerActuel.des[0], lancerActuel.des[1], lancerActuel.des[2]);
                }
                desLances.add(lancerActuel);

                joueurActuel++;
                if (joueurActuel == joueursNb) {
                    joueurActuel = 0;
                }
            }

            Collections.sort(desLances);
            int pot = 0;

            for (int i = 1; i < desLances.size(); i++) {
                int valeur = desLances.get(i).valeur();
                if (valeur > jetonJoueurs[desLances.get(i).proprietaire]) {
                    valeur = jetonJoueurs[desLances.get(i).proprietaire];
                    partieFinie = true;
                }
                jetonJoueurs[desLances.get(i).proprietaire] -= valeur;
                pot += valeur;
            }
            jetonJoueurs[desLances.get(0).proprietaire] += pot;
            System.out.format("Jetons :\n");
            for (int i = 0; i < joueursNb; i++) {
                System.out.format("Joueur %d : %d\n", i, jetonJoueurs[i]);
            }
        }

        System.out.format("FIN \n");
    }

}
