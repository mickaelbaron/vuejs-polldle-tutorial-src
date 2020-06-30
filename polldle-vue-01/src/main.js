import Vue from 'vue'
import App from './App.vue'

require('./assets/polldle.css')

// Import Bootstrap library

Vue.config.productionTip = false

new Vue({
  render: h => h(App)
}).$mount('#app')
