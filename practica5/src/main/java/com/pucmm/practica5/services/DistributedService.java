package com.pucmm.practica5.services;

import com.hazelcast.core.HazelcastInstance;
import com.pucmm.practica5.configuracion.HazelcastConfig;
import org.springframework.stereotype.Component;

@Component
public class DistributedService {
    private static HazelcastInstance instance;

    DistributedService(){
        instance = HazelcastConfig.getInstance();
    }
}
