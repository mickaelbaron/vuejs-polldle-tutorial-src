<script setup>
import { ref, reactive } from 'vue'

const errorMessage = ref('Problem to create a new Polldle')
const buttonShown = ref(false)
const polldle = reactive({
  question: 'Aimez-vous les frites ?',
  polldleOptions: []
})
const newPolldleOptionText = ref('')

function isCreatePolldleDisabled() {
  return polldle.polldleOptions.length < 2 || polldle.question === ''
}

function clearAllPolldleOptions() {
  polldle.polldleOptions = []
  errorMessage.value = ''
}

function addPolldleOption() {
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
        <!-- Directive v-model with question -->
        <input
          type="text"
          class="large-input mx-auto d-block"
          placeholder="Add your question here"
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
        />
      </div>
    </div>
    <div class="row" v-show="buttonShown">
      <div class="col">
        <!-- Directive v-on with clearAllPolldleOptions -->
        <button
          type="button"
          class="clear-button btn-lg btn-danger mx-auto d-block"
        >
          Clear all PollDLE Options
        </button>
      </div>
    </div>

    <!-- PollDLE option -->
    <div class="row justify-content-center"></div>

    <!-- Button Action -->
    <!-- Directive v-bind with isCreatePolldleDisabled() -->
    <div class="row">
      <div class="col">
        <!-- Directive v-on with createPolldle -->
        <button
          type="button"
          class="validate-button btn-lg btn-primary mx-auto d-block"
        >
          Create PollDLE
        </button>
      </div>
    </div>

    <div class="alert alert-primary" role="alert">
      <h4 class="alert-heading">Summary of your PollDLE</h4>
      <hr />
      <p>
        The question is: <strong><!-- Mustache with question --></strong>
      </p>
      <p>Number of PollDLE options: TODO</p>
    </div>

    <!-- Directive v-text with errorMessage -->
    <div class="error-message alert alert-danger" role="alert"></div>
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
