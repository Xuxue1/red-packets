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
    <div class="paddingtop30">
      <p class="theme-color fontsize3 textAlignCenter bold lineheight30">请选择领取方式</p>
    </div>
    <div class="paddingX15 paddingtop20">
      <p><el-radio v-model="radio" label="0"><span class="theme-color">我有NAS钱包，可以直接发到钱包里</span></el-radio></p>
      <p class="paddingtop10"><el-radio v-model="radio" label="1"><span class="theme-color">我没有NAS钱包，帮我一键生成个钱包地址</span></el-radio></p>
    </div>
    <div class="textAlignCenter paddingtop70">
      <button class="DAddressConfirmBtn" @click="confirmWay">确认</button>
    </div>
  </div>
</template>
<script>
  import store from '../../store/index'
  import {mapGetters} from 'vuex'

  export default {
    name: 'index',
    data () {
      return {
        minHeight:'',
        radio:'0',
      }
    },
    computed: {
      ...mapGetters({
        nickname: 'getNickname',
        word: 'getWord'
      }),
    },
    methods:{
      confirmWay(){
        if(this.radio == '0'){
          this.$router.push('/m/NASAddress')
        }else{
          this.$router.push('/m/password')
        }
        console.log(this.radio)
      }
    },
    mounted(){
      this.minHeight = ';min-height:'+ document.documentElement.clientHeight + 'px';
    },
  }
</script>
