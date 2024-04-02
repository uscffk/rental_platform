<template>
    <div class="heightAll widthAll"> <!--这样可以设置多个class-->

       <div style="margin-bottom: 10px;">
            <el-button @click="add" circle icon="el-icon-plus" size="mini" style="display: inline"
                       type="success"></el-button>
            <span style="color: #85C161; margin-left: 10px;">添加厂商</span>
            
            <el-row style="margin-top:10px">
              <el-col :span="4" style="margin-right:10px">
                  <el-input clearable  @clear="clearname" v-model="searchInfo.name" placeholder="厂商名"></el-input>
              </el-col>
               <el-button @click="search" icon="el-icon-search" type="primary">搜索</el-button>
            </el-row>
             
        </div>



        <el-table :data="slice" class="el-table" border
                  header-align="center"  style="width: 100%; text-align: center"
                  :row-class-name="tableRowClassName"
                  :header-cell-style="headStyle"  :cell-style="rowStyle">
            <el-table-column label="编号" prop="id">
            </el-table-column>
            <el-table-column label="厂商名称" prop="name">
            </el-table-column>
            <el-table-column label="联系电话" prop="phone">
            </el-table-column>
            <el-table-column label="用户名" prop="username">
            </el-table-column>
            <!-- <el-table-column label="余额/wei" prop="balance">
            </el-table-column> -->
           

            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button @click="rechargeOpen(scope.row)" size="mini" type="primary">充值</el-button>
                </template>
            </el-table-column>
        </el-table>
        <br>
        <!--分页组件-->
        <el-pagination :current-page="pageInfo.pageNo"
                       :page-size="pageInfo.pageSize"
                       :total="pageInfo.total"
                       @current-change="change"
                       background
                    style="text-align: center;"
                       class="my-changePage"
                       layout="prev, pager, next">
        </el-pagination>

        <el-dialog :title="'充值平台币->' + rechargeInfo.username" :visible.sync="dialogFormVisible1" width="20%">
            <el-input-number :min="0" label="充值金额" v-model="rechargeInfo.amount"></el-input-number>
            <img alt="" height="30" src="../../../assets/ethe.jpeg" style="position: relative; top: 7px;" width="30">
            <div class="dialog-footer" slot="footer">
                <el-button @click="close1" style="position: relative; left: -55px;">取 消</el-button>
                <el-button @click="recharge" style="position: relative; left: -35px" type="primary">确 定</el-button>
            </div>
        </el-dialog>


        <el-dialog title="添加厂商信息" :visible.sync="dialogFormVisible">
            
            <!-- :rules="rules"  这个表单验证现在不想做--> 
            <el-form :model="businesses" :label-position="left">

                <el-row>
                  <el-col>
                      <el-form-item label="厂商名">
                        <el-input v-model="business.name"></el-input>
                      </el-form-item>
                  </el-col>
                </el-row>
                
                <el-form-item label="用户名">
                    <el-input v-model="business.username"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="business.pwd" type="password"></el-input>
                </el-form-item>

                <el-form-item label="确认密码" prop="pwd2">
                    <el-input class="input" clearable type="password"
                            v-model="business.pwd2"></el-input>
                </el-form-item>

                <el-form-item label="联系电话">
                    <el-input v-model="business.phone"></el-input>
                </el-form-item>

            </el-form>

            <div class="dialog-footer" slot="footer" style="margin:0 auto;">
                <el-button @click="close">取 消</el-button>
                <el-button @click="submit" type="primary">确 定</el-button>
            </div>

        </el-dialog>
    </div>
</template>

<script>
import {queryBusinesses} from "../../../api/business";
import {recharge} from "../../../api/platform";
import constants from "../../../store/constants";
import {afterAction} from "../../../api/common";
import {addBusiness} from "../../../api/business"

export default {
    name: 'businessList',
    methods: {
        search(){
            queryBusinesses(this.searchInfo).then(rs=>{
                this.businesses = rs;
                this.pageInfo.total = rs.length;
                this.pageInfo.pageNo = 1;
                this.slice = rs.slice(0, this.pageInfo.pageSize);
            })
        },
        rowStyle(){
                return "text-align:center"
        },
        headStyle(){
                return "text-align:center"
        },
        clearname(){
            this.searchInfo.name = null;
        },
        recharge() {
            console.log(this.rechargeInfo);
            recharge(this.rechargeInfo).then(rs => {
                this.close();
                if (rs.code === constants.state.SUCCESS) {
                    afterAction(this, true, '充值', null, false);
                } else {
                    this.$message.error(rs.message);
                }
            })
        },
        close() {
            this.dialogFormVisible = false;
        },
        close1() {
            this.dialogFormVisible1 = false;
        },
        rechargeOpen(row) {
            this.rechargeInfo.userId = row.id;
            this.rechargeInfo.amount = 0;
            this.dialogFormVisible1 = true;
        },
        //加点颜色
        tableRowClassName({row, rowIndex}) {
                    if (rowIndex === 1) {
                    return 'warning-row';
                    } else if (rowIndex === 3) {
                    return 'success-row';
                    }else if(rowIndex === 5){
                        return 'danger-row';
                    }
                    return '';
        },
        change(page) {
            this.pageInfo.pageNo = page;
            this.slice = this.businesses.slice((page - 1) * this.pageInfo.pageSize, page * this.pageInfo.pageSize);
        } ,
        add(){
            //this.isMod = false;
            this.dialogFormVisible = true;
        },
        submit(){
            //判断两次密码一致
            if(this.business.pwd!=this.business.pwd2){
                const h = this.$createElement;
                this.$notify({
                title: '提示',
                message: h('i', { style: 'color: teal'}, '两次密码不一致')
                });
            }else{
                addBusiness(this.business).then(rs =>{
                if(rs.code == constants.state.SUCCESS){
                      afterAction(this, true, '添加厂商', null, false);
                }else{
                    this.$message.error(rs.message);
                }
            });
            }
            
        }
    },
    data() {
         const validatePass = (rule, value, callback) => {
            if (value === '' || !value) {
                callback(new Error('请输入密码'));
            } else {
                if (!this.business.pwd2 && this.business.pwd2 !== '') {
                    this.$refs.business.validateField('pwd2');
                }
                callback();
            }
        };
        const validatePass2 = (rule, value, callback) => {
            console.log(value)
            if (value === '' || !value) {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.business.pwd) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            pageInfo: {
                pageSize: 7,
                pageNo: 1,
                total: 0,
            },
            searchInfo:{
                name:null
            },
            business:{
                name:null,
                username:null,
                pwd:null,
                pwd2:null,
                phone:null
            },
            businesses:null,
            slice: null,
            dialogFormVisible: false,
            dialogFormVisible1: false,
            rechargeInfo: {
                userId: null,
                amount: 0,
                from:1
            },
            
             rules: {
                pwd: [
                    {validator: validatePass, trigger: 'blur'},
                    {
                        pattern: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/,
                        message: '密码至少包含：数字和英文字母，长度6-20',
                        trigger: 'blur'
                    }
                ],
                pwd2: [
                    {validator: validatePass2, trigger: 'blur'}
                ],
                name: [{required: true, trigger: 'blur', message: '请输入厂商名'}],
                username: [{required: true, trigger: 'blur', message: '请输入用户名'}]
            }
        }
    },
    mounted() {
        queryBusinesses({}).then(rs => {
            this.businesses = rs;
            this.pageInfo.total = rs.length;
            this.pageInfo.pageNo = 1;
            this.slice = rs.slice(0, this.pageInfo.pageSize);
        });
    },
    computed: {}
}

</script>

<style scoped>
.el-table-column{
        width:16%
}

.el-table .warning-row {
    background: #b8ccad;
}

.el-table .success-row {
    background: #c4dab9;
}

.el-table .danger-row{
    background:#c2ebac
}
 
</style>