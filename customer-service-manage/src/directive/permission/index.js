import Vue from 'vue'
import store from '@/store'

// 权限按钮检查方法
Vue.prototype.$_has = function(value) {
  // 当前用户所有的按钮列表
  const dynamicButtons = store.getters.buttons
  if (dynamicButtons === undefined || dynamicButtons === null || dynamicButtons.length < 1) {
    return false
  }
  dynamicButtons.forEach(button => {
    if (button.resources === value) {
      return true
    }
  })
  return false
}

/**
 * 判断权限的指令
 * 用法: <el-button v-has="'perm:new'" class="btns">添加</el-button>
 */
Vue.directive('has', {
  bind: function(el, binding) {
    if (!Vue.prototype.$_has(binding.value)) {
      el.parentNode.removeChild(el)
    }
  }
})
