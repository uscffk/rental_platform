<template>

    <div class="heightAll widthAll"> <!--这样可以设置多个class-->


        <el-row :gutter="20">
            <el-col :span="4"><el-input clearable  @clear="clearUsername" v-model="searchInfo.username" placeholder="用户名"></el-input></el-col>
            <el-col :span="4">
            

            <el-select clearable placeholder="省份"  style="margin-right: 15px;" v-model="searchInfo.province" @clear="clearProvince">
                <el-option
                        :key="province.label"
                        :label="province.label"
                        :value="province.label"
                        v-for="province in provinceListAll"> 
                </el-option>
            </el-select>

            </el-col>
            <el-col :span="4">

            <el-select clearable placeholder="租赁资格" style="margin-right: 15px;" v-model="searchInfo.rentQualification" @clear="clearQulifacation">
                <el-option
                        :key="Qualification.value"
                        :label="Qualification.name"
                        :value="Qualification.value"
                        v-for="Qualification in [{name:'有',value:1},{name:'无',value:0}]"> 
                </el-option>
            </el-select>
            </el-col>
            <el-button @click="search" icon="el-icon-search" type="primary">搜索</el-button>
            
        </el-row>

        <el-table :data="slice" class="el-table" border 
                  header-align="center"  style="width: 100%; text-align: center;margin-top:20px"
                  :row-class-name="tableRowClassName"
                  :header-cell-style="headStyle"  :cell-style="rowStyle">
            <el-table-column label="编号" prop="id" width="50">
            </el-table-column>
            <el-table-column label="用户名" prop="username">
            </el-table-column>

            <el-table-column label="头像" prop="userhead" width="150">
                <template slot-scope="scope">
                    <img :src='getImgUrl(scope.row.userhead)' style="width:50px;height:50px">
                </template>
            </el-table-column>

            <el-table-column label="信用" prop="credit" width="80" sortable>
            </el-table-column>
            
            <el-table-column label="余额/wei" prop="balance" width="100" sortable>
            </el-table-column>

            <el-table-column label="手机号码" prop="phone">
            </el-table-column>
            <el-table-column label="邮箱" prop="email">
            </el-table-column>
            <el-table-column label="身份证" prop="identity">
            </el-table-column>
            <el-table-column label="真实姓名" prop="realName">
            </el-table-column>
             <el-table-column label="城市" prop="city">
            </el-table-column>
             <el-table-column label="省份" prop="province">
            </el-table-column>
             <el-table-column label="性别" prop="sex">
                
                <template slot-scope="scope">
                    <el-tag 
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.sex===0 ? '男':'女'}}</span>
                    </el-tag>
                </template>

            </el-table-column>

             <el-table-column label="租赁资格" prop="rentQualification">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.rentQualification===1 ? 'success':'warning' "
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.rentQualification===1 ? '有':'无'}}</span>
                    </el-tag>
                </template>
            </el-table-column>

            <el-table-column label="免押资格">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.credit>=500 ? 'success':'warning' "
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.credit>=500 ? '有':'无'}}</span>
                    </el-tag>
                </template>
            </el-table-column>

            <el-table-column label="操作" width="150px">
                <template slot-scope="scope">
                    <el-button @click="update(scope.row)" size="mini" type="primary">修改</el-button>
                    <!-- 账户开的是合约账户 删除不仅仅是简单的从数据库删一条数据 先不做实现 这个功能其实也没必要 -->
                    <!-- <el-button @click="deleteUser(scope.row)" size="mini" type="danger">删除</el-button> -->
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


        <el-dialog
            title="用户信息修改"
            :visible.sync="dialogVisible"
            width="40%"
            :before-close="handleClose">
            
            <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">
                <el-form-item label="用户名">
                    <el-input v-model="formUser.username"></el-input>
                </el-form-item>
              
                <el-form-item label="手机号码">
                    <el-input v-model="formUser.phone"></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="formUser.email"></el-input>
                </el-form-item>
                <el-form-item label="身份证">
                    <el-input v-model="formUser.identity" disabled></el-input>
                </el-form-item>
                <el-form-item label="真实姓名">
                    <el-input v-model="formUser.realName" disabled></el-input>
                </el-form-item>
                <el-form-item label="城市">
                    <el-input v-model="formUser.city" disabled></el-input>
                </el-form-item>
                <el-form-item label="省份">
                    <el-input v-model="formUser.province" disabled></el-input>
                </el-form-item>
            
               

             <el-form-item label="租赁资格">
                <el-select style="width: 240px"
                           v-model="formUser.rentQualification">
                    <el-option
                            :key="status.id"
                            :label="status.name"
                            :value="status.id"
                            v-for="status in [{id:0,name:'无'},{id:1,name:'有'}]">
                    </el-option>
                </el-select>
            </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitUpdate">确认修改</el-button>
            </span>
        </el-dialog>
     
    </div>
</template>

<script>
import {queryUsers,updateUser} from "../../../api/user";

export default {
    name: 'userList',
    methods: {
        clearProvince(){
            this.searchInfo.province = null;
        },
        clearUsername(){
            this.searchInfo.username = null;
        },
        clearQulifacation(){
            this.searchInfo.rentQualification = null;
        }, 
        rowStyle(){
                return "text-align:center"
        },
        headStyle(){
                return "text-align:center"
        },
        //点击搜索
        search(){
            queryUsers(this.searchInfo).then(rs=>{
                console.log(rs)
                this.pageInfo.total = rs.length;
                this.pageInfo.pageNo = 1;
                this.slice = rs.slice(0, this.pageInfo.pageSize);
            })
        },

        submitUpdate(){
            updateUser(this.formUser).then(rs=>{
                //应该统一封装一个函数的 后续再说
                if(rs==200){
                    const h = this.$createElement;
                    this.$notify({
                    title: '消息',
                    message: h('i', { style: 'color: teal'}, '修改成功')
                    });
                    this.dialogVisible = false;
                }else{
                    const h = this.$createElement;
                    this.$notify({
                    title: '消息',
                    message: h('i', { style: 'color: teal'}, '修改失败')
                    });
                }
            })
        },
        update(row){
            console.log(row);
            this.dialogVisible = true;
            this.formUser = row
        },
        deleteUser(row){
            //先别去实现
        },
        change(page) {
            this.pageInfo.pageNo = page;
            this.slice = this.users.slice((page - 1) * this.pageInfo.pageSize, page * this.pageInfo.pageSize);
        },
        getImgUrl(item){ 
            var url = 'http://localhost:9527/userService/upload/userhead/'+item;
            console.log(url)
            return url;
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
    },
    data() {
        return {
            searchInfo:{
                username:null,
                city:null,
                province:null,
                rentQualification:null
            },
            provinceListAll: [],      // 全国34个省份（包括直辖市）列表
            cityListAll: [],        // 全国各个城市列表
            formUser:{
                username:null,
                city:null,
                province:null,
                sex:null,
                id:null,
                userhead:null,
                identity:null,
                realName:null,
                email:null,
                phone:null,
            },
            dialogVisible:false,
            //前端伪分页
            pageInfo: {
                pageSize: 5,
                pageNo: 1,
                total: 0,
            },
            users: null,
            slice: null,
        }
    },
    mounted() {
        queryUsers({}).then(rs => {
            this.users = rs;
            this.pageInfo.total = rs.length;
            this.pageInfo.pageNo = 1;
            this.slice = rs.slice(0, this.pageInfo.pageSize);
        })
        //初始化省份
       
        axios.get('/province/province.json').then((rs=>{
            this.provinceListAll = rs.data
            console.log(rs)
        }))
                
            
    },
    computed: {}
}

</script>

<style scoped>
.el-table-column{
        width:12%
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