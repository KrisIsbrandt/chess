package pl.isbrandt.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.isbrandt.chess.Alliance;
import pl.isbrandt.server.model.Game;
import pl.isbrandt.server.model.GameDTO;
import pl.isbrandt.server.model.GameLimitException;
import pl.isbrandt.server.repository.GameRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ServerController {

    private final SimpMessagingTemplate template;

    @Autowired
    public ServerController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @GetMapping("")
    public String showDashboard(HttpServletRequest request, HttpServletResponse response) {
        PlayerService.establishPlayerId(request, response);
        return "dashboard";
    }

    @GetMapping("/game/{gameId}")
    public String getGame(@PathVariable (value = "gameId") String gameId,
                          Model model) {
        Game game = GameRepository.findById(Long.parseLong(gameId));
        model.addAttribute("game", new GameDTO(game));
        return "game";
    }

    @ResponseBody
    @GetMapping("/chess/games")
    public List<GameDTO> getAvailableGames() {
        return GameRepository.findNonStartedGames()
                .stream()
                .map(game -> new GameDTO(game.getId(), game.getBoardName()))
                .collect(Collectors.toList());
    }

    @PostMapping("/chess/create")
    public String createGame(
            @RequestParam (value = "gamename", defaultValue = "game") String boardName,
            HttpServletRequest request, HttpServletResponse response) {
        String playerId = PlayerService.establishPlayerId(request, response);
        Game game = new Game(playerId, boardName);
        try {
            game = GameRepository.save(game);
        } catch (GameLimitException ignored) {}
        return "redirect:/game/" + game.getId();
    }

    @PostMapping("/chess/join")
    public String joinGame(
            @RequestParam (value = "gameId") String gameId,
            HttpServletRequest request, HttpServletResponse response) {
        String playerId = PlayerService.establishPlayerId(request, response);
        Game game = GameRepository.findById(Long.parseLong(gameId));
        if (game.getState().isPending() &&
            !playerId.equals(game.getBlackPlayer()) &&
            !playerId.equals(game.getWhitePlayer())) {
            game.join(playerId);
            GameRepository.update(game);
            updateGameState(game.getId(), game);
            return "redirect:/game/" + game.getId();
        }
        return "redirect:/";
    }

    @PostMapping("/chess/forfeit")
    public void forfeitGame(
            @RequestParam (value = "gameId") String gameId,
            HttpServletRequest request, HttpServletResponse response) {
        String playerId = PlayerService.establishPlayerId(request, response);
        Game game = GameRepository.findById(Long.parseLong(gameId));
        if (game.getState().canMove()) {
            Alliance alliance = game.determineAlliance(playerId);
            if (alliance.isWhite()) {
                game.setState(Game.GameState.BLACK_WON);
            } else {
                game.setState(Game.GameState.WHITE_WON);
            }
            GameRepository.update(game);
            updateGameState(game.getId(), game);
        }
    }

    private void updateGameState(Long gameId, Game game) {
        template.convertAndSend("/chess/gamestate/" + gameId, new GameDTO(game));
    }
}
