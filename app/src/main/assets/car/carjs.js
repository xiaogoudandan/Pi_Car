'use strict'
//请求链接jsBridge

var CSBdistance = "";//超声波距离
var Car = {};
var CodeResult =""
Car.executeCode = function (code) {
    try {
        eval(code);
    }catch (e){
        console.log(e.toString());
    }
}


if (window.WebViewJavascriptBridge) {
        init();
    } else {
        console.log("fail1");
        document.addEventListener(
            'WebViewJavascriptBridgeReady'
            , function() {
                //do your work here
                console.log("fail2");
                init();
            },
            false
        );
}

function init() {
    WebViewJavascriptBridge.registerHandler("dataInit", function (data, responseCallback) {
            console.log(data);
            console.log(document.body.clientHeight);
            console.log(window.screen.height);
            CodeResult = "暂无数据:<br>";
            var p = document.getElementById("codeResult");
            p.style.height = data/5*3 + 'px';
            console.log(p.style.height);
            p.innerHTML = CodeResult;
            var responseData = "init";
            responseCallback(responseData);
        });
    WebViewJavascriptBridge.registerHandler("functionInJs", function (data, responseCallback) {
        console.log("register");
        CodeResult = CodeResult + data;
        document.getElementById("codeResult").innerHTML = CodeResult;
        document.getElementById("codeResult").scrollTop = document.getElementById("codeResult").scrollHeight;
        var responseData = "运行结果";
        responseCallback(responseData);
    });
    WebViewJavascriptBridge.registerHandler("startRun", function (data, responseCallback) {
            console.log("startRun");
            CodeResult = "start run：" + "<br>";
            document.getElementById("codeResult").innerHTML = CodeResult;
            var responseData = "startRun";
            responseCallback(responseData);
        });

    WebViewJavascriptBridge.registerHandler("stopRun", function (data, responseCallback) {
                console.log("stopRun");
                CodeResult = CodeResult + "stop over！" + "<br>";
                document.getElementById("codeResult").innerHTML = CodeResult;
                document.getElementById("codeResult").scrollTop = document.getElementById("codeResult").scrollHeight;
                var responseData = "stopRun";
                responseCallback(responseData);
            });
}

function callJS(data){
    if (data == "error") {
        CSBdistance = "暂无数据";
    } else {
        CSBdistance = data;
    }
    console.log("CSB:" + data);
    document.getElementById("csb_id").innerHTML = CSBdistance;
}

function stopRun(){
     console.log("stopRun");
//     console.log(document.body.clientHeight);
//     console.log(window.screen.availHeight);
//     console.log(document.getElementById("codeResult").style.height);
//     document.getElementById("codeResult").innerHTML = document.getElementById("codeResult").innerHTML + "dfsdfd" + "<br>";
//     document.getElementById("codeResult").scrollTop = document.getElementById("codeResult").scrollHeight;
//调用本地java方法
     window.WebViewJavascriptBridge.callHandler(
               'stopRun'
               , {'param': ""}
               , function(responseData) {

               }
     );
}

function clearData(){
    document.getElementById("codeResult").innerHTML = "";
    console.log("clearData");
}

