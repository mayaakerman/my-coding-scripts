package assignment1;

public class ListOfUnits {

    //refer to ArrayList lecture
    //fields
    private Unit[] arr;
    private int size;

    //constructor
    public ListOfUnits() {
        arr = new Unit[10];
        size = 0;
    }

    //methods
    public int getSize() {
        return this.size;
    }

    public Unit[] getList() {
        int s = this.arr.length;
        int count = 0;

        for(int i = 0; i < s; i++){
            if(this.arr[i] != null)
                count++;
        }

        Unit[] c = new Unit[count];
        if(c.length ==0)
            return c;

        int index = 0;
        for(int i = 0; i < s; i++) {
            if(this.arr[i] != null) {
                c[index] = this.arr[i];
                index++;
            }
        }
        return c;
    }

    /*
    public Unit[] getList() {
        int s = this.size;
        Unit[] c = new Unit[s];
        if(c.length ==0)
            return c;
        for(int i = 0; i<s; i++) {
            if(c[i] != null)
                c[i] = this.arr[i];
        }
        return c;
    }
     */

    public Unit getUnit(int i){
        if (i >= 0 && i<this.size)
            return this.arr[i];
        else
            throw new IndexOutOfBoundsException();
    }

    public void addUnit(Unit u) {
        if(u == null)
            return;
        if (this.arr.length == this.size)
            resize();
        if (this.arr.length == 0) {
            this.arr = new Unit[10];
            this.size = 0;
        }
        this.arr[size] = u;
        size++;
    }

    private void resize(){
        Unit[] bigger = new Unit[arr.length*2];
        for(int i = 0; i < size; i++){
            bigger[i] = arr[i];
        }
        arr = bigger;
    }

    public int indexOf(Unit u){
        int i = 0;
        while (i < size) {
            if (arr[i].equals(u))
                return i;
            else
                i++;
        }
        return -1;
    }

    public boolean removeUnit(Unit u) {
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
    }

    public MilitaryUnit[] getArmy(){

        MilitaryUnit[] tempArmy = new MilitaryUnit[this.size];
        int armySize = 0;

        for(Unit unit : this.getList()) {
            if (unit instanceof MilitaryUnit) {
                tempArmy[armySize] = (MilitaryUnit) unit;
                armySize++;
            }
        }
        MilitaryUnit[] finalArmy = new MilitaryUnit[armySize];
        for(int i = 0; i < armySize; i++)
            finalArmy[i] = tempArmy[i];

        return finalArmy;
    }


}
