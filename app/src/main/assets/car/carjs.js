'use strict'
//请求链接jsBridge
var CSBdistance = "";//超声波距离
var Car = {};
Car.executeCode = function (code) {
    try {
        eval(code);
    }catch (e){
        console.log(e.toString());
    }
}



//function connectWebViewJavascriptBridge(callback) {
//    if (window.WebViewJavascriptBridge) {
//        callback(WebViewJavascriptBridge)
//    } else {
//        document.addEventListener(
//            'WebViewJavascriptBridgeReady'
//            , function () {
//                callback(WebViewJavascriptBridge)
//            },
//            false
//        );
//    }
//}
//
//
//function setupWebViewJavascriptBridge(callback) {
//   if (window.WebViewJavascriptBridge) {
//    callback(WebViewJavascriptBridge)
//   } else {
//    document.addEventListener(
//     'WebViewJavascriptBridgeReady'
//     , function() {
//      callback(WebViewJavascriptBridge)
//     },
//     false
//    );
//   }
//}


if (window.WebViewJavascriptBridge) {
        //do your work here
        WebViewJavascriptBridge.registerHandler("functionInJs", function(data, responseCallback) {
                 document.getElementById("csb_id").innerHTML = ("data from Java: = " + data);
                 var responseData = "Javascript Says Right back aka!";
                 responseCallback(responseData);
             });
    } else {
        console.log("fail1");
        document.addEventListener(
            'WebViewJavascriptBridgeReady'
            , function() {
                //do your work here
                console.log("fail2");
                WebViewJavascriptBridge.registerHandler("functionInJs", function(data, responseCallback) {
                         console.log("register");
                         document.getElementById("csb_id").innerHTML = ("data from Java: = " + data);
                         var responseData = "Javascript Says Right back aka!";
                         responseCallback(responseData);
                     });
                WebViewJavascriptBridge.registerHandler("init",function(data,responseCallback){
                    console.log("init");
                           while(true){

                           }
                    })
            },
            false
        );
}
//
////回调函数，接收java发送来的数据
//setupWebViewJavascriptBridge(function(bridge) {
//   console.log("registerHandler");
//   //指定接收，参数functionInJs 与java保持一致
//    bridge.registerHandler("functionInJs", function(data, responseCallback) {
//         if(data == "error"){
//             CSBdistance = "暂无数据";
//         }else{
//             CSBdistance = data;
//         }
//         document.getElementById("csb_id").innerHTML = CSBdistance;
//         var responseData = 'js指定接收完毕，并回传数据给java';
//         responseCallback(responseData); //回传数据给java
//        });
//        bridge.registerHandler("executeCode",function(data,responseCallback){
//             Car.executeCode(data);
//        })
//        bridge.registerHandler("init",function(data,responseCallback){
//                while(true){
//
//                }
//           })
//});

function init(){
     WebViewJavascriptBridge.registerHandler("functionInJs", function(data, responseCallback) {
         document.getElementById("csb_id").innerHTML = ("data from Java: = " + data);
         var responseData = "Javascript Says Right back aka!";
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

//
//setupWebViewJavascriptBridge(function (bridge) {
//    bridge.registerHandler("setCSBdistance", function (data, responseCallback) {
//        if (data == "error") {
//            CSBdistance = "暂无数据";
//        } else {
//            CSBdistance = data;
//        }
//        console.log("CSB:" + data);
//        document.getElementById("csb_id").innerHTML = CSBdistance;
//        if (responseCallback) {
//            var responseData = "Get";
//            responseCallback(responseData);
//        }
//    });
//});

