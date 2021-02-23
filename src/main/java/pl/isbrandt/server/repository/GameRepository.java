package pl.isbrandt.server.repository;

import pl.isbrandt.server.model.Game;
import pl.isbrandt.server.model.GameLimitException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameRepository {

    private static final int MAX_GAME_LIMIT = 100;

    private static Long id = 0L;
    public static final List<Game> games = new ArrayList<>();

    public static Game save(Game game) throws GameLimitException {
        if (games.size() >= MAX_GAME_LIMIT) {
            removeEndedGames();
            if (games.size() >= MAX_GAME_LIMIT) {
                throw new GameLimitException("No more games are allowed");
            }
        }
        game.setId(id++);
        games.add(game);
        return game;
    }

    public static Game findById(long id) {
        return games.stream().filter(game -> game.getId().equals(id)).findFirst().orElse(null);
    }

    public static List<Game> findNonStartedGames() {
        return games.stream()
                .filter(GameRepository::pendingGames)
                .collect(Collectors.toList());
    }

    private static void removeEndedGames() {
        games.removeAll(
                games.stream()
                        .filter(game -> game.getState().hasEnded())
                        .collect(Collectors.toList()));
    }

    public static void update(Game game) {
        games.set(games.indexOf(game), game);
    }

    public static Game findByPlayerSessionId(String playerId) {
        return games.stream()
                .filter(game -> game.isValidPlayerSessionId(playerId))
                .findFirst().orElse(null);
    }

    private static boolean pendingGames(Game game) {
        return game.getState().isPending();
    }
}
