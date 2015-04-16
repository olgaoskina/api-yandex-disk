package download;

import com.yandex.disk.client.Log;
import com.yandex.disk.client.ProgressListener;

/**
 * Print percentage of download
 * <p>
 * Created by olgaoskina on 02.07.14.
 */
public class PrintPercentProgressListener implements ProgressListener {

    private boolean cancelled = false;
    private long lastPercent = -1;
    private String fileName;

    public PrintPercentProgressListener(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void updateProgress(long loaded, long total) {
        long percent = loaded * 100 / total;
        if (percent % 10 == 0 && percent != lastPercent) {
            Log.i("[LOAD " + fileName + "]", String.valueOf(percent) + "%");
            lastPercent = percent;
        }
        if (total == loaded) {
            cancelled = true;
        }
    }

    @Override
    public boolean hasCancelled() {
        return cancelled;
    }
}
