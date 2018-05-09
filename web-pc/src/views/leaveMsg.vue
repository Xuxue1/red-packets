<template>
  <div class="leaveMsg" :style="minHeight">
    <img class="absolute top0 left0" src="/static/img/logo.png" style="width:200px;margin:20px 0 0 30px"/>
    <p class="textAlignCenter color-333 paddingtop70" style="font-size:45px">留言</p>
    <div class="topFormWrapper paddingtop50" style="margin-left:130px">
        <el-form label-width="165px" :model="ruleForm" :rules="rules" ref="ruleForm">
            <el-form-item label="您的称呼" prop="name">
                <el-input v-model="ruleForm.name" style="width:460px" placeholder="您的称呼">
                </el-input>
            </el-form-item>
            <el-form-item label="您的联系方式" prop="contact">
                <el-input v-model="ruleForm.contact" style="width:460px" placeholder="您的联系方式">
                </el-input>
            </el-form-item>
            <el-form-item label="有什么想对我们说的" prop="forUs">
                <el-input v-model="ruleForm.forUs" style="width:460px" placeholder="有什么想对我们说的">
                </el-input>
            </el-form-item>
        </el-form>
    </div>
    <div class="textAlignCenter paddingtop50" style="margin-bottom:80px">
        <button class="sendRedPacket" @click="sendMsg">发送留言</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'homePage',
  data () {
    return {
        minHeight:'',
        ruleForm:{
            name:'',
            contact:'',
            forUs:''
        },
        rules:{
            name: [
                { required: true, message: '内容不能为空哦', trigger: 'blur'}
            ],
            contact: [
                { required: true, message: '内容不能为空哦', trigger: 'blur' }
            ],
            forUs: [
                { required: true, message: '内容不能为空哦', trigger: 'blur' }
            ]
        }
    }
  },
    methods:{
        sendMsg(){
             this.$refs['ruleForm'].validate((valid) => {
                if (valid) {
                    console.log('submit')
                    this.$message.success('发送成功！')
                }else{
                    console.log('error submit!!');
                    return false;
                }
             })
        }
    },
    mounted(){
        this.minHeight = 'min-height:'+ document.documentElement.clientHeight + 'px';
    }
}
</script>
