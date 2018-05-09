<template>
  <div class="width100 minWidth1200" id="scroll">
    <img class="absolute top0 left0" src="/static/img/logo.png" style="width:200px;margin:20px 0 0 30px;z-index:3000"/>
    <div class="topContainer1" v-show="sendStatus">
      <!--<img class="width100 height100" src="../assets/img/homePage/BG-top.png"/>-->
      <div class="topContent width:100%">
        <!-- <p class="fontsize55 color-333 textAlignCenter">NAS手气红包‘大放送’</p>
        <p class="fontsize30 textAlignCenter color-darkRed margintop20">大佬你好，多发红包，多交朋友</p> -->
        <div class="topFormWrapper paddingtop70">
          <el-form label-width="120px" :model="ruleForm" :rules="rules" ref="ruleForm">
            <el-form-item label="发送总金额" prop="amount">
              <el-input v-model.number="ruleForm.amount" type="amount" style="width:460px" placeholder="填写NAS金额">
                <i slot="prefix" class="el-input__icon icon-money"></i>
              </el-input>
            </el-form-item>
            <el-form-item label="红包个数" prop="count">
              <el-input v-model="ruleForm.count" style="width:460px" placeholder="填写红包数量，须填写整数">
                <i slot="prefix" class="el-input__icon icon-count"></i>
              </el-input>
            </el-form-item>
            <el-form-item label="我的昵称" prop="nickname">
              <el-input v-model="ruleForm.nickname" style="width:460px" placeholder="别忘了告诉大家，是谁发的红包">
                <i slot="prefix" class="el-input__icon  icon-nicheng"></i>
              </el-input>
            </el-form-item>
            <el-form-item label="红包寄语" prop="word">
              <el-input v-model="ruleForm.word" style="width:460px" placeholder="（可不填）最长二十个中文字符">
              </el-input>
            </el-form-item>
          </el-form>
        </div>
        <div class="textAlignCenter paddingtop30">
          <button class="sendRedPacket" @click="openDialog">填好了，发送红包</button>
          <p class="textAlignCenter paddingtop30 color-333 fontsize0">注：领取时平台会收取1%的矿工费</p>
        </div>
      </div>
    </div>
    <div class="topContainer2" v-show="!sendStatus">
      <p class="successP"><i class="el-icon-circle-check fontsize55 color-green inlineMiddle marginright15"></i>NAS红包生成好啦！
      </p>
      <div class="codeWrapper" id="qcode">
      </div>
      <p class="padding30X lineheight25 textAlignCenter color-333">请使用微信扫描二维码，打开抢红包页面，再分享给小伙伴们吧</p>
      <div class="textAlignCenter paddingtop70">
        <button class="sendOnceMore" @click="reload">再发一个</button>
      </div>
    </div>
    <template-footer></template-footer>
    <el-dialog title="" :visible="dialogFormVisible" class="indexDialog">
        <div>
            <p class="color-black textAlignCenter fontsize2 marginbottom20">
                正在调起NAS钱包chrome插件，帮您完成转账中<i class="el-icon-loading marginleft5 color-333"></i>
            </p>
            <p class="color-999 lineHeight30">
            注：请保证您已安装了NAS的钱包chrome插件。</br>
            若没有安装，请点击链接进行安装，并在安装完成后重新发送红包<a href="https://github.com/ChengOrangeJu/WebExtensionWallet">https://github.com/ChengOrangeJu/WebExtensionWallet</a>
            </p>
        </div>
        <div slot="footer" class="dialog-footer textAlignCenter paddingbottom10">
            <button class="cancelSend" @click="dialogFormVisible = false">取消发送</button>
        </div>
    </el-dialog>
  </div>
</template>


<script>
  import QRCode from 'qrcodejs2'
  export default {
    components: {
      templateFooter: () => import("@/components/footer")
    },
    name: 'homePage',
    data() {
        var checkCount = (rule, value, callback) => {
            if(this.ruleForm.amount == ''|| this.ruleForm.amount == null || this.ruleForm.amount == undefined){
                return callback(new Error('请先输入NAS红包金额哦'));
            }else{
                if(parseInt((this.ruleForm.amount*10000000)/(value))<(0.01*10000000)){
                    return callback(new Error('人均分到的NAS数量不能少于0.01'));
                }else{
                    if (parseInt(value)>100) {
                        return callback(new Error('一次最多只能发放100个人哦'));
                    }else{
                      callback();
                    }
                }
            }
        };
      return {
        ruleForm:{
            count: null,
            amount: null,
            nickname:'',
            word:'',
        },
        dialogFormVisible: false,
        sendStatus: true,
        extends_install: false,
        nebPay: new NebPay(),
        transaction_status:-1,
        red_id: this.generateUUID(),
        red_address:"",
        qRCode:null,
        rules: {
            amount: [
                { required: true, message: '内容不能为空哦', trigger: 'blur'},
                { type: 'number', message: '红包金额只能为数字哦'}
            ],
            count: [
                { required: true, message: '内容不能为空哦', trigger: 'blur' },
                { pattern: /^[1-9]*[1-9][0-9]*$/, message: '只能是正整数哦'},
                { validator: checkCount, trigger:['change','blur']}
            ],
            nickname: [
                { required: true, message: '内容不能为空哦', trigger: 'blur' }
            ],
            word: [
                { max:20, message: '内容太长，请限制在20字以内哦', trigger: 'blur' }
            ],
        }
      }
    },
    methods: {
      openDialog() {
        this.onClickCallDapp();
      },
      reload(){
          this.sendStatus=true;
          this.qRCode = clear()
      },
      generateUUID() {
        var d = new Date().getTime();
        var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
          var r = (d + Math.random() * 16) % 16 | 0;
          d = Math.floor(d / 16);
          return (c == 'x' ? r : (r & 0x7 | 0x8)).toString(16);
        });
        return uuid;
      },

      onClickCallDapp() {
          this.$refs['ruleForm'].validate((valid) => {
              if (valid) {
                  this.dialogFormVisible = true;
                  let callFunction = "sent";
                  this.red_id = this.generateUUID();
                  let callArgs = [this.ruleForm.count,this.red_id,this.ruleForm.nickname,this.ruleForm.word,new Date().format("yyyy-MM-dd hh:mm:ss")];
                  console.log(this.ruleForm.amount);
                  this.nebPay.call(dappAddress, this.ruleForm.amount, callFunction, JSON.stringify(callArgs), {
                  qrcode: {
                      showQRCode: false
                  },
                  goods: {
                      name: "发红包",
                      desc: "发红包"
                  },
                  callback: this.cbCallDapp
                  });
              } else {
                  console.log('error submit!!');
                  return false;
              }
          });
      },
      cbCallDapp(resp) {
        console.log("callback resp: " + JSON.stringify(resp))
        this.status(resp.txhash);//这个回调成功   改变状态 表单隐藏   二维码展示
      },
      status(txhash){
        let  v = this;
        neb.api.getTransactionReceipt(txhash)
          .then(o => {
            console.log(JSON.stringify(o));
            this.transaction_status = o.status;
            this.red_address = o.from;
            if(o.status === 2){
               window.setTimeout(()=>this.status(txhash),2000)
            }else if(o.status ===1){
              this.sendStatus = false;
              document.getElementById("scroll").scrollTop = 0
              this.dialogFormVisible = false;
              if(v.qRCode!==null){
                v.qRCode.clear(); // 清除代码
                v.qRCode.makeCode("http://wxapi.fishiny.com/m/?red_address="+this.red_address+"&red_id="+this.red_id); // 生成另外一个二维码
              }else{
                v.qRCode = new QRCode(document.getElementById("qcode"), {
                  width : 200,//设置宽高
                  height : 200,
                  text: "http://wxapi.fishiny.com/m/?red_address="+this.red_address+"&red_id="+this.red_id
                });
              }
              console.log("http://wxapi.fishiny.com/m/?red_address="+this.red_address+"&red_id="+this.red_id)
            }
          })
          .catch(function (o) {
            console.log(o)
          })
      }
    },
    mounted() {
      console.log("mounted")
    },
    computed: {
      inserted: function () {
        console.log("NebPay_Init && NebPay");
        return NebPay_Init && NebPay
      }
    }
  }
</script>
