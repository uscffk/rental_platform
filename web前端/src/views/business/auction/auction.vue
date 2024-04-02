<template>
    <div class="heightAll widthAll"> <!--这样可以设置多个class-->

    <el-card class="box-card" v-for="auction in auctionView" :key="auction">
        <div slot="header" class="clearfix">
            <span><i class="el-icon-s-opportunity"></i>{{auction.status==false?'进行中':'已完成'}}</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="querySingle(auction)" >查看详情</el-button>
        </div>
        <div  class="text item">
            {{'租赁权商品名称:'+auction.name}}
        </div>
        <div  class="text item">
            {{'租赁权商品分类:'+auction.category}}
        </div>
        <div  class="text item">
            {{'租赁权NFT ID:'+auction.rentNftToken}}
        </div>
         <div  class="text item">
            {{'租赁区域:'+auction.detailLoc}}
        </div>
        <div  class="text item">
            {{'竞拍结束时间:' + timestampToTime(auction.auctionInfo.auctionEnd)}}
        </div>
        <div  class="text item">
            {{'发起竞拍者:' + auction.auctionInfo.beneficiary}}
        </div>
        <div  class="text item">
            {{'最高价:' + auction.auctionInfo.highestBid}}
        </div>
         <div  class="text item">
            {{'最高出价者:' + auction.auctionInfo.highestBidder}}
        </div>
        
    </el-card>

<!-- 拍卖详情 -->
        <el-dialog
            title="拍卖信息"
            :visible.sync="dialogVisible1"
            width="60%"
            :before-close="handleClose">
           
            <el-form :model="auctionSingle" :label-position="left" label-width="100px">
               <el-form-item label="商品名称">
                <el-input size="medium" style="width: 240px" :disabled="true" v-model="auctionSingle.name"></el-input>
            </el-form-item>
            <el-form-item label="商品分类">
                <el-input size="medium" style="width: 240px" :disabled="true" v-model="auctionSingle.category"></el-input>
            </el-form-item>
             <el-form-item label="租赁权NFT ID">
                <el-input size="medium" style="width: 240px" :disabled="true" v-model="auctionSingle.rentNftToken"></el-input>
            </el-form-item>
             <el-form-item label="租赁区域">
                <el-input size="medium" style="width: 240px" :disabled="true" v-model="auctionSingle.detailLoc"></el-input>
            </el-form-item>
             <el-form-item label="结束时间">
                <el-input size="medium" style="width: 240px" :disabled="true" v-model="currSingleAuctionEndToDate"></el-input>
            </el-form-item>
             <el-form-item label="发起者">
                <el-input size="medium" style="width: 240px" :disabled="true" v-model="auctionSingle.auctionInfo.beneficiary"></el-input>
            </el-form-item>
             <el-form-item label="最高出价">
                <el-input size="medium" style="width: 240px" :disabled="true" v-model="auctionSingle.auctionInfo.highestBid"></el-input>
            </el-form-item>
             <el-form-item label="最高出价人">
                <el-input size="medium" style="width: 240px" :disabled="true" v-model="auctionSingle.auctionInfo.highestBidder"></el-input>
            </el-form-item>
             <el-form-item label="当前状态">
                <el-input size="medium" style="width: 240px" :disabled="true" v-model="currSingleAuctionStatus"></el-input>
            </el-form-item> 
            <el-form-item label="合约地址">
                <el-input size="medium" style="width: 240px" :disabled="true" v-model="auctionSingle.auctionAddress"></el-input>
            </el-form-item> 

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
            </el-form>
           
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible1 = false">取 消</el-button>
                <el-button type="primary" @click="dialogVisible1 = false">确 定</el-button>
                <el-button type="danger" @click="paticipateBid()">参与竞拍</el-button>
            </span>
        </el-dialog>

        <el-dialog
            title="出价"
            :visible.sync="dialogVisible"
            width="30%"
            :before-close="handleClose">
            <span>金额:</span>
            <el-input-number style="width:100%" label="出价" v-model="num" controls-position="right" :min="1" ></el-input-number>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="bid()">提交</el-button>
            </span>
        </el-dialog>

        <div style="float:left;margin-top:30px">
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
    </div>

</template>

<script>
import {queryCategories} from "../../../api/category";
import {addAuction,queryAllAuctionInfo,endAuctions,bids} from "../../../api/auction"
import constants from "../../../store/constants";
import Vue from 'vue'
import {queryTotal} from "../../../api/good"
import BaiduMap from 'vue-baidu-map';
import { getUser } from "../../../api/session";
Vue.use(BaiduMap, {
          /* Visit http://lbsyun.baidu.com/apiconsole/key for details about app key. */
          ak: 'BO1gWgR8LDt1mmYsaTrvhWFszkBxMmYf'
    })
export default {
    name:"auction",
    methods:{
        paticipateBid(){
            //判断竞拍是否结束
            console.log(this.currSingleAuctionEnd)
            console.log(Date.now())
            if(this.currSingleAuctionEnd*1000<Date.now()){
                    const h = this.$createElement;
                    this.$notify({
                    title: '拍卖消息',
                    message: h('i', { style: 'color: teal'}, '拍卖已结束')
                    });
                    return;
            }else{
                
                this.dialogVisible = true;

            }
        },
        bid(){
            bids(this.num,this.auctionSingle.auctionAddress).then(rs=>{
                if(rs.code==200){
                    //竞拍成功
                    const h = this.$createElement;
                    this.$notify({
                    title: '竞拍消息',
                    message: h('i', { style: 'color: teal'}, '竞拍成功')
                    });
                }else{
                    //竞拍成功
                    const h = this.$createElement;
                    this.$notify({
                    title: '竞拍消息',
                    message: h('i', { style: 'color: teal'}, '竞拍失败,时间到了或者余额不足')
                    });
                }
                console.log(rs.data)
            })
            this.dialogVisible = true;
        },
        change(page) {        
           this.pageInfo.pageNo = page;
            console.log('当前页:'+this.pageInfo.pageNo)
            //查询
            queryAllAuctionInfo(this.pageInfo.pageNo,this.pageInfo.pageSize).then(rs=>{
                this.auctionView = rs
            })     
           
        },
        querySingle(auction){
            this.auctionSingle =auction;
            this.currSingleAuctionEnd = this.auctionSingle.auctionInfo.auctionEnd;
            this.currSingleAuctionEndToDate = this.timestampToTime(this.auctionSingle.auctionInfo.auctionEnd)
            this.dialogVisible1 = true;
            this.currSingleAuctionStatus = this.auctionSingle.status==false?'进行中':'已结束';
            //竞价记录
            var rs = [];
            var bidder = auction.record.bidder;
            console.log(bidder);
            var price = auction.record.price;
            console.log(price);
            for(var i=0;i<bidder.length;i++){
                rs.push({
                    'bidder':bidder[i],
                    'price':price[i]
                })
            }
            this.bidData = rs;    
        },
        // 提交表单
        submitForm(formName) {
            const _this = this;
            addAuction(_this.auctionInfo)
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        }, 
        afterAction(rs) {
            if (rs.code === constants.state.SUCCESS) {
                this.$message.success(rs.message);
                clearTimeout(_this.timer);
                _this.timer = setTimeout(() => {
                    this.$router.push('/');
                }, 1000);
            } else {
                this.$message.warning(rs.message);
            }
        },
        handler ({BMap, map}) {
            var geolocation = new BMap.Geolocation()
            // 获取逆解析方法实例
            this.myGeo = new BMap.Geocoder()
            this.zoom = 15
            // 获取自动定位获取的坐标信息
            geolocation.getCurrentPosition(
                function (r) {
                this.center = {
                    lng: r.point.lng,
                    lat: r.point.lat
                }
                this.point = {
                    lng: r.point.lng,
                    lat: r.point.lat
                }
                },
                { enableHighAccuracy: true }
            )
        },
        //时间戳转日期格式
        timestampToTime(timestamp) {
        console.log(timestamp)
        var date = new Date(timestamp * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
        var Y = date.getFullYear() + '-';
        var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        var  D = date.getDate() + ' ';
        var h = date.getHours() + ':';
        var m = date.getMinutes() + ':';
        var s = date.getSeconds();
        console.log(Y+M+D+h+m+s)
        return Y+M+D+h+m+s;
        },

        selectPoint ({ type, target, point, pixel, overlay }) {
        this.point = point
        const _this = this
        // 根据坐标逆解析获取地址详细描述
        this.myGeo.getLocation(point, function (result) {
            if (result) {
                console.log(result)
            _this.city = result.addressComponents.city
            _this.province = result.addressComponents.province
            _this.district = result.addressComponents.district
            _this.auctionInfo.detailLoc = result.address
            }
        })
        },

    },
    data() {
        return {
            currSingleAuctionStatus:null,
            currSingleAuctionEndToDate:null,
            currSingleAuctionEnd:null,
            auctionSingle:{
              auctionInfo:{
                auctionEnd:null,
                beneficiary:null,
                ended:null,
                highestBid:null,
                highestBidder:null     
            },
            auctionAddress:null,
            category:null,
            detailLoc:null,
            name:null,
            record:{
                bidder:null,
                price:null
            },
            // 出价
            num:0,
            rentNftToken:null,
            status:null
            },
            pageInfo: {
                pageSize:4 ,
                pageNo: 1,
                total: 10,
            },
            bidData:[
                {
                    bidder:'ffk',
                    price:10
                },
                  {
                    bidder:'ffk',
                    price:15
                },
                
            ],
            auctionView:null,
            slice:null,
            dialogVisible:false,
            categories: null,
            categoryMap: new Map(),
            dialogVisible1:false,
            auctionInfo:{
                name:null,
                category:null,
                detailLoc:null,
                time:null,     
            },
          sizeForm: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        },
            // 地图你解析方法实例
            myGeo: null,
            city: '',
            province: '',
            district: '',
            point: { lng: 116.404, lat: 39.915 },
            center:{
                lng:0,
                lat:0
            },
            labelCol: {
                xs: { span: 24 },
                sm: { span: 5 }
            },
            wrapperCol: {
                xs: { span: 24 },
                sm: { span: 12 }
            },
            zoom:3,
        }
    },
    mounted(){

        //请求总条数
        queryTotal().then(rs=>{
            console.log(rs)
            this.pageInfo.total = rs.data
        })

        //请求拍卖数据 
        queryAllAuctionInfo(this.pageInfo.pageNo,this.pageInfo.pageSize).then(rs => {
            this.auctionView = rs
            console.log(this.auctionView)
        })
        queryCategories().then(rs => {
            this.categories = rs
            this.categoryMap = new Map();
            this.categories.forEach(function (item) {
                this.categoryMap.set(item.id, item.name);
                console.log(this.categoryMap.get(item.id))
                this.categoryMap = new Map(this.categoryMap);
                console.log(this.categoryMap)
            })
        })
    }
}
</script>

<style scoped>
.el-form-item{
    display:inline-block;
    margin-left:50px
}

  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 23%;
    height: 400px;
    float: left;
    margin-left: 20px;
    margin-top: 20px;
  }

.map {
  width: 100%;
  height: 400px;
}

.BMap_cpyCtrl{
display:none;
}

</style>