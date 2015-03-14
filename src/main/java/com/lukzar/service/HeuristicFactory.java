package com.lukzar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author lukasz
 */
@Component
public class HeuristicFactory {
    
    @Autowired
    HeuristicService randomHeuristic;
    
    @Autowired
    HeuristicService secondHeuristic;
    
    public HeuristicService getService(String name){
        if("random".equals(name))
            return randomHeuristic;
        else if("second".equals(name))
            return secondHeuristic;
        else return null;
    }
}
