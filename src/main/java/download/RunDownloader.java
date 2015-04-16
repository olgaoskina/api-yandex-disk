package download;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.yandex.disk.client.Credentials;
import com.yandex.disk.client.ListItem;
import com.yandex.disk.client.Log;
import download.images.ImageDownloader;
import download.images.ImageResolver;

import java.nio.file.Paths;
import java.util.List;

/**
 * Runner for download photos
 * <p>
 * Created by olgaoskina on 02.07.14.
 */

public class RunDownloader {

    @Parameter(
            names = {"-token"},
            description = "Token",
            required = true
    )
    private String userToken;

    @Parameter(
            names = {"-username", "-name"},
            description = "User name on Yandex Disk",
            required = true
    )
    private String username;

    @Parameter(
            names = {"-to-download", "-to", "-t"},
            description = "Local path to download images",
            required = false
    )
    private String downloadTo = "/tmp";

    @Parameter(
            names = {"-from-download", "-from", "-f"},
            description = "Path on Yandex Disk from download",
            required = true
    )
    private String downloadFrom;

    @Parameter(names = {"-count-of-threads", "-c"},
            description = "Count of thread",
            required = false
    )
    private int threadCount = 4;

    @Parameter(names = {"-h", "-help"},
            help = true
    )
    private boolean help = false;

    public static void main(String[] args) {
        RunDownloader runDownloader = new RunDownloader();
        JCommander jCommander = new JCommander(runDownloader, args);
        if (runDownloader.help) {
            jCommander.usage();
        } else {
            Log.d("[WORK]", "START");
            runDownloader.run();
            Log.d("[WORK]", "DONE!");
        }
    }

    private void run() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", String.valueOf(threadCount));
        Credentials credentials = new Credentials(username, userToken);
        List<ListItem> photos = new ImageResolver(credentials, downloadFrom).resolve();
        new ImageDownloader(credentials, photos, Paths.get(downloadTo)).download();
    }
}
