package lermitage.intellij.ilovedevtoys;

import com.intellij.ide.AppLifecycleListener;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.util.concurrency.AppExecutorUtil;
import com.intellij.util.io.HttpRequests;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyAppLifecycleListener implements AppLifecycleListener {

    private static final @NonNls Logger LOGGER = Logger.getInstance(MyAppLifecycleListener.class);

    @Override
    public void appFrameCreated(@NotNull List<String> commandLineArgs) {
        AppExecutorUtil.getAppScheduledExecutorService().schedule(() -> {

            if ("false".equals(System.getProperty("extra-tools-pack.data-sharing", "true"))) {
                return;
            }

            try {
                ApplicationInfo applicationInfo = ApplicationInfo.getInstance();

                String ideName = applicationInfo.getFullApplicationName().replace(applicationInfo.getFullVersion(), "").trim();
                if (ideName.isBlank()) {
                    ideName = applicationInfo.getFullApplicationName().trim();
                }

                String ideVersion = applicationInfo.getApiVersion();

                String pluginID = "lermitage.intellij.ilovedevtoys";

                String pluginLicenseType = "free";

                String pluginVersion = "";
                try {
                    IdeaPluginDescriptor plugin = PluginManager.getInstance().findEnabledPlugin(PluginId.getId(pluginID));
                    if (plugin != null) {
                        pluginVersion = plugin.getVersion();
                    }
                } catch (Exception e) {
                    LOGGER.warn("Failed to detect plugin: " + pluginID, e);
                }

                String requestBody = "{" +
                    "\"ideName\":\"" + toBase64(ideName) + "\", " +
                    "\"ideVersion\":\"" + toBase64(ideVersion) + "\", " +
                    "\"pluginID\":\"" + pluginID + "\", " +
                    "\"pluginLicenseType\":\"" + pluginLicenseType + "\", " +
                    "\"pluginVersion\":\"" + pluginVersion + "\"" +
                    "}";
                LOGGER.info("Found ideName: " + ideName + ", ideVersion: " + ideVersion + ", sending stats (ideName and ideVersion are Base64 encoded): " + requestBody);
                HttpRequests.post("https://www.extratoolspack.com/api/stats.php", HttpRequests.JSON_CONTENT_TYPE)
                    .connect(request -> {
                        request.write(requestBody);
                        request.getConnection();
                        return "";
                    });
            } catch (Exception e) {
                LOGGER.warn("Failed to send stats", e);
            }

        }, 4, TimeUnit.SECONDS);
    }

    private static String toBase64(String text) {
        if (text.isBlank()) {
            return "";
        }
        try {
            return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
