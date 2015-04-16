package download.images;

import com.yandex.disk.client.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Create list of tasks to download
 *
 * Created by olgaoskina on 05/07/14.
 */
public class ListTask {

    private static volatile List<ListItem> items;

    public ListTask() {
        items = new ArrayList<>();
    }

    public static ListItem getTask() {
        if (items.size() == 0) {
            return null;
        }
        return items.remove(0);
    }

    public void putTask(ListItem item) {
        items.add(item);
    }

}
