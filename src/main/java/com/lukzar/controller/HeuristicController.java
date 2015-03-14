package com.lukzar.controller;

import com.lukzar.model.MoveDTO;
import com.lukzar.service.AlphaBetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lukasz
 */
@RestController
@RequestMapping(value = "/heuristic")
public class HeuristicController {

    @Autowired
    AlphaBetaService alphaBetaService;

    @RequestMapping(value = "/postMove/{player}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    MoveDTO getMove(
            @RequestBody int[][] board,
            @PathVariable("player") int player) {
        return alphaBetaService.getMove(board, player);
    }

}
