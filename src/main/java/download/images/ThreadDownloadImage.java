package download.images;

import com.yandex.disk.client.Credentials;
import com.yandex.disk.client.ListItem;
import com.yandex.disk.client.Log;
import com.yandex.disk.client.TransportClient;
import com.yandex.disk.client.exceptions.WebdavClientInitException;

import java.io.File;

/**
 * One thread that download image from ListTask to PATH_TO_DOWNLOAD_FOLDER
 * <p/>
 * Created by olgaoskina on 04/07/14.
 */
public class ThreadDownloadImage extends Thread {

    private String PATH_TO_DOWNLOAD_FOLDER;
    private TransportClient transportClient;

    public ThreadDownloadImage(Credentials credentials, String pathToDownload) {
        this.PATH_TO_DOWNLOAD_FOLDER = pathToDownload;

        try {
            transportClient = TransportClient.getInstance(credentials);
        } catch (WebdavClientInitException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            ListItem item = ListTask.getTask();
            if (item == null) {
                return;
            } else {
                downloadImage(item);
            }

        }
    }

    private void downloadImage(ListItem item) {
        try {
            Log.i("INFO", "IN_DOWNLOAD_IMAGE");
            transportClient.downloadFile(
                    item.getFullPath(),
                    new File(PATH_TO_DOWNLOAD_FOLDER + item.getName()),
                    new PrintPercentProgressListener(item.getName())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
