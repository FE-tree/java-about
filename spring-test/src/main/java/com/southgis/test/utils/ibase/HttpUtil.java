package com.southgis.test.utils.ibase;

import com.southgis.ibase.utils.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * http工具类
 * @author lifengqin
 * @date 2023/6/14
 */
@Slf4j
public class HttpUtil {

    /**
     * http 请求
     * @param path 请求地址
     * @param httpMethod 请求方式
     * @param formParam form参数
     * @param queryParam query参数
     * @param rawParam json参数
     * @param hearderParam 请求头参数
     * @param clazz 返回类型
     * @return
     */
    public static <T> T call(String path, HttpMethod httpMethod, Map<String, String> queryParam, Map<String, Object> formParam, String rawParam,
                              Map<String, String> hearderParam, Class<T> clazz, RestTemplate restTemplate){
        log.info(String.format("%s请求, formParam参数：{%s}，queryParam参数：{%s}，rawParam参数：{%s}，hearderParam参数：{%s}",
                path, formParam, queryParam, rawParam, hearderParam));
        final StringBuilder url = new StringBuilder();
        url.append(path);
        //设置query参数
        if (queryParam != null && !queryParam.isEmpty()) {
            String prefix = path.contains("?") ? "&" : "?";
            for (Map.Entry<String, String> param : queryParam.entrySet()) {
                if (param.getValue() != null) {
                    if (prefix != null) {
                        url.append(prefix);
                        prefix = null;
                    } else {
                        url.append("&");
                    }
                    String value = param.getValue();
                    url.append(escapeString(param.getKey())).append("=").append(escapeString(value));
                }
            }
        }
        //设置请求头参数
        HttpHeaders headers = new HttpHeaders();
        if (hearderParam != null && !hearderParam.isEmpty()) {
            for (Map.Entry<String, String> entry : hearderParam.entrySet()) {
                headers.add(entry.getKey(), entry.getValue());
            }
        }
        //设置form参数
        LinkedMultiValueMap<String, Object> formParams = new LinkedMultiValueMap<>();
        if (formParam != null && !formParam.isEmpty()){
            for (Map.Entry<String, Object> entry : formParam.entrySet()) {
                if (entry.getValue() instanceof File) {
                    File file = (File) entry.getValue();
                    FileInputStream fileInputStream = null;
                    try {
                        fileInputStream = new FileInputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    InputStreamResource resource = new InputStreamResource(fileInputStream){
                        @Override
                        public String getFilename() {
                            return file.getName();
                        }
                        @Override
                        public long contentLength() throws IOException {
                            return file.length();
                        }
                    };
                    formParams.add(entry.getKey(), resource);
                } else if (entry.getValue() instanceof InputStream){
                    InputStreamResource resource = new InputStreamResource((InputStream) entry.getValue());
                    formParams.add(entry.getKey(), resource);
                }else {
                    formParams.add(entry.getKey(), entry.getValue());
                }
            }
        }
        HttpEntity request;
        if (StringUtils.isNotBlank(rawParam)) {
            request = new HttpEntity<>(rawParam, headers);
        } else if (formParams != null && !formParams.isEmpty()){
            request = new HttpEntity<>(formParams, headers);
        } else {
            request = new HttpEntity(null, headers);
        }
        ResponseEntity<T> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url.toString(), httpMethod, request, clazz);
        }catch (Exception e){
            if (e instanceof HttpServerErrorException){
                HttpServerErrorException d = (HttpServerErrorException) e;
                throw new ServiceException(String.format("%s请求异常, body：{%s}，message: {%s}", url.toString(),d.getResponseBodyAsString(Charset.forName("UTF-8")), e.getMessage()));
            }
        }
        checkSuccess(url.toString(), responseEntity);
        log.info(String.format("%s请求响应, statusCode：{%s}，body：{%s}", url.toString(), responseEntity.getStatusCodeValue(), responseEntity.getBody()));
        return responseEntity.getBody();
    }

    /**
     * 检查返回是否成功，Http状态小于400
     * @param url
     * @param response
     */
    private static void checkSuccess(String url, ResponseEntity<?> response) {
        if(response != null && response.getStatusCodeValue() >= 400) {
            if (response.getBody() instanceof Byte){
                try {
                    throw new ServiceException("远程调用出错[" + url + "]："
                            +response.getStatusCode()+", content:" + new String((byte[]) response.getBody(), "utf-8"));
                }catch (Exception e){}

            }
            throw new ServiceException("远程调用出错[" + url + "]："
                    +response.getStatusCode()+", content:" + response.getBody());
        }
    }

    /**
     * 设置编码格式
     */
    public static String escapeString(String str) {
        try {
            return URLEncoder.encode(str, "utf8").replace("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

}
