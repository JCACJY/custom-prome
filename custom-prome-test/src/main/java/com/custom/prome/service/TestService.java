package com.custom.prome.service;

import com.custom.prome.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestDao dao ;

    public List<String> getList(){
        if (!dao.isListEmpty()){
            return dao.getList();
        }
        return null;
    }

}
