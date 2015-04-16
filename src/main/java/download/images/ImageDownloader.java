package download.images;

import com.yandex.disk.client.Credentials;
import com.yandex.disk.client.ListItem;
import com.yandex.disk.client.TransportClient;
import download.Downloader;
import download.PrintPercentProgressListener;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by olgaoskina
 * 17 April 2015
 */
public class ImageDownloader implements Downloader {

    private final List<ListItem> items;
    private final Credentials credentials;
    private final Path downloadTo;

    public ImageDownloader(Credentials credentials, List<ListItem> items, Path downloadTo) {
        this.items = items;
        this.credentials = credentials;
        this.downloadTo = downloadTo;
    }

    @Override
    public void download() {
        new File(downloadTo.toString()).mkdir();
        items.parallelStream()
                .map(ListItem::getFullPath)
                .forEach(fullPath -> {
                    String name = new File(fullPath).getName();
                    try {
                        TransportClient
                                .getInstance(credentials)
                                .downloadFile(
                                        fullPath,
                                        new File(downloadTo
                                                .resolve(name)
                                                .toString()
                                        ),
                                        new PrintPercentProgressListener(name)
                                );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
