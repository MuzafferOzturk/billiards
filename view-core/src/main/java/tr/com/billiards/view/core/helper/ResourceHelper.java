package tr.com.billiards.view.core.helper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.PropertyResourceBundle;

public class ResourceHelper {
    private static ResourceHelper instance;
    private PropertyResourceBundle resourceBundle;

    private ResourceHelper() {
        try {
            resourceBundle = new PropertyResourceBundle(new InputStreamReader(this.getClass().getResourceAsStream("/label.properties")
                    , StandardCharsets.UTF_8));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ResourceHelper getInstance() {
        if (instance == null)
            instance = new ResourceHelper();
        return instance;
    }

    public PropertyResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
