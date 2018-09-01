package nl.limakajo.numbers.sync;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by mwbou on 12-12-2017.
 */

public class NumbersSyncIntentService extends IntentService {

    public NumbersSyncIntentService() {
        super("NumberSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        NumbersSyncTasks.sync(this, action);
    }
}
