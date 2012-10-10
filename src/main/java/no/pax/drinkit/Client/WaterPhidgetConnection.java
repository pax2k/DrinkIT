package no.pax.drinkit.Client;

import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;

/**
 * @author raymond
 */
public class WaterPhidgetConnection {
    private InterfaceKitPhidget interfaceKitPhidget;
    private static final int portNumber = 10;

    public void start() {
        try {
            interfaceKitPhidget = new InterfaceKitPhidget();
            interfaceKitPhidget.openAny();
            interfaceKitPhidget.waitForAttachment();

            System.out.println("Phidget controller connected!");
        } catch (PhidgetException e) {
            e.printStackTrace();
        }
    }

    public void communicateWithPort(boolean on) {
        if (null == interfaceKitPhidget) {
            throw new RuntimeException(
                    "PhidgetController, interfaceKitPhidget is null!");
        }
        try {
            interfaceKitPhidget.setOutputState(portNumber, on);
        } catch (PhidgetException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (null == interfaceKitPhidget) {
            throw new RuntimeException(
                    "PhidgetController, interfaceKitPhidget is null!");
        }

        try {
            interfaceKitPhidget.close();
            interfaceKitPhidget = null;

            System.out.println("Phidget controller closed!");
        } catch (PhidgetException e) {
            e.printStackTrace();
        }
    }
}
