package assignment1;

public class Archer extends MilitaryUnit{

    //fields
    private int availableArrows;

    //constructor
    public Archer(Tile p, double h, String f) {
        super(p, h, 2, f, 15.0, 2, 0);
        this.availableArrows = 5;
    }

    public void takeAction(Tile tile) {
        if (this.availableArrows > 0){
            this.availableArrows = this.availableArrows-1;
            super.takeAction(tile);
        }
        else
            this.availableArrows = 5;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Archer)
            return super.equals(obj) && this.availableArrows == ((Archer) obj).availableArrows;
        else
            return false;
    }

}
