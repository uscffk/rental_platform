<template>
    <div class="heightAll widthAll"> <!--这样可以设置多个class-->

    <!-- 操作 -->
        <div class="widthAll" style="margin-bottom: 15px; text-align: left;">
        
            <el-select clearable placeholder="分类" style="margin-right: 15px;" v-model="searchInfo.category" @clear="clearCategory">
                <el-option
                        :key="category.id"
                        :label="category.name"
                        :value="category.id"
                        v-for="category in categories"> 
                </el-option>
            </el-select>

            <el-select clearable placeholder="计费方式" style="margin-right: 15px;" v-model="searchInfo.billMethod" @clear="clearBillMethod">
                <el-option
                        :key="billMethods.value"
                        :label="billMethods.name"
                        :value="billMethods.value"
                        v-for="billMethods in [{name:'按月',value:2},{name:'按天',value:1},{name:'按小时',value:0}]"> <!--三种选择-->
                </el-option>
            </el-select>


            <el-button @click="search" icon="el-icon-search" type="primary">搜索</el-button>
     
        </div>

    <!-- 表格 -->
        <el-table :data="slice" class="el-table"
                  header-align="center" stripe style="width: 100%; text-align: center"
                  :header-cell-style="headStyle"  :cell-style="rowStyle">
            <el-table-column label="编号" prop="id" width="150" fixed>
            </el-table-column>
            <el-table-column label="商品名称" prop="name" width="130">
            </el-table-column>

            <el-table-column label="价格">

            <el-table-column label="租赁价格/每期" prop="rentPrice" width="180">
                    <template slot-scope="scope">
                        {{scope.row.rentPrice==0?'以租代售商品':'￥'+scope.row.rentPrice}}
                    </template>
            </el-table-column>

            <el-table-column label="出售价格" prop="sellPrice" width="180">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.sellPrice===0 ? 'danger':'' "
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.sellPrice===0 ? '不售出':'￥'+scope.row.sellPrice}}</span>
                    </el-tag>
                </template>
            </el-table-column>
            </el-table-column>

           
            <el-table-column label="分类" width="180">
                <template slot-scope="scope">
                 <span>{{categoryMap.get(scope.row.category)}}</span>
                </template>
            </el-table-column>
            <el-table-column label="商品图片" prop="picture" width="180">
                <template slot-scope="scope">
                    <img :src='getImgUrl(scope.row.picture)' style="width:90px;height:90px">
                </template>
            </el-table-column>


            <el-table-column label="商品描述" prop="discription" width="180">
            </el-table-column>
            <el-table-column label="租赁模式">
                    <el-table-column label="是否支持共享租赁" width="180">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.sharingRent===1 ? 'success':'warning' "
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.sharingRent===1 ? '支持':'不支持'}}</span>
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="是否支持先租后买" prop="rentBeforeBuy" width="180">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.rentBeforeBuy === 1 ? 'success':'warning' "
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.rentBeforeBuy===1 ? '支持':'不支持'}}</span>
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="是否支持以租代售" prop="rentForSale" width="180">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.rentForSale === 1? 'success':'warning' "
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.rentForSale===1 ? '支持':'不支持'}}</span>
                    </el-tag>
                </template>
            </el-table-column>
            </el-table-column>
            
              <el-table-column label="租赁区域" width="180">
                <template slot-scope="scope">
                    <span>{{scope.row.detailLoc}}</span>
                </template>
            </el-table-column>
            <el-table-column label="计费方式" width="180">
                <template slot-scope="scope">
                    <span v-if="scope.row.billMethod === 0">按小时</span>
                    <span v-else-if="scope.row.billMethod === 1">按天</span>
                    <span v-else>按月</span>
                </template>
            </el-table-column>
           
            <el-table-column label="最大期数" prop="timeNumber" width="180">
                <template slot-scope="scope">
                    <el-tag :type="scope.row.timeNumber === 0 ? 'success':'warning' "
                                style="text-align: center;"><span
                                style="float: left;">{{scope.row.timeNumber === 0 ? '不支持分期':
                                scope.row.timeNumber}}</span>
                    </el-tag>
                 </template>
            </el-table-column>
            <el-table-column label="押金" prop="deposit" width="180">
             <template slot-scope="scope">
                <el-tag :type="scope.row.deposit === 0 ? 'success':'warning' "
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.deposit === 0 ? '免押':'￥'+
                            scope.row.deposit}}</span>
                </el-tag>
            </template>
            </el-table-column>

            <!-- 这是商家才有的操作 -->
            <el-table-column label="操作" width="300" v-if="identity==2?true:false">
                <template slot-scope="scope">
                    <el-button @click="addStation(scope.row)" size="mini" type="primary">添加站点</el-button>
                    <el-button @click="add(scope.row)" size="mini" type="primary">上架商品</el-button>
                    <el-button @click="update(scope.row)" size="mini" type="primary">修改</el-button>
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

        <el-dialog :visible.sync="dialogFormVisible" width="20%">
            <img :src="src" alt="二维码" height="200" width="200">
        </el-dialog>


        <el-dialog
            title="上架商品"
            :visible.sync="dialogVisible"
            width="60%"
            :before-close="handleClose">
            
            <el-form :model="formData" label-width="100px" >
                
              <el-form-item label="商品ID">
                  <el-input style="width:240px" v-model="dialogGoodsInfo.id" placeholder="" :disabled="true"></el-input>
              </el-form-item>

              <el-form-item label="商品名称">
                  <el-input style="width:240px" v-model="dialogGoodsInfo.name" placeholder="" :disabled="true"></el-input>
              </el-form-item>

              <el-form-item label="商品分类">   
               <el-select placeholder="" style="width:240px" :disabled="true"
                           v-model="dialogGoodsInfo.category">
                    <el-option
                            :key="category.id"
                            :label="category.name"
                            :value="category.id"
                            v-for="category in categories">
                    </el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="租赁范围">
                  <el-input style="width:240px" v-model="dialogGoodsInfo.detailLoc" placeholder="" :disabled="true"></el-input>
              </el-form-item>


              <el-form-item label="库存">
                  <el-input style="width:240px" v-model="dialogGoodsInfo.stock" placeholder="" :disabled="true"></el-input>
              </el-form-item>


              <el-form-item label="上架数量">
                <el-input-number style="width:240px" v-model="formData.amount" placeholder="" ></el-input-number> 
              </el-form-item>


            <el-row>
              <el-col :span="位置信息"></el-col>
              <span>位置信息</span>
              <i class="el-icon-location-information" style="color:red"></i>
          
            </el-row>
           
            <el-divider></el-divider>

            <el-row>
              <el-col style="margin-left:35px">选择站点:
                   <template>
                    <el-select v-model="selectStation" placeholder="请选择投放站点" @change="selectStations">
                        <el-option
                        v-for="item in allStations"
                        :key="item.id"
                        :label="item.detailLoc"
                        :value="item.id">
                        </el-option>
                    </el-select>
            </template>
              </el-col>
            </el-row>
           

              <el-form-item label="经度" style="margin-top:20px">
                 <el-input style="width:240px" v-model="formData.longitude" placeholder="" :disabled="true"></el-input>
              </el-form-item>

              <el-form-item label="纬度">
                <el-input style="width:240px" v-model="formData.latitude" placeholder="" :disabled="true"></el-input>
              </el-form-item>


              <el-form-item label="详细位置">
                <el-input style="width:240px" v-model="formData.detailLoc" placeholder="" :disabled="true"></el-input>
              </el-form-item>

            </el-form>

        <!-- 百度地图 -->
         <baidu-map class="map" :center="center" :zoom="zoom" @ready="handler" :scroll-wheel-zoom="true" @rightclick="selectPoint">      
                 <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT" :offset="{width:10,height:10}"></bm-city-list>
                 <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
                 <bm-map-type :map-types="['BMAP_NORMAL_MAP', 'BMAP_HYBRID_MAP']" anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-map-type>
                 <bm-local-search :keyword="keyword" @searchcomplete="search" :auto-viewport="true"></bm-local-search>
                 <bm-navigation anchor="BMAP_ANCHOR_BOTTOM_LEFT"></bm-navigation>
                 <bm-marker :position="point" :dragging="true" animation="BMAP_ANIMATION_BOUNCE"></bm-marker>
                 <bm-local-search :keyword="keyword" @searchcomplete="search" :auto-viewport="true"></bm-local-search>
        </baidu-map>

            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addNFTs()">上架</el-button>
            </span>
        </el-dialog>
        
        <el-dialog
            title="站点分布(选择一个位置可添加站点 ⭐号代表已是站点)"
            :visible.sync="dialogVisible1"
            width="70%"
            > 
            <el-row>
              经度:<el-input label="经度" v-model="formData.longitude" :disabled="true" placeholder="经度"></el-input>
            </el-row>
             <el-row style="margin-top:15px">
              纬度:<el-input label="纬度" v-model="formData.latitude" :disabled="true" placeholder="纬度"></el-input>
            </el-row>
            <el-row style="margin-top:15px;margin-bottom:25px">
              详细位置:<el-input label="详细位置" v-model="formData.detailLoc" :disabled="true" placeholder="详细位置"></el-input>
            </el-row>
        <!-- 百度地图 -->
        <baidu-map class="map" :center="center" :zoom="zoom" @ready="handler" :scroll-wheel-zoom="true" @rightclick="selectPoint">   
                 <bm-point-collection :points="points" shape="BMAP_POINT_SHAPE_STAR" color="red" size="BMAP_POINT_SIZE_BIG"></bm-point-collection>   
                 <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT" :offset="{width:10,height:10}"></bm-city-list>
                 <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
                 <bm-map-type :map-types="['BMAP_NORMAL_MAP', 'BMAP_HYBRID_MAP']" anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-map-type>
                 <bm-local-search :keyword="keyword" @searchcomplete="search" :auto-viewport="true"></bm-local-search>
                 <bm-navigation anchor="BMAP_ANCHOR_BOTTOM_LEFT"></bm-navigation>
                 <bm-marker :position="point" :dragging="true" animation="BMAP_ANIMATION_BOUNCE"></bm-marker>
                 <bm-local-search :keyword="keyword" @searchcomplete="search" :auto-viewport="true"></bm-local-search>
                 <bm-boundary :name="rentLocation" :strokeWeight="2" strokeColor="blue"></bm-boundary>
        </baidu-map> 

        <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addStations()">添加</el-button>
        </span>

        </el-dialog>

    </div>
</template>

<script>
import {getBasicForm, queryGoods,queryGoodByCondition,addNFT,queryStation,addToStation,queryTotal} from "../../../api/good";
import {queryCategories} from "../../../api/category";
import {getUser,getToken} from "../../../api/session"
import Vue from 'vue'
import BaiduMap from 'vue-baidu-map';
Vue.use(BaiduMap, {
          /* Visit http://lbsyun.baidu.com/apico    ole/key for details about app key. */
          ak: 'BO1gWgR8LDt1mmYsaTrvhWFszkBxMmYf'
    })
export default {
    name: 'goodsList',
    methods: {
        clearCategory(){
            this.searchInfo.category = null;
        },
        clearBillMethod(){
            this.searchInfo.billMethod = null;
        },
        addNFTs(){
           this.formData.id = this.dialogGoodsInfo.id
            addNFT(this.formData).then(rs=>{
                if(rs.code==200){
                    this.$message({
                        message:'上架成功',
                        type:'success'
                        });
                    this.dialogVisible = false;
                }
            })
        },
        getIdentity(){
            return getToken();
        },
        selectStations(data){
            console.log(data)
            //遍历addstations
            console.log(this.allStations)
            var _this = this;
            for(let i = 0;i<this.allStations.length;i++){
                if(data == this.allStations[i].id){
                    console.log(22222)
                    //更新
                    
                    _this.formData.latitude = _this.allStations[i].latitude;
                    _this.formData.longitude = _this.allStations[i].longitude;
                    _this.formData.detailLoc = _this.allStations[i].detailLoc;

                       //根据地址解析坐标
            this.myGeo = new BMap.Geocoder()
            this.myGeo.getPoint(_this.formData.detailLoc,result=>{
                this.center.lat = result.lat;
                this.center.lng = result.lng;
            })

            this.point = {
                lat:_this.formData.latitude,
                lng:_this.formData.longitude
            }
                }
            }

        },
        getLocations(longitude,latitude){
            console.log(Number(longitude))
            console.log(latitude)
            this.myGeo.getLocation({
                lng:Number(longitude),
                lat:Number(latitude)
            }, function (result) {
             console.log(result)
            if (result) {
            return result.address
        }
      })
        },
        showQr(row) {
            this.dialogFormVisible = true;
            axios({
                url: '/api/commodityService/qrCode',
                method: 'get',
                params: {content: row.id},
                responseType: 'blob',
            }).then(rs => {
                this.src = window.URL.createObjectURL(rs.data);
            })
        },
        getImgUrl(item){
            console.log()
            var url = 'http://localhost:9527/commodityService/upload/imgs/'+item;
            return url;
        },

        add(row) { 
            queryStation(row).then(rs=>{
                this.allStations =  rs.data
                console.log(rs.data)
            });
            console.log(row)
            this.dialogGoodsInfo = row;
            this.dialogGoodsInfo.stock = row.stock.number
            this.dialogVisible = true;
            //根据地址解析坐标
            this.myGeo = new BMap.Geocoder()
            console.log(row.detailLoc)
            this.myGeo.getPoint(row.detailLoc,result=>{
                this.center.lat = result.lat;
                this.center.lng = result.lng;
            }
          )
            console.log(this.center) 
        },
        update(row) {
            let query = {id: row.id, type: true};
            console.log(query)
            this.$router.push({
                path: '/business/updateGood', /*给vue组件传递参数，并跳转*/
                query: query //vue页面接受的参数, 通过$router.query.id/..接受
            });
        },
        addStation(row){
            this.rentLocation = row.detailLoc;
            console.log(this.rentLocation)
            //打开地图
            console.log(row);
            this.currId  =row.id;
            this.dialogVisible1 = true;
            queryStation(row).then(rs=>{
                console.log(rs.data);
                this.center.lat = rs.data[0].latitude;
                this.center.lng = rs.data[0].longitude;
                 for(var i=0; i< rs.data.length; i++){
                    //遍历list
                    let position={
                        lng:null,
                        lat:null
                    }
                    position.lat = rs.data[i].latitude;
                    position.lng = rs.data[i].longitude
                    this.points.push(position)
                }  
            })
        },
        addStations(){  
            var parm = {
                id:null,
                commodityId:this.currId,
                latitude:this.formData.latitude,
                longitude:this.formData.longitude,
                detailLoc:this.formData.detailLoc
            }
            console.log(parm);
            addToStation(parm).then(rs=>{
                console.log(rs.data);
                if(rs.code==200){
                    this.$notify({
                    title: '成功',
                    message: '添加站点成功',
                    type: 'success'
                });
                this.dialogVisible1 = false;
                }
            })
        },
        returnHome() {
            window.location.reload(); //重新加载页面的
        },
        /*搜索功能*/
        search() {
            this.querys();
        },
        rowStyle(){
                return "text-align:center"
        },
        headStyle(){
                return "text-align:center"
        },
        //条件查询
        querys() {
                queryGoodByCondition(this.searchInfo).then((rs) => {
                this.goods = rs;
                this.pageInfo.total = rs.length;
                this.pageInfo.pageNo = 1;
                this.slice = rs.slice(0, this.pageInfo.pageSize);
            });
            },
        //真分页查询
        query() {
            if( typeof(getUser().id)!='undefined'){
                queryGoods({offset:this.pageInfo.pageNo,
                            limit:this.pageInfo.pageSize}).then((rs) => {
                this.goods = rs; 
                this.slice = rs;
            });
            }else{
                queryGoods({offset:this.pageInfo.pageNo,
                            limit:this.pageInfo.pageSize}).then((rs) => {
                this.goods = rs;
                this.slice = rs;
            });
            }
           
        },
        change(page) {        
            //判断是否是条件查询
            if(this.searchInfo.billMethod==null&&this.searchInfo.category==null){
                this.pageInfo.pageNo = page;
                //查询
                this.query();
            }else{
                    
                    this.pageInfo.pageNo = page;
                    this.slice = this.goods.slice((page - 1) * this.pageInfo.pageSize, page * this.pageInfo.pageSize);

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
          _this.formData.longitude = result.point.lng
          _this.formData.latitude = result.point.lat
          _this.formData.detailLoc = result.address
        }
      })
    },
    },

    data() {
        return {
            //租赁权的区域 地图描边用的
            rentLocation:null,
            identity:null,
            value:'',
            allStations:null,
            selectStation:'',
            pageInfo: {
                pageSize:5 ,
                pageNo: 1,
                total: 10,
            },
            //站点的数据
            station:{
                id:null,
                commodityId:null,
                longitude:null,
                latitude:null,
                detailLoc:null
            },
            searchInfo: {
                category: null,
                status: null,
                manufacturer: getUser().id,
                billMethod:null
            },
            currId:null,
            slice: null,
            goods: [getBasicForm()],
            categories: null,
            categoryMap: new Map(),
            dialogFormVisible: false,
            dialogVisible:false,
            dialogVisible1:false,
            src: null,
            //上架商品弹框展示的参数
            dialogGoodsInfo:{
                id:null,
                name:null,
                stocks:null,
                detailLoc:null,
                category:null
            },
            //上架商品用的参数
            formData:{
                amount:null,
                longitude:null,
                latitude:null,
                detailLoc:null,
                goodsId:null
            },
             // 地图你解析方法实例
            myGeo: null,
            city: '',
            province: '',
            district: '',
            points: [],
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
            zoom:3
        }
    }
    ,
    mounted() {
        this.identity = getToken();
        this.query();
        //查询商品总数
        queryTotal().then(rs=>{
            this.pageInfo.total = rs.data
        })
        queryCategories().then(rs => {
            const _this = this;
            _this.categories = rs
            _this.categoryMap = new Map();
            _this.categories.forEach(function (item) {
                _this.categoryMap.set(item.id, item.name);
                _this.categoryMap = new Map(_this.categoryMap);
            })
        })
    }
    ,
    computed: {}
}

</script>

<style scoped>
 .cell {
    text-align: center;
}
.el-form-item{
    display:inline-block
}

.map {
  width: 100%;
  height: 400px;
}

.BMap_cpyCtrl{
display:none;
}
</style>