<template>
    <div class="heightAll widthAll"> <!--这样可以设置多个class-->
<!-- 其实两个auction页面可以复用的，我都不知道我在干嘛。。。。。摆烂摆烂 -->
    <el-row>
      <el-col >
          <el-button type="primary" @click="dialogVisible = true">发起竞拍</el-button>
      </el-col>
    </el-row>
     

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
            发起竞拍者:<br>{{auction.auctionInfo. beneficiary}}
        </div>
        <div  class="text item">
            {{'最高价￥:' + auction.auctionInfo.highestBid}}
        </div>
         <div  class="text item">
            最高出价者:<br>{{auction.auctionInfo.highestBidder}}
        </div>
        
    </el-card>

    <div style="float:left;margin-top:30px">
         <el-pagination :current-page="pageInfo.pageNo"
                       :page-size="pageInfo.pageSize"
                       :total="pageInfo.total"
                       @current-change="change"
                       background
                       class="my-changePage"
                       layout="prev, pager, next">
        </el-pagination>
    </div>

        <!-- 填写拍卖信息 -->
    <el-dialog
        title="拍卖信息"
        :visible.sync="dialogVisible"
        width="55%"
        :before-close="handleClose">
        <span>基本信息</span>

        <el-divider></el-divider>
        <el-form :model="auctionInfo" label-position="left" label-width="auto" ref="auctionInfo"
                 style="margin: 10px;">
           <el-form-item label="商品名称" prop="name">
                <el-input placeholder="请输入商品名称" size="medium"
                          style="width: 240px" v-model="auctionInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="分类" prop="category" style="">
                <el-select placeholder="请选择商品分类" style="width: 240px"
                           v-model="auctionInfo.category">
                    <el-option
                            :key="category.id"
                            :label="category.name"
                            :value="category.id"
                            v-for="category in categories">
                    </el-option>
                </el-select>
            </el-form-item> 

            <el-form-item label="详细位置" prop="detailLoc" >
                <el-input size="medium" style="width: 240px" :disabled="true" 
                          v-model="auctionInfo.detailLoc"></el-input>
            </el-form-item>

            <el-divider></el-divider>
                   
            <el-form-item label="持续时间/s" prop="time">
                <el-input size="medium" style="width: 240px" 
                          v-model="auctionInfo.time"></el-input>
          </el-form-item>

         <el-divider></el-divider>

            <el-form-item style="text-align:center">
                <div style="">
                         <el-button style="width:115px" @click="submitForm('auctionInfo')" type="primary">发起竞拍 
                        </el-button>
                        <el-button  type="warning" style="width:115px" @click="resetForm('auctionInfo')">重置</el-button>
                </div>
            </el-form-item>
        </el-form>

        <el-row>
              <el-col :span="位置信息"></el-col>
              <span>鼠标右击选择位置信息</span>
              <i class="el-icon-location-information" style="color:red"></i>
          
        </el-row>
           
            <el-divider></el-divider>

        <!-- 百度地图 -->
            <baidu-map class="map" :center="北京" :zoom="zoom"  @ready="handler" :scroll-wheel-zoom="true" @rightclick="selectPoint">      
                 <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT" :offset="{width:10,height:10}"></bm-city-list>
                 <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
                 <bm-map-type :map-types="['BMAP_NORMAL_MAP', 'BMAP_HYBRID_MAP']" anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-map-type>
                 <bm-local-search :keyword="keyword" @searchcomplete="search" :auto-viewport="true"></bm-local-search>
                 <bm-navigation anchor="BMAP_ANCHOR_BOTTOM_LEFT"></bm-navigation>
                 <bm-marker :position="point" :dragging="true" animation="BMAP_ANIMATION_BOUNCE"></bm-marker>
                 <bm-local-search :keyword="keyword" @searchcomplete="search" :auto-viewport="true"></bm-local-search>
            </baidu-map>

        </el-dialog>


<!-- 拍卖详情 -->
        <el-dialog
            title="拍卖信息"
            :visible.sync="dialogVisible1"
            width="60%"
            :before-close="handleClose">
           
            <el-form :model="auctionSingle" :label-position="right" label-width="100px">
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
   
            <el-row>
              <el-col>竞价记录</el-col>
            </el-row>
            <el-divider></el-divider>

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
                <el-button type="danger" @click="endAuction()">结束拍卖</el-button>
            </span>
        </el-dialog>

    </div>

</template>

<script>
import {queryCategories} from "../../../api/category";
import {addAuction,queryAllAuctionInfo,endAuctions} from "../../../api/auction"
import {queryTotal} from "../../../api/good"
import constants from "../../../store/constants";
import Vue from 'vue'
import BaiduMap from 'vue-baidu-map';
Vue.use(BaiduMap, {
          /* Visit http://lbsyun.baidu.com/apiconsole/key for details about app key. */
          ak: 'BO1gWgR8LDt1mmYsaTrvhWFszkBxMmYf'
    })
export default {
    name:"auction",
    methods:{
        endAuction(){ 
            //判断是否已经结束
            if(this.auctionSingle.status== true){
                const h = this.$createElement;
                this.$notify({
                title: '拍卖消息',
                message: h('i', { style: 'color: teal'}, '拍卖已结束!')
                });
               
            }else{
            //判断是否已经到拍卖结束时间  wc 后面的人给我把这个提示消息封装一下到common里面 我复制粘贴用的舒服懒得搞了
            console.log(this.currSingleAuctionEnd)
            console.log(Date.now())
            if(this.currSingleAuctionEnd*1000>Date.now()){
                //时间还未到
                const h = this.$createElement;
                this.$notify({
                title: '拍卖消息',
                message: h('i', { style: 'color: teal'}, '拍卖还未结束,请等待拍卖结束时间')
                });
            }else{
                endAuctions(this.auctionSingle.auctionAddress).then(rs=>{
                    if(rs.code==200){
                        const h = this.$createElement;
                        this.$notify({
                        title: '拍卖消息',
                        message: h('i', { style: 'color: teal'}, '结束拍卖成功')
                        });
                    }else{
                        const h = this.$createElement;
                        this.$notify({
                        title: '拍卖消息',
                        message: h('i', { style: 'color: teal'}, '结束拍卖失败')
                        });
                    }
            })
            } 
         }         
                    this.dialogVisible1 = false;
        },
        //点击查看详情
        querySingle(auction){
            this.auctionSingle = auction;
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
        //提交发布拍卖表单
        submitForm(formName) {
            const _this = this;
            addAuction(_this.auctionInfo).then(rs=>{
                if(rs.code==200){
                //发布成功
                    const h = this.$createElement;
                    this.$notify({
                    title: '拍卖消息',
                    message: h('i', { style: 'color: teal'}, '发布拍卖成功')
                    });
                }else{
                //发布失败
                    const h = this.$createElement;
                    this.$notify({
                    title: '拍卖消息',
                    message: h('i', { style: 'color: teal'}, '发布拍卖失败')
                    });
                }
                //关窗口
                this.dialogVisible = false;
            })
        },
        //切换页
        change(page) {        
            this.pageInfo.pageNo = page;
            console.log('当前页:'+this.pageInfo.pageNo)
            //查询
            queryAllAuctionInfo(this.pageInfo.pageNo,this.pageInfo.pageSize).then(rs=>{
                this.auctionView = rs
            })      
        },
        //重置表单
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
        //坐标选点 用于平台选择租赁权的地址 省市区
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
            _this.auctionInfo.detailLoc = _this.province + _this.city + _this.district
            }
        })
        },
},
    data() {
        return {
            currSingleAuctionStatus:null,
            currSingleAuctionEnd:null,
            currSingleAuctionEndToDate:null,
            auctionSingle:{
              //链上的拍卖信息
              auctionInfo:{
                auctionEnd:null,
                beneficiary:null,
                ended:null,
                highestBid:null,
                highestBidder:null     
            },
            category:null,
            detailLoc:null,
            name:null,
            auctionAddress:null,
            //竞价记录 这里或许其实应该大概也许Maybe应该合并到auctionInfo 懒人一个不想改了
            record:{
                bidder:null,
                price:null
            },
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