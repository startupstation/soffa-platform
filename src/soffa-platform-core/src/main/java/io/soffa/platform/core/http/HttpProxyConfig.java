package io.soffa.platform.core.http;

import io.soffa.platform.core.commons.StringUtil;
import io.soffa.platform.core.exception.TechnicalException;
import lombok.Getter;

import java.net.MalformedURLException;
import java.net.URL;

@Getter
public class HttpProxyConfig {

    private String proxyHost = "localhost";
    private int proxyPort = 3128;
    private String proxyUsername;
    private String proxyPassword;
    private String proxyDomain;

    private HttpProxyConfig() {
    }

    public HttpProxyConfig(String proxyHost, int proxyPort, String proxyUsername, String proxyPassword, String proxyDomain) {
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.proxyUsername = proxyUsername;
        this.proxyPassword = proxyPassword;
        this.proxyDomain = proxyDomain;
    }

    public static HttpProxyConfig getProxy() {
        String proxy = System.getProperty("http.proxy");
        if (StringUtil.isNullOrEmpty(proxy)) {
            proxy = System.getenv("https_proxy");
        }
        if (StringUtil.isNullOrEmpty(proxy)) {
            proxy = System.getenv("http_proxy");
        }

        if (StringUtil.isNullOrEmpty(proxy)) {
            return null;
        } else {
            return parse(proxy);
        }
    }

    public static HttpProxyConfig parse(String proxy) {
        try {
            URL parsedUrl = new URL(proxy);
            String userInfos = parsedUrl.getUserInfo();
            String username = "";
            String password = null;
            String domain = null;
            if (userInfos != null) {
                if (userInfos.contains(":")) {
                    String[] userAndPassword = userInfos.trim().split(":");
                    password = userAndPassword[1];
                    if (userAndPassword[0].contains("\\")) {
                        String[] domainAndUser = userAndPassword[0].split("\\\\");
                        domain = domainAndUser[0];
                        username = domainAndUser[1];
                    } else {
                        username = userAndPassword[0];
                    }
                } else {
                    username = userInfos.trim();
                }
            }
            return new HttpProxyConfig(parsedUrl.getHost(), parsedUrl.getPort(), username, password, domain);
        } catch (MalformedURLException e) {
            throw new TechnicalException(("INVALID_PROXY_URL"));
        }
    }


}
