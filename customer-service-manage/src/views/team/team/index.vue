<template>
  <div class="app-container">
    <!-- 查询过滤 -->
    <div class="filter-container">
      <el-input v-model="listQuery.username" placeholder="请输入用户名搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.email" placeholder="请输入邮箱搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.status" placeholder="用户状态" clearable class="filter-item" style="width: 130px">
        <el-option v-for="item in statusOptions" :key="item.key" :label="item.label" :value="item.key" />
      </el-select>
      <el-select v-model="listQuery.order" style="width: 140px" class="filter-item" @change="handleFilter">
        <el-option v-for="item in sortOptions" :key="item.key" :label="item.label" :value="item.key" />
      </el-select>
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
      <el-table-column label="昵称" min-width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.nickname }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户名" min-width="160px">
        <template slot-scope="{row}">
          <span>{{ row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="邮箱" min-width="160px">
        <template slot-scope="{row}">
          <span>{{ row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别" min-width="100px" align="center">
        <template slot-scope="{row}">
          <el-tag :type="row.gender | genderTagFilter">
            {{ row.gender | genderNameFilter }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" min-width="100px" align="center">
        <template slot-scope="{row}">
          <el-tag :type="row.status | statusTagFilter">
            {{ row.status | statusNameFilter }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="微信登录" min-width="100px" align="center">
        <template slot-scope="{row}">
          <el-tag :type="row.openId != null && row.openId.length === 0 ? 'info' : 'success'">
            {{ row.openId != null && row.openId.length === 0 ? '未开通' : '已开通' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="注册时间" min-width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.createdAt }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="230px" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button v-if="row.status != '2'" type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button v-if="row.status == '0'" size="mini" type="success" @click="handleModifyStatus(row,'1')">
            启用
          </el-button>
          <el-button v-if="row.status == '1'" size="mini" type="warning" @click="handleModifyStatus(row,'0')">
            禁用
          </el-button>
          <el-button v-if="row.status != '2'" size="mini" type="danger" @click="handleModifyStatus(row,'2')">
            删除
          </el-button>
          <el-button v-if="row.status == '2'" size="mini" type="warning" @click="handleModifyStatus(row,'0')">
            恢复
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.pageSize" @pagination="getList" />

    <!-- 编辑框 -->
    <el-dialog :title="dialogTitleMap[dialogStatus]" :visible.sync="dialogFormVisible" width="600px">
      <el-form ref="dataForm" :rules="rules" :model="userInfo" :inline="true" label-width="66px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userInfo.username" />
        </el-form-item>
        <el-form-item v-if="dialogStatus === 'create'" label="密码" prop="password">
          <el-input v-model="userInfo.password" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userInfo.email" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="userInfo.nickname" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="userInfo.gender" class="filter-item" placeholder="请选择">
            <el-option v-for="item in genderOptions" :key="item.key" :label="item.label" :value="item.key" />
          </el-select>
        </el-form-item>
        <el-form-item style="margin-bottom: 0px;" label="角色" prop="roleIds">
          <el-select v-model="userInfo.roleIds" style="width: 450px;" multiple placeholder="请选择">
            <el-option
              v-for="item in allRoles"
              :key="item.id"
              :disabled="level !== 1 && item.level <= level"
              :label="item.nameZh"
              :value="item.id"
            />
          </el-select>
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
import waves from '@/directive/waves'
import Pagination from '@/components/Pagination'
import userApi from '@/api/user'
import roleApi from '@/api/role'

export default {
  components: { Pagination },
  directives: { waves },
  filters: {
    genderTagFilter(gender) {
      const genderMap = {
        '0': 'danger',
        '1': 'success',
        '2': 'info'
      }
      return genderMap[gender]
    },
    genderNameFilter(gender) {
      const genderMap = {
        '0': '女',
        '1': '男',
        '2': '未知'
      }
      return genderMap[gender]
    },
    statusTagFilter(status) {
      const statusMap = {
        '0': 'danger',
        '1': 'success',
        '2': 'info'
      }
      return statusMap[status]
    },
    statusNameFilter(status) {
      const statusMap = {
        '0': '已禁用',
        '1': '已启用',
        '2': '已删除'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      list: null, // 列表数据
      total: 0, // 列表总数
      listLoading: true, // 是否正在加载
      listQuery: {
        page: 1,
        pageSize: 10,
        order: 'DESC',
        username: undefined,
        email: undefined,
        status: '1',
        teamId: 0
      }, // 列表查询条件
      sortOptions: [{ label: '最早注册', key: 'ASC' }, { label: '最新注册', key: 'DESC' }], // 排序选项
      statusOptions: [{ label: '已禁用', key: '0' }, { label: '已启用', key: '1' }, { label: '已删除', key: '2' }], // 状态选项
      genderOptions: [{ label: '女', key: '0' }, { label: '男', key: '1' }, { label: '未知', key: '2' }], // 性别选项
      level: 10, // 当前登录的用户的角色最高级别级别
      allRoles: [], // 所有角色集合
      userInfo: { roleIds: [] }, // 当前用户信息
      dialogFormVisible: false, // 是否显示弹窗
      dialogStatus: '', // 弹窗的状态 create/update
      dialogTitleMap: {
        update: '编辑用户',
        create: '创建用户'
      }, // 弹窗标题
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 5, max: 18, message: '用户名长度在 5 到 18 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 18, message: '密码长度在 6 到 18 个字符', trigger: 'blur' }
        ],
        email: [
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ],
        roleIds: [
          { type: 'array', required: true, message: '请至少选择一个角色', trigger: 'change' }
        ]
      } // 用户编辑表单的验证规则
    }
  },
  created() {
    this.getList()
    this.getAllRole()
    this.getTallestRoleLevel()
  },
  methods: {
    // 根据列表查询条件查询表格数据
    getList() {
      this.listLoading = true
      // 获取当前登录用户的团队编号
      if (this.$store.getters.userInfo.team) {
        this.listQuery.teamId = this.$store.getters.userInfo.team.id
      }
      userApi.getUserList(this.listQuery).then((response) => {
        this.listLoading = false
        this.list = response.data.list
        this.total = response.data.totalCount
      })
    },
    // 获取最高角色级别
    getTallestRoleLevel() {
      roleApi.getTallestRoleLevel(this.$store.getters.id).then((response) => {
        if (response.status === 200) {
          this.level = response.data
        }
      })
    },
    // 获取所有角色
    getAllRole() {
      roleApi.getAllRole().then((response) => {
        if (response.status === 200) {
          this.allRoles = response.data
        }
      })
    },
    // 清空编辑表单用到的临时数据
    restForm() {
      this.userInfo = { password: '', gender: '2', roleIds: [] }
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
    handleTeam(team) {
      console.log(team)
      this.$router.push('/system/team')
    },
    // 处理创建
    handleCreate() {
      this.restForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs.dataForm.clearValidate()
      })
    },
    // 处理更新
    handleUpdate(row) {
      // 获取指定用户信息
      userApi.getUserInfoById(row.id).then((response) => {
        if (response.status === 200) {
          // 当前用户信息
          this.userInfo = response.data.userInfo
          const roles = response.data.roles
          if (roles && roles instanceof Array) {
            // 当前用户角色ID数组
            const roleIds = roles.map((role) => {
              return role.id
            })
            // Vue不能检测到对象属性的添加删除
            this.$set(this.userInfo, 'roleIds', roleIds)
          }

          this.dialogStatus = 'update'
          this.dialogFormVisible = true
          this.$nextTick(() => {
            this.$refs.dataForm.clearValidate()
          })
        }
      })
    },
    // 修改用户状态
    handleModifyStatus(row, status) {
      if (this.$store.getters.userInfo.id === row.id) {
        this.$message({ type: 'info', message: '不能修改自己的状态' })
        return
      }
      row.status = status
      const userDto = {
        userInfo: row
      }
      userApi.updateUser(userDto).then((response) => {
        if (response.status === 200) {
          // 隐藏弹窗并弹窗通知提示
          this.dialogFormVisible = false
          this.$notify({
            title: '成功',
            message: '更新用户信息成功',
            type: 'success',
            duration: 2000
          })
          // 重新加载数据
          this.getList()
        }
      })
    },
    // 创建用户
    createData() {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          const userDto = {
            userInfo: this.userInfo,
            roleIds: this.userInfo.roleIds
          }
          userApi.addUser(userDto).then((response) => {
            if (response.status === 200) {
              // 隐藏弹窗并弹窗通知提示
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '创建用户成功',
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
    // 更新用户
    updateData() {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          const userDto = {
            userInfo: this.userInfo,
            roleIds: this.userInfo.roleIds
          }
          userApi.updateUser(userDto).then((response) => {
            if (response.status === 200) {
              // 隐藏弹窗并弹窗通知提示
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '更新用户信息成功',
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
