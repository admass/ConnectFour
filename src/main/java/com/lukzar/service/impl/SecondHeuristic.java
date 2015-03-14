package com.lukzar.service.impl;

import com.lukzar.service.HeuristicService;
import org.springframework.stereotype.Service;

/**
 *
 * @author lukasz
 */
@Service
public class SecondHeuristic implements HeuristicService {

    @Override
    public int getMove(int[][] board, int player) {
        return 20;
    }
    
}
