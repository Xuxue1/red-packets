<template>
    <div class="DWrpper" :style="minHeight">
        <div class="Dtitle">
            输入我的NAS钱包地址
        </div>
        <div class="paddingtop30 paddingX25">
            <el-form  :model="ruleForm" :rules="rules" ref="ruleForm">
                <el-form-item prop="address">
                    <el-input v-model="ruleForm.address" class="DAddressInput" placeholder="输入地址"></el-input>
                </el-form-item>
            </el-form>
        </div>
        <div class="DAddressConfirmWrap">
            <button class="DAddressConfirmBtn" @click="confirm">确认</button>
        </div>
    </div>
</template>

<script>
  import store from '../../store/index'
  import {mapGetters} from 'vuex'
export default {
  name: 'index',
  data () {
    var checkCount = (rule, value, callback) => {
      if(!Account.isValidAddress(this.ruleForm.address)){
        return callback(new Error('请输入合法的NAS地址'));
      }else{
        return callback()
      }
    };
    return {
        minHeight:'',
        ruleForm:{
            address:'',
        },
        rules: {
            address: [
                { required: true, message: '内容不能为空', trigger: 'blur' },
                { validator: checkCount, trigger:['change','blur']}
            ],
        }
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
    methods:{
        confirm(){
            this.$refs['ruleForm'].validate((valid) => {
            if (valid) {
              store.commit('updateAddress', {address: this.ruleForm.address});
              this.$router.push('/m?red_id='+this.redId+"&red_address="+this.redAddress)
            } else {
                console.log('error submit!!');
                return false;
            }
            });
        },
    },
    mounted(){
        window.title = "输入我的NAS钱包地址";
        this.minHeight = 'min-height:'+ document.documentElement.clientHeight + 'px';
    }
}
</script>
