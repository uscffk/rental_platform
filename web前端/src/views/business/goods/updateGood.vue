<template>


    <!--组件的配置需要去仔细看官网组件代码示例-->
    <div class="heightAll widthAll">
        <!-- 配置商品信息 -->
        <el-button type="primary" @click="dialogVisible = true">设置商品信息</el-button>


        <el-dialog
            title="商品信息"
            :visible.sync="dialogVisible"
            width="50%"
            :before-close="handleClose">
            
            <el-row>
              <el-col :span="位置信息"></el-col>
              <span>基本信息</span>
          
            </el-row>

             <el-divider></el-divider>

               <el-form :model="goodInfo" :rules="rules" label-position="left" label-width="auto" ref="goodInfo"
                 style="margin: 10px;">
            <el-form-item label="编号" prop="id" v-if="type">
                <el-input :disabled="true" size="medium"
                          style="width: 240px" v-model="goodInfo.id"></el-input>
            </el-form-item>
            <el-form-item label="商品名称" prop="name">
                <el-input placeholder="请输入商品名称" size="medium" :disabled="true"
                          style="width: 240px" v-model="goodInfo.name"></el-input>
            </el-form-item>

            <el-form-item label="单期租赁价格" prop="rentPrice">
                <el-input :min="0" size="medium" style="width: 240px" type="number"
                          v-model="goodInfo.rentPrice"></el-input>
            </el-form-item>

             <el-form-item label="库存" prop="stock">
                <el-input :min="0" size="medium" style="width: 240px" type="number" :disabled="true"
                          v-model="goodInfo.stock.number"></el-input>
            </el-form-item>

            <el-form-item label="出售价格" prop="sellPrice">
                <el-input :min="0" size="medium" style="width: 240px" type="number"
                          v-model="goodInfo.sellPrice"></el-input>
            </el-form-item>
            <el-form-item label="最大期数" prop="timeNumber">
                <el-input :min="0" size="medium" style="width: 240px" type="number"
                          v-model="goodInfo.timeNumber"></el-input>
            </el-form-item>
            <el-form-item label="押金" prop="deposit">
                <el-input :min="0" size="medium" style="width: 240px" type="number"
                          v-model="goodInfo.deposit"></el-input>
            </el-form-item>
            <el-form-item label="分类" prop="category">
                <el-select placeholder="请选择商品分类" style="width: 240px" :disabled="true"
                           v-model="goodInfo.category">
                    <el-option
                            :key="category.id"
                            :label="category.name"
                            :value="category.id"
                            v-for="category in categories">
                    </el-option>
                </el-select>
            
            </el-form-item>
            <el-form-item label="商品描述" prop="discription">
                <el-input size="medium" style="width: 240px"
                          v-model="goodInfo.discription"></el-input>
            </el-form-item>

                 <el-form-item label="计费方式" prop="billMethod">
                <el-select style="width: 240px"
                           v-model="goodInfo.billMethod">
                    <el-option
                            :key="status.id"
                            :label="status.name"
                            :value="status.id"
                            v-for="status in [{id:0,name:'小时制'},{id:1,name:'天制'},{id:2,name:'月制'}]">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-row>
              <el-col :span="租赁模式"></el-col>
              <span>租赁模式(一种商品可以同时支持共享租赁和先租后买，但是支持了以租代售就不可支持共享租赁和先租后买，设置的时候麻烦遵循该原则)</span>
            </el-row>
             <el-divider></el-divider>

            <el-form-item label="是否支持共享租赁" prop="sharingRent">
                <el-select style="width: 240px"
                           v-model="goodInfo.sharingRent">
                    <el-option
                            :key="status.id"
                            :label="status.name"
                            :value="status.id"
                            v-for="status in [{id:0,name:'不支持'},{id:1,name:'支持'}]">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="是否支持先租后买" prop="rentBeforeBuy">
                <el-select style="width: 240px"
                           v-model="goodInfo.rentBeforeBuy">
                    <el-option
                            :key="status.id"
                            :label="status.name"
                            :value="status.id"
                            v-for="status in [{id:0,name:'不支持'},{id:1,name:'支持'}]">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="是否支持以租代售" prop="rentForSale">
                <el-select style="width: 240px"
                           v-model="goodInfo.rentForSale">
                    <el-option
                            :key="status.id"
                            :label="status.name"
                            :value="status.id"
                            v-for="status in [{id:0,name:'不支持'},{id:1,name:'支持'}]">
                    </el-option>
                </el-select>
            </el-form-item>

            <!-- 图片描述 -->
            
            <el-row>
              <el-col :span="图片描述"></el-col>
              <span>图片描述</span>
            </el-row>
            <el-divider></el-divider>
         
          <template>
                 <div>		
                 <el-upload
                    action="http://localhost:9527/commodityService/uploadCommodityPic"
                    list-type="picture-card"
                    :on-preview="handlePictureCardPreview"
                    :on-remove="handleRemove"
                    :data="{commodityId:this.goodInfo.id}">
                    <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisible1">
                    <img width="100%" :src="dialogImageUrl" alt="">
                    </el-dialog>
                 </div>
        </template>


            <el-row>
              <el-col :span="位置信息"></el-col>
              <span>位置信息</span>
              <i class="el-icon-location-information" style="color:red"></i>
          
            </el-row>
           
            <el-divider></el-divider>


            <el-form-item label="详细位置" prop="detailLoc" >
                <el-input size="medium" style="width: 240px" :disabled="true"
                          v-model="goodInfo.detailLoc"></el-input>
            </el-form-item>

            <el-form-item>
                <!--见el-form ref属性-->
                <el-button style="width:115px" @click="submitForm('goodInfo')" type="primary">{{type?'修改':'添加'}}商品 
                </el-button>
                <el-button  type="warning" style="width:115px" @click="resetForm('goodInfo')">重置</el-button>
            </el-form-item>

            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="dialogVisible = false" style="width:120px">确 定</el-button>
            </span>

               </el-form>
        </el-dialog>



    </div>
</template>

<script>

import {addGood, getBasicForm, queryGoodById, updateGood} from "../../../api/good";
import {queryCategories} from "../../../api/category";
import constants from "../../../store/constants";
import Vue from 'vue'
import BaiduMap from 'vue-baidu-map';
Vue.use(BaiduMap, {
          /* Visit http://lbsyun.baidu.com/apiconsole/key for details about app key. */
          ak: 'BO1gWgR8LDt1mmYsaTrvhWFszkBxMmYf'
    })
export default {
    name: 'addGood',
    data() {
        return {
            dialogImageUrl: '',
            dialogVisible1: false,
            dialogVisible: false,
            fileList2: [],
            imageUrl:'',
            backgroundUrl:'',
            categoryMap: new Map(),
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

            type: false, //是否是修改操作
            goodInfo: JSON.parse(JSON.stringify(getBasicForm())),
            rules: {
                name: [{required: true, message: '商品名称不能为空'}],
                rentPrice: [{required: true, message: '租赁价格不能为空'}],
                sellPrice: [{required: true, message: '出售价格不能为空'}],
                timeNumber: [{required: true, message: '最大期数不能为空'}],
                deposit: [{required: true, message: '押金不能为空'}],
                category: [{required: true, message: '分类不能为空'}],
                sharingRent: [{required: true, message: '不能为空'}],
                rentBeforeBuy: [{required: true, message: '不能为空'}],
                rentForSale: [{required: true, message: '不能为空'}],
                billMethod: [{required: true, message: '不能为空'}],
                status: [{required: true, message: '不能为空'}],
            },
            categories: null,
        };
    },
    methods: {
        
      handleRemove(file, fileList) {
      },
      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible1 = true;
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
          _this.city = result.addressComponents.city
          _this.province = result.addressComponents.province
          _this.district = result.addressComponents.district
          _this.goodInfo.longitude = result.point.lng
          _this.goodInfo.latitude = result.point.lat
          _this.goodInfo.detailLoc = result.address
        }
      })
    },
    handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
      },
    beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      } ,
        // 提交表单
        submitForm(formName) {
            const _this = this;
            _this.$refs[formName].validate((valid) => {
                if (valid) {
                    if ( _this.type)
                        updateGood(this.goodInfo).then(rs => {
                            this.afterAction(rs);
                            _this.dialogVisible = false;
                        });
                    else
                        addGood(this.goodInfo).then(rs => {
                            this.afterAction(rs);
                        });
                }
            });
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
        }
    }, mounted() {
        const _this = this;
        this.type = this.$route.query.type;
        if (this.$route.query.type) {
            queryGoodById(this.$route.query.id).then(rs => {
                if (rs.code === constants.state.SUCCESS) {
                    this.goodInfo = rs.data;
                } else {
                    this.$message.warning(rs.message + ',无法修改');
                    clearTimeout(_this.timer);
                    _this.timer = setTimeout(() => {
                        this.$router.push('/');
                    }, 1000);
                }
            })
        }
        queryCategories().then(rs => {
             const _this = this;
            this.categories = rs
             _this.categoryMap = new Map();
            _this.categories.forEach(function (item) {
                _this.categoryMap.set(item.id, item.name);
                console.log(_this.categoryMap.get(item.id))
                _this.categoryMap = new Map(_this.categoryMap);
                console.log(_this.categoryMap)
            })
        })
    }, computed: {}
}
</script>

<style scoped>
.el-form-item{
    display:inline-block
}

.map {
  width: 100%;
  height: 100%;
}

.BMap_cpyCtrl{
display:none;
}

.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>