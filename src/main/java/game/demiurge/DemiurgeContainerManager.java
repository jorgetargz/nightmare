package game.demiurge;

import game.dungeon.object.container.Container;
import game.dungeon.object.exceptions.ContainerFullException;
import game.dungeon.object.exceptions.ContainerInvalidExchangeException;
import game.dungeon.object.exceptions.ContainerUnacceptedItemException;
import game.dungeon.object.item.Item;

public class DemiurgeContainerManager {

    Container wearables;
    Container bag;
    Container site;

    DemiurgeContainerManager(Container w, Container b, Container s) {
        wearables = w;
        bag = b;
        site = s;
    }

    public Container getWearables() {
        return wearables;
    }

    public Container getBag() {
        return bag;
    }

    public Container getSite() {
        return site;
    }

    public void setSite(Container site) {
        this.site = site;
    }

    public void deleteItem(Container c, int aIndex) {
        c.remove(aIndex);
    }

    public void addItem(Container a, int aIndex, Container b) throws ContainerUnacceptedItemException, ContainerFullException {
        Item aItem = a.get(aIndex);
        b.add(aItem);
        a.remove(aIndex);
    }

    public void exchangeItem(Container a, int aIndex, Container b, int bIndex) throws ContainerInvalidExchangeException {
        Item aItem = a.get(aIndex);
        Item bItem = a.get(bIndex);

        if (aItem.getClass().equals(bItem.getClass())) {
            a.remove(aIndex);
            b.remove(bIndex);

            try {
                a.add(bItem);
                b.add(aItem);
            } catch (ContainerFullException | ContainerUnacceptedItemException ignored) {
            }
        } else {
            throw new ContainerInvalidExchangeException();
        }
    }
}
