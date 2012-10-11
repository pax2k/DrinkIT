var statusElement;
var theImage;

var constance = {
    protocolName:"drinkit",
    serverName:"SERVER",
    musicClient:"MUSIC_CLIENT",
    web_view_water_client:"WEB_WATER_CLIENT",
    water_client:"WATER_CLIENT"
};

if (!window.WebSocket) {
    window.WebSocket = window.MozWebSocket;
    if (!window.WebSocket) {
        alert("WebSocket not supported by this browser");
    }
}

var connection = {
    initConnection:function () {
        var location = document.location.toString()
            .replace('http://', 'ws://')
            .replace('https://', 'wss://')
            + constance.protocolName + "/";

        this._ws = new WebSocket(location, constance.protocolName);
        this._ws.onopen = this.onopen;
        this._ws.onmessage = this.onmessage;
        this._ws.onclose = this.onclose;
    },

    onopen:function () {
        connectionStatus.innerHTML = 'Connected';
        sendMessage(constance.serverName, constance.web_view_water_client);
    },
    send:function (message) {
        this._ws.send(message);
    },
    onmessage:function (m) {
        var jsonObject = eval("(" + m.data + ')');     // eval is evil use JQuery method or something like that.
        var sendFrom = jsonObject.from;
        var sentValue = jsonObject.value;

        switch (sendFrom) {
            case constance.serverName:
                // todo
                break;
        }
    },
    onclose:function () {
        connectionStatus.innerHTML = 'Closed';
        this._ws = null;
    }
};

function init() {
    statusElement = document.getElementById('connectionStatus');
    theImage = document.getElementById('waterButton');
    connection.initConnection();
}

function waterDown() {
    sendMessage(constance.water_client, "ON");
}

function waterUp() {
    sendMessage(constance.water_client, "OFF");
}

function sendMessage(to, message) {
    connection.send("{'to' : '" + to + "', 'from' : '" + constance.web_view_water_client + "' , 'value' : '" + message + "'}");
}
      