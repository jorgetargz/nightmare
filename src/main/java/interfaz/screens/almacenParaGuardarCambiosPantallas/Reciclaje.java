package interfaz.screens.almacenParaGuardarCambiosPantallas;

import game.character.Wizard;
import game.dungeon.Dungeon;
import game.dungeon.Home;
import jakarta.inject.Singleton;
import lombok.Data;

@Data
@Singleton
public class Reciclaje {

    private Home home;

    private Dungeon dungeon;

    private Wizard wizard;

    private Integer currentRoom;


}
