<script setup>
import { ref, reactive, computed, watch, onMounted, onBeforeMount } from 'vue'
// Import useRouter
import { useRouter } from 'vue-router'

// Import CreatePolldleOption component
import CreatePolldleOption from "./CreatePolldleOption.vue";

const errorMessage = ref('')
const buttonShown = ref(false)
// Declare attribute ref
const el = ref()

const polldle = reactive({
  question: '',
  polldleOptions: []
})
const newPolldleOptionText = ref('')

// Computed property listSize when polldle.polldleOptions changes
const listSize = computed(() => {
  return polldle.polldleOptions.length
})

// Declare useRouter object
const router = useRouter()

// Watcher on polldle.polldleOptions
watch(
  () => [...polldle.polldleOptions],
  () => {
    buttonShown.value = !(polldle.polldleOptions.length === 0)
  }
)

// Use onBeforeMount and onMounted hooks to log the text content of the DOM
onBeforeMount(() => {
  console.log('onBeforeMount:' + el.value)
})

onMounted(() => {
  console.log('onMounted:' + el.value.innerHTML)
})

function isCreatePolldleDisabled() {
  return polldle.polldleOptions.length < 2 || polldle.question === ''
}

function clearAllPolldleOptions() {
  polldle.polldleOptions = []
  errorMessage.value = ''
}

function addPolldleOption() {
  // Read attribute ref value
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

  // Call REST web service with fetch API
  // Use environment variable to define REST web service URL
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
      console.log(data.pathUrl)
      // Programmatic navigation to display VotePolldle component
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
  <!-- Declaring ref attribute -->
  <div ref="el" class="container">
    <!-- Titre + description -->
    <h1>PollDLE</h1>
    <h2>Voting done simply in real-time</h2>

    <!-- PollDLE name -->
    <div class="row">
      <div class="col">
        <!-- Directive v-model with question -->
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
        <!-- Directive v-model with newPolldleOptionText -->
        <!-- Directive v-on with addPolldleOption -->
        <input
          type="text"
          placeholder="Polldle Option"
          class="large-input mx-auto d-block"
          v-model="newPolldleOptionText"
          @keypress.enter="addPolldleOption"
        />
      </div>
    </div>
    <!-- Directive v-show with buttonShown -->
    <div class="row" v-show="buttonShown">
      <div class="col">
        <!-- Directive v-on with clearAllPolldleOptions -->
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
    <!-- Directive v-for with polldleOptions -->
    <div class="row justify-content-center"
      v-for="currentPolldleOption in polldle.polldleOptions"
      :key="currentPolldleOption.text"
      >
        <!-- Instance CreatePolldleOption component -->
        <!-- Send object value for polldleOption property -->
        <!-- Listening the removed-polldle-option event -->
        <CreatePolldleOption :polldleOption="currentPolldleOption" @removed-polldle-option="removedPolldleOption"/>
    </div>

    <!-- Button Action -->
    <!-- Directive v-bind with isCreatePolldleDisabled() -->
    <div class="row">
      <div class="col">
        <!-- Directive v-on with createPolldle -->
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
      <!-- Mustache with computed property: listSize -->
      <p>Number of PollDLE options: {{ listSize }}</p>
    </div>

    <!-- Directive v-text with errorMessage -->
    <!-- Directive v-show with errorMessage -->
    <div
      class="error-message alert alert-danger"
      role="alert"
      v-text="errorMessage"
      v-show="errorMessage !== ''"
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
