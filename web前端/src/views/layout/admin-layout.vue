<template>
    <!--这个页面很重要，是基础页面，导航布局-->
    <!--本来这一整段都是放在 App.vue <div>中的</div>-->
    <div class="heightAll widthAll"> <!--有些css会继承-->
        <!--整个容器-->
        <el-container class="el-container heightAll widthAll">
            <!--左侧导航栏-->
            <!--width可以定义在style中，也可以直接写, 切记直接写的话没有分号-->
            <el-aside :width="width" class="el-aside heightAll" style="overflow: hidden;"> <!--动态设置width-->
                <!--带冒号才能使用vue变量，属性介绍 https://element.eleme.cn/#/zh-CN/component/menu -->
                <!--整个菜单,  其中border-width解决菜单item展开多出1px， default-Active会选中subItem index与当前路由url相同的菜单项-->
                <div @click="handleCollapse" class="toggle-button widthAll"><i
                        :class="isCollapsed ? 'el-icon-s-unfold' : 'el-icon-s-fold'"></i>
                </div>
                <el-menu :collapse="isCollapsed"
                         :collapse-transition="false"
                         :default-active="$route.path"
                         active-text-color="#ffd04b"
                         background-color="#304156"
                         class="heightAll widthAll el-menu-vertical-demo"
                         router
                         style="border-width: 0"
                         text-color="#fff"
                         unique-opened> <!--菜单栏-->
                    <admin-compo></admin-compo>
                </el-menu>
            </el-aside>
            <!--右半部分-->
            <el-container class="el-container heightAll">
                <el-header class="el-header widthAll myHeader">
                    <span class="projectICon">
                    <span
                            style="color: cornflowerblue; font-weight: bold; font-size: 18px;">智能化共享租赁平台-管理端</span>
                    </span>
                    <el-dropdown class="el-dropdown myDrop">
                        <img alt="guitar"
                             src="../../assets/guitar.gif"/>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item>
                                <el-tooltip :content="userInfo" effect="light"
                                            placement="top-start" transition="el-fade-in-linear">
                                    <span><i class="el-icon-share"/>个人信息</span>
                                </el-tooltip>
                            </el-dropdown-item>
                            <el-dropdown-item @mouseover.native="queryBalance"><img height="20"
                                                                                    src="../../assets/ethe.jpeg"
                                                                                    style="position: relative; top: 5px; right: 5px;"
                                                                                    width="20">余额:{{amount}}
                            </el-dropdown-item>
                            <!--我靠，要加native-->
                            <el-dropdown-item @click.native="logout" divided><i class="el-icon-moon-night"/>Logout
                            </el-dropdown-item>

                            <el-dropdown-item @click.native="openRecharge" divided><i class="el-icon-money"/>合约充值
                            </el-dropdown-item>

                            <el-dropdown-item @click.native="transferToExteneralAccounts" divided><i class="el-icon-money"/>转移合约账户
                            </el-dropdown-item>

                        </el-dropdown-menu>
                    </el-dropdown>
                    <i class="el-icon-caret-bottom"></i>
                </el-header>
                <el-main class="my-el-main widthAll" style="padding: 20px;">
                    <!--有意思的是padding不卸载这里生效不了-->
                    <router-view></router-view> <!--被children组件渲染-->
                </el-main>
            </el-container>
        </el-container>


        <el-dialog :title='充值平台币' :visible.sync="dialogFormVisible" width="20%">
            <el-input-number :min="0" label="充值金额" v-model="rechargeInfo.amount"></el-input-number>
            <img alt="" height="30" src="../../assets/ethe.jpeg" style="position: relative; top: 7px;" width="30">
            <div class="dialog-footer" slot="footer">
                <el-button @click="close" style="position: relative; left: -55px;">取 消</el-button>
                <el-button @click="recharge" style="position: relative; left: -35px" type="primary">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>

import {removeToken} from "../../api/session";
import adminCompo from "./admin-copo";
import {queryBalance,rechargeToSelf,transferToExteneralAccount} from "../../api/platform";

export default {
    name: "admin",
    data() {
        return {
            userInfo: '系统管理员：admin',
            width: "200px",
            isCollapsed: false,
            amount:0,
            rechargeInfo:{
                amount:0
            },
            dialogFormVisible:false
        }
    },
    components: {adminCompo},
    methods: {
        transferToExteneralAccounts(){
            transferToExteneralAccount().then(rs=>{
                if(rs.code==200){
                    //更新余额
                    this.queryBalance();
                    const h = this.$createElement;
                    this.$notify({
                    title: '提示',
                    message: h('i', { style: 'color: teal'}, '提取成功')
                    });
                }else{
                    const h = this.$createElement;
                    this.$notify({
                    title: '提示',
                    message: h('i', { style: 'color: teal'}, '提取失败')
                    });
                }
            })
        },
        openRecharge(){
            this.dialogFormVisible = true;
        },
        close(){
            this.dialogFormVisible = false;
        },
        recharge(){
            rechargeToSelf(this.rechargeInfo.amount).then(rs=>{
                console.log(rs);
                if(rs.code==200){
                    this.dialogFormVisible = false;
                    //更新余额
                    this.queryBalance();
                    const h = this.$createElement;
                    this.$notify({
                    title: '提示',
                    message: h('i', { style: 'color: teal'}, '充值成功')
                    });
                }else{
                    const h = this.$createElement;
                    this.$notify({
                    title: '提示',
                    message: h('i', { style: 'color: teal'}, '充值失败')
                    });
                    this.dialogFormVisible = false;
                }
            })
        },
        queryBalance() {
           
            const _this = this;
            queryBalance().then(rs => {
                console.log(JSON.parse(rs.data))
                _this.amount = JSON.parse(rs.data)
            })
        },

        logout() {
            removeToken();
            this.$message.success('您已退出登入');
            clearTimeout(this.timer);
            this.timer = setTimeout(() => {
                this.$router.push('/login');
            }, 1000);
        },
        handleCollapse() {
            this.isCollapsed = !this.isCollapsed;
            if (this.isCollapsed) this.width = "70px";
            else this.width = "200px";
        }
    },
    mounted(){
        this.queryBalance();
    }
}

</script>
<style scope>
    /*样式才是最烦的*/
    .myHeader {
        text-align: right;
        font-size: 12px;
        margin-bottom: 10px;
        padding: 5px;
        background-color: #fff;
        overflow: hidden;
        height: 10%;
        box-shadow: 0px 0px 8px 0px #d0d0d0;
    }

    .projectICon {
        margin-top: 10px;
        float: left;
    }

    .projectICon img {
        vertical-align: middle;
    }

    .projectICon > label {
        color: #67C23A;
        font-size: 18px;
        font-family: 宋体, sans-serif;
        font-weight: bold;
        text-shadow: 1px 1px 1px green, 0 0 1px aqua;
    }

    .myDrop {
        font-size: 20px;
        margin-top: 10px;
        margin-right: 5px;
        cursor: pointer
    }

    .myDrop img {
        height: 40px;
        width: 40px;
        border-radius: 5px;
    }

    .my-el-main {
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
        border-radius: 6px;
        overflow: scroll;
    }

    .my-footer {
        margin-top: 10px;
        text-align: center;
        font-size: 14px;
        text-shadow: 5px 5px 5px antiquewhite, 0 0 2px black;
    }

    .my-footer > div {
        vertical-align: center;
        margin-top: 20px;
    }

    .toggle-button {
        background-color: #2b4b6b;
        font-size: 20px;
        line-height: 30px;
        color: #fff;
        text-align: center;
        letter-spacing: 0.2em;
        cursor: pointer;
    }

    .el-menu-vertical-demo:not(.el-menu--collapse) {
        width: 200px;
        min-height: 400px;
    }

</style>