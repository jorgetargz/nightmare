package game.dungeon;

public class PhysicalAttack implements Attack {

    int value;

    public PhysicalAttack(int value) {
        this.value = value;
    }

    @Override
    public int getAttackValue() {
        return value;
    }
}
