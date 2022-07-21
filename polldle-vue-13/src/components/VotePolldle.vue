<script setup>
import { ref, reactive } from 'vue'

// Import AXIOS JavaScript library

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

// To retrieve PollDLE information from REST web service

function vote() {
  if (!isWaitingVoteState()) {
    return
  }

  // To vote for a PollDLE from REST web service
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
