package com.exampleMainService.utils.services;

import com.exampleMainService.utils.entities.LoggerDB;
import com.exampleMainService.utils.repositories.LoggerDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoggerDBService {
    @Autowired
    LoggerDBRepository loggerDBRepository;

    public boolean log(LoggerDB obj){
        obj = loggerDBRepository.save(obj);
        if(obj == null)
            return false;
        return true;
    }
    public List<LoggerDB> logs(){
        return loggerDBRepository.findAll();
    }
}
