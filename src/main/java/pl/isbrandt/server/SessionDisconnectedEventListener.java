package pl.isbrandt.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import pl.isbrandt.chess.Alliance;
import pl.isbrandt.server.model.Game;
import pl.isbrandt.server.model.GameDTO;
import pl.isbrandt.server.repository.GameRepository;

@Component
public class SessionDisconnectedEventListener implements ApplicationListener<SessionDisconnectEvent> {

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String playerId = headerAccessor.getSessionId();
        if (playerId == null) return;

        Game game = GameRepository.findByPlayerSessionId(playerId);
        if (!game.getState().hasEnded()) {
            Alliance alliance = game.determineAlliance(game.getPlayerIdBasedOnSessionId(playerId));
            if (alliance == null) {
                game.setState(Game.GameState.DRAW);
            } else {
                if (alliance.isWhite()) {
                    game.setState(Game.GameState.BLACK_WON);
                } else {
                    game.setState(Game.GameState.WHITE_WON);
                }
            }
            GameRepository.update(game);
            template.convertAndSend("/chess/gamestate/" + game.getId(), new GameDTO(game));
        }
    }
}
