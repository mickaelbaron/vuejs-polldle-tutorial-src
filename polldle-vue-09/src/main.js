import { createApp } from 'vue'
import App from './App.vue'

import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"

import './assets/polldle.css'

const app = createApp(App)

// Global Registration of CreatePolldleOption component
//import CreatePolldleOption from './components/CreatePolldleOption.vue'
//app.component('CreatePolldleOption', CreatePolldleOption)

app.mount('#app')
