package assignment1;

public abstract class Unit {

    //fields
    private Tile position;
    private double hp;
    private int range;
    private String faction;

    //constructor
    public Unit(Tile p, double h, int r, String f) {
        this.position = p;
        this.hp = h;
        this.range = r;
        this.faction = f;
        //problem: I was initializing this.position = addUnit(this) before the if statement which creates problems!!!!
        //if statement is already initializing this.position, previously it would have always returned true?
        if (!this.position.addUnit(this))
            throw new IllegalArgumentException();
    }

    //get methods
    public final Tile getPosition() {
        return this.position;
    }

    public final double getHP() {
        return this.hp;
    }

    public final String getFaction() {
        return this.faction;
    }

    public boolean moveTo(Tile target) {
        if(target == null)
            return false;

        if(target == this.position)
            return true;

        double distance = Tile.getDistance(this.position, target);

        if(distance <= this.range){

            boolean removedFromOriginal = this.position.removeUnit(this);
            if(removedFromOriginal) {
                boolean addedToTarget = target.addUnit(this);
                if(addedToTarget) {
                    this.position = target;
                    return true;
                }
                else {
                    this.position.addUnit(this);
                    return false;
                }
            }
            else
                return false;
        }
        else
            return false;
    }


    public void receiveDamage(double damage) {
        double hp = this.getHP();
        Tile position = this.getPosition();

        if(damage > 0) {
            if (position.isCity())
                this.hp = hp - (damage * 0.9);
            else
                this.hp = hp - damage;
        }
        else if(damage < 0)
            return;

        if(this.hp <= 0)
            position.removeUnit(this);

    }

    public abstract void takeAction(Tile tile);

    public boolean equals(Object obj) {

        if (obj == this)
            return true;
        else if (obj == null)
            return false;
        else if (obj instanceof Unit) {
            Unit other = (Unit) obj;
            double diffHP = Math.pow((other.hp - this.hp), 2);
            if (Tile.getDistance(this.position, other.position) == 0
                    && this.faction.equals(other.faction) && diffHP < 0.001)
                return true;
            else
                return false;
        }
        else
            return false;
    }
}

