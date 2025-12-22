<script setup>
import { ref, reactive } from 'vue'
// Import useRouter and useRoute

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

// To retrieve PollDLE information from REST web service
onMounted(async () => {
  try {
    const { data } = await axios.get('http://127.0.0.1:9080/polldles/1')

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

  // To vote for a PollDLE from REST web service
  try {
    await axios.post(
      'http://127.0.0.1:9080/polldles/1/votes',
      {
        polldleOptionResponses: polldle.polldleOptionResponses
      }
    )

    console.log('Voted!')
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
