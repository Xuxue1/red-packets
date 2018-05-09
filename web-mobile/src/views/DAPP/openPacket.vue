<template>

  <div>

    <div class="DWrpper" :style="minHeight" v-if="!to_open">
      <div class="DPacketTop">
        <div class="DTopOne"></div>
        <div class="DTopTwo">
          <div class="DTopDash"></div>
          <div class="DTopCircleLeft"></div>
          <div class="DTopCircleRight"></div>
          <div class="DTopContent">
            <p class="fontsize3 textAlignCenter bold lineheight25">{{nickname}} 给你发了一个NAS红包</p>
            <p class="fontsize0 paddingtop15 lineheight20 textAlignCenter">{{word}}</p>
          </div>
        </div>
      </div>
      <div class="DBottom">
        <div class="DBottomLeft">红包剩余<span class="fontsize3 marginleft5">{{parseInt(num) - parseInt(geted)}}</span><span
          class="fontsize-1">/{{parseInt(num)}}个</span></div>
        <div class="DBottomContentClose">
          <div class="DClose">
            <div class="DCloseContent">
              <div class="circleAvatar">
                <img :src="logoImg" style="width:60px;height:60px;"/>
              </div>
              <p class="margintop15 textAlignCenter fontsize0 moumou">{{nickname}}</p>
            </div>
          </div>
          <div class="DChai">
            <div class="DChaiContent" @click="getAddress">拆</div>
          </div>
        </div>
      </div>

      <!--  *****************弹框   温馨提示**********************-->
      <el-dialog title="温馨提示" :visible="i_DTips" center class="DTip">
        <div class="lineheight25 color-999 textAlignCenter">
          红包只支持在微信端打开，且需要登录授权。请在微信内授权打开吧。
        </div>
        <div slot="footer" class="dialog-footer textAlignCenter paddingbottom10">
          <button class="cancelSend" @click="i_DTips = false">我知道了</button>
        </div>
      </el-dialog>

      <el-dialog :visible="loading" center class="DAPPDialog">
        <p class="textAlignCenter"><i class="el-icon-loading"></i></p>
        <p class="textAlignCenter theme-color fontsize16 marginbottom20">{{show_word}}</p>
      </el-dialog>

    </div>

    <div v-if="to_open">
      <div class="DWrpper" :style="minHeight">
        <div class="DPacketTop">
          <div class="DTopOne"></div>
          <div class="DTopTwo">
            <div class="DTopDash"></div>
            <div class="DTopCircleLeft"></div>
            <div class="DTopCircleRight"></div>
            <div class="DTopContent">
              <p class="fontsize3 textAlignCenter bold lineheight25">{{nickname}} 给你发了一个NAS红包</p>
              <p class="fontsize0 paddingtop15 lineheight20 textAlignCenter">{{word}}</p>
            </div>
          </div>
        </div>
        <div v-if="status == 1&&!loading" class="paddingtop25 textAlignCenter color-333">
          <p v-if="!over">我领取到了<span class="DMoney" v-for="(item, i) in moneyList">{{item}}</span>个NAS币</p>
          <p v-if="over && !to_many">太遗憾了，手慢一步，红包抢光了</p>
          <p v-if="to_many">同时抢红包的人太多啦<br>请返回上一页面，重新点击领取吧</p>
        </div>
        <div v-if="status == 1&&!loading" class="paddingtop25 textAlignCenter color-333">
          <button class="cancelSend" @click="goSend">我也要发NAS红包</button>
        </div>
        <div class="DBottom">
          <div class="DBottomContentClose" v-if="loading">
            <div class="DClose">
              <div class="DCloseContent">
                <div class="circleAvatar">
                  <img :src="touxiang"/>          <!-- 微信头像  -->
                </div>
                <p class="margintop15 textAlignCenter fontsize0 moumou">{{nickname}}</p>
              </div>
            </div>
            <div class="DChai">
              <div class="DChaiContent">拆</div>
            </div>
          </div>
          <!-- 红包已经打开   红包按钮 -->
          <div class="DBottomContentOpen" v-if="status == 1&&!loading">
            <div class="DPacket">
              <div class="DOpenContent">
                <div class="DPacketList paddingX15">
                  <div class="DTopTwo" style="background:#fff;top:0;width:100%;margin:0">
                    <p class="DPacketLast clear">
                      <span class="floatLeft fontsize-2">红包剩余<span style="color:#e31436;font-size:16px;">{{num}}</span>/{{parseInt(num) + parseInt(record.length)}}</span>
                      <span class="floatRight fontsize-2">总金额&nbsp;&nbsp;<span
                        class="fontsize0">{{total.toFixed(6)}}</span>个NAS币</span>
                    </p>
                    <div class="DTopCircleLeft" style="left:-15px;top:43px"></div>
                    <div class="DTopCircleRight" style="right:-15px;top:43px"></div>
                    <div class="DTopDash" style="border-color:#e6e6e6;padding-top:0;margin:0"></div>
                  </div>
                  <p class="DListHeader"><span class="floatLeft">姓名</span><span class="floatRight">获得币数</span></p>
                  <div class="DPacketItem" v-for="(item,i) in DPacketList">
                    <img :src="item.img" class="DAvatar"/>
                    <div class="DPacketNi">
                      <p class="DNicheng">{{item.nicheng}}</p>
                      <p class="DNidate">{{item.date}}</p>
                    </div>
                    <div class="DGetMoney">{{item.money}}</div>
                  </div>
                </div>
              </div>
            </div>
            <div style="background:#d74d43;border-radius:20px">
              <div class="DOpen">
                <div class="DOpenIn"></div>
              </div>
              <div class="DChai">
                <div class="DChaiContent" style="font-size:20px">已拆</div>
              </div>
            </div>
          </div>
        </div>

        <!--  *****************弹框   温馨提示**********************-->
        <el-dialog title="温馨提示" :visible="DTips" center class="DTip">
          <div class="lineheight25 color-999 textAlignCenter">
            红包只支持在微信端打开，且需要登录授权。请在微信内授权打开吧。
          </div>
          <div slot="footer" class="dialog-footer textAlignCenter paddingbottom10">
            <button class="cancelSend" @click="DTips = false">我知道了</button>
          </div>
        </el-dialog>

        <!--  *****************弹框  倒计时**********************-->
        <el-dialog :visible="loading" center class="DAPPDialog">
          <p class="textAlignCenter"><i class="el-icon-loading"></i></p>
          <p class="textAlignCenter theme-color fontsize16 marginbottom20">{{show_word}}<span v-if="count_down"
                                                                                              class="marginleft5">{{timeout}}</span>
          </p>
        </el-dialog>
      </div>

    </div>

  </div>
</template>

<script>
  import store from '../../store/index'
  import {mapGetters} from 'vuex'
  import {getQueryString} from '../../libs/urlResolve'
  import api from '../../fetch/api'
  import wx from 'weixin-js-sdk';
  import qs from 'qs'


  export default {
    name: 'index',
    data() {
      return {
        touxiang: '',           //微信头像
        loading: true,
        num: '',   //红包数量
        nickname: '',   // 昵称
        word: '',    //寄语
        geted: '',           //已领取红包数
        minHeight: '',
        DTips: false,
        DTipZJC: false,
        status: 1,  // 红包状态：1红包未打开 2红包已经打开
        money: "-1",
        total: 0,
        moneyList: [],
        over: true,  //红包是否抢光   切换布尔值  看效果
        red_address: "",
        red_id: "",
        DPacketList: [],
        record: [],
        recordInfo: [],
        myAmount: -1,
        timeout: 30,
        timer: null,
        show_word: "正在读取区块信息",
        count_down: false,
        to_open: false,
        i_DTips: false,
        i_DTipZJC: false,
        logoImg: 'https://nebulas.io/assets/images/logo.png',
        is_share:false,
        to_many: false
      }
    },
    methods: {
      share(){
        if(this.is_share){
          return;
        }
        this.is_share = true;
        let url = window.location.href.split('#')[0];
        let v = this;
        let param = {
          url: url
        };
        let permissions = ['onMenuShareTimeline', 'onMenuShareAppMessage'];
        api.api('get', `/wx/rest/signature?` + qs.stringify(param)).then(res => {
            let config = {
              debug: false, // 开启调试模式为true后可以通过alert弹窗将公众号签名等结果反馈出来
              appId: res.data.appId, // 必填，公众号的唯一标识
              timestamp: res.data.timestamp, // 必填，生成签名的时间戳
              nonceStr: res.data.nonceStr, // 必填，生成签名的随机串
              signature: res.data.signature, // 必填，签名，见附录1
              jsApiList: permissions
            };
            wx.config(config)
          }
        );
        wx.error(function (res) {
          console.log(res);
        });
        let link = window.location.href.split('#')[0];
        store.commit('updateShareUrl', {shareUrl: url});
        //let imgUrl ="https://nebulas.io/assets/images/logo.png";
        let imgUrl ="http://wxapi.fishiny.com/red2.png";

        wx.ready(function () {
          wx.onMenuShareTimeline({
            title: v.nickname+" 给你发了一个NAS红包",
            desc: "NAS手气红包，手慢无",
            link: link,
            imgUrl: imgUrl,
            success: function (res) {
            },
            cancel: function (res) {
            }
          });
          //分享给朋友
          wx.onMenuShareAppMessage({
            title: v.nickname+" 给你发了一个NAS红包",
            desc: "NAS手气红包，手慢无",
            link: link,
            imgUrl: imgUrl,
            success: function (res) {
            },
            cancel: function (res) {
            }
          });
        });
      },
      getResultByHash(hash) {
        neb.api.getEventsByHash({hash: hash}).then(function (events) {
          console.log(JSON.stringify(events))
        });
      },
      get_status(txhash) {
        let v = this;
        neb.api.getTransactionReceipt(txhash)
          .then(o => {
            if (o.status === 2 && this.count > 0) {
              window.setTimeout(() => this.get_status(txhash), 2000)
            } else if (o.status === 1) {
              this.count = 0;
              store.commit('updateStatus', {status: true});
              clearInterval(this.timer);
              this.listInfo();
            } else {
              this.listInfo();
            }
          })
          .catch(function (o) {
            store.commit('updateStatus', {status: false});
            v.listInfo();
            v.to_many = true;
            v.count = 0;
          })
      },
      listInfo() {
        let callArgs = [this.redAddress, this.redId];
        let v = this;
        console.log(v.redAddress);
        console.log(v.dappAddress);
        neb.api.call({
          chainID: glob_chainID,
          from: v.redAddress,
          to: v.dappId,
          value: 0,
          nonce: 0,
          gasPrice: 1000000,
          gasLimit: 2000000,
          contract: {
            function: "omessage",
            args: JSON.stringify(callArgs)
          }
        }).then(function (tx) {
          if(tx.execute_err.length==0){
            let result = JSON.parse(tx.result);
            v.nickname = result.nickname;   // 昵称
            v.word = result.word;     //寄语
            v.geted = result.recods.length;   //已经领的
            v.num = result.count;   //红包数量
            if(parseInt(v.num) === 0){
              v.to_many = false;
              v.over = true;
              store.commit('updateStatus', {status: true});
            }
            v.share();
            if (parseInt(result.count) === 0) {
              v.over = true;
            }
            v.total += parseInt(result.amount) / nas_rate;
            v.record = result.recods;
            v.getRecord();
            v.loading = false
          }else{
            v.loading = false
            v.over = true;
          }
        }).catch(function (o) {
          v.over = true;
        });
      },
      take() {
        let parm = {
          address: this.address,
          redAddress: this.redAddress,
          redId: this.redId,
          openid: this.wxUserInfo.openid
        };
        api.api('post', 'wx/reds/red/take', parm)
          .then(res => {
            console.log(res);
            if (res.success) {
              console.log("result console is call")
              this.Time();
              this.show_word = "正在抢红包!";
              store.commit('updateStatus', {status: true});
              this.get_status(res.data)
            } else {
              this.listInfo();
            }
          }, (err) => {
            console.log(err)
          })
      },
      getRecord() {
        let v = this;
        v.record.forEach(r => {
          v.DPacketList = [];
          api.api('get', 'wx/reds/red/message?id=' + r.wx_id)
            .then(res => {
              console.log(JSON.stringify(res.data.user));
              let detail = {};
              detail.img = res.data.user.headImgurl;
              detail.date = r.time.replace("2018-", "");
              detail.nicheng = res.data.user.nickName;
              detail.money = (r.amount / nas_rate).toFixed(6);
              v.total += r.amount / nas_rate;
              detail.address = r.address;
              v.DPacketList.push(detail);
              v.DPacketList.sort((a,b)=>{
                  if(a.date>b.date){
                    return -1;
                  }else if(a.date === b.date){
                    return 0;
                  }else{
                    return 1;
                  }
              });
              //console.log(JSON.stringify(v.DPacketList));
              if (this.wxUserInfo.openid === res.data.user.openId) {
                v.money = (r.amount / nas_rate).toFixed(6);
                v.moneyList = v.money.toString().split('');
              }
              if (parseInt(v.money) === -1) {
                v.over = true
              } else {
                v.over = false;
              }
            }, (err) => {
              console.log(err)
            });
        });
      },
      Time() {
        this.count_down = true;
        const TIME_COUNT = 30;
        if (!this.timer) {
          this.count = TIME_COUNT;
          this.timer = setInterval(() => {
            if (this.count > 0 && this.count <= TIME_COUNT) {
              this.timeout--;
              this.count--;
            } else {
              clearInterval(this.timer);
              this.loading = false;
              this.timer = null;
            }
          }, 1000)
        }
      },
      goSend() {
        this.$router.push('/m/sendNAS');
      },
      getAddress(){
        this.$router.push('/m/getWay');
      },

      openRedPacket() {
        this.to_open = true;
        this.loading = true;
        let v = this;
        console.log("Red_status "+v.red_status);
        if (!v.red_status) {
          let callArgs = [this.redAddress, this.redId, this.address, "1", new Date().format("yyyy-MM-dd hh:mm:ss")];
          neb.api.call({
            chainID: glob_chainID,
            from: this.address,
            to: this.dappId,
            value: 0,
            nonce: 0,
            gasPrice: 1000000,
            gasLimit: 2000000,
            contract: {
              function: "take",
              args: JSON.stringify(callArgs)
            }
          }).then(function (tx) {
            if (tx.execute_err === undefined || tx.execute_err.length === 0) {
              v.take()
            } else {
              console.log(tx.execute_err)
              v.over = true;
            }
          });
        } else {
          this.listInfo();
        }
      },
      getRedPacketNum() {
        let callArgs = [this.redAddress, this.redId];
        let v = this;
        console.log("will get ");
        console.log("red_address ")
        neb.api.call({
          chainID: glob_chainID,
          from: this.redAddress,
          to: v.dappId,
          value: 0,
          nonce: 0,
          gasPrice: 1000000,
          gasLimit: 2000000,
          contract: {
            function: "omessage",
            args: JSON.stringify(callArgs)
          }
        }).then(function (tx) {
          let result = JSON.parse(tx.result);
          console.log(JSON.stringify(result));
          v.nickname = result.nickname;   // 昵称
          v.word = result.word;     //寄语
          v.geted = result.recods.length;   //已经领的
          v.share();
          store.commit('updateNickname', {nickname: v.nickname});
          store.commit('updateWord', {word: v.word});
          v.num = parseInt(result.count) + parseInt(v.geted); //红包数量
          if (parseInt(v.geted) === parseInt(v.num)) { //如果红包已经领取完成
            store.commit('updateStatus', {status: true});
            v.openRedPacket()
          }else{
            v.loading = false;
          }
          result.recods.forEach(recod => {
            api.api('get', 'wx/reds/red/message?id=' + recod.wx_id)
              .then(res => {
                console.log(JSON.stringify(res.data.user));
                console.log(res.data.user.openId);
                console.log(v.wxUserInfo.openid);
                if (res.data.user.openId === v.wxUserInfo.openid && parseInt(v.geted) !== parseInt(v.num)) { //如果我已经领取过红包
                  v.receiveed = true;
                  store.commit('updateStatus', {status: true});
                  v.openRedPacket();
                }
              }, (err) => {
                console.log(err)
              });
          });
        });
      }
    },
    computed: {
      ...mapGetters({
        dappId: 'getDappAddress',
        wxUserInfo: 'getUserInfo',
        address: 'getAddress',
        redAddress: 'getRedAddress',
        redId: 'getRedId',
        shareUrl: 'getShareUrl',
        red_status: 'getStatus',
        init: 'getInit',
        appId: 'getAppId'
      }),
    },
    created() {
      this.loading = true;
      this.to_open = false;
      this.is_share = false;
      let red_address = getQueryString('red_address');
      let red_id = getQueryString('red_id');
      if(red_address !== undefined && red_address!==null  && red_address.length>0){
        store.commit('updateRedAddress', {redAddress: red_address});
      }
      if(red_id !== undefined && red_id!==null&&red_id.length >0){
        store.commit('updateRedId', {redId: red_id});
      }
      store.commit('updateInit', {init: true});
      if (this.address !== undefined && this.address.length !== 0 && !this.red_status) { //如果已经输入过地址了 开始领红包
        this.openRedPacket();
      } else {// 没有输入地址
        this.getRedPacketNum()
      }
    },
    mounted() {
      this.minHeight = 'min-height:' + document.documentElement.clientHeight + 'px';
    },
  }
</script>
