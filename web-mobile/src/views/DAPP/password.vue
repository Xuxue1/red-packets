<template>
    <div class="DWrpper" :style="minHeight">
        <div class="Dtitle">
            设置我的NAS钱包密码
        </div>
        <div class="paddingtop30 paddingX25">
            <el-form  :model="ruleForm" :rules="rules" ref="ruleForm">
                <el-form-item prop="password">
                    <el-input type="password" v-model="ruleForm.password" class="DAddressInput" placeholder="设置我的NAS钱包密码"></el-input>
                </el-form-item>
            </el-form>
            <p class="fontsize-2 color-999 paddingleft15">请谨慎设置您的密码，后续钱包将会使用</p>
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
    return {
        minHeight:'',
        ruleForm:{
            password:'',
        },
        rules: {
            password: [
                { required: true, message: '内容不能为空', trigger: 'blur' },
                { min: 9, message: '密码需要大于9位', trigger: 'blur' },
            ],
        }
    }
  },
    methods:{
        confirm(){
            this.$refs['ruleForm'].validate((valid) => {
            if (valid) {
              store.commit('updatePassword', {password: this.ruleForm.password});
              console.log("set passsword"+this.ruleForm.password);
              this.$router.push('/m/helpRemWord')
            } else {
                console.log('error submit!!');
                return false;
            }
            });
        },
    },
    mounted(){
        this.minHeight = 'min-height:'+ document.documentElement.clientHeight + 'px';
    },
}
</script>
