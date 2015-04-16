package download.images;

import com.yandex.disk.client.Credentials;

/**
 * Create SIZE threads and run
 * <p/>
 * Created by olgaoskina on 04/07/14.
 */
public class RunDownload {
    private final int SIZE;
    private String PATH_TO_DOWNLOAD_FOLDER;
    private ThreadDownloadImage[] threadDownloadImages;

    public RunDownload(final int countOfThread, String pathToDownloadFolder, Credentials credentials) {
        this.SIZE = countOfThread;
        PATH_TO_DOWNLOAD_FOLDER = pathToDownloadFolder;
        threadDownloadImages = new ThreadDownloadImage[SIZE];
        for (int i = 0; i < SIZE; i++) {
            threadDownloadImages[i] = new ThreadDownloadImage(credentials, PATH_TO_DOWNLOAD_FOLDER);
        }
        run();
    }

    private void run() {
        for (int i = 0; i < SIZE; i++) {
            threadDownloadImages[i].start();
        }
    }

    public boolean isAliveSomeThread() {
        boolean isAlive = false;
        for (int i = 0; i < SIZE; i++) {
            isAlive |= threadDownloadImages[i].isAlive();
        }
        return isAlive;
    }
}
