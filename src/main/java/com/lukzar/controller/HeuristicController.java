package com.lukzar.controller;

import com.lukzar.service.HeuristicFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lukasz
 */
@RestController
@RequestMapping(value = "/heuristic")
public class HeuristicController {

    @Autowired
    HeuristicFactory heuristicFactory;
    
    @RequestMapping(value="/postMove/{player}/{heuristic}", method = RequestMethod.POST)
    @ResponseBody int getMove(
            @RequestBody int[][] board, 
            @PathVariable("player") int player, 
            @PathVariable("heuristic") String heuristic){
        return heuristicFactory.getService(heuristic).getMove(board, player);
    }
    
    @RequestMapping(value="/getMove/{name}", method = RequestMethod.GET)
    @ResponseBody int getMove(@PathVariable String name){
        return heuristicFactory.getService(name).getMove(null, 1);
    }
    
    
}
