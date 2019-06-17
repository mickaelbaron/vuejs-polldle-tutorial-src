import Vue from 'vue'
import Router from 'vue-router'

import CreatePolldle from '@/components/CreatePolldle'
import VotePolldle from '@/components/VotePolldle'
import ResultPolldle from '@/components/ResultPolldle'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.VUE_APP_SUBDOMAIN_PATH,
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

