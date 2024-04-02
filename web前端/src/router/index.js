import Vue from 'vue'
import VueRouter from 'vue-router'
import login from "../views/login";
import error from "../views/error/error";
import {getToken} from "../api/session";
import 'nprogress/nprogress.css'
import Nprogress from 'nprogress'
import admin_layout from "../views/layout/admin-layout";
import business_layout from "../views/layout/business-layout";
import addBusiness from "../views/admin/business/addBusiness";
import userList from "../views/admin/user/userList";
import goodsList from "../views/business/goods/goodsList";
import addGood from "../views/business/goods/updateGood";
import category from "../views/admin/category/category";
import businessList from "../views/admin/business/businessList";
import profit from "../views/business/profit/profit";
import feedBack from "../views/feedBack";
import order from "../views/business/order/order.vue"
import Platprofit from "../views/admin/profit/profit"
import recharge from "../views/admin/profit/recharge"
import platformInfo from '@/views/admin/platformInfo/platformInfo'
import auction from '../views/admin/auction/auction.vue'
import bussinessAuction from '../views/business/auction/auction.vue'
import auctionList from '../views/business/auction/auctionList.vue' 
import goodsDistribute from '../views/business/goods/goodsDistribute.vue'
import deposit from '../views/admin/deposit/deposit.vue'
import comment from '../views/admin/comment/comment.vue'
import hystrix from '@/views/admin/hystrixDashboard/hystrix'
import eureka from '@/views/admin/hystrixDashboard/eureka'
import queryAllBackLocation from '../views/business/goods/backLocation.vue'
Vue.use(VueRouter)

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
    if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
    return originalPush.call(this, location).catch(err => err)
}

const routes = [
    {name: 'login', component: login, path: '/login'},
    {
        name: 'admin',
        component: admin_layout,
        path: '/admin',
        redirect: '/admin/platformInfo',
        menus: ['数据统计','用户管理', '商品管理', '厂商管理', '分类管理', '反馈管理','报表统计','拍卖管理','系统监控','押金管理','订单管理','评论管理'], /*自定义属性，admin后台的菜单项名*/
        icons: ['el-icon-s-data','el-icon-user-solid', 'el-icon-sold-out', 'el-icon-eleme', 'el-icon-s-help', 'el-icon-s-comment', 'el-icon-s-data','el-icon-trophy','el-icon-view','el-icon-money','el-icon-notebook-2','el-icon-chat-round'], /*对应的图标*/
        pathIndex: [[8],[0], [1,10], [2], [3],[4],[5,7],[9],[14,15],[11],[12],[13]], /*每个菜单的子菜单在children中对应的index*/
        children: [
            { name: '用户列表', component: userList, path: '/admin/userList' },
            { name: '商品列表', component: goodsList,path: '/admin/goodsList'},
            { name: '厂商列表', component: businessList, path: '/admin/businessList'},
            { name: '分类参数管理', component: category, path: '/admin/category'},
            { name: '反馈列表', component: feedBack, path: '/admin/feedBack' },
            { name: '商品收益统计', component: Platprofit, path: '/admin/profit' },
            { name: '申请受理', component: feedBack, path: '/admin/feedBack' },
            { name: '充值提现统计', component: recharge, path: '/admin/recharge'},
            { name: '平台信息', component: platformInfo,path:'/admin/platformInfo'},
            { name: '拍卖管理',component: auction,path:'/admin/auction'},
            { name: '商品分布', component: goodsDistribute, path: '/admin/goods'},
            { name: '平台押金', component: deposit,path:'/admin/deposit'},
            { name: '全部订单', component: order,path:'/admin/order'},
            { name: '商品评论', component: comment,path:'/admin/comment'},
            { name: '服务监控', component: hystrix,path:'/admin/hystrix'},
            { name: '注册中心', component: eureka,path:'/admin/eureka'}
        ]
    },
    {
        name: 'business',
        component: business_layout,
        path: '/business',
        redirect: '/business/goodsList',
        menus: ['商品管理', '订单管理','收益统计', '反馈管理','拍卖管理','评论管理'],
        icons: ['el-icon-sold-out', 'el-icon-sold-out','el-icon-s-data', 'el-icon-s-comment', 'el-icon-trophy', 'el-icon-chat-round'],
        pathIndex: [[0,7,10],[4], [2,9], [3],[5,6],[8]],
        children: [
            { name: '商品列表', component: goodsList, path: '/business/goodsList'},
            { name: '更新商品', component: addGood, path: '/business/updateGood'},
            { name: '收益报表', component: profit, path: '/business/profit'},
            { name: '反馈列表', component: feedBack, path: '/business/feedBack' },
            { name: '订单列表', component: order, path: '/business/order'},
            { name: '已拍得', component: auctionList, path: '/business/auctionList'},
            { name: '拍卖行', component: bussinessAuction, path: '/business/auction'},
            { name: '商品分布', component: goodsDistribute, path: '/business/goods'},
            { name: '商品评论', component: comment, path: '/business/comment'},
            { name: '充值提现统计', component: recharge, path: '/business/recharge'},
            { name: '租赁站点管理',component: queryAllBackLocation,path: '/business/allBackLocation'}
        ]
    },
    {name: 'error', path: '*', component: error}
]
 
const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})


router.beforeEach((to, from, next) => {//beforeEach是router的钩子函数，在进入路由前执行
    Nprogress.start();
    if (getToken() != null) {
        if (to.path === '/login')
            next('/');
        else {
            if (to.path === '/') { /*根据不同的token跳转不同的视图*/
                if (getToken() === '1')
                    next('/admin')
                else
                    next('/business')
            } else
                next();
        }
    } else {
        if (to.path === '/login')
            next();
        else
            next('/login')
    }
});

router.afterEach(() => {
    Nprogress.done();
})
export default router
