package aurora.engine.V1.Logic.mixpanelapi;

import aurora.engine.V1.Logic.JSON.JSONObject;
import java.io.IOException;
import java.util.List;


/**
 * Thrown when the Mixpanel server refuses to accept a set of messages.
 *
 * This exception can be thrown when messages are too large,
 * event times are too old to accept, the api key is invalid, etc.
 */
public class MixpanelServerException extends IOException {
    private static final long serialVersionUID = 8230724556897575457L;

    public MixpanelServerException(String message, List<JSONObject> badDelivery) {
        super(message);
        mBadDelivery = badDelivery;
    }

    public List<JSONObject> getBadDeliveryContents() {
        return mBadDelivery;
    }

    private final List<JSONObject> mBadDelivery;
}
