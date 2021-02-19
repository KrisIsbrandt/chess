$(function () {

	const gamesEndpoint = '/chess/games/'; //get all pending games

	function getAllPendingGames() {
		$.getJSON(gamesEndpoint, function(games){
			$("#games").empty();

			if (games.length > 0) {
				for (var game in games) {
					game = games[game];
					console.log(game);
					$("#games").append(
						"<tr>" +
							"<td>" +
								"<form method='post' action='/chess/join' >" +
									"<input type='hidden' name='gameId' value='" + game.id + "'>" +
									"<input type='submit' value='join' class=\"btn btn-success btn-sm\"/>&nbsp;" + game.boardName  +
								"</form>" +
							"</td>" +
						"</tr>");
				}
			}
			else {
				$("#games").append("<tr><td>There are no games currently available. Try creating your own!</td></tr>");
			}
		});
	}

	$("#gameView").hide();
	$("#refresh").click(function() { getAllPendingGames(); });
	getAllPendingGames();
});