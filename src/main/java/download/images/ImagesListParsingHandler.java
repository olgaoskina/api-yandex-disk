package download.images;


import com.yandex.disk.client.*;
import com.yandex.disk.client.exceptions.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Download all images to folder PATH_TO_DOWNLOAD_FOLDER
 *
 * Created by olgaoskina on 02.07.14.
 */
public class ImagesListParsingHandler extends ListParsingHandler {

    private String TYPE_IMAGE = "image";
    private ListTask listTask;

    public ImagesListParsingHandler() throws WebdavClientInitException {
        listTask = new ListTask();
    }

    @Override
    public boolean handleItem(ListItem item) {
        if (item.getContentType() != null) {
            Pattern pattern = Pattern.compile(TYPE_IMAGE);
            Matcher matcher = pattern.matcher(item.getContentType());
            if (matcher.find()) {
                listTask.putTask(item);
                return true;
            }
        }
        return false;
    }
}
