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
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-sort" @click="handleExpand">
        展开/折叠
      </el-button>
    </div>

    <!-- 表格 -->
    <el-table
      ref="table"
      v-loading="listLoading"
      :data="list"
      style="width: 100%; margin-bottom: 20px;"
      row-key="id"
      fit
      :tree-props="{children: 'children'}"
    >
      <el-table-column label="名称" min-width="160px">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="图标" min-width="160px">
        <template slot-scope="{row}">
          <svg-icon :icon-class="row.icon" />
        </template>
      </el-table-column>
      <el-table-column label="路径" min-width="160px">
        <template slot-scope="{row}">
          <span v-if="row.parentId === 0">/{{ row.path }}</span>
          <span v-else>{{ row.path }}</span>
        </template>
      </el-table-column>
      <el-table-column label="路由组件" min-width="200px">
        <template slot-scope="{row}">
          <span>{{ row.component }}</span>
        </template>
      </el-table-column>
      <el-table-column label="权限标识" min-width="180px">
        <template slot-scope="{row}">
          <span>{{ row.resources }}</span>
        </template>
      </el-table-column>
      <el-table-column label="类型" min-width="160px">
        <template slot-scope="{row}">
          <el-tag :type="row.type === 'menu' ? 'primary' : 'success'">
            {{ row.type === 'menu' ? '菜单' : '按钮' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="排序" min-width="160px">
        <template slot-scope="{row}">
          <span>{{ row.sort }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="220px" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button size="mini" type="primary" @click="handleCreate(row)">
            新增
          </el-button>
          <el-button size="mini" type="warning" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 编辑框 -->
    <el-dialog :title="dialogTitleMap[dialogStatus]" :visible.sync="dialogFormVisible" width="600px">
      <el-form ref="dataForm" :rules="rules" :model="permissionInfo" :inline="true" label-width="80px">
        <el-form-item label="类型" prop="type">
          <el-radio v-model="permissionInfo.type" label="menu">菜单</el-radio>
          <el-radio v-if="permissionInfo.parentId !== 0" v-model="permissionInfo.type" label="button">按钮</el-radio>
        </el-form-item>
        <el-form-item label="上级类目" prop="parentId">
          <treeselect v-model="permissionInfo.parentId" :clearable="false" :options="menus" placeholder="选择上级类目" style="width: 460px;" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="permissionInfo.name" placeholder="请输入名称" style="width: 460px;" />
        </el-form-item>
        <div v-if="permissionInfo.type === 'menu'">
          <el-form-item label="图标" prop="icon">
            <el-popover placement="bottom-start" width="460" trigger="click" @show="$refs.iconSelect.reset()">
              <el-input slot="reference" v-model="permissionInfo.icon" style="width: 460px; " placeholder="点击选择图标" readonly>
                <svg-icon v-if="permissionInfo.icon" slot="prefix" :icon-class="permissionInfo.icon" class="el-input__icon" style="height: 40px;width: 16px;" />
                <i v-else slot="prefix" class="el-icon-search el-input__icon" />
              </el-input>
              <IconSelect ref="iconSelect" @selected="selected" />
            </el-popover>
          </el-form-item>
          <el-form-item label="排序" prop="sort">
            <el-input-number v-model="permissionInfo.sort" placeholder="请输入排序" :min="1" :max="999" controls-position="right" style="width: 460px;" />
          </el-form-item>
          <el-form-item label="路径" prop="path">
            <el-input v-model="permissionInfo.path" placeholder="请输入路径" style="width: 460px;" />
          </el-form-item>
          <el-form-item label="路由组件" prop="component">
            <el-input v-model="permissionInfo.component" placeholder="请输入路由组件" style="width: 460px;" />
          </el-form-item>
        </div>
        <div v-if="permissionInfo.parentId !== 0">
          <el-form-item label="权限标识" prop="resources">
            <el-input v-model="permissionInfo.resources" placeholder="请输入权限标识" style="width: 460px;" />
          </el-form-item>
        </div>
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
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import Treeselect from '@riophae/vue-treeselect'
import IconSelect from '@/components/IconSelect'
import waves from '@/directive/waves'
import permissionApi from '@/api/permission'

export default {
  components: { IconSelect, Treeselect },
  directives: { waves },
  data() {
    return {
      list: [], // 列表数据
      listLoading: true, // 是否正在加载
      listQuery: {
        name: undefined
      }, // 列表查询条件
      menus: [], // 所有菜单
      permissionInfo: {}, // 当前编辑的权限信息
      dialogFormVisible: false, // 是否显示弹窗
      dialogStatus: '', // 弹窗的状态 create/update
      dialogTitleMap: {
        update: '编辑权限',
        create: '创建权限'
      }, // 弹窗标题
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'change' }
        ]
      }, // 用户编辑表单的验证规则
      expand: false
    }
  },
  computed: {
    permissionInfoParentId() {
      return this.permissionInfo.parentId
    }
  },
  watch: {
    // 监听父类目变化
    permissionInfoParentId(val) {
      if (val === 0) {
        this.permissionInfo.type = 'menu'
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 根据列表查询条件查询表格数据
    getList() {
      this.listLoading = true
      permissionApi.getAllPermission(this.listQuery).then((response) => {
        this.listLoading = false
        this.list = response.data
        // 每次更新列表，就更新一次菜单
        this.getMenus()
      })
    },
    // 获取树结构的菜单
    getMenus() {
      permissionApi.getAllMenus().then((response) => {
        this.menus = response.data
        this.recursionMenus(this.menus)
        const menu = { id: 0, label: '顶级类目', children: [] }
        menu.children = this.menus
        this.menus = []
        this.menus.push(menu)
      })
    },
    // 递归增加label属性
    recursionMenus(menus) {
      menus.map((item) => {
        item.label = item.name
        this.recursionMenus(item.children)
        if (item.children.length === 0) {
          delete (item.children)
        }
      })
    },
    // 处理展开/折叠
    handleExpand() {
      this.expand = !this.expand
      this.recursionList(this.list)
    },
    // 递归切换展开/折叠状态
    recursionList(list) {
      list.map((item) => {
        if (item.children.length > 0) {
          this.$refs.table.toggleRowExpansion(item, this.expand)
          this.recursionList(item.children)
        }
      })
    },
    // 处理搜索过滤
    handleFilter() {
      this.getList()
    },
    // 处理创建
    handleCreate(row) {
      if (row) {
        // 点击table里的新增，则根据当前行的类型，设置不同的父类目
        if (row.type === 'button') {
          this.permissionInfo = { parentId: row.parentId, type: 'menu', sort: 1, icon: '' }
        } else {
          this.permissionInfo = { parentId: row.id, type: 'menu', sort: 1, icon: '' }
        }
      } else {
        // 点击顶部新增，则默认父类目为最顶级
        this.permissionInfo = { parentId: 0, type: 'menu', sort: 1, icon: '' }
      }
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs.dataForm.clearValidate()
      })
    },
    // 处理更新
    handleUpdate(row) {
      this.permissionInfo = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs.dataForm.clearValidate()
      })
    },
    // 处理删除
    handleDelete(row) {
      MessageBox.confirm(`确定删除【${row.name}】吗？删除后无法恢复，请谨慎操作！`, '温馨提示', {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteData(row.id)
      })
    },
    // 删除权限
    deleteData(permissionId) {
      permissionApi.deletePermission(permissionId).then((response) => {
        if (response.status === 200) {
          // 隐藏弹窗并弹窗通知提示
          this.dialogFormVisible = false
          this.$notify({
            title: '成功',
            message: '删除权限成功',
            type: 'success',
            duration: 2000
          })
          // 重新加载数据
          this.getList()
        }
      })
    },
    // 创建权限
    createData() {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          permissionApi.addPermission(this.permissionInfo).then((response) => {
            if (response.status === 200) {
              // 隐藏弹窗并弹窗通知提示
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '创建权限成功',
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
    // 更新权限
    updateData() {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          permissionApi.updatePermission(this.permissionInfo).then((response) => {
            if (response.status === 200) {
              // 隐藏弹窗并弹窗通知提示
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '更新权限信息成功',
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
    // 选择图标
    selected(name) {
      this.permissionInfo.icon = name
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  /deep/ .el-input-number .el-input__inner {
    text-align: left;
  }
</style>

<style>
.vue-treeselect__single-value {
    line-height: 40px;
  }
</style>

