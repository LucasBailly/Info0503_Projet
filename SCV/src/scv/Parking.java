package scv;

import java.util.*;

public class Parking {
    //ATTRIBUTS
    private int NBR_RANGE ;
    private int NBR_PLACE ;

    //CONSTRUCTEUR
    public Parking(int NBR_RANGE, int NBR_PLACE){
        this.NBR_RANGE = NBR_RANGE;
        this.NBR_PLACE = NBR_PLACE;
    }
    public Parking(){
        this.NBR_RANGE = 1;
        this.NBR_PLACE = 10;
    }

    //METHODES
    public int getNbRange(){
        return NBR_RANGE;
    }
    public int getNbPlace(){
        return NBR_PLACE;
    }
}
