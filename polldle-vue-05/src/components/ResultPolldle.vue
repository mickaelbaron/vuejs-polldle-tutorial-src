<script setup>
import { ref } from 'vue'

const stateResult = {
  RESULT: 'result',
  EMPTY: 'empty',
  ERROR: 'error'
}

const chartOptions = {
  chart: {
    plotBackgroundColor: null,
    plotBorderWidth: null,
    plotShadow: false,
    type: 'pie',
    animation: false
  },
  accessibility: {
    enabled: false
  },
  title: {
    text: ' '
  },
  plotOptions: {
    series: {
      animation: false
    }
  },
  tooltip: {
    borderWidth: 1,
    shadow: true
  },
  credits: {
    enabled: false
  },
  series: [
    {
      name: 'Vote',
      colorByPoint: true,
      data: []
    }
  ],
  loading: false
}

const question = ref('')
const state = ref(null)
const errorMessage = ref('')
const options = ref(chartOptions)

function capitalizeFirstLetter(string) {
  return string.charAt(0).toUpperCase() + string.slice(1)
}

function isResultState() {
  return state.value === stateResult.RESULT
}

function isErrorState() {
  return state.value === stateResult.ERROR
}

function isEmptyState() {
  return state.value === stateResult.EMPTY
}
</script>

<template>
  <div class="container">
    <!-- Directive v-if with isResultState() -->
    <div v-if="isResultState()">
      <!-- Mustache with question -->
      <h1>{{ question }}</h1>
      <div class="row">
        <div class="col-8"></div>
        <div class="col-4">
          <!-- Directive v-for with data -->
          <div
            v-for="polldleOption in options.series[0].data"
            :key="polldleOption.name"
          >
            {{ polldleOption.name }}: {{ polldleOption.y }}
          </div>
        </div>
      </div>
    </div>
    <!-- Directive v-else-if with isEmptyState() -->
    <div v-else-if="isEmptyState()">
      <h2>
        No vote at this moment, keep in touch.<br />Results update in real-time.
      </h2>
    </div>
    <!-- Directive v-else-if with isErrorState() -->
    <!-- Mustache with errorMessage -->
    <div 
      v-else-if="isErrorState()"
      class="error-message alert alert-danger" 
      role="alert"
    >
      {{ errorMessage }}
    </div>
  </div>
</template>

<style></style>
