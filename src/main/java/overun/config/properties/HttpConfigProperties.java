package overun.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: HttpConfigProperties
 * @Description:
 * @author: 薏米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/6/22 13:47
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */

@Component
@ConfigurationProperties(prefix = "http")
public class HttpConfigProperties {

    /** 代理设置 */
    private Proxy proxy;
    /** 超时设置 */
    private Timeout timeout;

    public static class Proxy {
        /** 是否启用 */
        private boolean active;
        /** 代理服务器地址 */
        private String host;
        /** 代理服务器端口 */
        private int port;

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }

    public static class Timeout {
        /** connectTimeout */
        private int connect;
        /** readTimeout */
        private int read;

        public int getConnect() {
            return connect;
        }

        public void setConnect(int connect) {
            this.connect = connect;
        }

        public int getRead() {
            return read;
        }

        public void setRead(int read) {
            this.read = read;
        }
    }

    public Proxy getProxy() {
        return proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public Timeout getTimeout() {
        return timeout;
    }

    public void setTimeout(Timeout timeout) {
        this.timeout = timeout;
    }
}
