package com.southgis.test.config;

import com.southgis.test.utils.ibase.EncryptUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xieshuhe
 * @date：2025-09-22 10:10 
 * @Description: 
 */
@Data
@Component
@ConfigurationProperties(prefix = "province.ythpt")
public class YthptLoginProperties {
    /**
     * 是否打印同步日志
     */
    private boolean syncLogEnabled = false;

    /**
     * 中测院用户登录接口
     */
    private String zcyUserLogin = "https://19.15.219.112:8081/uums/userLogin";

    /**
     * 一体化门户
     */
    private String ythmhBasePath = "https://ythpt.nr.gdgov.cn/ythpt-tymh/am-portal/api";

    /**
     * 通过code 获取 token
     */
    private String uaaSocialToken = "/uaa/social/token";

    private String tsp_passid = "gdszrzytchcpzljdjyglxt";

    private String tsp_passtoken = "gdszrzytchcpzljdjyglxtG~K25ktk";

    private String tif_passid = "zjzxmh";

    private String tif_passtoken = "4Ug7q5VyrxkF1kO5oSPXTVtgaff62hhC";

    /**
     * 是否根据请求头的baseUrl设置流程的网关地址
     */
    private boolean pre_bcodeEnabled = true;

    private String pre_tsp_passid;

    private String pre_tsp_passtoken;

    private String pre_tif_passid;

    private String pre_tif_passtoken;


    /**
     * 构造默认请求头参数
     * @return
     */
    public Map<String, String> defaultHeaderParam(boolean isInternet){
        Map<String, String> headerParamMap = new HashMap<>();
        String tspPasstoken = tsp_passtoken;
        String tspPassid = tsp_passid;
        String tifPasstoken = tif_passtoken;
        String tifPassid = tif_passid;
        if (isInternet){
            if (StringUtils.isNotBlank(pre_tsp_passtoken)){
                tspPasstoken = pre_tsp_passtoken;
            }
            if (StringUtils.isNotBlank(pre_tsp_passid)){
                tspPassid = pre_tsp_passid;
            }
            if (StringUtils.isNotBlank(pre_tif_passtoken)){
                tifPasstoken = pre_tif_passtoken;
            }
            if (StringUtils.isNotBlank(pre_tif_passid)) {
                tifPassid = pre_tif_passid;
            }
            headerParamMap.put("x-tsp-internet","true");
        }
        String tsp_timestamp = Long.toString(new Date().getTime() / 1000L);
        String tsp_nonce = "N" + tsp_timestamp + "N";
        String tsp_signature = EncryptUtils.getSHA256(tsp_timestamp + tspPasstoken + tsp_nonce + tsp_timestamp, true);
        String tif_timestamp = Long.toString(new Date().getTime() / 1000L);
        String tif_nonce = "N" + tif_timestamp + "N";
        String tif_signature = EncryptUtils.getSHA256(tif_timestamp + tifPasstoken + tif_nonce + tif_timestamp, true);
        headerParamMap.put("x-tsp-paasid", tspPassid);
        headerParamMap.put("x-tsp-signature", tsp_signature);
        headerParamMap.put("x-tsp-timestamp", tsp_timestamp);
        headerParamMap.put("x-tsp-nonce", tsp_nonce);
        headerParamMap.put("x-tif-paasid", tifPassid);
        headerParamMap.put("x-tif-signature", tif_signature);
        headerParamMap.put("x-tif-timestamp", tif_timestamp);
        headerParamMap.put("x-tif-nonce", tif_nonce);
        return headerParamMap;
    }
}
