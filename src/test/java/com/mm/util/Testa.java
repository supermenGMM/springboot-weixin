package com.mm.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Testa {
    private HttpConnectionManager connectionManager;
    private String isNeedTransId="N";

    private void createConnectionPool()
    {
        if (this.connectionManager == null) {
            synchronized (this)
            {
                if (this.connectionManager == null)
                {
                    this.connectionManager = new MultiThreadedHttpConnectionManager();
                    HttpConnectionManagerParams params = new HttpConnectionManagerParams();

                    params.setConnectionTimeout(5000);

                    params.setSoTimeout(30000);

                    params.setDefaultMaxConnectionsPerHost(100);

                    params.setMaxTotalConnections(100);
                    this.connectionManager.setParams(params);
                }
            }
        }
    }
    @Test
    public void sendHttpGetJson() throws IOException {
        createConnectionPool();
        HttpClient httpClient = new HttpClient(this.connectionManager);
        GetMethod getMethod = new GetMethod("http://192.168.0.140:8083/baseData/face/find?params=");

        String resJson = "";
        HttpMethodParams httpMethodParams = new HttpMethodParams();
        httpMethodParams.setContentCharset("utf-8");
        getMethod.setParams(httpMethodParams);
        try
        {
            Map<String, String> paramMap = new java.util.HashMap<>();
            //json2Map(json);
            paramMap.put("aaa","aaaa");
            paramMap.put("acaa","aaaa");


            if (null != paramMap)
            {
                Set<String> keys = paramMap.keySet();
                NameValuePair[] nameValuePair = new NameValuePair[paramMap.entrySet().size()];
                int i = 0;
                for (String key : keys) {

                        String value = (String)paramMap.get(key);
                        nameValuePair[i] = new NameValuePair(key, value);
                        i++;

                }
                String queryString = EncodingUtil.formUrlEncode(nameValuePair, "utf-8");
                System.out.println(queryString);
                getMethod.setQueryString(queryString);

                  }
            httpClient.executeMethod(getMethod);
            resJson = getMethod.getResponseBodyAsString();
        }
        catch ( Exception e)
        {
        }
        finally
        {
            getMethod.releaseConnection();
        }
        return ;
    }






}
