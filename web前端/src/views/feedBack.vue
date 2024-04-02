<template>
    <div class="heightAll widthAll"> <!--这样可以设置多个class-->

        <el-table :data="slice" class="el-table"
                  header-align="center" style="width: 100%; text-align: center"
                  :row-class-name="tableRowClassName"
                  :header-cell-style="headStyle"  :cell-style="rowStyle">
            <el-table-column label="编号" prop="id">
            </el-table-column>
            <el-table-column label="反馈用户" prop="users.username">
            </el-table-column>
            <el-table-column label="反馈对象(商家/平台)" prop="manufacturer.name" v-if="type"> <!--只有管理员才需要看到商家id-->
                <template slot-scope="scope">
                    {{scope.row.manufacturerId==0?'平台':scope.row.manufacturer.name}}
                </template>
            </el-table-column>
            <el-table-column label="反馈内容" prop="content">
            </el-table-column>
            <el-table-column label="反馈图片" prop="picture" width="180">
                <template slot-scope="scope">
                    <img :src='getImgUrl(scope.row.picture)' style="width:100px;height:100px">
                </template>
            </el-table-column>
            <el-table-column label="操作" v-if="identity==1?true:false">
                <template slot-scope="scope">
                    <el-button @click="deleteFeed(scope.row.id)" size="mini" type="danger">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <br>
        <el-pagination :current-page="pageInfo.pageNo"
                       :page-size="pageInfo.pageSize"
                       :total="pageInfo.total"
                       @current-change="change"
                       background
                       class="my-changePage"
                       layout="prev, pager, next">
        </el-pagination>
    </div>
</template>

<script>
import {getToken, getUser} from "../api/session";
import {queryFeedBack} from "../api/user";
import {deleteFeedBack} from "../api/feedback";

export default {
    name: 'feedBack',
    methods: {
        query() {
            this.identity = getToken();
            let id = null
            if (getToken() === '2')
                id = getUser().id
            queryFeedBack(id).then((rs) => {
                this.backs = rs;
                this.pageInfo.total = rs.length;
                this.pageInfo.pageNo = 1;
                this.slice = rs.slice(0, this.pageInfo.pageSize);
            });
        },
        rowStyle(){
                return "text-align:center"
        },
        headStyle(){
                return "text-align:center"
        },
        getImgUrl(item){
            console.log(item)
            var url = 'http://localhost:9527/userService/upload/'+item;
            console.log(url)
            return url;
        },
        tableRowClassName({row, rowIndex}) {
        if (rowIndex === 1) {
          return 'warning-row';
        } else if (rowIndex === 3) {
          return 'success-row';
        }
        return '';
       },
        change(page) {
            this.pageInfo.pageNo = page;
            this.slice = this.backs.slice((page - 1) * this.pageInfo.pageSize, page * this.pageInfo.pageSize);
        }, 
        clearManufacturerId(){
            this.searchInfo.manufacturerId = null
        },
        deleteFeed(id) {
            deleteFeedBack(id).then(rs=>{
                if(rs==200){
                    //提示成功
                    const h = this.$createElement;
                    this.$notify({
                    title: '提示',
                    message: h('i', { style: 'color: teal'}, '删除成功')
                    });
                    //更新视图
                    this.query();
                }else{
                     //提示成功
                    const h = this.$createElement;
                    this.$notify({
                    title: '提示',
                    message: h('i', { style: 'color: teal'}, '删除失败')
                    });
                }
            })
        }
    },

    data() {
        return {
            identity:null,
            pageInfo: {
                pageSize: 5,
                pageNo: 1,
                total: 0,
            },
            slice: null,
            backs: null,
            type: true,
        }
    },
    mounted() {
        this.query();
        this.type = getToken() === '1'
    }
}

</script>

<style>
el-table-column{
    width:20%
}
</style>