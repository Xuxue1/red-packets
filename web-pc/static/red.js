'use strict';
var RedEnvelopeIteam = function (text) {
  if (text) {
    var o = JSON.parse(text);
    this.from = o.from;
    this.amount = new BigNumber(o.amount);
    this.count = new BigNumber(o.count);
    this.recods = o.recods;
    this.id = o.id;
    this.nickname = o.nickname;
    this.word = o.word;
    this.time = o.time;

  } else {
    this.from = "";
    this.amount = new BigNumber(0);
    this.count = new BigNumber(0);
    this.recods = [];
    this.nickname = "";
    this.word = "";
    this.time = "";
    this.id = "";
  }
};
RedEnvelopeIteam.prototype = {
  toString: function () {
    return JSON.stringify(this);
  }
};
var RedEnvelopeId = function (text) {
  if (text) {
    var o = JSON.parse(text);
    this.ids = o.ids;
  } else {
    this.ids = []
  }
};


RedEnvelopeId.prototype = {
  toString: function () {
    return JSON.stringify(this);
  }
};

var RedEnvelope = function () {
  LocalContractStorage.defineMapProperty(this, "re", {
    parse: function (text) {
      return new RedEnvelopeIteam(text);
    },
    stringify: function (o) {
      return o.toString();
    }
  });

  LocalContractStorage.defineMapProperty(this, "id", {
    parse: function (text) {
      return new RedEnvelopeId(text);
    },
    stringify: function (o) {
      return o.toString();
    }
  });
};

RedEnvelope.prototype = {
  init: function () {
  },

  sent: function (count,redId,nickname,word,time) {
    var value = Blockchain.transaction.value;
    var from = Blockchain.transaction.from;
    if(value.toNumber()<0){
      throw new Error("value not less ran zero");
    }
    var redIds = this.id.get(from);
    if(!redIds){
      redIds = new RedEnvelopeId()
    }
    var redItem = new RedEnvelopeIteam();
    redItem.from = from;
    redItem.count = new BigNumber(count);
    redItem.amount = value;
    redItem.id = redId;
    redItem.nickname = nickname;
    redItem.word = word;
    redItem.time = time;
    redIds.ids.push(redId);
    this.re.put(from+"_"+redId,redItem);
    this.id.put(from,redIds);
  },
  take: function (red_address,red_id,address,wx_id) {
    var red = this.re.get(red_address+"_"+red_id);
    if(red){
      if(!red.count){
        throw new Error("红包数目为定义");
      }
      if(!red.amount){
        throw new Error("红包金额为定义");
      }
      if(red.count.gt(new BigNumber(0)) && red.amount.gt(new BigNumber(0))){
        var sub_num = red.amount.dividedBy(red.count);
        var result = Blockchain.transfer(address, sub_num);
        if (!result) {
          throw new Error("transfer failed.");
        }
        Event.Trigger("BankVault", {
          Transfer: {
            from: Blockchain.transaction.to,
            to: address,
            value: sub_num.toString()
          }
        });
        var recods = red.recods;
        var recod = {};
        recod.wx_id = wx_id;
        recod.amount = sub_num;
        recod.address = address;
        recods.push(recod);
        red.recods = recods;
        red.amount = red.amount.sub(sub_num);
        red.count = red.count.sub(1);
        this.re.put(red_address+"_"+red_id,red);
      }else{
        throw new Error("红包被抢完了!");
      }
    }
  },
  ids: function () {
    var address = Blockchain.transaction.from;
    return this.id.get(address);
  },
  message: function (red_id) {
    var address = Blockchain.transaction.from;
    return this.re.get(address+"_"+red_id);
  }
};
module.exports = RedEnvelope;
