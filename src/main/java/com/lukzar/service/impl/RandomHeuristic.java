package com.lukzar.service.impl;

import com.lukzar.service.HeuristicService;
import java.util.Random;
import org.springframework.stereotype.Service;

/**
 *
 * @author lukasz
 */
@Service
public class RandomHeuristic implements HeuristicService {

    static Random generator = new Random();
    
    @Override
    public int getMove(int[][] board, int player) {
        return generator.nextInt(7);
    }
    
}
