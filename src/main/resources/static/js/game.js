$(function () {

	var gameId = gamestate.id;
	var playerId = getCookie('playerId');
	var playerColor = determineColor();
	var stompClient = null;

	//Endpoints
	var serverEndpoint = '/chess-websocket'; 	 //register client in broker
	var gameStateEndpoint = '/chess/gamestate/'; //subscriber to receive gamesstate updates
	var moveEndpoint = '/chess/move/';			 //send move endpoint
	var joinGameEndpoint = '/chess/join/';
	var forfeitEndpoint = '/chess/forfeit';

	//Chessboard configuration
	var config = {
		position: 'start',
		draggable: true,
		onDragStart: onDragStart, //event fires every time a piece is picked up.
		onDrop: onDrop, //event fires every time a piece is dropped.
		orientation: playerColor
	};

	const statusMap = new Map([
		['BLACK_WON', 'Black won!'],
		['WHITE_WON', 'White won!'],
		['DRAW', "It's a draw!"],
		['WHITE_TO_MOVE', 'white to move'],
		['BLACK_TO_MOVE', 'black to move']
	])
	function connect() {
		var socket = new SockJS(serverEndpoint);
		stompClient = Stomp.over(socket);
		stompClient.connect({}, function (frame) {
			console.log('Connected: ' + frame);
			stompClient.subscribe(gameStateEndpoint + gameId, function (data) {
				updateGamestate(JSON.parse(data.body));
			});
			stompClient.send(joinGameEndpoint + gameId, {},
				JSON.stringify({'playerId': playerId}));
		});
	}

	function onDragStart(source, piece, position, orientation) {
		//do not allow to pick piece if in pending or in gameOver
		if (gamestate.state === 'DRAW' ||
			gamestate.state === 'BLACK_WON' ||
			gamestate.state === 'WHITE_WON' ||
			gamestate.state === 'PENDING' ||
			gamestate.state === 'RESIGNED') {
			return false;
		}
		//pick only your pieces
		if ((playerColor === 'white' && piece.search(/^b/) !== -1) ||
			(playerColor === 'black' && piece.search(/^w/) !== -1)) {
			return false
		}
	}

	function onDrop(from, to) {
		if ((gamestate.state === 'WHITE_TO_MOVE' && playerColor !== 'white') ||
			(gamestate.state === 'BLACK_TO_MOVE' && playerColor !== 'black')) {
			return 'snapback';
		}
		sendMove(from, to);
	}

	function sendMove(from, to) {
		stompClient.send(moveEndpoint + gameId, {},
			JSON.stringify({
				'playerId': playerId,
				'from': from,
				'to': to
			}));
	}

	function determineColor() {
		if (playerId === gamestate.whitePlayerId) {
			return 'white';
		} else if (playerId === gamestate.blackPlayerId) {
			return 'black';
		} else {
			return null;
		}
	}

	function forfeit() {
		$('#forfeitModal').modal('hide');
		if (gameId != null) {
			$.ajax({
				url: forfeitEndpoint,
				type: "post",
				data: {
					gameId: gameId,
				}
			}).done(updateGamestate);
		}
	}

	function redirectToDashboard() {
		document.location.href="/";
	}

	function updateGamestate(data) {
		gamestate = data;
		gameStatus();
		drawBoard();
	}

	function drawBoard() {
		//Update board after receiving new gamestate
		var fenString = gamestate.fen.split(' ')[0];
		board.position(fenString);
	}

	function gameStatus() {
		var status = "";
		var status2 = "";

		// status
		if (gamestate.state === 'PENDING') {
			status = "Waiting for second player...";
		}
		else if (gamestate.state === 'BLACK_TO_MOVE' || gamestate.state === 'WHITE_TO_MOVE') {
			status = "Both players are here! You are '" + playerColor + "'."
		}

		status2 = statusMap.get(gamestate.state);
		if (status2 === undefined) {
			status2 = "";
		}

		$("#status").text(status);
		$("#status2").text(status2);
	}

	function getCookie(cname) {
		var name = cname + "=";
		var decodedCookie = decodeURIComponent(document.cookie);
		var ca = decodedCookie.split(';');
		for(var i = 0; i <ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) == ' ') {
				c = c.substring(1);
			}
			if (c.indexOf(name) == 0) {
				return c.substring(name.length, c.length);
			}
		}
		return "";
	}

	$("#forfeit").click(function() { forfeit(); });
	$("#confirmation").click(function() { redirectToDashboard(); });

	var board = Chessboard('board', config);
	connect();
	gameStatus();
});