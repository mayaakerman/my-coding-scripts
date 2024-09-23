package assignment1;

public class Worker extends Unit{

    //fields
    private int jobsPerformed;

    //constructor
    public Worker(Tile p, double h, String f){
        super(p, h, 2, f);
        this.jobsPerformed = 0;
    }

    public void takeAction(Tile tile) {
        if (this.getPosition() == tile && !tile.isImproved()) {
            tile.buildImprovement();
            jobsPerformed++;
            if(jobsPerformed >= 10)
                this.getPosition().removeUnit(this);
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof Worker)
            return super.equals(obj) && this.jobsPerformed == ((Worker) obj).jobsPerformed;
        else
            return false;
    }

}
