<template>
    <div>

      <el-col :span="4" style="margin-right:10px">
        <el-input  clearable  @clear="clearNFTID" v-model="searchInfo.nftId" placeholder="NFT ID"></el-input>
      </el-col>


      <el-select clearable placeholder="是否上链" style="margin-right: 15px;" v-model="searchInfo.upChain" @clear="clearUpchain">
                <el-option
                        :key="upchain.value"
                        :label="upchain.name"
                        :value="upchain.value"
                        v-for="upchain in [{name:'是',value:1},{name:'否',value:0}]"> 
                </el-option>
      </el-select>

      <el-button @click="search" icon="el-icon-search" type="primary">搜索</el-button>

        <el-table
      :data="slice"
      style="width: 100%"
      :header-cell-style="headStyle"  :cell-style="rowStyle"
      :row-class-name="tableRowClassName">
      <el-table-column
        prop="id"
        label="编号"
        >
      </el-table-column>
      <el-table-column
        prop="nftId"
        label="商品NFTID"
        width="180">
      </el-table-column>
      <el-table-column
        prop="content"
        label="内容">
      </el-table-column>
      <el-table-column
        prop="agree"
        label="同意数" sortable>
      </el-table-column>
      <el-table-column
        prop="deny"
        label="否定数" sortable>
      </el-table-column>
      <el-table-column
        prop="upchain"
        label="是否上链">

        <template slot-scope="scope">
                    <el-tag :type="scope.row.upchain===1 ? 'success':''"
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.upchain===1 ? '已上链':'未上链'}}</span>
                    </el-tag>
                </template>
      </el-table-column>
      <el-table-column
        prop="time"
        label="时间">
      </el-table-column>
    </el-table>

        <!--分页组件-->
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

import {getComments} from "../../../api/comment"

export default {
    name: 'comment',
    methods:{
      clearNFTID(){
        this.searchInfo.nftId = null;
      },
      clearUpchain(){
        this.searchInfo.upChain = null;
      },
      rowStyle(){
        return "text-align:center"
      },
      headStyle(){
        return "text-align:center"
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
      change(page) {
            console.log(this.pageInfo)
            this.pageInfo.pageNo = page;
            this.slice = this.tableData.slice((page - 1) * this.pageInfo.pageSize, page * this.pageInfo.pageSize);
      },
      search(){
        console.log(this.searchInfo)
        getComments(this.searchInfo).then(rs => {
            this.tableData= rs;
            this.pageInfo.total = rs.length;
            this.pageInfo.pageNo = 1;
            this.slice = rs.slice(0, this.pageInfo.pageSize);
        })
      }
    },
    data(){
        return{
            searchInfo:{
             nftId:null,
             upChain:null
            },
            tableData:null,
            pageInfo: {
                pageSize: 5,
                pageNo: 1,
                total: 0,
            },
            slice: null,
        }
    },
    mounted() {
        getComments(this.searchInfo).then(rs => {
            this.tableData= rs;
            this.pageInfo.total = rs.length;
            this.pageInfo.pageNo = 1;
            this.slice = rs.slice(0, this.pageInfo.pageSize);
            console.log(this.slice)
        })
    },
}
</script>
<style scoped>


</style>