var express = require('express');
var app = express();
var bodyParser = require('body-parser');

var HttpRequest = require("./lib/httprequest");
var Neb = require('./lib/neb');
var Account = require('./lib/account');
var Transaction = require('./lib/transaction');
var neb = new Neb();
// neb.setRequest(new HttpRequest("https://testnet.nebulas.io"));
neb.setRequest(new HttpRequest("https://mainnet.nebulas.io"));
app.use(bodyParser.text());


Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
},

app.post('/take', function (req, res) {
    console.log(req.body);
    var parm = JSON.parse(req.body);
    var account_message = parm.account;
    var address = parm.address;
    var password = parm.password;
    var red_id = parm.red_id;
    var wx_id = parm.wx_id;
    var red_address = parm.red_address;
    var acc = new Account();
    acc = acc.fromKey(account_message, password, true);
    var tx;
    neb.api.getAccountState(acc.getAddressString()).then(function (state) {

        // var testnetchainID = 1001;
        var testnetchainID = 1;
        var callFunction = "take";
        var callArgs = [red_address,red_id,address,wx_id,new Date().format("yyyy-MM-dd hh:mm:ss")];
        console.log(JSON.stringify(tx));
        tx = new Transaction({
            chainID: testnetchainID,
            from: acc,
            to: "",
            value: 0,
            nonce: parseInt(state.nonce) + 1,
            gasPrice: 1000000,
            gasLimit: 2000000,
            contract: {
                function: callFunction,
                args: JSON.stringify(callArgs)
            }
        });
        tx.signTransaction();
        neb.api.sendRawTransaction(tx.toProtoString()).then(function (resp) {
            resp = resp.result || resp;
            console.log(resp.txhash)
            res.send(resp.txhash)
        }).catch(function (err) {
            console.log(err);
            res.send("-1")
        })
    }).catch(function (err) {
        console.log(err);
    });
});

var server = app.listen(8081, function () {
    var host = server.address().address
    var port = server.address().port
    console.log("应用实例，访问地址为 http://%s:%s", host, port)

});
