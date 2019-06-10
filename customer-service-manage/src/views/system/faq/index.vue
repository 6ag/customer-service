<template>
  <div class="app-container">
    <!-- 查询过滤 -->
    <div class="filter-container">
      <el-input v-model="listQuery.title" placeholder="请输入标题搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button v-waves class="filter-item" style="margin-left: 5px;" type="success" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-plus" @click="handleCreate">
        新增
      </el-button>
    </div>

    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column label="序号" prop="id" sortable="custom" align="center" min-width="80">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="标题" min-width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column label="团队" min-width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.user.team.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建者" min-width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.user.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="排序" prop="sort" sortable="custom" min-width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.sort }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建日期" min-width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.createdAt }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="160px" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.pageSize" @pagination="getList" />

    <!-- 编辑框 -->
    <el-dialog :title="dialogTitleMap[dialogStatus]" :visible.sync="dialogFormVisible" width="880px">
      <el-form ref="dataForm" :rules="rules" :model="faqInfo" :inline="true" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="faqInfo.title" style="width: 400px;" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="faqInfo.sort" placeholder="请输入排序" :min="1" :max="999" controls-position="right" style="width: 400px;" />
        </el-form-item>
        <el-form-item label="内容">
          <template>
            <div ref="editor" style="text-align:left" />
          </template>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus === 'create' ? createData() : updateData()">
          确定
        </el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { MessageBox } from 'element-ui'
import waves from '@/directive/waves'
import Pagination from '@/components/Pagination'
import faqApi from '@/api/faq'
import E from 'wangeditor'
import { getToken } from '@/utils/auth'

export default {
  components: { Pagination },
  directives: { waves },
  data() {
    return {
      list: null, // 列表数据
      total: 0, // 列表总数
      listLoading: true, // 是否正在加载
      listQuery: {
        page: 1,
        pageSize: 10,
        order: 'DESC',
        orderByField: 'id',
        title: undefined
      }, // 列表查询条件
      faqInfo: { title: '', content: '' }, // 常见问题信息
      dialogFormVisible: false, // 是否显示弹窗
      dialogStatus: '', // 弹窗的状态 create/update
      dialogTitleMap: {
        update: '编辑常见问题',
        create: '创建常见问题'
      }, // 弹窗标题
      rules: {
        title: [
          { required: true, message: '请输入标题', trigger: 'change' }
        ]
      }, // 用户编辑表单的验证规则
      editor: null,
      headers: {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': 'Bearer ' + getToken()
      },
      imagesUploadApi: '' // 上传图片到服务器
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      faqApi.getFaqList(this.listQuery).then((response) => {
        this.listLoading = false
        this.list = response.data.list
        this.total = response.data.totalCount
      })
    },
    restForm() {
      this.createEditor()

      const userInfo = this.$store.getters.userInfo
      this.faqInfo = Object.assign({}, { title: '', content: '', userId: userInfo.id, teamId: userInfo.team.id })

      this.$nextTick(() => {
        this.$refs.dataForm.clearValidate()
      })
    },
    // 创建编辑器
    createEditor() {
      this.$nextTick(() => {
        if (!this.editor) {
          this.editor = new E(this.$refs.editor)
          this.editor.customConfig.uploadImgShowBase64 = true // 使用 base64 保存图片
          this.editor.customConfig.uploadImgHeaders = this.headers
          this.editor.customConfig.uploadFileName = 'file'
          this.editor.customConfig.uploadImgServer = this.imagesUploadApi
          // 编辑器文本改变
          this.editor.customConfig.onchange = (html) => {
            this.faqInfo.content = html
          }
          this.editor.create()
        }
      })
    },
    sortChange(data) {
      const { prop, order } = data
      this.listQuery.orderByField = prop
      if (order === 'ascending') {
        this.listQuery.order = 'ASC'
      } else {
        this.listQuery.order = 'DESC'
      }
      this.handleFilter()
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleCreate() {
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.restForm()

      // 清空编辑器内容
      this.$nextTick(() => {
        this.editor.txt.clear()
      })
    },
    handleUpdate(row) {
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.restForm()

      // 获取当前行数据
      this.faqInfo = Object.assign({}, row)

      // 编辑器内容回显
      this.$nextTick(() => {
        this.editor.txt.html(this.faqInfo.content)
      })
    },
    handleDelete(row) {
      MessageBox.confirm(`确定删除【${row.title}】吗？删除后无法恢复，请谨慎操作！`, '温馨提示', {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteData(row.id)
      })
    },
    deleteData(faqId) {
      faqApi.deleteFaq(faqId).then((response) => {
        if (response.status === 200) {
          // 隐藏弹窗并弹窗通知提示
          this.dialogFormVisible = false
          this.$notify({
            title: '成功',
            message: '删除常见问题成功',
            type: 'success',
            duration: 2000
          })
          // 重新加载数据
          this.getList()
        }
      })
    },
    createData() {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          faqApi.addFaq(this.faqInfo).then((response) => {
            if (response.status === 200) {
              // 隐藏弹窗并弹窗通知提示
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '创建常见问题成功',
                type: 'success',
                duration: 2000
              })
              // 重新加载数据
              this.getList()
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    updateData() {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          faqApi.updateFaq(this.faqInfo).then((response) => {
            if (response.status === 200) {
              // 隐藏弹窗并弹窗通知提示
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '更新常见问题信息成功',
                type: 'success',
                duration: 2000
              })
              // 重新加载数据
              this.getList()
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  /deep/ .el-input-number .el-input__inner {
    text-align: left;
  }
</style>
