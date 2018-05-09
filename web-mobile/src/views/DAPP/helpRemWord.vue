<template>
    <div class="DWrpper paddingX20" :style="minHeight">
        <div class="Dtitle">
            记录keystore，保证NAS不丢失
        </div>
        <div class="paddingtop30">
            <p class="fontsize0 theme-color"><span class="bold">我的NAS钱包地址</span><span class="color-999 marginleft20"  style="font-size:10px">请复制粘贴到备忘录</span></p>
            <div class="DNASAddress" style="word-break:break-all">
                {{a1}}
            </div>
        </div>
        <div class="paddingtop20">
            <p class="fontsize0 theme-color"><span class="bold">我的keystore</span><span class="color-999 marginleft20" style="font-size:10px">请复制粘贴到备忘录</span></p>
            <div class="DZJC" style="word-break:break-all">
              {{ZJC}}
            </div>
        </div>
        <div class="paddingtop20">
            <p class="fontsize0 theme-color bold lineheight25">注：</p>
            <p class="lineheight25 fontsize0 theme-color">1.keystore是下载钱包后，唯一能够导入地址的工具，如若没有记录，会导致NAS币遗失；</p>
            <p class="lineheight25 fontsize0 theme-color">2.请确定，已将keystore黏贴到了备忘录。</p>
        </div>
        <div class="DAddressConfirmWrap">
            <button class="DAddressConfirmBtn" @click="DTipZJC = true">我已记录，去领红包</button>
        </div>
        <!--  *****************弹框   确认keystore已经记录好了吗**********************-->
        <el-dialog :visible="DTipZJC" center class="DTipZJC" width="85%">
          <div class="lineheight25 color-333 textAlignCenter">
            确认keystore已经记录好了吗？
          </div>
          <div slot="footer" class="dialog-footer textAlignCenter paddingbottom10 clear">
            <button class="notYet" @click="DTipZJC = false">还没有</button>
            <button class="already" @click="remember">记好了</button>
          </div>
        </el-dialog>
    </div>
</template>

<script>
  import store from '../../store/index'
  import {mapGetters} from 'vuex'
  import Clipboard from 'clipboard'

export default {
  name: 'index',
  data () {
    return {
        DTipZJC:false,
        minHeight:'',
        a1:'',
        ZJC:""
    }
  },
    methods:{
      remember(){
          this.$router.push('/m/openPacket')
      },
      copy() {
        console.log('111111')
        var clipboard = new Clipboard('.DZJC')
        clipboard.on('success', e => {
          console.log('复制成功')
          // 释放内存
          clipboard.destroy()
        })
        clipboard.on('error', e => {
          // 不支持复制
          console.log('该浏览器不支持自动复制')
          // 释放内存
          clipboard.destroy()
        })
      }
    },
  computed: {
    ...mapGetters({
      password: 'getPassword'
    }),
  },
    mounted(){
      console.log(this.password);
      var account = Account.NewAccount();
      var address = account.getAddressString();
      var keyStr = account.toKeyString(this.password);
      store.commit('updateAddress', {address: address});
      this.a1 = address;
      this.ZJC = keyStr;
      this.minHeight = 'min-height:'+ document.documentElement.clientHeight + 'px';
    },
}
</script>
