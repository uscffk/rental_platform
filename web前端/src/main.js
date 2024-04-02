import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import 'nprogress/nprogress.css'
import './assets/common.css'
import Fragment from 'vue-fragment'
import echarts from 'echarts'

Vue.prototype.$echarts = echarts
Vue.use(Fragment.Plugin)

Vue.config.productionTip = false


new Vue({
    router,
    render: h => h(App)
}).$mount('#app')
