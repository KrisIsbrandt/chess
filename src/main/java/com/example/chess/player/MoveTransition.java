package com.example.chess.player;

import com.example.chess.board.Board;
import com.example.chess.board.Move;

/**
 * MoveTransition is a transition board created after a move is made.
 * It helps to evaluate if the move is valid.
 */
public class MoveTransition {

    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus moveStatus;


    public MoveTransition(final Board transitionBoard,
                          final Move move,
                          final MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }

    public Board getTransitionBoard() {
        return this.transitionBoard;
    }
}
