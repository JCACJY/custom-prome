package com.custom.prome.dao;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class TestDao {

    private static List<String> list = Arrays.asList("aaa","bbb","ccc","ddd");

    public List<String> getList(){
        return list;
    }

    public boolean isListEmpty(){
        if(this.list == null){
            return true;
        }
       return false;
    }

}
