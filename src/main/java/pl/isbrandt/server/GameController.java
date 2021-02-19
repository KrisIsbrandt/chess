package pl.isbrandt.server;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import pl.isbrandt.server.model.Game;
import pl.isbrandt.server.model.GameDTO;
import pl.isbrandt.server.model.MoveMessage;
import pl.isbrandt.server.model.PlayerJoinMessage;
import pl.isbrandt.server.repository.GameRepository;

@Controller
public class GameController {

    @MessageMapping("/move/{gameId}")
    @SendTo("/chess/gamestate/{gameId}")
    public GameDTO movePiece(
            @DestinationVariable (value = "gameId") String gameId,
            MoveMessage move) {
        Game game = GameRepository.findById(Long.parseLong(gameId));
        game.makeMove(move.getPlayerId(), move.getFrom(), move.getTo());
        GameRepository.update(game);
        return new GameDTO(game);
    }

    @MessageMapping("/join/{gameId}")
    public void join(
            @DestinationVariable (value = "gameId") Long id,
            PlayerJoinMessage joinMessage,
            SimpMessageHeaderAccessor headerAccessor) {
        Game game = GameRepository.findById(id);
        game.websocketJoin(joinMessage.getPlayerId(), headerAccessor.getSessionId());
        GameRepository.update(game);
    }
}
