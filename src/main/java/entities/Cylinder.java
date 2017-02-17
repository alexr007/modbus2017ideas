package entities;

/**
 * Created by alexr on 07.02.2017.
 *
 * Реализация гидроцилиндра
 * ========================
 *
 * для работы цилиндра надо два клапана:
 * один - на закрытие
 * один - на открытие
 *
 */
public class Cylinder extends AbstractEntity {
    private final Valve valveToOpen;
    private final Valve valveToClose;

    public Cylinder(Valve valveToOpen, Valve valveToClose) {
        this.valveToOpen = valveToOpen;
        this.valveToClose = valveToClose;
    }

    public void open() throws Exception {
        valveToClose.off();
        valveToOpen.on();
    }

    public void close() throws Exception {
        valveToOpen.off();
        valveToClose.on();
    }

    public void stop() throws Exception {
        valveToOpen.off();
        valveToClose.off();
    }
}
