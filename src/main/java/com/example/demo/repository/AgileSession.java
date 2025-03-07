package com.example.demo.repository;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.agile.api.APIException;
import com.agile.api.AgileSessionFactory;
import com.agile.api.IAgileSession;

@Component
public class AgileSession implements Session {

    // @Value("${agile.url}")
    // String apUrl;

    @Value("${agile.name}")
    String name;

    @Value("${agile.password}")
    String password;

    String url1 = "srv-agile-test.accton.com";
    String url2 = "srv-agile-ap.accton.com";
    String url = url1;

    public IAgileSession connect() throws APIException {
        System.setProperty("weblogic.security.SSL.ignoreHostnameVerification", "true");
        AgileSessionFactory factory = AgileSessionFactory
                .getInstance("http://" + url + ":7001/Agile");

        System.out.println("連線機器:" + url);
        HashMap<Integer, String> params = new HashMap<Integer, String>();
        params.put(AgileSessionFactory.USERNAME, name);
        params.put(AgileSessionFactory.PASSWORD, password);
        return factory.createSession(params);
    }

    public IAgileSession connect(String url) throws APIException {
        System.setProperty("weblogic.security.SSL.ignoreHostnameVerification", "true");
        AgileSessionFactory factory = AgileSessionFactory
                .getInstance("http://" + url + ":7001/Agile");

        System.out.println("連線機器:" + url);
        HashMap<Integer, String> params = new HashMap<Integer, String>();
        params.put(AgileSessionFactory.USERNAME, name);
        params.put(AgileSessionFactory.PASSWORD, password);
        return factory.createSession(params);
    }
}
