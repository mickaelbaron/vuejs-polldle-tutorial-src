import Vue from 'vue'
import App from './App.vue'

require('./assets/polldle.css')

// Import Bootstrap library
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false

new Vue({
  render: h => h(App)
}).$mount('#app')
