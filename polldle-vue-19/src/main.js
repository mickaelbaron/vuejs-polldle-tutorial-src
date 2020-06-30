import Vue from 'vue'
import App from './App.vue'
// Import routing configuration
import router from './router'

require('./assets/polldle.css')

// Import Bootstrap library
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false

new Vue({
  // Enable routing
  router,
  render: h => h(App)
}).$mount('#app')
