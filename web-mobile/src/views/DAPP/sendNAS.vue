<template>
    <div class="DWrpper paddingX20" :style="minHeight">
        <div class="Dtitle" style="padding-top:20%">发送NAS红包</div>
        <div class="paddingtop30">
            <div class="DNASAddress">
                <p class="fontsize1 theme-color lineheight30">由于技术原因，目前仅电脑chrome浏览器支持发送NAS红包。</p>
            </div>
        </div>
        <div class="paddingtop20">
                <p class="fontsize1 theme-color lineheight30">请复制以下链接，在电脑上的chrome浏览器打开。<br> <span>http://xu.renmaixifen.com/</span></p>
        </div>
    </div>
</template>

<script>
export default {
  name: 'index',
  data () {
    return {
        minHeight:'',
    }
  },
    methods:{
    },
    mounted(){
        this.minHeight = 'min-height:'+ document.documentElement.clientHeight + 'px';
    },
}
</script>
