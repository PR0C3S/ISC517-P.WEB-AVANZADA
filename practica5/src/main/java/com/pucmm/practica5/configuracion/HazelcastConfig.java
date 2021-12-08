package com.pucmm.practica5.configuracion;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Collections;

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

        JoinConfig joinConfig = config.getNetworkConfig().getJoin();
        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(true).setMembers(Collections.singletonList("127.0.0.1"));
        return config;
    }
}
