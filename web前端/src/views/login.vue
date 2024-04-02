
<template>
    <div class="login_container heightAll widthAll" style="text-align: center">
        <div class="login_box">
            <div class="avatar_box">
                <img alt="avatar" src="../assets/logo.png"/>
            </div>
            <div>
                <el-form
                        label-position="left"
                        :model="loginForm"
                        :rules="loginFormRules"
                        class="login_form"
                        label-width="60px"
                        ref="loginFormRef">
                    <el-form-item label="账号" prop="username">
                        <el-input prefix-icon="iconfont icon-user" v-model="loginForm.username"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                        <el-input
                                prefix-icon="iconfont icon-3702mima"
                                type="password"
                                v-model="loginForm.password"
                        ></el-input>
                    </el-form-item>
                    <el-form-item prop="type">
                        <el-radio-group v-model="loginForm.type">
                            <el-radio :label="1" border style="margin-left:-40px">系统管理员
                            </el-radio>
                            <el-radio :label="2" border style="margin-left:-15px">平台商家</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item class="widthAll" style="text-align: center">
                        <el-button @click="login" class="btns" style="position:absolute; left:30px;"
                                   type="primary">登录
                        </el-button>
                        <el-button @click="resetLoginForm" class="btns" style="margin-left: 80px;" type="info">重置
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>


import {afterAction} from "../api/common";
import {setToken,setUser,getUser} from '../api/session'
import qs from 'qs'

export default {
    data() {
        return {
            loginForm: {
                type: 1,
                username: 'admin',
                password: '123'
            },
            // 表单验证
            loginFormRules: {
                username: [
                    {pattern: /^\w+$/, message: '请勿输入非法字符', trigger: "blur"},
                    {required: true, message: '请输入用户名', trigger: 'blur'},
                    {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}
                ],
                password: [
                    {pattern: /^\w+$/, message: '请勿输入非法字符', trigger: "blur"},
                    {required: true, message: '请输入用户密码', trigger: 'blur'},
                    {min: 2, max: 18, message: '长度在 2 到 18 个字符', trigger: 'blur'}
                ],
                type: [
                    {required: true, message: '请选择登入方式', trigger: 'blur'},
                ]
            }
        }
    },
    methods: {
        resetLoginForm() {
            this.$refs.loginFormRef.resetFields()
        },
        login() {
            this.$refs.loginFormRef.validate(async valid => {
                if (!valid) return false
                setToken(this.loginForm.type); //登录类型， 1-amdin, 2-商家
                var url = '/api/businessService/login'
                let param = this.loginForm
                console.log(param)
                let config = { 
                };
                param = qs.stringify(param);
                if (this.loginForm.type === 1) {
                    url = '/api/platformService/login'       
                }
                console.log(param)
                axios.post(url, param, config).then(resp => {
                        console.log(resp.data)
                        if (resp.data.code === 200) {
                            setUser(resp.data.data); //设置登录对象  
                            afterAction(this, true, '登录', '/', true);
                        } else {
                            this.$message.error('登入失败');
                        }
                    }
                ).catch(() => {
                    this.$message.error('登入失败');
                });
            })
        }
    }
}
</script>

<style scoped>
    .login_container {
        background-color: #2b4b6b;
    }

    .login_box {
        width: 450px;
        height: 400px;
        background-color: #fff;
        border-radius: 3px;
        position: absolute;
        left: 50%;
        top: 50%;
        -webkit-transform: translate(-50%, -50%);
    }

    .login_box .avatar_box {
        width: 130px;
        height: 130px;
        border: 1px solid #eee;
        border-radius: 50%;
        padding: 10px;
        box-shadow: 0 0 10px #ddd;
        position: absolute;
        left: 50%;
        transform: translate(-50%, -50%);
        background-color: #fff;
    }


    .avatar_box > img {
        width: 100%;
        height: 100%;
        border-radius: 50%;
        background-color: #eee;
    }

    .login_form {
        position: absolute;
        bottom: 40px;
        width: 100%;
        padding: 0 20px;
        box-sizing: border-box;
    }

    .btns {
        width: 120px;
        border-radius: 8px;
    }
</style>
