<template>
    <div>

         <el-col :span="4" style="margin-right:10px">
          <el-input  clearable  @clear="clearNFTID" v-model="searchInfo.nftId" placeholder="NFT ID"></el-input>
         </el-col>

        <el-select clearable placeholder="有效性" style="margin-right: 15px;" v-model="searchInfo.effective" @clear="clearEffective">
                <el-option
                        :key="effective.value"
                        :label="effective.name"
                        :value="effective.value"
                        v-for="effective in [{name:'有效',value:true},{name:'已退还',value:false}]"> 
                </el-option>
        </el-select>

        <el-button @click="search" icon="el-icon-search" type="primary">搜索</el-button>
        <!-- <el-row>
          <el-col :span="6">押金池余额:{{depositPoolBalance}}</el-col>
        </el-row> -->
         <el-table
            :data="slice"  
            border
            :row-class-name="tableRowClassName"
            style="width: 100%;margin-top:20px"
            :header-cell-style="headStyle"  :cell-style="rowStyle">

            <el-table-column
            prop="nftId"
            label="NFT ID"
            >
            </el-table-column>

            <el-table-column
            prop="amount"
            label="金额"
            >
              <template slot-scope="scope">
                  ￥{{scope.row.amount}}
              </template>
            </el-table-column>

            <el-table-column
            prop="isEffective"
            label="是否有效">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.effective===true?'success':'danger'"
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.effective==true?'有效':'已退还'}}</span>
                    </el-tag>
                </template>
            </el-table-column>

            <el-table-column
            prop="commodityNFT"
            label="商品NFT ID">
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
import {queryAllDeposit,queryDepositPoolBalance} from '../../../api/deposit'

export default {
    data(){
        return{
            depositPoolBalance:0,
            searchInfo:{
              nftId:null,
              effective:null
            },
            tableData:null,
            searchTableData:null,
            pageInfo: {
                pageSize: 5,
                pageNo: 1,
                total: 0,
            },
            backTableData:{},
            slice: null,
        }
    },
    methods:{
        clearNFTID(){
          this.searchInfo.nftId = null;
        },
        clearEffective(){
          this.searchInfo.effective = null;
        },
        //这里就用伪分页好了 因为这里本来就很慢了 重复发请求得不偿失
        search(){
          //遍历tableData
          var rs = [];
          var nftId = this.searchInfo.nftId;
          var effective = this.searchInfo.effective;
          if(nftId!=null&&effective!=null){
            console.log('按NFTID和有效性')
            this.tableData = this.backTableData;
            for(let i = 0;i<this.tableData.length;i++){
                if(this.tableData[i].effective == effective && this.tableData[i].nftId == nftId){
                  rs.push(this.tableData[i])
                }
            }
          }else if(nftId!=null){ //effective为空
           console.log('按NFT ID')
           console.log(this.searchInfo.nftId)
           this.tableData = this.backTableData;
           console.log(this.tableData.length)
            for(let i = 0;i<this.tableData.length;i++){
                console.log(this.tableData[i].nftId)
                if(this.tableData[i].nftId == nftId){
                  rs.push(this.tableData[i])
                }
            }

          }else if(effective!=null){ //nftid为空
            console.log('按有效性')
            this.tableData = this.backTableData;
            console.log(this.searchInfo.effective)
            for(let i = 0;i<this.tableData.length;i++){
                if(this.tableData[i].effective == effective){
                  rs.push(this.tableData[i])
                }
            }
          }else{
            console.log('都为空')
            //都为空
            this.tableData = this.backTableData;
            this.pageInfo.total = this.backTableData.length;
            this.pageInfo.pageNo = 1;
            this.slice = this.backTableData.slice(0, this.pageInfo.pageSize);
            
            return;
          }

          this.tableData= rs;
          this.pageInfo.total = rs.length;
          this.pageInfo.pageNo = 1;
          this.slice = rs.slice(0, this.pageInfo.pageSize);
          rs = [];
          
        },
        change(page) {
            this.pageInfo.pageNo = page;
            this.slice = this.tableData.slice((page - 1) * this.pageInfo.pageSize, page * this.pageInfo.pageSize);
        },
        rowStyle(){
                return "text-align:center"
        },
        headStyle(){
                return "text-align:center"
        },
        tableRowClassName({row, rowIndex}) {
        if (rowIndex === 1) {
          return 'warning-row';
        } else if (rowIndex === 3) {
          return 'success-row';
        }
        return '';
      }
    },
     mounted() {
        queryDepositPoolBalance().then(rs=>{
          this.depositPoolBalance = rs;
        })
        queryAllDeposit().then(rs => {
            console.log(rs)
            this.tableData= rs;
            this.pageInfo.total = rs.length;
            this.pageInfo.pageNo = 1;
            this.slice = rs.slice(0, this.pageInfo.pageSize);
            this.backTableData = rs;
        })
    },
}
</script>

<style scoped>
  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>