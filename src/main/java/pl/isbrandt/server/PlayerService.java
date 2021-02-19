package pl.isbrandt.server;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class PlayerService {

    private static final int ONE_DAY = 24 * 60 * 60;

    private PlayerService() {
        throw new RuntimeException("You cannot instantiate PlayerService class");
    }

    public static String establishPlayerId(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> playerId = getCookieValue(request, "playerId");
        if (playerId.isPresent()) {
            return playerId.get();
        } else {
            Cookie playerIdCookie = new Cookie("playerId", UUID.randomUUID().toString());
            playerIdCookie.setMaxAge(ONE_DAY);
            response.addCookie(playerIdCookie);
            return playerIdCookie.getValue();
        }
    }

    private static Optional<String> getCookieValue(HttpServletRequest request, String key) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> key.equals(cookie.getName()))
                    .map(Cookie::getValue).findFirst();
        }
        return Optional.empty();
    }
}
