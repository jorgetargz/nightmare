package interfaz.screens.pantallajuego;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PantallaJuegoViewModel {

    private final ObjectProperty<PantallaJuegoState> _state;

    @Inject
    public PantallaJuegoViewModel() {
        _state = new SimpleObjectProperty<>(new PantallaJuegoState(null, null, null, null, null, null, null, null, null, null));
    }

    public ReadOnlyObjectProperty<PantallaJuegoState> getState() {
        return _state;
    }

}
