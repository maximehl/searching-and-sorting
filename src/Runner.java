import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

public class Runner {
    public static void main(String[] args) {
        Deck d = new Deck(true);
        d.shuffleDeck();
        d.dealHand(7);
        System.out.println("Bubble sort!");
        d.bubbleSortHand();
        System.out.println("Bubble sort done!");
        d.shuffleDeck();
        d.dealHand(7);
        System.out.println("Selection sort!");
        d.selectionSortHand();
        System.out.println("Selection sort done!");
        d.shuffleDeck();
        d.dealHand(7);
        System.out.println("Merge sort!");
        d.mergeSortHand(0, 7);
        System.out.println("Merge sort done!");
    }
}

class Card{
    int suit;
    int value;
    //String name;
    public Card(int suit, int value){
        this.suit = suit;
        this.value = value;
    }
}

class Deck{
    Card[] allCards;
    Card[] hand;
    public Deck(boolean wholeDeck){
        int index = 0;
        if(wholeDeck){
            allCards = new Card[52];
            for(int suit = 0; suit<4; suit++){
                for(int value = 1; value<14; value++){
                    allCards[index] = new Card(suit, value);
                    index++;
                }
            }
        }else{
            allCards = new Card[13];
            for(int value = 1; value<14; value++){
                allCards[index] = new Card(0, value);
                index++;
            }
        }
    }

    public void swap(int first, int second) {
        Card tempCard = hand[first];
        hand[first] = hand[second];
        hand[second] = tempCard;
    }

    public void dealHand(int numCards){
        hand = new Card[numCards];
        hand = Arrays.copyOfRange(allCards, 0, numCards);
        this.listCards(hand);
    }

    public void listCards(Card[] cardArray){
        String[] suits = new String[] {"♤", "♡", "♢", "♧"};
        String[] returnArray = new String[cardArray.length];
        for(int n = 0; n<cardArray.length; n++){
            returnArray[n] = Integer.toString(cardArray[n].value) + suits[cardArray[n].suit];
        }
        System.out.println(Arrays.toString(returnArray));
    }

    public void shuffleDeck(){
        Collections.shuffle(Arrays.asList(allCards));
    }

    public void bubbleSortHand(){
        for(int lastCheck = hand.length-1; lastCheck>0; lastCheck--){
            for(int n = 0; n<lastCheck; n++){
                if(hand[n].value>hand[n+1].value){
                    this.swap(n, n+1);
                }
            }
            this.listCards(hand);
        }
    }

    public void selectionSortHand(){
        int lowestIndex;
        for(int firstCheck=0; firstCheck<hand.length-1; firstCheck++){
            lowestIndex = firstCheck;
            for(int checkIndex=firstCheck+1; checkIndex<hand.length; checkIndex++){
                if(hand[checkIndex].value<hand[lowestIndex].value){
                    lowestIndex=checkIndex;
                }
            }
            this.swap(firstCheck, lowestIndex);
            this.listCards(hand);
        }
    }

    public void mergeSortHand(int lowerIndex, int higherIndex){
        //if lowerIndex is not lower than higherIndex, they must be equal, so it's successfully broken down the
        //array to single elements
        System.out.println(Integer.toString(lowerIndex) + ' ' + Integer.toString(higherIndex));
        if(lowerIndex<higherIndex){
            //find the center of whatever group is being sorted right now, could be the largest or a smaller group
            //since it's an int variable, it automatically rounds to the lowest integer
            int middleIndex = lowerIndex + (higherIndex-lowerIndex)/2;

            //now sort the bottom group, come back when you're done
            //if it's a group of just one element, it comes right back
            //if it's a group with more than one element, it sorts that group
            mergeSortHand(lowerIndex, middleIndex);

            //now sort the top group, come back when you're done
            mergeSortHand(middleIndex+1, higherIndex);

            //now we have to combine the terms from the lower group and higher group
            // say you're just sorting one array, [3, 2]
            //lowerIndex is 0, higherIndex is 1
            Card[] setAside = new Card[(higherIndex-lowerIndex)/*+1*/];
            int bottomGroupChecker = lowerIndex;
            int topGroupChecker = middleIndex;
            int elemsSorted = 0;
            /*this.listCards(hand);
            System.out.println(Integer.toString(hand[topGroupChecker].value));
            System.out.println(Integer.toString(hand[bottomGroupChecker].value));*/
            while(bottomGroupChecker<middleIndex || topGroupChecker<higherIndex){
                //System.out.println("this works 3");
                if((hand[bottomGroupChecker].value<hand[topGroupChecker].value&&bottomGroupChecker<middleIndex)
                        ||topGroupChecker==higherIndex){
                    //System.out.println("this works 1");
                    setAside[elemsSorted] = hand[bottomGroupChecker];
                    bottomGroupChecker++;
                }else{
                    //System.out.println("this works 2");
                    setAside[elemsSorted] = hand[topGroupChecker];
                    topGroupChecker++;
                }
                elemsSorted++;
            }

            //now copy over setAside to hand
            /*System.out.println("setAside length: " + Integer.toString(setAside.length));
            System.out.println(Arrays.toString(setAside));*/
            this.listCards(setAside);
            for(int n = 0; n<setAside.length; n++){
                hand[lowerIndex+n] = setAside[n];
            }
            this.listCards(hand);
        }
    }

    public void binarySearch(int suit, int face){
        //nothing yet
    }
}