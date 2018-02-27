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
        d.mergeSortHand(0, 6);
        System.out.println("Merge sort done!");
        d.shuffleDeck();
        d.dealHand(7);
        System.out.println("Merge sort 2!");
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
        //higherIndex should be index of the last value of the array, there is an object at higherIndex

        //if lowerIndex is not lower than higherIndex, they must be equal, so it's successfully broken down the
        //array to single elements
        //System.out.println("split " + Integer.toString(lowerIndex) + ' ' + Integer.toString(higherIndex));
        if(lowerIndex<higherIndex){
            //find the center of whatever group is being sorted right now, could be the largest or a smaller group
            //since it's an int variable, it automatically rounds to the lowest integer

            // say you're just sorting a two term array, [3,2]
            //lowerIndex is 0, higherIndex is 1 (the number of things in the array), middleIndex is 0
            int middleIndex = lowerIndex + (higherIndex-lowerIndex)/2;

            //now sort the bottom group, come back when you're done
            //if it's a group of just one element, it comes right back
            //if it's a group with more than one element, it sorts that group, from the element at lowerIndex
            // and including the element at the middleIndex
            mergeSortHand(lowerIndex, middleIndex);

            //now sort the top group, come back when you're done
            //sorts the group from the element just after middleIndex and including higherIndex
            mergeSortHand(middleIndex+1, higherIndex);

            //now we have to combine the terms from the lower group and higher group

            //setAside is where I'll stick the elements as I compare them
            //it's +1, since there's we're including the bottomIndex
            Card[] setAside = new Card[(higherIndex-lowerIndex)+1];

            //in the [3,2] example, this will make bottomGroupChecker 0, with value 3
            //and topGroupChecker will be 1, with value 2
            int bottomGroupChecker = lowerIndex;
            //this should compare the elements from lowerIndex and including middleIndex
            int topGroupChecker = middleIndex+1;
            //this should compare the elements from just after middleIndex and including higherIndex
            int elementsSorted = 0;
            //System.out.println("combine " + Integer.toString(lowerIndex) + ' ' + Integer.toString(higherIndex));
            //higherIndex is always out of bounds, we never want to check there
            //middleIndex gets checked by the bottom group
            //bottomGroup wants to stop before middleIndex+1, not check that value, and topGroup wants to stop before
            // higherIndex, not check that value
            while(bottomGroupChecker<middleIndex+1 || topGroupChecker<higherIndex+1){
                //if we're in here, we know that there are values that still have to be checked, either in the
                // bottomGroup, or the topGroup, or both
                if(topGroupChecker==higherIndex+1 ||
                        (hand[bottomGroupChecker].value<hand[topGroupChecker].value&&bottomGroupChecker<middleIndex+1)){
                    //if we're here, we know that either the topGroupChecker is at the higherIndex+1, and we don't
                    // want to check those values anymore, or the bottomGroupChecker is below the middleIndex+1 (so
                    // there are bottomGroup values that remain to be checked) AND the bottomGroup value is lower than
                    // the topGroup value
                    setAside[elementsSorted] = hand[bottomGroupChecker];
                    bottomGroupChecker++;
                }else{
                    //if we're here, then we know that the topGroupChecker is below the higherIndex (so there are
                    // values in the topGroup that still have to be checked), and either the
                    // value at the bottomGroupChecker is lower than that at the topGroupChecker, or the
                    // bottomGroupChecker is at the middleIndex+1, and we don't want to check those values anymore.
                    setAside[elementsSorted] = hand[topGroupChecker];
                    topGroupChecker++;
                }
                //increment elementsSorted so that the next element goes into setAside at the next index
                elementsSorted++;
            }

            //now copy over setAside to hand
            //this.listCards(setAside);
            System.arraycopy(setAside, 0, hand, lowerIndex, setAside.length);
            /*for(int n = 0; n<setAside.length; n++){
                hand[lowerIndex+n] = setAside[n];
            }*/
            this.listCards(hand);
        }
    }

    public void binarySearch(int suit, int face){
        //nothing yet
    }
}