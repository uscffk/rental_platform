<template>
    <div class="heightAll widthAll"> <!--这样可以设置多个class-->

    <!-- 操作 -->
        <div class="widthAll" style="margin-bottom: 15px; text-align: left;">

            <el-row :gutter="10">

              <el-col :span="6">
                  <el-input v-model="searchInfo.nftTokenId" placeholder="NFT ID"  clearable="true" @clear="clearNFTSearch"></el-input>
              </el-col>

              <el-col :span="6">
                  <el-select clearable placeholder="商品状态" style="margin-right: 0px;" v-model="searchInfo.status" @clear="clearSelect">
                    <el-option
                            :key="status.value"
                            :label="status.name"
                            :value="status.value"
                            v-for="status in [{name:'空闲中',value:0},{name:'维护中',value:1},{name:'使用中',value:2},{name:'已售出',value:3}]"> 
                    </el-option>
                  </el-select>
              </el-col>

              <el-col :span="2">
                  <el-button @click="search" icon="el-icon-search" type="primary" style="">搜索</el-button>
              </el-col>

              <el-col :span="2">
                  <el-button @click="lookAll" icon="el-icon-right" type="primary" style="margin-left:0px">查看所有分布</el-button>
              </el-col>
                

            </el-row>

            <el-table

                :data="slice"
                style="width: 100%;margin-top:20px"
                :row-class-name="tableRowClassName"
                :header-cell-style="headStyle"  :cell-style="rowStyle">
                <el-table-column
                prop="nftTokenId"
                label="NFT ID"
                width="180"
                sortable>
                </el-table-column>
                <el-table-column label="位置信息">
                    <el-table-column
                    prop="longitude"
                    label="经度"
                    width="180">
                    </el-table-column>
                    <el-table-column
                    prop="latitude"
                    label="纬度">
                    </el-table-column>

                    <el-table-column
                    prop="detailLoc"
                    label="详细位置">
                    </el-table-column>
                </el-table-column>
                <el-table-column width="100"
                prop="goodsId"
                label="商品ID"
                sortable>
                </el-table-column>

                <el-table-column
                prop="status"
                label="商品状态" width="130">
                    <template slot-scope="scope">
                        <el-tag :type="scope.row.status===0?'success':scope.row.status===1?'warning':'danger'"
                        style="text-align: center;">
                        <span style="float: left;">{{scope.row.status===0?'空闲中':scope.row.status===1?'维护中':scope.row.status===2?'使用中':'已售出'}}</span>

                        </el-tag>
                    </template>
                </el-table-column>


                <el-table-column 
                width="270"
                label="操作">
                    <template slot-scope="scope">
                          <el-button @click="located(scope.row)" size="mini" type="primary">定位</el-button>
                            
                          <el-button @click="showQr(scope.row)" size="mini" type="primary">二维码</el-button>

                          <el-button @click="fix(scope.row)" size="mini" type="danger">{{scope.row.status==1?'解除维护':'维护'}}</el-button>
                    </template>
                   
                </el-table-column>

            </el-table>
        </div>

        <!--分页组件-->
        <el-pagination :current-page="pageInfo.pageNo"
                       :page-size="pageInfo.pageSize"
                       :total="pageInfo.total"
                       @current-change="change"
                       background
                       class="my-changePage"
                       layout="prev, pager, next">
        </el-pagination>

        <el-dialog :visible.sync="dialogFormVisible" width="20%" style="text-align:center">
            <img :src="src" alt="二维码" height="200" width="200" style="vertical-align:middle">
        </el-dialog>


          

        <el-dialog
            title="商品分布"
            :visible.sync="dialogVisible"
            width="70%"
            > 

        <!-- 百度地图 -->
        <baidu-map class="map" :center="center" :zoom="zoom" @ready="handler" :scroll-wheel-zoom="true">   
                 <bm-point-collection :points="points" shape="BMAP_POINT_SHAPE_STAR" color="red" size="BMAP_POINT_SIZE_BIG" @click="clickHandler"></bm-point-collection>   
                 <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT" :offset="{width:10,height:10}"></bm-city-list>
                 <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
                 <bm-map-type :map-types="['BMAP_NORMAL_MAP', 'BMAP_HYBRID_MAP']" anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-map-type>
                 <bm-local-search :keyword="keyword" @searchcomplete="search" :auto-viewport="true"></bm-local-search>
                 <bm-navigation anchor="BMAP_ANCHOR_BOTTOM_LEFT"></bm-navigation>
                 <bm-marker :position="point" :dragging="true" animation="BMAP_ANIMATION_BOUNCE"></bm-marker>
                 <bm-local-search :keyword="keyword" @searchcomplete="search" :auto-viewport="true"></bm-local-search>
        </baidu-map>

           
        </el-dialog>



      

    </div>
</template>
<script>
import Vue from 'vue'
import BaiduMap from 'vue-baidu-map';
import {queryGoods,queryGoodByCondition,updateNFT} from "../../../api/good";
import {queryCategories} from "../../../api/category";
import {getUser} from "../../../api/session"
Vue.use(BaiduMap, {
          /* Visit http://lbsyun.baidu.com/apiconsole/key for details about app key. */
          ak: 'BO1gWgR8LDt1mmYsaTrvhWFszkBxMmYf'
    })
export default {
    name:'goodsDistribute',
    data(){
        return{
            searchInfo: {
                status: null,
                goodsId:null,
                nftTokenId:null,
            },
            pageInfo: {
                pageSize:5 ,
                pageNo: 1,
                total: 5,
            },
            tableData:[],
            backtableData:[],
            slice:null,
            categories: null,
            categoryMap: new Map(),
            dialogVisible:false,
            dialogFormVisible:false,
            src: null,
            points: [],
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
            goods:[],


        }
    },
    methods:{ 
        clearNFTSearch(){
            this.searchInfo.nftTokenId = null;
        },
        clearSelect(){
            this.searchInfo.status = null;
        },
        //返回
        back(){
            this.query();
        },
        rowStyle(){
                return "text-align:center"
        },
        headStyle(){
                return "text-align:center"
        },
        //搜索
        search(){
            //this.query();
            //从tableData里面去找 这是糟糕的设计 but 还是那句话 真的太懒。。。
            //别学我这种风格的查询 不太好 我不想修改代码了 直接摆烂
            let rs = [];
            if(this.searchInfo.nftTokenId==null&&this.searchInfo.status==null){

                 this.tableData = this.backtableData;
                 this.pageInfo.total = this.backtableData.length;
                 this.pageInfo.pageNo = 1;
                 this.slice = this.backtableData.slice(0, this.pageInfo.pageSize);
                return;
            }else if(this.searchInfo.nftTokenId==null){
                    this.tableData = this.backtableData;
                    for(var i = 0;i<this.tableData.length;i++){
                        var e = this.tableData[i];    
                            if(e.status==this.searchInfo.status)
                                 rs.push(e)
                    }
                    // status不为空               
                    console.log(rs)
            }else if(this.searchInfo.status==null){          
                    this.tableData = this.backtableData;
                    for(var i = 0;i<this.tableData.length;i++){
                            var e = this.tableData[i];    
                                if(e.nftTokenId==this.searchInfo.nftTokenId)
                                    rs.push(e)
                    }  
                    //nftTokenId不为空
                    console.log(rs)
            }else{
                //都不为空
                 this.tableData = this.backtableData;
                    for(var i = 0;i<this.tableData.length;i++){
                            var e = this.tableData[i];    
                                if(e.nftTokenId==this.searchInfo.nftTokenId&&e.status==this.searchInfo.status)
                                    rs.push(e)
                    }  
            }
            this.tableData = rs;
            this.pageInfo.pageNo = 1;
            this.pageInfo.total = rs.length;
            this.slice = this.tableData.slice(0, this.pageInfo.pageSize);
            rs = [];
        },
        located(row){
            this.dialogVisible = true;
            this.center.lng = row.longitude;
            this.center.lat = row.latitude;
            //先清零
            this.points = [];
            this.points.push({
                lng:row.longitude,
                lat:row.latitude
            })
        },
        //这里是伪分页
        change(page) {
            //动态切割
            this.pageInfo.pageNo = page;
            this.slice = this.tableData.slice((page - 1) * this.pageInfo.pageSize, page * this.pageInfo.pageSize);
        },
        //这里其实应该发nftCommodity请求的 不想改了就这样 摆烂了属于是
        query() {
            console.log(getUser().id)
            if(typeof(getUser().id)!='undefined'){
                queryGoods({}).then((rs) => {
                this.goods = rs;
                this.pageInfo.pageNo = 1;
                var count = 0;
                for(var i=0;i<this.goods.length;i++){
                    var comList = this.goods[i].nftCommodityList;
                    for(let j=0;j<comList.length;j++){
                        this.tableData.push(comList[j])
                        count++;
                    }
                }
                this.pageInfo.total = count;
                this.slice = this.tableData.slice(0, this.pageInfo.pageSize);
                this.backtableData = this.tableData;
            });
            }else{
                queryGoods({}).then((rs) => {
                this.goods = rs;
                this.pageInfo.pageNo = 1;
                var count = 0;
                for(var i=0;i<this.goods.length;i++){
                    var comList = this.goods[i].nftCommodityList;
                    for(let j=0;j<comList.length;j++){  
                        this.tableData.push(comList[j])
                        count++;
                    }
                }
                this.pageInfo.total = count;
                this.slice = this.tableData.slice(0, this.pageInfo.pageSize);
                this.backtableData = this.tableData;
            });
            }
           
        },
        //维护
        fix(row){
            //先判断是否处于空闲状态
            if(row.status==0){
                    console.log(row)
                    row.status = row.status==1?0:row.status==0?1:1;
                    updateNFT(row).then((rs)=>{
                        if(rs.code==200){
                            this.$notify({
                                title: '成功',
                                message: '状态更新成功',
                                type: 'success'
                                });
                        }else{
                            this.$notify.error({
                            title: '错误',
                            message: '操作失败'
                            });
                        }
                    })
            }else if(row.status==1){
                row.status = row.status==1?0:row.status==0?1:1;
                    updateNFT(row).then((rs)=>{
                        if(rs.code==200){
                            this.$notify({
                                title: '成功',
                                message: '状态更新成功',
                                type: 'success'
                                });
                        }else{
                            this.$notify.error({
                            title: '错误',
                            message: '操作失败'
                            });
                        }
                    })
            }else{
                const h = this.$createElement;
                this.$notify({
                title: '提示',
                message: h('i', { style: 'color: teal'}, '只有处于空闲状态下的商品才可进行维护操作')
                });
            }
       
        },
        //打开二维码
        showQr(row) {
            this.dialogFormVisible = true;
            axios({
                url: '/api/commodityService/qrCode',
                method: 'get',
                params: {content: row.nftTokenId},
                responseType: 'blob',
            }).then(rs => {
                this.src = window.URL.createObjectURL(rs.data);
            })
        },
        //加点颜色或许会好看点?
        tableRowClassName({row, rowIndex}) {
                    if (rowIndex === 1) {
                    return 'warning-row';
                    } else if (rowIndex === 3) {
                    return 'success-row';
                    }
                    return '';
        },
        
        clickHandler (e) {
            alert(`单击点的坐标为：${e.point.lng}, ${e.point.lat}`);
        },
           
        //查看所有分布
        lookAll(){
            //默认定位到北京  要查看所有分布请点地图左上角查看全国
            this.center = {
                lng: 116.3,
                lat: 39.9
            }
            //定位到商品的位置
            //查询
            //这个页面复用 增加判断
            if(typeof(getUser().id)!='undefined'){
                //貌似这里是不用发请求的 算了 继续摆
                queryGoods(this.searchInfo.manufacturer).then((rs) => {
                //遍历goods
                for(var i=0; i< this.goods.length; i++){
                    var comList = this.goods[i].nftCommodityList;
                    //遍历list
                    for(let j=0;j<comList.length;j++){
                        let position={
                        lng:null,
                        lat:null
                    }
                        position.lat =  comList[j].latitude;   
                        position.lng =  comList[j].longitude
                        this.points.push(position)
                    }
                }
            })
            }else{
                queryGoods({}).then((rs) => {
                //遍历goods
                for(var i=0; i< this.goods.length; i++){
                    var comList = this.goods[i].nftCommodityList;
                    //遍历list
                    for(let j=0;j<comList.length;j++){
                        let position={
                        lng:null,
                        lat:null
                    }
                    position.lat =  comList[j].latitude;
                    position.lng =  comList[j].longitude
                    this.points.push(position)
                    }
                }
            });
            }
            //打开地图弹框 
            this.dialogVisible = true;
          },
        //模拟假数据
        addPoints () {
            const points = [];
            for (var i = 0; i < 1000; i++) {
                const position = {lng: Math.random() * 40 + 85, lat: Math.random() * 30 + 21}
                points.push(position)
            }
            this.points = points
        },
        handler ({BMap, map}) {
            var geolocation = new BMap.Geolocation()
            // 获取逆解析方法实例
            this.myGeo = new BMap.Geocoder()
            this.zoom = 15
            // 获取自动定位获取的坐标信息 这鬼东西好像没有用啊
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
    },
    mounted(){
        // 页面初始化工作
        queryCategories().then(rs => {
            const _this = this;
            //查数据
            this.query();
            _this.categories = rs
            _this.categoryMap = new Map();
            _this.categories.forEach(function (item) {
                _this.categoryMap.set(item.id, item.name);
                _this.categoryMap = new Map(_this.categoryMap);
            })
        })
    }
}
</script>
<style scoped>
.map{
    width: 100%;
    height: 500px;
}

.el-table .warning-row {
    background: rgb(226, 161, 40);
  }

  .el-table .success-row {
    background: #a1db82;
}
</style>