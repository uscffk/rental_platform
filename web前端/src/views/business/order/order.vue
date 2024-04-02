<template>
    <div class="heightAll widthAll"> <!--这样可以设置多个class-->


         <el-select clearable placeholder="订单状态" style="margin-right: 15px;" v-model="searchInfo.status" @clear="clearStatus">
                <el-option
                        :key="status.value"
                        :label="status.name"
                        :value="status.value"
                        v-for="status in [{name:'进行中',value:0},{name:'已完成',value:1},{name:'黑名单',value:3},{name:'已购买',value:4}]"> 
                </el-option>
        </el-select>


         <el-select clearable placeholder="订单类型" style="margin-right: 15px;" v-model="searchInfo.orderType" @clear="clearType">
                <el-option
                        :key="type.value"
                        :label="type.name"
                        :value="type.value"
                        v-for="type in [{name:'共享租赁',value:0},{name:'以租代售',value:1},{name:'先租后买',value:2}]"> 
                </el-option>
        </el-select>

        

        <el-select clearable placeholder="评价" style="margin-right: 15px;" v-model="searchInfo.comment" @clear="clearComment">
                <el-option
                        :key="comment.value"
                        :label="comment.name"
                        :value="comment.value"
                        v-for="comment in [{name:'已评价',value:1},{name:'待评价',value:0}]"> 
                </el-option>
        </el-select>

          <el-select clearable placeholder="计费方式" style="margin-right: 15px;" v-model="searchInfo.billMethod" @clear="clearbillMethod">
                <el-option
                        :key="billMethod.value"
                        :label="billMethod.name"
                        :value="billMethod.value"
                        v-for="billMethod in [{name:'按小时',value:0},{name:'按天',value:1},{name:'按月',value:2}]"> 
                </el-option>
        </el-select>



        <el-button @click="search" icon="el-icon-search" type="primary">搜索</el-button>
        
        <el-table :data="slice" class="el-table"   :header-cell-style="headStyle"  :cell-style="rowStyle"
                  header-align="center"  style="width: 100%; text-align: center">
                

            <el-table-column label="编号" prop="orderId" width="150" fixed>
            </el-table-column>
        
            <el-table-column label="订单状态" prop="status" width="180">
                 <template slot-scope="scope">
                    <el-tag :type="scope.row.status===0 ? '':scope.row.status===1 ?'success':'danger'"
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.status===0 ? '进行中':scope.row.status===1?'已完成':'已购买'}}</span>
                    </el-tag>
                </template>
            </el-table-column>

            <el-table-column label="下单时间" prop="creatTime" width="180" sortable>
            </el-table-column>

            <el-table-column label="商家ID" prop="manufacturerId" width="180">
            </el-table-column>

            <el-table-column label="总金额"  width="180" sortable>
                <template slot-scope="scope">
                 <span>￥{{scope.row.sumMoney}}</span>
                </template>
            </el-table-column>
            <el-table-column label="已付金额" width="180" sortable>
                <template slot-scope="scope">
                 <span>￥{{scope.row.actualPay}}</span>
                </template>
            </el-table-column>
            <el-table-column label="当前期数" prop="currTime" width="180">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.status===0 ? '':scope.row.status===1 ?'success':'danger'"
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.orderType===0||scope.row.orderType===2? '无':scope.row.currTime}}</span>
                    </el-tag>
                </template>
            </el-table-column>

             <el-table-column label="总期数" prop="totalTime" width="180">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.status===0 ? '':scope.row.status===1 ?'success':'danger'"
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.orderType===0||scope.row.orderType===2? '无':scope.row.totalTime}}</span>
                    </el-tag>
                </template>
            </el-table-column>
           
             <el-table-column label="押金" width="180">
                <template slot-scope="scope">
                 <span>{{scope.row.deposit==0?'免押':scope.row.deposit}}</span>
                </template>
            </el-table-column>
             <el-table-column label="订单类型" prop="orderType" width="180">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.orderType===0 ? '':scope.row.orderType===1 ?'success':'danger'"
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.orderType===0 ? '共享租赁':scope.row.orderType===1?'以租代售':'先租后买'}}</span>
                    </el-tag>
                </template>
            </el-table-column>

            <el-table-column label="下次支付时间" width="200" sortable>
                <template slot-scope="scope">
                    <span>{{scope.row.orderType===0||scope.row.orderType===2?'无':scope.row.nextPay==='2100-12-22 22:22:22.0'?'已付清':scope.row.nextPay}}</span>
                </template>
            </el-table-column>

            <el-table-column label="计费方式" prop="billMethod" width="200">
                 <template slot-scope="scope">
                    <el-tag :type="scope.row.billMethod===0 ? '':scope.row.orderType===1 ?'success':'danger'"
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.billMethod===0 ? '按小时':scope.row.billMethod===1?'按天':'按月'}}</span>
                    </el-tag>
                </template>
            </el-table-column>

            <el-table-column label="完成时间" width="180" sortable>
                <template slot-scope="scope">
                 <span>{{scope.row.finishTime===null?'尚未完成':scope.row.finishTime}}</span>
                </template>
            </el-table-column>

            <!-- 是否已评价 -->
            <el-table-column label="评价情况" width="180">
                <template slot-scope="scope">
                 <span>{{scope.row.comment===0?'待评价':'已评价'}}</span>
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
                       class="my-changePage"
                       layout="prev, pager, next">
        </el-pagination>
    </div>
</template>

<script>
import {getOrders} from "../../../api/order";
import {getUser} from '../../../api/session'
export default {
    name: 'orderList',
    methods: {

        search(){
            getOrders(this.searchInfo).then(rs => {
                 this.orders = rs;
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
        clearbillMethod(){
            this.searchInfo.billMethod = null;
        },
        clearStatus(){
            this.searchInfo.status =  null;
        },
        clearType(){
            this.searchInfo.orderType = null;
        },
        clearComment(){
            this.searchInfo.comment = null;
        },
        change(page) {
            this.pageInfo.pageNo = page;
            this.slice = this.orders.slice((page - 1) * this.pageInfo.pageSize, page * this.pageInfo.pageSize);
        },
     
    
    },
    data() {
        return {
            searchInfo:{
                status:null,
                orderType:null,
                comment:null,
                billMethod:null
            },
            pageInfo: {
                pageSize: 8,
                pageNo: 1,
                total: 0,
            },
            orders: null,
            slice: null,
        }
    },
    mounted() {
        getOrders({}).then(rs => {
            this.orders = rs;
            this.pageInfo.total = rs.length;
            this.pageInfo.pageNo = 1;
            this.slice = rs.slice(0, this.pageInfo.pageSize);
        })
    },
    computed: {}
}

</script>

<style scoped>
.el-table-column{
        width:12%
}

</style>