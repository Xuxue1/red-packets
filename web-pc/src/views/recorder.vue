<template>
  <div class="recorder">
    <img class="absolute top0 left0" src="/static/img/logo.png" style="width:200px;margin:20px 0 0 30px"/>
    <p class="textAlignCenter color-333 paddingtop70" style="font-size:45px">NAS红包发送记录</p>
    <p class="textAlignCenter color-999 margintop10">注：仅记录了浏览器内存储的发送信息</p>
    <div class="paddingtop30" style="width: 980px;margin:0 auto">
      <el-table
        border
        stripe
        :data="tableData"
        :empty-text="emptyText"
      >
        <el-table-column
          prop="name"
          label="二维码"
          width="180"
          align="center">
          <template slot-scope="scope">
            <div :id="'qcode'+ (scope.$index)" class="ImgCenter">
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="date"
          label="日期"
          width="180"
          align="center">
        </el-table-column>
        <el-table-column
          prop="money"
          label="NAS金额"
          align="center">
        </el-table-column>
        <el-table-column
          prop="account"
          label="红包数"
          align="center">
        </el-table-column>
        <el-table-column
          prop="name"
          label="昵称"
          align="center">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
  import QRCode from 'qrcodejs2'
  export default {
    name: 'homePage',
    data() {
      return {
        tableData: [],
        ids: [],
        message: [],
        nebPay: new NebPay(),
        a:null,
        emptyText:'加载中...'
      }
    },
    created() {
      console.log("create...");
      this.getId()
    },
    //simulateCall
    methods: {
      getId() {
        var callFunction = "ids";
        var callArgs = [];
        this.nebPay.simulateCall(dappAddress, "0", callFunction, JSON.stringify(callArgs), {
          callback: this.cbCallDapp
        });
      },
      cbCallDapp(resp) {
        var data = JSON.parse(resp.result);
        this.emptyText = (resp.data == null?'暂无数据':'');
        data.ids.forEach((a,i) => {
            // console.log(a)
            let callFunction = "message";
            let callArgs = [a];
            this.nebPay.simulateCall(dappAddress, "0", callFunction, JSON.stringify(callArgs), {
              callback: (resp) => {
                // console.log('1111111111',JSON.stringify(resp))
                let resp_data = JSON.parse(resp.result);
                let iteam = {}
                iteam.name = "";
                iteam.date = resp_data.time;
                iteam.money = resp_data.amount/nas_rate;
                console.log(iteam.money);
                iteam.account = parseInt(resp_data.count)+resp_data.recods.length;
                resp_data.recods.forEach(r=>{
                  iteam.money+=parseInt(r.amount)/nas_rate;
                });
                iteam.money = iteam.money.toFixed(6);
                iteam.name = resp_data.nickname;
                this.tableData.push(iteam);
                console.log(JSON.stringify(resp),'11111111111111');
                this.$nextTick(function(){
                  if(document.getElementById("qcode"+i) == null){
                    setTimeout( () => {
                      var code = new QRCode(document.getElementById("qcode"+i), {
                        width : 100,//设置宽高
                        height : 100,
                        text: "http://wxapi.fishiny.com/m/?red_address="+resp_data.from+"&red_id="+resp_data.id
                      });
                    },1000)
                  }else{
                    var code = new QRCode(document.getElementById("qcode"+i), {
                      width : 100,//设置宽高
                      height : 100,
                      text: "http://wxapi.fishiny.com/m/?red_address="+resp_data.from+"&red_id="+resp_data.id
                    });
                  }
                })
              }
            });
          });
        },
      getDetail(id) {

      }
    },
  }
</script>
