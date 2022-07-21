<script setup>
import { ref, reactive } from 'vue'
// Import useRouter and useRoute
import { useRouter, useRoute } from 'vue-router'

// Import AXIOS JavaScript library
import axios from 'axios';

const stateResult = {
  WAITING_VOTE: 'waiting_vote',
  VOTE_ERROR: 'vote_error',
  ERROR: 'error'
}

const polldle = reactive({
  question: '',
  polldleOptions: [],
  polldleOptionResponses: []
})

const state = ref(null)
const errorMessage = ref('')

// Declare useRouter and userRoute objects
const router = useRouter()
const route = useRoute()

// Use environment variable to define REST web service URL
const url =
  import.meta.env.VITE_APP_SERVER_URL + '/polldles/' + route.params.pathurl

// To retrieve PollDLE information from REST web service
axios
  .get(url)
  .then((response) => {
    if (response.status === 200) {
      polldle.question = response.data.question
      polldle.polldleOptions = response.data.polldleOptions

      state.value = stateResult.WAITING_VOTE
    } else {
      errorMessage.value = 'Polldle can not be loaded.'
      state.value = stateResult.ERROR
    }
  })
  .catch((error) => {
    console.error(error)

    errorMessage.value = 'Polldle can not be loaded.'
    state.value = stateResult.ERROR
  })

function vote() {
  if (!isWaitingVoteState()) {
    return
  }

  // To vote for a PollDLE from REST web service
  axios({
    method: 'post',
    baseURL: url + '/votes',
    data: JSON.stringify({
      polldleOptionResponses: [polldle.polldleOptionResponses]
    }),
    headers: {
      'Content-Type': 'application/json'
    }
  })
    .then((response) => {
      if (response.status === 200) {
        console.log('Voted!')

        // Programmatic navigation to display ResultPolldle component
        router.push({
          name: 'ResultPolldle',
          params: { pathurl: route.params.pathurl }
        })
      } else if (response.status === 204) {
        state.value = stateResult.VOTE_ERROR
        errorMessage.value = 'Already voted!'
      }
    })
    .catch((error) => {
      console.error(error)

      state.value = stateResult.VOTE_ERROR
      errorMessage.value = 'Problem to vote for this Polldle.'
    })
}

function isErrorState() {
  return state.value === stateResult.ERROR
}

function isWaitingVoteState() {
  return state.value === stateResult.WAITING_VOTE
}

function isVoteErrorState() {
  return state.value === stateResult.VOTE_ERROR
}
</script>

<template>
  <div class="container">
    <div v-if="!isErrorState()">
      <h1>{{ polldle.question }}</h1>

      <form>
        <div
          class="row justify-content-md-center"
          v-for="polldleOption in polldle.polldleOptions"
          :key="polldleOption.key"
        >
          <div class="col-4">
            <div class="radio">
              <label>
                <input
                  type="radio"
                  v-model="polldle.polldleOptionResponses"
                  :value="polldleOption.id.toString()"
                />
                {{ polldleOption.name }}
              </label>
            </div>
          </div>
        </div>
      </form>

      <div class="row">
        <div class="col">
          <button
            type="button"
            class="validate-button btn-lg btn-primary mx-auto d-block"
            @click="vote()"
          >
            Vote
          </button>
        </div>
      </div>
    </div>

    <div
      v-show="isErrorState() || isVoteErrorState()"
      class="error-message alert alert-danger"
      role="alert"
    >
      {{ errorMessage }}
    </div>
  </div>
</template>

<style></style>
