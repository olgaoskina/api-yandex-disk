package download.images;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.yandex.disk.client.Credentials;
import com.yandex.disk.client.Log;
import com.yandex.disk.client.TransportClient;
import com.yandex.disk.client.exceptions.WebdavException;

import java.io.IOException;

/**
 * Runner for download photos
 * <p>
 * Created by olgaoskina on 02.07.14.
 */

public class RunDownloaderTool {

    public static Object sync = new Object();
    public static volatile int count = 0;

    @Parameter(names = {"-token"},
            description = "Token",
            required = true
    )
    private String TOKEN;

    @Parameter(names = {"-username", "-name"},
            description = "User name on Yandex Disk",
            required = true
    )
    private String USERNAME;

    @Parameter(names = {"-to-download", "-to", "-t"},
            description = "Local path to download images",
            required = true
    )
    private String TO_DOWNLOAD;

    @Parameter(names = {"-from-download", "-from", "-f"},
            description = "Path on Yandex Disk from download",
            required = true
    )
    private String FROM_DOWNLOAD;

    @Parameter(names = {"-count-of-threads", "-c"},
            description = "Count of thread",
            required = true
    )
    private int COUNT_OF_THREAD;

    @Parameter(names = {"-h", "-help"},
            help = true
    )
    private boolean help = false;

    public static void main(String[] args) {
        RunDownloaderTool runDownloaderTool = new RunDownloaderTool();
        JCommander jCommander = new JCommander(runDownloaderTool, args);
        if (runDownloaderTool.help) {
            jCommander.usage();
        } else {
            Log.d("[WORK]", "START");
            try {
                runDownloaderTool.run();
            } catch (WebdavException | IOException | InterruptedException e) {
                Log.d("[ERROR]: ", "exception when run", e);
            }
            Log.d("[WORK]", "DONE!");
        }
    }

    private void run() throws WebdavException, IOException, InterruptedException {
        Credentials credentials = new Credentials(USERNAME, TOKEN);
        TransportClient transportClient = TransportClient.getInstance(credentials);
        transportClient.getList(FROM_DOWNLOAD, new ImagesListParsingHandler());

        RunDownload runDownload = new RunDownload(COUNT_OF_THREAD, TO_DOWNLOAD, credentials);

        while (runDownload.isAliveSomeThread()) {
            Thread.sleep(1000);
        }
    }
}
