package download.images;

import com.yandex.disk.client.Credentials;
import com.yandex.disk.client.ListItem;
import com.yandex.disk.client.TransportClient;
import com.yandex.disk.client.exceptions.WebdavException;
import download.TypeResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olgaoskina
 * 17 April 2015
 */
public class ImageResolver implements TypeResolver {

    private final Credentials credentials;
    private final String downloadFrom;

    public ImageResolver(Credentials credentials, String downloadFrom) {
        this.credentials = credentials;
        this.downloadFrom = downloadFrom;
    }

    @Override
    public List<ListItem> resolve() {
        List<ListItem> photos = new ArrayList<>();
        try {
            TransportClient.getInstance(credentials).getList(downloadFrom, new ImagesListParsingHandler(photos));
        } catch (WebdavException | IOException e) {
            e.printStackTrace();
        }
        return photos;
    }
}
