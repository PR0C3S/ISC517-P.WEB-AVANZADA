package com.pucmm.practica5.configuracion;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastConfig {
    private static HazelcastInstance instance;

    public static  HazelcastInstance getInstance() {
        if (instance == null) {
            instance = Hazelcast.newHazelcastInstance(getConfig());
        }
        return  instance;
    }

    private static Config getConfig(){
        Config config = new Config();
        config.setInstanceName("my-instance");
        return config;
    }
}
