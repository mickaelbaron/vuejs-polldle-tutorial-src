import Vue from 'vue'
import App from './App.vue'
// Import routing configuration

require('./assets/polldle.css')

Vue.config.productionTip = false

new Vue({
  // Enable routing
  render: h => h(App)
}).$mount('#app')
