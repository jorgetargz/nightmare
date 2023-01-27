package interfaz.screens.pantallajuego;

import interfaz.screens.common.BaseScreenController;
import jakarta.inject.Inject;

public class PantallaJuegoController extends BaseScreenController {

    private final PantallaJuegoViewModel viewModel;

    @Inject
    public PantallaJuegoController(PantallaJuegoViewModel viewModel) {
        this.viewModel = viewModel;
    }


//    //Pongamos que en la ventana "padre" tengo una lista llamada "listaDeLaVentanaPadre"
////que quiero modificar desde la ventana "hija" y obtener el resultado de nuevo en la
////ventana "padre"
//
////En la ventana "hija" tengo un método al cual llamo desde la ventana "padre" antes de
////cargarla
//public void parametros(ArrayList<String>() lista, ControladorVentanaPadre cvp){
//    miCvp = cvp;
//    miLista = lista
//}
//
////También en la ventana "hija", después de manipular la lista, quiero devolvérsela a
////la ventana "padre".
////Para lograrlo, en el método asignado en este caso al botón "Aceptar" ...
//public void aceptar(){
//    miCvp.listaDeLaVentanaPadre = miLista;
//    //La siguiente línea cierra la ventana
//    botonAceptar.getScene().getWindow().close();
//}

    // otra opcion es crear una clase que ambos controladores hereden y que tenga un metodo que devuelva el mago :)

    //9.20 nos queremos morir, no sabemos por dónde empezar
    //9.30 nos hemos dado cuenta de que no tenemos ni idea de cómo hacerlo

}