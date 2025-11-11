package com.southgis.test.controller;

import com.google.common.collect.Maps;
import com.southgis.ibase.utils.json.JsonResult;
import com.southgis.ibase.utils.json.JsonUtil;
import com.southgis.test.config.YthptLoginProperties;
import com.southgis.test.utils.ibase.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @Author：xieshuhe
 * @date：2025-10-22 18:07
 * @Description:
 */

@RestController
@RequestMapping("/ibase")
public class IBaseController {
 @Autowired
 private YthptLoginProperties ythptLoginProperties;

 private RestTemplate restTemplate;

 /**
  * bean初始化完之后创建省平台组织项相关部门、岗位
  */
 @PostConstruct
 private void initProvinceOrgan(){
  try {
   if(this.restTemplate==null) {
    this.restTemplate = new RestTemplateBuilder().build();
   }
  }catch (Exception e){}

 }

 @GetMapping("/getUaaSocialToken")
 public JsonResult getUaaSocialToken(String code) {
  Map<String, String> headerParams = ythptLoginProperties.defaultHeaderParam(false);
  String url = ythptLoginProperties.getYthmhBasePath() + ythptLoginProperties.getUaaSocialToken();
  Map<String, String> queryParam = Maps.newHashMap();
  queryParam.put("code", code);
  queryParam.put("x-tsp-paasid", headerParams.get("x-tsp-paasid"));
  System.out.println("getUaaSocialToken<>url<>" + url);
  System.out.println("getUaaSocialToken<>code<>" + code);
  System.out.println("getUaaSocialToken<>queryParam<>" + JsonUtil.toJsonString(queryParam));
  Map<String, Object> result = HttpUtil.call(url, HttpMethod.GET, queryParam, null, null, headerParams, Map.class, restTemplate);
  return JsonResult.success(result);
 }
}
