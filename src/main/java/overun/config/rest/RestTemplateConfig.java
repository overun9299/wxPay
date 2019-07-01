package overun.config.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import overun.config.properties.HttpConfigProperties;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @ClassName: RestTemplateConfig
 * @Description:
 * @author: 薏米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/6/22 13:46
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */
@Configuration
public class RestTemplateConfig {

    @Resource
    private HttpConfigProperties httpConfigProperties;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        for(HttpMessageConverter converter : converterList) {
            if(converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(Charset.forName("utf-8"));
                break;
            }
        }
        initProxy(restTemplate);
        initTimeout(restTemplate);
        return restTemplate;
    }

    /**
     * 为restTemplate配置代理
     *
     * @param restTemplate
     */
    private void initProxy(RestTemplate restTemplate) {
        if (httpConfigProperties.getProxy().isActive()) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(httpConfigProperties.getProxy().getHost(), httpConfigProperties.getProxy().getPort()));
            SimpleClientHttpRequestFactory clientHttpRequestFactory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
            clientHttpRequestFactory.setProxy(proxy);
        }
    }

    /**
     * 为restTemplate配置超时时间
     *
     * @param restTemplate
     */
    private void initTimeout(RestTemplate restTemplate) {
        if (httpConfigProperties.getProxy().isActive()) {
            SimpleClientHttpRequestFactory clientHttpRequestFactory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
            clientHttpRequestFactory.setConnectTimeout(httpConfigProperties.getTimeout().getConnect());
            clientHttpRequestFactory.setReadTimeout(httpConfigProperties.getTimeout().getRead());
        }
    }
}
