package assignment1;

public class Tile {

    //fields
    private int xCoord;
    private int yCoord;
    private boolean tileCity;
    private boolean tileImprovement;
    private ListOfUnits tileUnits;

    //constructor
    public Tile(int x, int y) {
        this.xCoord = x;
        this.yCoord = y;
        this.tileUnits = new ListOfUnits();
        this.tileCity = false;
        this.tileImprovement = false;
    }

    //methods
    public int getX() {
        return this.xCoord;
    }

    public int getY() {
        return this.yCoord;
    }

    public boolean isCity() {
        return this.tileCity;
    }

    public boolean isImproved() {
        return this.tileImprovement;
    }

    public void buildCity() {
        this.tileCity = true;
    }

    public void buildImprovement() {
        this.tileImprovement = true;
    }

    public boolean addUnit(Unit u) {

        if(u instanceof MilitaryUnit) {
            MilitaryUnit[] tileArmies = this.tileUnits.getArmy();

            if (tileArmies.length == 0) {
                this.tileUnits.addUnit(u);
                return true;
            }
            boolean enemyPresent = false;

            for (int i = 0; i < tileArmies.length; i++){
                if(!u.getFaction().equals(tileArmies[i].getFaction())) {
                    enemyPresent = true;
                    break;
                }
            }
            if (enemyPresent)
                return false;
            else {
                this.tileUnits.addUnit(u);
                return true;
            }
        }
        else {
            this.tileUnits.addUnit(u);
            return true;
        }
    }

    public boolean removeUnit(Unit u) {
        return this.tileUnits.removeUnit(u);
    }
        /*int size = this.tileUnits.getSize();
        Unit[] oldArr = this.tileUnits.getList();
        ListOfUnits newArr = new ListOfUnits();

        int index = this.tileUnits.indexOf(u);
        if (index == -1)
            return false;

        for (int i = index; i < size - 1; i++)
            oldArr[i] = oldArr[i + 1];
        for (int j = 0; j < size - 1; j++)
            newArr.addUnit(oldArr[j]);

        this.tileUnits = newArr;
        return true;
    }

         */


/*
        int size = this.tileUnits.getSize();
        Unit[] oldArr = this.tileUnits.getList();
        ListOfUnits newArr = new ListOfUnits();
        boolean bool = false;

        if (u != null) {
            for (int i = 0; i < size; i++) {
                if (oldArr[i].equals(u)) {
                    bool = true;

                    for (int j = i; i < size - 1; j++)
                        oldArr[j] = oldArr[j + 1];
                    for (int j = 0; j < size - 1; j++)
                        newArr.addUnit(oldArr[j]);

                    this.tileUnits = newArr;
                    return bool;
                }
            }
            return bool;
        }
        return bool;
    }




    /*
        int index = this.tileUnits.indexOf(u);
        if (index == -1)
            return false;

        for (int i = index; i < size - 1; i++)
            oldArr[i] = oldArr[i + 1];
        for (int j = 0; j < size - 1; j++)
            newArr.addUnit(oldArr[j]);

        this.tileUnits = newArr;
        return true;
    }



        /*
        int index = indexOf(u);
        if (index == -1) {
            return false;
        }
        else {
            Unit[] newArr = new Unit[this.size - 1];
            for(int i = 0; i < index; i++)
                newArr[i] = arr[i];
            for(int j = index; j < size-1; j++)
                newArr[j] = arr[j+1];

            arr = newArr;
            size = size-1;
            return true;
        }
        */
        //return this.tileUnits.removeUnit(u);


    public Unit selectWeakEnemy(String f) {
        Unit[] potentialEnemies = this.tileUnits.getList();

        if(potentialEnemies.length == 0)
            return null;

        Unit weakestEnemy = null;
        double weakestHP = Double.MAX_VALUE;

        for (Unit unit : potentialEnemies) {
            if(unit.getFaction().equals(f))
                continue;
            double enemyHP = unit.getHP();
            if(enemyHP<weakestHP) {
                weakestHP = enemyHP;
                weakestEnemy = unit;
            }
        }
        return weakestEnemy;
    }

    public static double getDistance(Tile tile1, Tile tile2) {

        double x1 = tile1.xCoord;
        double y1 = tile1.yCoord;
        double x2 = tile2.xCoord;
        double y2 = tile2.yCoord;

        return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }


}
