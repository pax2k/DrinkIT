package no.pax.drinkit.Client;

import no.pax.drinkit.Util.Util;
import org.json.JSONObject;

/**
 * Created: rak
 * Date: 08.10.12
 */
public class WaterPumpClient extends AbstractClient {
    WaterPhidgetConnection connection;

    public WaterPumpClient() {
        super(Util.WATER_CLIENT);
        connection = new WaterPhidgetConnection();
        connection.start();
    }

    public static void main(String[] args) {
        new WaterPumpClient();
    }

    @Override
    public void onMessage(String data) {
        final JSONObject jsonObject = Util.convertToJSon(data);
        final String from = Util.getValueFromJSon(jsonObject, "from");
        final String value = Util.getValueFromJSon(jsonObject, "value");

        if (Util.WEB_VIEW_WATER_CLIENT.equals(from)) {
            if ("ON".equals(value)) {
                System.out.println("TURN PUMP ON");
                connection.communicateWithPort(true);
            } else if ("OFF".equals(value)) {
                System.out.println("TURN PUMP OFF");
                connection.communicateWithPort(false);
            }
        }
    }
}
