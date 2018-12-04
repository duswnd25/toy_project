package app.kimyeonjung.visionmirror.core.bus;

import com.squareup.otto.Bus;

public final class BusProvider {
    private static final CustomBus BUS = new CustomBus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // Singleton
    }
}