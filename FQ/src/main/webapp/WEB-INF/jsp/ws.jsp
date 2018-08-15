<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WebSocket</title>
</head>
<body>
	<div id="result"></div>
	<script type="text/javascript" src="/thread/js/common/stomp.js"></script>
	<script type="text/javascript" src="/thread/js/common/sockjs.js"></script>
	<script type="text/javascript">
		var socket = new SockJS('/thread_auth/ws');
		var client = Stomp.over(socket);
		var onConnect = function() {
			client.subscribe("/user/queue/time", function(message) {
				document.getElementById("result").innerHTML += message.body + "<br />";
			});
			client.subscribe("/user/queue/errors", function(message) {
				console.error("Error " + message.body);
			});
		};
		client.connect({}, onConnect);
	</script>
</body>
</html>