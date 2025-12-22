<script setup>
import { ref, reactive, onMounted } from 'vue'
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
const url = import.meta.env.VITE_APP_SERVER_URL + '/polldles/' + route.params.pathurl

// To retrieve PollDLE information from REST web service
onMounted(async () => {
  try {
    const { data } = await axios.get(url)

    polldle.question = data.question
    polldle.polldleOptions = data.polldleOptions
    state.value = stateResult.WAITING_VOTE
  } catch (error) {
    console.error(error)
    errorMessage.value = 'Polldle can not be loaded.'
    state.value = stateResult.ERROR
  }
})

async function vote() {
  if (!isWaitingVoteState()) {
    return
  }

  console.log(polldle.polldleOptionResponses)
  console.log(typeof polldle.polldleOptionResponses)

  // To vote for a PollDLE from REST web service
  try {
    const response = await axios.post(
      url + '/votes',
      {
        polldleOptionResponses: polldle.polldleOptionResponses
      },
      {
        headers: {
          'Content-Type': 'application/json'
        }
      }
    )

    console.log('Voted!')

    // Programmatic navigation to display ResultPolldle component
    router.push({
      name: 'ResultPolldle',
      params: { pathurl: route.params.pathurl }
    })

  } catch (error) {
    if (error.response?.status === 204) {
      state.value = stateResult.VOTE_ERROR
      errorMessage.value = 'Already voted!'
    } else {
      console.error(error)
      state.value = stateResult.VOTE_ERROR
      errorMessage.value = 'Problem to vote for this Polldle.'
    }
  }
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
        <div class="row justify-content-md-center" v-for="polldleOption in polldle.polldleOptions"
          :key="polldleOption.key">
          <div class="col-4">
            <div class="checkbox">
              <label>
                <input type="checkbox" v-model="polldle.polldleOptionResponses" :value="polldleOption.id.toString()" />
                {{ polldleOption.name }}
              </label>
            </div>
          </div>
        </div>
      </form>

      <div class="row">
        <div class="col">
          <button type="button" class="validate-button btn-lg btn-primary mx-auto d-block" @click="vote()">
            Vote
          </button>
        </div>
      </div>
    </div>

    <div v-show="isErrorState() || isVoteErrorState()" class="error-message alert alert-danger" role="alert">
      {{ errorMessage }}
    </div>
  </div>
</template>

<style></style>
