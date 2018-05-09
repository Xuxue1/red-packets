'use strict';

//定义红包
var RedEnvelopeIteam = function (text) {
  if (text) {
    var o = JSON.parse(text);
    this.from = o.from; //发红包人的地址
    this.amount = new BigNumber(o.amount); //红包剩余的金额
    this.count = new BigNumber(o.count); //红包的数量
    this.recods = o.recods; //领红包的记录
    this.id = o.id; //红包的id
    this.nickname = o.nickname; //发红包人的昵称
    this.word = o.word; //发红包的寄语
    this.time = o.time; //发红包的时间
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
    this.ids = o.ids; //一个地址所有发红包的记录
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


  init: function (address) {
    if(address === undefined || address.length>0){
      LocalContractStorage.set("address", address);
    } else{
      LocalContractStorage.set("address", "test");
    }
  },

  /**
   * 发红包
   * @param count 发红包的数量
   * @param redId 红包的id(UUID)
   * @param nickname 发红包人你昵称
   * @param word 发红包人的寄语
   * @param time 发红包的时间
   */
  sent: function (count,redId,nickname,word,time) {
    let value = Blockchain.transaction.value;
    let from = Blockchain.transaction.from;
    let red = this.re.get(from+"_"+redId);
    if(red){ //如果红包的id已经使用了
      throw new Error("redId: "+redId+" send");
    }

    if(value.toNumber()<0){ //
      throw new Error("value not less ran zero");
    }

    let poundage = new BigNumber(parseInt(value.toNumber()*0.01));
    let t_result = Blockchain.transfer(LocalContractStorage.get("address"), poundage);
    if (!t_result) {
      throw new Error("transfer failed."+result);
    }
    value = value.sub(poundage);

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
  /**
   * 领红包
   * @param red_address 红包的地址
   * @param red_id 红包的id
   * @param address 领取人的地址
   * @param wx_id 领取人的微信id
   * @param time 领取时间
   */
  take: function (red_address,red_id,address,wx_id,time) {
    let red = this.re.get(red_address+"_"+red_id);
    if(red){
      if(!red.count){
        throw new Error("红包数目为定义");
      }
      if(!red.amount){
        throw new Error("红包金额为定义");
      }
      //********************************
      if(red.count.gt(new BigNumber(0)) && red.amount.gt(new BigNumber(0))){
        //如果还剩下最后一个 全部给他
        let result = 1;
        if(red.count.toNumber()===1){
          result=1
        }else{//如果红包数额大于1 随机一个数字给他 但是最大金额是剩余红包金额的0.8
          while(result>0.8||result<0.001){
            result = parseFloat(Math.random().toFixed(2));
          }
        }
        let sub_num = new BigNumber(parseInt(red.amount.toNumber()*result/10000)*10000);
        let t_result = Blockchain.transfer(address, sub_num);
        if (!t_result) {
          throw new Error("transfer failed."+result);
        }
        let recods = red.recods;
        let recod = {};
        recod.wx_id = wx_id;
        recod.amount = sub_num;
        recod.address = address;
        recod.time = time;
        recods.push(recod);
        red.recods = recods;
        red.amount = red.amount.sub(sub_num);
        red.count = red.count.sub(1);
        this.re.put(red_address+"_"+red_id,red);
        return sub_num+"|"+result;
      }else{
        throw new Error("红包被抢完了!");
      }
    }
  },

  /**
   * 获取发红的人的发红包的id列表
   */
  ids: function () {
    var address = Blockchain.transaction.from;
    return this.id.get(address);
  },

  /**
   * 获取红包的领取信息
   * @param red_id 红包ud
   */
  message: function (red_id) {
    var address = Blockchain.transaction.from;
    return this.re.get(address+"_"+red_id);
  },

  /**
   * 获取红包的领取信息
   * @param red_address 红包地址
   * @param red_id 红包id
   */
  omessage: function (red_address,red_id) {
    return this.re.get(red_address+"_"+red_id);
  },

  getAddress: function () {
    return LocalContractStorage.get("address");
  }
};

module.exports = RedEnvelope;
