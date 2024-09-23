package assignment2;
import java.awt.Color;
import assignment2.food.*;


public class MyTest {

    public static void main(String[] args) {

        Position startingPoint = new Position(3, 2);
        Caterpillar gus = new Caterpillar(startingPoint, GameColors.GREEN, 10);

        System.out.println("1) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        /* testing getSegmentColor()
        Position pos = new Position(3,2);
        System.out.println(GameColors.colorToString(gus.getSegmentColor(pos)));
        */

        gus.move(new Position(3,1));
        gus.eat(new Fruit(GameColors.RED));
        gus.move(new Position(2,1));
        gus.move(new Position(1,1));
        gus.eat(new Fruit(GameColors.YELLOW));

        System.out.println("\n2) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        gus.move(new Position(1,2));
        gus.eat(new IceCream());

        System.out.println("\n3) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        gus.move(new Position(3,1));
        gus.move(new Position(3,2));
        gus.eat(new Fruit(GameColors.ORANGE));

        System.out.println("\n4) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        gus.move(new Position(2,2));
        gus.eat(new SwissCheese());

        System.out.println("\n5) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

        gus.move(new Position(2, 3));
        gus.eat(new Cake(4));

        System.out.println("\n6) Gus: " + gus);
        System.out.println("Stack of previously occupied positions: " + gus.positionsPreviouslyOccupied);

    }
}