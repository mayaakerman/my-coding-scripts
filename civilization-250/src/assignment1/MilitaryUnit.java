package assignment1;

public abstract class MilitaryUnit extends Unit{

    //fields
    private double attackDamage;
    private int attackRange;
    private int armor;

    //constructor
    public MilitaryUnit(Tile p, double h, int r, String f, double ad, int ar, int a) {
        super(p, h, r, f);
        this.attackDamage = ad;
        this.attackRange = ar;
        this.armor = a;
    }

    public void takeAction(Tile tile) {

        Tile position = this.getPosition();
        String faction = this.getFaction();
        Unit weakestEnemy;

        if(Tile.getDistance(position, tile) <= this.attackRange) {
            weakestEnemy = tile.selectWeakEnemy(faction);
            if(weakestEnemy != null) {
                if(getPosition().isImproved())
                    weakestEnemy.receiveDamage(this.attackDamage*1.05);
                else
                    weakestEnemy.receiveDamage(this.attackDamage);
            }
        }
    }

    public void receiveDamage(double damage) {
        double multiplier = ((double) 100 /(100+this.armor));
        super.receiveDamage(damage*multiplier);
    }

}
