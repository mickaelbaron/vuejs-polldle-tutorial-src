import Vue from 'vue'
import App from './App.vue'
// Import routing configuration
import router from './router'

require('./assets/polldle.css')

Vue.config.productionTip = false

new Vue({
  // Enable routing
  router,
  render: h => h(App)
}).$mount('#app')
