<template>

    <div class="heightAll widthAll">
        <el-table
        border=""
      :data="slice"
      :header-cell-style="headStyle"  :cell-style="rowStyle"
      :row-class-name="tableRowClassName"
      style="width: 100%">
      <el-table-column
        prop="name"
        label="商品名称"
        >
      </el-table-column>

      <el-table-column
        prop="category"
        label="商品种类"
        >
      </el-table-column>

      <el-table-column
        prop="detailLoc"
        label="租赁区域">
      </el-table-column>

       <el-table-column
        prop="rentNftToken"
        label="NFTToken"
        sortable>
      </el-table-column>

      <el-table-column
        prop="auctionInfo.highestBid"
        label="出价"
        sortable>
      </el-table-column>

      <el-table-column label="操作">
            <template slot-scope="scope">
                <el-button @click="auctionRecord(scope.row)" size="mini" type="primary">查看竞价记录</el-button>
            </template>
      </el-table-column>

        <el-dialog
                title="竞拍记录"
                :visible.sync="dialogVisible"
                width="50%"
                :append-to-body="true"
                >
                    <el-table
                        :data="bidData"
                        stripe
                        style="width: 100%">
                        <el-table-column
                        prop="bidder"
                        label="竞拍者"
                        >
                        </el-table-column>
                        <el-table-column
                        prop="price"
                        label="出价"
                    
                        >
                        </el-table-column>
                        
                    </el-table>
            
                <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
                </span>
        </el-dialog>

      
    </el-table>

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

    </div>

</template>



<script>
import {queryAuctionInfoByMFID} from "../../../api/auction"
export default {
    name:'auctionList',
    methods:{
        auctionRecord(row){
                //竞价记录
                    this.dialogVisible = true;
                    var rs = [];
                    var bidder = row.record.bidder;
                    var price = row.record.price;
                    for(var i=0;i<bidder.length;i++){
                        rs.push({
                            'bidder':bidder[i],
                            'price':price[i]
                        })
                    }
                    this.bidData = rs;
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
                this.pageInfo.pageNo = page;
                this.slice = this.tableData.slice((page - 1) * this.pageInfo.pageSize, page * this.pageInfo.pageSize);
            } ,
    },
    data(){
        return{
            pageInfo: {
                pageSize: 5,
                pageNo: 1,
                total: 0,
            },
            tableData:null,
            dialogVisible: false,
            slice:null,
            bidData:[],
            record:{
                bidder:null,
                price:null
            },

        }
    },
    mounted(){
        queryAuctionInfoByMFID().then(rs => {
            this.tableData = rs;
            this.pageInfo.total = rs.length;
            this.pageInfo.pageNo = 1;
            this.slice = rs.slice(0, this.pageInfo.pageSize);
        })
    }
}
</script>
<style scoped>

</style>