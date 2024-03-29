import { createApp } from 'vue'
import App from './App.vue'

import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"

// Import routing configuration
import './assets/polldle.css'

const app = createApp(App)

// Global Registration of CreatePolldleOption component
//import CreatePolldleOption from './components/CreatePolldleOption.vue'
//app.component('CreatePolldleOption', CreatePolldleOption)

// Enable routing

app.mount('#app')
