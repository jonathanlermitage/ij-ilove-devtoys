# Privacy Policy

**What is *not* sent?**

I Love DevToys plugin does NOT collect or store any personal data. All settings are stored locally, at IDE level and optionally at project level.

**What is the data sent?**

When the IDE starts up, certain technical and anonymous data may be collected. The installed plugin may transmit the following information:

| Property            | Description                                        | Example values                                                                                     |
|---------------------|----------------------------------------------------|----------------------------------------------------------------------------------------------------|
| `ideName`           | The name of the IDE currently running.             | `IntelliJ IDEA`, `PhpStorm`, `Android Studio` <br/>*Data is Base64-encoded before send*            |
| `ideVersion`        | The API version of the IDE currently running.      | `IC-251.27812.49`, `PS-261.22158.283`, `AI-253.30387.90` <br/>*Data is Base64-encoded before send* |
| `pluginID`          | The ID of the plugin reporting the data.           | `lermitage.intellij.ilovedevtoys`<br>*This value never changes*                                    |
| `pluginLicenseType` | The license type of the plugin reporting the data. | `free`<br>*This value never changes*                                                               |
| `pluginVersion`     | The version of the plugin reporting the data.      | `1.11.2`, `1.11.1`                                                                                 |

The sender's IP address is also collected on the server side.

This data is retained for a maximum of 31 days on a self-hosted server hosted by [Infomaniak](https://infomaniak.com), in Switzerland. This data is used to determine which versions of the IDEs are in use and to assess whether it is worthwhile to continue supporting older IDE versions. The data is never shared with anyone else. The endpoint is [https://www.extratoolspack.com/api/stats.php](https://www.extratoolspack.com/api/stats.php), in case that you're keeping a whitelist.

**How to disable data collection**

You can disable data collection by following these steps:
- Open your IDE
- Go to <ui-path>Help | Edit Custom Properties...</ui-path>
- Add this line: `extra-tools-pack.data-sharing=false`
- Restart your IDE

If you have any questions or concerns about my privacy practices, please contact me at *jonathan.lermitage AT gmail.com*.
