<template>
  <div class="app-container">
    <!-- 查询过滤 -->
    <div class="filter-container">
      <el-input v-model="listQuery.name" placeholder="请输入团队/公司名称搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
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
      <el-table-column label="团队/公司名称" min-width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所在行业" min-width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.industry }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所在省份" min-width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.province }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所在城市" min-width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.city }}</span>
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
    <el-dialog :title="dialogTitleMap[dialogStatus]" :visible.sync="dialogFormVisible" width="600px">
      <el-form ref="dataForm" :rules="rules" :model="teamInfo" :inline="true" label-width="120px">
        <el-form-item label="团队/公司名称" prop="name">
          <el-input v-model="teamInfo.name" style="width: 400px;" />
        </el-form-item>
        <el-form-item label="所在行业" prop="industry">
          <el-select v-model="teamInfo.industry" clearable placeholder="请选择所在行业" style="width: 400px;">
            <el-option
              v-for="item in industryList"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="所在省市">
          <el-cascader
            :value="[teamInfo.province, teamInfo.city]"
            placeholder="请选择所在省市"
            :options="provinceCityList"
            :props="{ value: 'name', label: 'name', children: 'children' }"
            clearable
            style="width: 400px;"
            @change="handleCitySelect"
          />
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
import teamApi from '@/api/team'
import commonApi from '@/api/common'

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
        name: undefined
      }, // 列表查询条件
      teamInfo: { name: '', industry: '', province: '', city: '' }, // 当前角色信息
      dialogFormVisible: false, // 是否显示弹窗
      dialogStatus: '', // 弹窗的状态 create/update
      dialogTitleMap: {
        update: '编辑团队',
        create: '创建团队'
      }, // 弹窗标题
      rules: {
        name: [
          { required: true, message: '请输入团队/公司名称', trigger: 'blur' }
        ]
      }, // 用户编辑表单的验证规则
      provinceCityList: [], // 省市
      industryList: [] // 行业
    }
  },
  created() {
    this.getList()
    this.getProvinceCityList()
    this.getIndustryList()
  },
  methods: {
    getList() {
      this.listLoading = true
      teamApi.getTeamList(this.listQuery).then((response) => {
        this.listLoading = false
        this.list = response.data.list
        this.total = response.data.totalCount
      })
    },
    getProvinceCityList() {
      commonApi.getProvinceCityList().then((response) => {
        if (response.status === 200) {
          this.provinceCityList = response.data
        }
      })
    },
    getIndustryList() {
      commonApi.getIndustryList().then((response) => {
        if (response.status === 200) {
          this.industryList = response.data
        }
      })
    },
    handleCitySelect(selectedValue) {
      if (selectedValue && selectedValue instanceof Array && selectedValue.length === 2) {
        const provinceCity = Object.assign([], selectedValue)
        this.teamInfo.province = provinceCity[0]
        this.teamInfo.city = provinceCity[1]
      }
    },
    restForm() {
      this.teamInfo = Object.assign({}, { name: '', industry: '', province: '', city: '' })

      this.$nextTick(() => {
        this.$refs.dataForm.clearValidate()
      })
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        if (order === 'ascending') {
          this.listQuery.order = 'ASC'
        } else {
          this.listQuery.order = 'DESC'
        }
        this.handleFilter()
      }
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleCreate() {
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.restForm()
    },
    handleUpdate(row) {
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.restForm()

      this.teamInfo = Object.assign({}, row)
    },
    handleDelete(row) {
      MessageBox.confirm(`确定删除【${row.name}】吗？删除后无法恢复，请谨慎操作！`, '温馨提示', {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteData(row.id)
      })
    },
    deleteData(teamId) {
      teamApi.deleteTeam(teamId).then((response) => {
        if (response.status === 200) {
          // 隐藏弹窗并弹窗通知提示
          this.dialogFormVisible = false
          this.$notify({
            title: '成功',
            message: '删除团队成功',
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
          teamApi.addTeam(this.teamInfo).then((response) => {
            if (response.status === 200) {
              // 隐藏弹窗并弹窗通知提示
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '创建团队成功',
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
          teamApi.updateTeam(this.teamInfo).then((response) => {
            if (response.status === 200) {
              // 隐藏弹窗并弹窗通知提示
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '更新团队信息成功',
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

<style scoped>

</style>
