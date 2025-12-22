import { createApp } from 'vue'
import App from './App.vue'

// Dependency to the Bootstrap CSS file
import "bootstrap/dist/css/bootstrap.min.css"
// Dependency on the Bootstrap JavaScript file (for dynamic behavior)
import "bootstrap"

import './assets/polldle.css'

const app = createApp(App)

// Global Registration of CreatePolldleOption component
//import CreatePolldleOption from './components/CreatePolldleOption.vue'
//app.component('CreatePolldleOption', CreatePolldleOption)

app.mount('#app')
