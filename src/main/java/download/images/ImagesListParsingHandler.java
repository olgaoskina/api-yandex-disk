package download.images;

import com.yandex.disk.client.ListItem;
import com.yandex.disk.client.ListParsingHandler;
import com.yandex.disk.client.exceptions.WebdavClientInitException;

import java.util.List;

/**
 * Added photos to list of tasks
 * <p>
 * Created by olgaoskina on 02.07.14.
 */
public class ImagesListParsingHandler extends ListParsingHandler {

    private final String TYPE_IMAGE = "image.*";
    private final List<ListItem> items;

    public ImagesListParsingHandler(List<ListItem> items) throws WebdavClientInitException {
        this.items = items;
    }

    @Override
    public boolean handleItem(ListItem item) {
        if (item.getContentType() != null && item.getContentType().matches(TYPE_IMAGE)) {
            items.add(item);
            return true;
        }
        return false;
    }
}