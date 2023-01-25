package interfaz.screens.pantallajuego;

import game.character.Creature;
import game.character.Wizard;
import game.object.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PantallaJuegoState {
    // propias de la pantalla
    private LocalTime tiempo;
    private String descripcion;
    private Integer puertas;
    private Creature creature;
    private String imagenFondo;

    // recolectar
    private List<SingaCrystal> cristales;
    private List<Ring> rings;
    private List<Necklace> necklaces;
    private List<Weapon> weapons;

    // mago
    private Wizard mago;
}