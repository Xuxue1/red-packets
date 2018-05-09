<template>
  <div class="DWrpper" :style="minHeight">
    <div class="DPacketTop">
      <div class="DTopOne"></div>
      <div class="DTopTwo">
        <div class="DTopDash"></div>
        <div class="DTopCircleLeft"></div>
        <div class="DTopCircleRight"></div>
        <div class="DTopContent">
          <p class="fontsize3 textAlignCenter bold lineheight25">{{nickname}}给你发了一个NAS红包</p>
          <p class="fontsize0 paddingtop15 lineheight20 textAlignCenter">{{word}}</p>
        </div>
      </div>
    </div>
    <div class="DBottom">
      <div class="DBottomLeft">红包剩余<span class="fontsize3 marginleft5">{{parseInt(num)-parseInt(geted)}}</span><span
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
          <div class="DChaiContent" @click="openRedPacket">拆</div>
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

    <el-dialog :visible="loading" center class="DAPPDialog">
        <p class="textAlignCenter"><i class="el-icon-loading"></i></p>
        <p class="textAlignCenter theme-color fontsize16 marginbottom20">{{show_word}}</p>
    </el-dialog>

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
        show_word:'正在读取区块链信息',
        num:'',   //红包数量
        nickname:'',   // 昵称
        word:'',    //寄语
        geted:'',           //已领取红包数
        minHeight: '',
        DTips: false,
        DTipZJC: false,
        red_address:"",
        red_id:"",
        receiveed:false,
        loading:true,
        logoImg:'https://nebulas.io/assets/images/logo.png',  //'http://wxapi.fishiny.com/static/img/logo.png'
      }
    },
    methods: {
      share(){
        let url = window.location.href.split('#')[0];
        let v = this;
        console.log(url);
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
        let imgUrl ="http://wxapi.fishiny.com/red.png";

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
      openRedPacket() {
        this.$router.push('/m/getWay')
      },
      getResultByHash(hash){
        neb.api.getEventsByHash({hash: hash}).then(function(events) {
          console.log(JSON.stringify(events)+"event>>>>>>>>>>>>>>>>>>")
        });
      },
      getRedPacketNum() {
        var callArgs=[this.red_address,this.red_id];
        var v = this;
        console.log("will get ");
        neb.api.call({
          chainID: glob_chainID,
          from: this.red_address,
          to: v.dappId,
          value: 0,
          nonce: 0,
          gasPrice: 1000000,
          gasLimit: 2000000,
          contract: {
            function: "omessage",
            args: JSON.stringify(callArgs)
          }
        }).then(function(tx) {
            let result = JSON.parse(tx.result);
            console.log(JSON.stringify(result));
            v.nickname = result.nickname;   // 昵称
            v.word = result.word;     //寄语
            v.geted = result.recods.length;   //已经领的
            store.commit('updateNickname', {nickname: v.nickname});
            store.commit('updateWord', {word: v.word});
            v.num = parseInt(result.count)+parseInt(v.geted); //红包数量
            if(parseInt(v.geted) === parseInt(v.num)){
              store.commit('updateStatus', {status: true});
              v.$router.push('/m/openPacket?red_id='+v.red_id+"&red_address="+v.red_address)
            }
            v.loading = false;
            result.recods.forEach(recod=>{
              api.api('get','wx/reds/red/message?id='+recod.wx_id)
                .then(res => {
                  console.log(JSON.stringify(res.data.user));
                  console.log(res.data.user.openId);
                  console.log(v.wxUserInfo.openid);
                  if(res.data.user.openId === v.wxUserInfo.openid){
                      v.receiveed = true;
                      store.commit('updateStatus', {status: true});
                      console.log("update");
                      v.$router.push('/m/openPacket?red_id='+v.red_id+"&red_address="+v.red_address)
                  }
                },(err)=>{
                  console.log(err)
                });
            });
            v.share()
        });
      }
    },
    computed: {
      ...mapGetters({
        dappId: 'getDappAddress',
        wxUserInfo: 'getUserInfo',
        appId:'getAppId'
      }),
    },
    mounted() {
      this.minHeight = 'min-height:' + document.documentElement.clientHeight + 'px';
      let red_address = getQueryString('red_address');
      let red_id = getQueryString('red_id');
      store.commit('updateRedAddress', {redAddress: red_address});
      store.commit('updateInit', {init: true});
      store.commit('updateRedId', {redId: red_id});
      this.red_address = red_address;
      this.red_id = red_id;
      this.getRedPacketNum();
      window.title = "领取NAS红包";
    },
  }
</script>
