package structural.composite;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import structural.composite.Bag;
import structural.composite.Item;

class CompositeTest {

    @Test
    void 단일객체와_복합객체를_동일하게_다룰수있다() {
        Item doranBlade = new Item("도란검", 450);
        Item healPotion = new Item("체력 물약", 50);
        int totalPrice = doranBlade.getPrice() + healPotion.getPrice();

        Bag bag = new Bag();

        bag.add(doranBlade);
        bag.add(healPotion);

        assertEquals(totalPrice, bag.getPrice());
    }

}
