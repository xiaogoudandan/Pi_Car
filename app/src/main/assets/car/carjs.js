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
            console.log("dataInit");
            CodeResult = "如下输出:<br>";
            document.getElementById("codeResult").innerHTML = CodeResult;
            window.scrollTo(0,document.body.scrollHeight);
            var responseData = "init";
            responseCallback(responseData);
        });
    WebViewJavascriptBridge.registerHandler("functionInJs", function (data, responseCallback) {
        console.log("register");
        CodeResult = CodeResult + data;
        document.getElementById("codeResult").innerHTML = CodeResult;
        var responseData = "运行结果";
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

