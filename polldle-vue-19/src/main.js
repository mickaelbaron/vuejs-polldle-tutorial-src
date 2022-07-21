import { createApp } from 'vue'
import App from './App.vue'
// Import routing configuration
import router from './router'
import './assets/polldle.css'

const app = createApp(App)

// Global Registration of CreatePolldleOption component
//import CreatePolldleOption from './components/CreatePolldleOption.vue'
//app.component('CreatePolldleOption', CreatePolldleOption)

// Enable routing
app.use(router)

app.mount('#app')
