import { createRouter, createWebHistory } from 'vue-router'

import CreatePolldle from '../components/CreatePolldle.vue'
import VotePolldle from '../components/VotePolldle.vue'
import ResultPolldle from '../components/ResultPolldle.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'CreatePolldle',
      component: CreatePolldle
    },
    {
      path: '/:pathurl',
      name: 'VotePolldle',
      component: VotePolldle
    },
    {
      path: '/:pathurl/result',
      name: 'ResultPolldle',
      component: ResultPolldle
    }
  ]
})

export default router
