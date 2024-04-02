/


<template>
<!-- 注册中心的监控页面 -->
    <div>
        <iframe id="iframe" src="http://localhost:7001/" width="300" height="300" frameborder="0" scrolling="auto">

        </iframe>
    </div>
</template>
<script>
export default {
    mounted(){
            /**
             * iframe-宽高自适应显示   
             */
            const oIframe = document.getElementById('iframe');
            const deviceWidth = document.documentElement.clientWidth;
            const deviceHeight = document.documentElement.clientHeight;
            oIframe.style.width = (Number(deviceWidth)-220) + 'px'; //数字是页面布局宽度差值
            oIframe.style.height = (Number(deviceHeight)-120) + 'px'; //数字是页面布局高度差
        }

}
</script>