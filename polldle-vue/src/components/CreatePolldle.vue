<script setup>
import { ref, reactive, computed, watch, onMounted, onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'

import CreatePolldleOption from './CreatePolldleOption.vue'

const errorMessage = ref('')
const buttonShown = ref(false)
const el = ref()

const polldle = reactive({
  question: '',
  polldleOptions: []
})

const newPolldleOptionText = ref('')

const router = useRouter()

watch(
  () => [...polldle.polldleOptions],
  () => {
    buttonShown.value = !(polldle.polldleOptions.length === 0)
  }
)

const listSize = computed(() => {
  return polldle.polldleOptions.length
})

onBeforeMount(() => {
  console.log('onBeforeMount:' + el.value)
})

onMounted(() => {
  console.log('onMounted:' + el.value)
})

function isCreatePolldleDisabled() {
  return polldle.polldleOptions.length < 2 || polldle.question === ''
}

function clearAllPolldleOptions() {
  polldle.polldleOptions = []
  errorMessage.value = ''
}

function addPolldleOption() {
  console.log('Root element:' + el.value.innerHTML)

  polldle.polldleOptions.push({
    text: newPolldleOptionText.value
  })
  newPolldleOptionText.value = ''
}

function removedPolldleOption(polldleOption) {
  let index = polldle.polldleOptions.indexOf(polldleOption)
  polldle.polldleOptions.splice(index, 1)
  errorMessage.value = ''
}

function createPolldle() {
  let polldleObject = {
    question: polldle.question,
    polldleOptions: []
  }

  polldle.polldleOptions.forEach((element) => {
    var newPollOptionElement = { name: element.text }
    if (element.text !== '') {
      polldleObject.polldleOptions.push(newPollOptionElement)
    }
  })

  let request = new Request(import.meta.env.VITE_APP_SERVER_URL + '/polldles', {
    method: 'POST',
    body: JSON.stringify(polldleObject),
    headers: {
      'Content-Type': 'application/json'
    }
  })

  fetch(request)
    .then((response) => {
      if (response.ok) {
        return response.json()
      } else {
        errorMessage.value = 'Problem to create a new Polldle.'
      }
    })
    .then((data) => {
      router.push({
        name: 'VotePolldle',
        params: { pathurl: data.pathUrl }
      })
    })
    .catch((error) => {
      console.error(error)

      errorMessage.value = 'Problem to create a new Polldle.'
    })
}
</script>

<template>
  <div ref="el" class="container">
    <!-- Titre + description -->
    <h1>PollDLE</h1>
    <h2>Voting done simply in real-time</h2>

    <!-- PollDLE name -->
    <div class="row">
      <div class="col">
        <input
          type="text"
          class="large-input mx-auto d-block"
          placeholder="Add your question here"
          v-model="polldle.question"
        />
      </div>
    </div>

    <h3>Add your PollDLE options</h3>

    <div class="row">
      <div class="col">
        <input
          type="text"
          placeholder="Polldle Option"
          class="large-input mx-auto d-block"
          v-model="newPolldleOptionText"
          @keypress.enter="addPolldleOption"
        />
      </div>
    </div>
    <div class="row" v-show="buttonShown">
      <div class="col">
        <button
          type="button"
          class="clear-button btn-lg btn-danger mx-auto d-block"
          @click="clearAllPolldleOptions"
        >
          Clear all PollDLE Options
        </button>
      </div>
    </div>

    <!-- PollDLE option -->
    <div
      class="row justify-content-center"
      v-for="currentPolldleOption in polldle.polldleOptions"
      :key="currentPolldleOption.text"
    >
      <CreatePolldleOption
        :polldle-option="currentPolldleOption"
        @removed-polldle-option="removedPolldleOption"
      />
    </div>

    <!-- Button Action -->
    <div class="row">
      <div class="col">
        <button
          type="button"
          class="validate-button btn-lg btn-primary mx-auto d-block"
          :disabled="isCreatePolldleDisabled()"
          @click="createPolldle"
        >
          Create PollDLE
        </button>
      </div>
    </div>

    <div class="alert alert-primary" role="alert">
      <h4 class="alert-heading">Summary of your PollDLE</h4>
      <hr />
      <p>
        The question is: <strong>{{ polldle.question }}</strong>
      </p>
      <p>Number of PollDLE options: {{ listSize }}</p>
    </div>

    <div
      v-show="errorMessage !== ''"
      class="error-message alert alert-danger"
      role="alert"
      v-text="errorMessage"
    ></div>
  </div>
</template>

<style>
.large-input {
  box-sizing: border-box;
  width: 500px;
  max-width: 80%;
  border-radius: 7px;
  border: 1px solid #bdc3c7;
  padding: 10px 20px;
  outline: none;
  text-align: center;
  line-height: 42px;
  font-size: 15px;
  margin: 20px;
  margin-top: 0;
  font-size: 20px;
}

.clear-button {
  margin-bottom: 25px;
}

.error-message {
  font-size: 125%;
  font-weight: bold;
}
</style>
