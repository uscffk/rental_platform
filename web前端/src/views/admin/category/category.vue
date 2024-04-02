<template>
    <div class="heightAll widthAll"> <!--这样可以设置多个class-->
        <div style="margin-bottom: 10px;">
            <el-button @click="add" circle icon="el-icon-plus" size="mini" style="display: inline"
                       type="success"></el-button>
            <span style="color: #85C161; margin-left: 10px;">添加分类</span>
        </div>
        <el-table :data="slice" class="el-table"
                  header-align="center" stripe style="width: 100%; text-align: center"
                  :header-cell-style="headStyle"  :cell-style="rowStyle">
            <el-table-column label="编号" prop="id">
            </el-table-column>
            <el-table-column label="分类名称" prop="name">
            </el-table-column>
            <el-table-column label="描述" prop="discription">
            </el-table-column>
            </el-table-column>
            <el-table-column label="首页推荐" prop="recommend">
                 <template slot-scope="scope">
                    <el-tag :type="scope.row.recommend===1 ? 'success':'warning' "
                            style="text-align: center;"><span
                            style="float: left;">{{scope.row.recommend===1 ? '是':'否'}}</span>
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button @click="update(scope.row)" size="mini" type="primary">修改
                    </el-button>
                    <el-button @click="deleteCategory(scope.row)" size="mini" type="danger">删除</el-button>
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
                       layout="prev, pager, next">
        </el-pagination>

        <el-dialog :title="isMod? '修改分类信息':'增加分类信息'" :visible.sync="dialogFormVisible">
            <el-form :model="category">
                <el-form-item label="编号"  v-if="isMod"  style="width:100%">
                    <el-input readonly v-model="category.id" disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="分类名称" style="width:100%">
                    <el-input autocomplete="off" v-model="category.name"></el-input>
                </el-form-item>
                <el-form-item label="描述信息" style="width:100%">
                    <el-input autocomplete="off" v-model="category.discription"></el-input>
                </el-form-item>
            </el-form>
            <div class="dialog-footer" slot="footer">
                <el-button @click="resetForm">取 消</el-button>
                <el-button @click="action" type="primary">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import {addCategory, deleteCategory, queryCategories, updateCategory} from "../../../api/category";
import constants from "../../../store/constants";
import {afterAction} from "../../../api/common";

export default {
    name: 'category',
    methods: {
        update(row) {
            this.isMod = true;
            this.category = JSON.parse(JSON.stringify(row));
            this.dialogFormVisible = true;
        },
        change(page) {
            this.pageInfo.pageNo = page;
            this.slice = this.categories.slice((page - 1) * this.pageInfo.pageSize, page * this.pageInfo.pageSize - 1);
        },
        add() {
            this.isMod = false;
            this.dialogFormVisible = true;
        },
         rowStyle(){
                return "text-align:center"
        },
        headStyle(){
                return "text-align:center"
        },
        resetForm() {
            this.dialogFormVisible = false;
            this.category = {id: null, name: null, discription: null};
        },
        action() {
            if (this.isMod) {
                updateCategory(this.category).then(rs => {
                    this.updateCommonAct(rs, '修改')
                });
            } else {
                addCategory(this.category).then(rs => {
                    this.updateCommonAct(rs, '添加')
                });
            }
        },
        updateCommonAct(rs, msg) {
            this.resetForm();
            if (rs.code === constants.state.SUCCESS) {
                afterAction(this, true, msg, null, false);
            } else {
                afterAction(this, false, msg, this.$router.path, true);
            }
        },
        deleteCategory(row) {
            this.$confirm('确认删除吗, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteCategory(row.id).then(rs => {
                    this.updateCommonAct(rs, '删除');
                });
            });
        }
    },
    data() {
        return {
            category: {id: null, name: null, discription: null},
            pageInfo: {
                pageSize: 5,
                pageNo: 1,
                total: 0,
            },
            slice: null,
            categories: null,
            dialogFormVisible: false,
            isMod: true,
        }
    },
    mounted() {
        queryCategories().then(rs => {
            this.categories = rs;
            this.pageInfo.total = rs.length;
            this.pageInfo.pageNo = 1;
            this.slice = rs.slice(0, this.pageInfo.pageSize - 1);
        })
    },
    computed: {}
}

</script>

<style scoped>
  .el-table-column{
        width:20%
    }
</style>