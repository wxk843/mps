package com.cesske.mps.service;

import com.codingapi.tx.config.service.TxManagerTxUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * 加载分布式服务的服务器地址
 */
@Service
public class TxManagerTxUrlServiceImpl implements TxManagerTxUrlService {


    @Value("${tm.manager.url}")
    private String url;

    @Autowired
    private DiscoveryClient discoveryClient;


    @Override
    public String getTxUrl() {
        System.out.println("load tm.manager.url ");
        List<ServiceInstance> list = discoveryClient.getInstances(url);
        if (list == null) {
            System.out.format("===== Null list returned for %s%n", url);
        } else {
            System.out.format("===== Found %d instances of %s%n", list.size(), url);
            for (ServiceInstance serviceInstance : list) {
                System.out.println(
                        serviceInstance.getServiceId() + " -- " + serviceInstance.getUri());
            }
        }
        Random random = new Random();
        int n = random.nextInt(list.size());
        list.get(n);
        System.out.println(">>>>>>" + list.get(n).getUri().toString());
        return list.get(n).getUri().toString()+"/tx/manager/";
    }
}
