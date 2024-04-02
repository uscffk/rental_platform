<template>
    <div class="heightAll widthAll"> <!--这样可以设置多个class-->
        
       


        <el-select v-if="identity==1?true:false" clearable placeholder="身份" style="margin-right: 15px;" v-model="searchInfo.identity" @clear="clearIdentity">
                <el-option
                        :key="identity.value"
                        :label="identity.name"
                        :value="identity.value"
                        v-for="identity in [{name:'用户',value:0},{name:'商家',value:1}]"> 
                </el-option>
        </el-select>


         <el-select clearable placeholder="类型" style="margin-right: 15px;" v-model="searchInfo.type" @clear="clearType">
                <el-option
                        :key="type.value"
                        :label="type.name"
                        :value="type.value"
                        v-for="type in [{name:'充值',value:0},{name:'提现',value:1}]"> 
                </el-option>
        </el-select>

         <el-col :span="4" style="margin-right:10px" v-if="identity==1?true:false">
        <el-input  clearable  @clear="clearUserId" v-model="searchInfo.fromId" placeholder="用户Id"></el-input>
         </el-col>
         <el-button @click="search" icon="el-icon-search" type="primary">搜索</el-button>

        <el-table :data="slice" class="el-table"
        border
            header-align="center" style="width: 100%; text-align: center;margin-top:20px"
            :row-class-name="tableRowClassName"
            :header-cell-style="headStyle"  :cell-style="rowStyle"

            >
           
            <el-table-column label="编号" prop="id">
            </el-table-column>
            
            <el-table-column label="用户身份" prop="identity">
                 <template slot-scope="scope">
                    <el-tag :type="scope.row.identity===0 ? '':scope.row.identity===1 ?'success':'danger'"
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.identity===0 ? '用户':'商家'}}</span>
                    </el-tag>
                </template>
            </el-table-column>
            
            <el-table-column label="用户ID" prop="fromId">
                 
            </el-table-column>

             
            <el-table-column label="金额/wei" prop="amount" sortable>
            </el-table-column>

            <el-table-column label="类型" prop="type">
              <template slot-scope="scope">
                    <el-tag :type="scope.row.type===0 ? '':scope.row.type===1 ?'success':'danger'"
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.type===0 ? '充值':'提现'}}</span>
                    </el-tag>
                </template>
            </el-table-column>

            <el-table-column label="时间" prop="time" sortable>
            </el-table-column>


            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button @click="deleteRecharge(scope.row)" size="mini" type="danger">删除</el-button>
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
                       layout="prev, pager, next">
        </el-pagination>

    </div>
</template>

<script>

import constants from "../../../store/constants";
import {afterAction} from "../../../api/common";
import {queryRecharge,deleteRecharge} from '../../../api/platform'
import {getToken} from "../../../api/session"

export default {
    name: 'recharge',
    methods: {
       
       deleteRecharge(row){
           console.log(row)
           deleteRecharge(row).then(rs => {
               if(rs.code==200){
                    const h = this.$createElement;
                    this.$notify({
                    title: '通知',
                    message: h('i', { style: 'color: teal'}, '删除成功')
                    });
                    this.search();
               }else{
                    const h = this.$createElement;
                    this.$notify({
                    title: '通知',
                    message: h('i', { style: 'color: teal'}, '删除失败')
                    });
               }
           })
       },
       search(){
            console.log(this.searchInfo.identity)
            console.log(this.searchInfo.type)
            console.log(this.searchInfo.fromId)
            queryRecharge(this.searchInfo).then(rs => {
            console.log(rs)
            this.recharge = rs;
            console.log(rs.data)
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
       clearUserId(){
           this.searchInfo.fromId = null
       },
       clearIdentity(){
           this.searchInfo.identity = null
       },
       clearType(){
           this.searchInfo.type = null
       },
       change(page) {
            this.pageInfo.pageNo = page;
            this.slice = this.recharge.slice((page - 1) * this.pageInfo.pageSize, page * this.pageInfo.pageSize);
       },
        //加点颜色
        tableRowClassName({rowIndex}) {
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
            identity:null,
            searchInfo:{
                identity:null,
                type:null,
                fromId:null
            },
            pageInfo: {
                pageSize: 5,
                pageNo: 1,
                total: 0,
            },
            slice: null,
            recharge: null,
            dialogFormVisible: false,
            isMod: true,
        }
    },
    mounted() {
        queryRecharge(this.searchInfo).then(rs => {
            this.identity = getToken();
            console.log(rs)
            this.recharge = rs;
            console.log(rs.data)
            this.pageInfo.total = rs.length;
            this.pageInfo.pageNo = 1;
            this.slice = rs.slice(0, this.pageInfo.pageSize);
        })
    },
    computed: {}
}

</script>

<style >
/* .el-table-column{
        width:20%
} */


.el-table .warning-row {
    background: rgb(245, 236, 220);
  }

  .el-table .success-row {
    background: #edf6e7;
}
</style>