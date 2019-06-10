<template>
  <div class="app-container">
    <!-- 查询过滤 -->
    <div class="filter-container">
      <el-input v-model="listQuery.name" placeholder="请输入名称搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
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
      <el-table-column label="中文名称" min-width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.nameZh }}</span>
        </template>
      </el-table-column>
      <el-table-column label="英文名称" min-width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.nameEn }}</span>
        </template>
      </el-table-column>
      <el-table-column label="角色级别" min-width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.level }}</span>
        </template>
      </el-table-column>
      <el-table-column label="描述" min-width="160px">
        <template slot-scope="{row}">
          <span>{{ row.remark }}</span>
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
      <el-row>
        <el-col :span="12">
          <el-form ref="dataForm" :rules="rules" :model="roleInfo" :inline="true" label-width="80px">
            <el-form-item label="中文名称" prop="nameZh">
              <el-input v-model="roleInfo.nameZh" />
            </el-form-item>
            <el-form-item label="英文名称" prop="nameEn">
              <el-input v-model="roleInfo.nameEn" />
            </el-form-item>
            <el-form-item label="角色级别" prop="level">
              <el-input v-model="roleInfo.level" />
            </el-form-item>
            <el-form-item label="描述" prop="remark">
              <el-input v-model="roleInfo.remark" rows="3" type="textarea" />
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="12">
          <el-tree
            ref="permissionTree"
            :data="allPermissions"
            :props="{children: 'children', label: 'name'}"
            :default-checked-keys="permissionIds"
            show-checkbox
            accordion
            node-key="id"
          />
        </el-col>
      </el-row>

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
import roleApi from '@/api/role'
import permissionApi from '@/api/permission'

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
        order: 'ASC',
        name: undefined
      }, // 列表查询条件
      allPermissions: [], // 所有权限集合
      permissionIds: [], // 当前角色的权限编号集合
      roleInfo: {}, // 当前角色信息
      dialogFormVisible: false, // 是否显示弹窗
      dialogStatus: '', // 弹窗的状态 create/update
      dialogTitleMap: {
        update: '编辑角色',
        create: '创建角色'
      }, // 弹窗标题
      rules: {
        nameZh: [
          { required: true, message: '请输入中文名称', trigger: 'change' }
        ],
        nameEn: [
          { required: true, message: '请输入英文名称', trigger: 'change' }
        ],
        level: [
          { required: true, message: '请输入角色级别', trigger: 'change' }
        ]
      } // 用户编辑表单的验证规则
    }
  },
  created() {
    this.getList()
    this.getAllPermission()
  },
  methods: {
    // 根据列表查询条件查询表格数据
    getList() {
      this.listLoading = true
      roleApi.getRoleList(this.listQuery).then((response) => {
        this.listLoading = false
        this.list = response.data.list
        this.total = response.data.totalCount
      })
    },
    // 获取所有权限
    getAllPermission() {
      permissionApi.getAllPermission().then((response) => {
        if (response.status === 200) {
          this.allPermissions = response.data
        }
      })
    },
    // 清空编辑表单用到的临时数据
    restForm() {
      this.roleInfo = {}
      this.permissionIds = []

      // 在Dialog上的表单和tree组件渲染完成后清空
      this.$nextTick(() => {
        this.$refs.dataForm.clearValidate()
        this.$refs.permissionTree.setCheckedKeys([])
        // 折叠全部节点
        for (let i = 0; i < this.$refs.permissionTree.store._getAllNodes().length; i++) {
          this.$refs.permissionTree.store._getAllNodes()[i].expanded = false
        }
      })
    },
    // 排序方式改变
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
    // 处理搜索过滤
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    // 处理创建
    handleCreate() {
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.restForm()
    },
    // 处理更新
    handleUpdate(row) {
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.restForm()

      // 获取指定用户信息
      roleApi.getRoleInfoById(row.id).then((response) => {
        if (response.status === 200) {
          // 当前角色信息
          this.roleInfo = response.data.roleInfo
          this.permissionIds = response.data.childrenPermissionIds
          console.log(this.permissionIds)
        }
      })
    },
    // 处理删除
    handleDelete(row) {
      MessageBox.confirm(`确定删除【${row.nameZh}】吗？删除后无法恢复，请谨慎操作！`, '温馨提示', {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteData(row.id)
      })
    },
    deleteData(roleId) {
      roleApi.deleteRole(roleId).then((response) => {
        if (response.status === 200) {
          // 隐藏弹窗并弹窗通知提示
          this.dialogFormVisible = false
          this.$notify({
            title: '成功',
            message: '删除角色成功',
            type: 'success',
            duration: 2000
          })
          // 重新加载数据
          this.getList()
        }
      })
    },
    // 创建角色
    createData() {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          const permissionIds = this.$refs.permissionTree.getCheckedKeys()
            .concat(this.$refs.permissionTree.getHalfCheckedKeys())
          console.log(permissionIds)
          const roleDto = {
            roleInfo: this.roleInfo,
            permissionIds: permissionIds
          }
          roleApi.addRole(roleDto).then((response) => {
            if (response.status === 200) {
              // 隐藏弹窗并弹窗通知提示
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '创建角色成功',
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
    // 更新角色
    updateData() {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          const permissionIds = this.$refs.permissionTree.getCheckedKeys()
            .concat(this.$refs.permissionTree.getHalfCheckedKeys())
          console.log(permissionIds)
          const roleDto = {
            roleInfo: this.roleInfo,
            permissionIds: permissionIds
          }
          roleApi.updateRole(roleDto).then((response) => {
            if (response.status === 200) {
              // 隐藏弹窗并弹窗通知提示
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '更新角色信息成功',
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
