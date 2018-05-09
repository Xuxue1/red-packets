<template>
    <div class="bottomContainer paddingtop70" id="footer">
        <p class="fontsize55 color-333 textAlignCenter">FAQ</p>
        <div class="bottomContent">
            <el-collapse @change="change" v-model="activeName">
                <el-collapse-item name="1">
                    <template slot="title">
                        <i v-if="!first" class="header-icon el-icon-plus fontsize6 marginright20"></i>
                        <i v-if="first" class="header-icon el-icon-minus fontsize6 marginright20"></i>
                        <span class="bold">什么是NAS手气红包</span>
                    </template>
                    <div class="paddingleft30">
                        <p class="paddingleft15">NAS手机红包全新出炉，不知道你就out了！</p>
                        <p class="paddingleft15">玩法与微信手气红包类似，发出NAS红包，与朋友们一起拼手气，看谁能拿到最大的一份。</p>
                        <p class="paddingleft15">NAS由红包发起人进行出资，最终会存放在好友们的NAS个人地址里。</p>
                        <p class="paddingleft15">目前由于技术原因，发送红包只能在PC的chrome浏览器进行；但生成红包后可以再微信内随意分享和领取。</p>
                    </div>
                </el-collapse-item>
                <el-collapse-item name="2">
                    <template slot="title">
                        <i v-if="!second" class="header-icon el-icon-plus fontsize6 marginright20"></i>
                        <i v-if="second" class="header-icon el-icon-minus fontsize6 marginright20"></i>
                        <span class="bold">钱包会如何分享给好友们</span>
                    </template>
                    <div class="paddingleft30">
                        <p class="paddingleft15">生成红包后，使用微信扫描二维码，在微信内打开领红包页面，分享到朋友圈或聊天内，大家就能抢红包啦。</p>
                    </div>
                </el-collapse-item>
                <el-collapse-item name="3">
                    <template slot="title">
                        <i v-if="!third" class="header-icon el-icon-plus fontsize6 marginright20"></i>
                        <i v-if="third" class="header-icon el-icon-minus fontsize6 marginright20"></i>
                        <span class="bold">我没有NAS钱包怎么办</span>
                    </template>
                    <div class="paddingleft30">
                        <p class="paddingleft15">点击下方链接，根据官方引导，在chrome浏览器中安装钱包插件，并生成使用你的个人钱包。</p>
                        <a class="paddingleft15" href="https://github.com/ChengOrangeJu/WebExtensionWallet">https://github.com/ChengOrangeJu/WebExtensionWallet</a>
                    </div>
                </el-collapse-item>
                <el-collapse-item name="4">
                    <template slot="title">
                        <i v-if="!fourth" class="header-icon el-icon-plus fontsize6 marginright20"></i>
                        <i v-if="fourth" class="header-icon el-icon-minus fontsize6 marginright20"></i>
                        <span class="bold">钱包里没有钱怎么办</span>
                    </template>
                    <div class="paddingleft30">
                        <p class="paddingleft15">NAS可以在各大数字货币交易所进行购买，再转账到你个人的NAS地址。</p>
                    </div>
                </el-collapse-item>
            </el-collapse>
        </div>
        <div class="footer">
            <div class="floatLeft width50 textAlignCenter"><router-link to="/leaveMsg" target="_blank">给我们留言</router-link></div>
            <div class="floatLeft width50 textAlignCenter"><router-link to="/recorder" target="_blank">红包发送记录</router-link></div>
        </div>
    </div>
</template>

<script>
export default {
  data () {
    return {
        activeName:[],
        first:false,
        second:false,
        third:false,
        fourth:false,
    }
  },
    methods:{
        change(val){
            if(val.length == 0){
                this.first=false;
                this.second=false;
                this.third=false;
                this.fourth=false;
            }else{
                if(val.indexOf('1')!= -1){
                    this.first = true
                }else{
                    this.first = false;
                }
                if(val.indexOf('2')!= -1){
                    this.second = true
                }else{
                    this.second = false;
                }
                if(val.indexOf('3')!= -1){
                    this.third = true
                }else{
                    this.third = false;
                }
                if(val.indexOf('4')!= -1){
                    this.fourth = true
                }else{
                    this.fourth = false;
                }
            }
                console.log(this.first)
        }
    }
}
</script>
