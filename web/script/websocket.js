var ws;
let ids = new Array();

function openForm() {
    if (ws == null)
        connect();

    document.getElementById("myForm").style.display = "block";
}

function closeForm() {
    document.getElementById("myForm").style.display = "none";
}
function connect() {
    var username = document.getElementById("username").value;
    ids.push(username);
    console.log(ids);

    var host = document.location.host;
    var pathname = document.location.pathname;

    ws = new WebSocket("ws://" + host  + pathname + "chat/" + username);

    ws.onmessage = function(event) {
        var log = document.getElementById("log");
        console.log(event.data);
        var message = JSON.parse(event.data);
        log.innerHTML += message.from + " : " + message.content + "\n";
    };
}

function send() {
    var content = document.getElementById("msg").value;
    var json = JSON.stringify({
        "content":content
    });

    ws.send(json);
}