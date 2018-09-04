package nl.limakajo.numbers.sync;

import android.app.IntentService;
import android.content.Intent;

import java.util.Objects;

/**
 * @author M.W.Bouwkamp
 */

public class NumbersSyncIntentService extends IntentService {

    public NumbersSyncIntentService() {
        super("NumberSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        NumbersSyncTasks.sync(this, Objects.requireNonNull(action));
    }
}
