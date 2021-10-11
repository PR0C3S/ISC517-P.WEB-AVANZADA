package com.pucmm.practica2.services;

import com.pucmm.practica2.entities.Mock;
import com.pucmm.practica2.repositorios.MockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MockServices {

    private final MockRepository mockRepository;

    public MockServices(MockRepository mockRepository) {
        this.mockRepository = mockRepository;
    }

    public long cantidadMocks(){
        return mockRepository.count();
    }

    @Transactional
    public Mock mockCreation(Mock mock){
        mockRepository.save(mock);
        return mock;
    }

    public Mock mockByID(Long id) {
        return mockRepository.consultaMock(id);
    }
}
