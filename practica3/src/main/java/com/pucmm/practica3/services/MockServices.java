package com.pucmm.practica3.services;

import com.pucmm.practica3.entities.Mock;
import com.pucmm.practica3.repositorios.MockRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Component
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
