package lermitage.intellij.ilovedevtoys;

import com.intellij.ide.AppLifecycleListener;
import com.intellij.ide.plugins.cl.PluginAwareClassLoader;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.concurrency.AppExecutorUtil;
import com.intellij.util.io.HttpRequests;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
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

                String pluginVersion = getPluginVersion();

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

    private String toBase64(String text) {
        if (text.isBlank()) {
            return "";
        }
        try {
            return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String getPluginVersion() {
        Class<?> myClass = this.getClass();
        try {
            if (myClass.getClassLoader() instanceof PluginAwareClassLoader c) {
                return c.getPluginDescriptor().getVersion();
            }
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        try (InputStream pluginXmlResStream = myClass.getResourceAsStream("/META-INF/plugin.xml")) {
            if (pluginXmlResStream != null) {
                String pluginXml = new String(pluginXmlResStream.readAllBytes(), StandardCharsets.UTF_8);
                LOGGER.warn(">>" + pluginXml);
                Optional<String> versionLineOpt = pluginXml.lines().filter(s ->
                    s.contains("<version>") && s.contains("</version>")
                ).findFirst();
                if (versionLineOpt.isPresent()) {
                    return versionLineOpt.get().replace("<version>", "").replace("</version>", "").trim();
                }
            }
        } catch (Exception e) {
            LOGGER.warn(e);
        }
        return "<unknown>";
    }
}
