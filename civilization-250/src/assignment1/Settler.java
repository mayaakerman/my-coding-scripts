package assignment1;

public class Settler extends Unit{

    //constructor
    public Settler(Tile p, double h, String f) {
        super(p, h, 2, f);
    }

    public void takeAction(Tile tile) {
        if (this.getPosition() == tile && !tile.isCity()) {
            tile.buildCity();
            this.getPosition().removeUnit(this);
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof Settler)
            return super.equals(obj);
        else
            return false;
    }

}
