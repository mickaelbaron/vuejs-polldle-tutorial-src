<template>
  <div class="container">
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
          v-model="question"
        >
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
          v-model="newPolldleOptionText"
          class="large-input mx-auto d-block"
          @keypress.enter="addPolldleOption"
        >
      </div>
    </div>
    <!-- Directive v-show with buttonShown -->
    <div
      class="row"
      v-show="buttonShown"
    >
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
    <div
      class="row justify-content-center"
      v-for="currentPolldleOption in polldleOptions"
      :key="currentPolldleOption.text"
    >
      <!-- Instance CreatePolldleOption component -->
      <!-- Send object value for currentPolldleOption variable -->
      <CreatePolldleOption :polldle-option="currentPolldleOption" />
    </div>

    <!-- Button Action -->
    <div class="row">
      <div class="col">
        <!-- Directive v-bind with isCreatePolldleDisabled() -->
        <!-- Directive v-on with createPolldle -->
        <button
          type="button"
          class="validate-button btn-lg btn-primary mx-auto d-block"
          @click="createPolldle"
          :disabled="isCreatePolldleDisabled()"
        >
          Create PollDLE
        </button>
      </div>
    </div>

    <div
      class="alert alert-primary"
      role="alert"
    >
      <h4 class="alert-heading">
        Summary of your PollDLE
      </h4>
      <hr>
      <p>
        The question is:
        <strong>
          <!-- Mustache with question -->
          <strong>{{ question }}</strong>
        </strong>
      </p>
      <!-- Mustache with computed property: listSize -->
      <p>Number of PollDLE options: {{ listSize }}</p>
    </div>

    <!-- Directive v-show with errorMessage -->
    <!-- Directive v-text with errorMessage -->
    <div
      v-show="errorMessage !== ''"
      class="error-message alert alert-danger"
      role="alert"
      v-text="errorMessage"
    />
  </div>
</template>

<script>
// Import CreatePolldleOption component
import CreatePolldleOption from "@/components/CreatePolldleOption.vue";

export default {
  name: "CreatePolldle",
  // Add dependencies on CreatePolldleOption component
  components: { CreatePolldleOption },
  data() {
    return {
      question: "",
      newPolldleOptionText: "",
      polldleOptions: [],
      errorMessage: "",
      buttonShown: false
    };
  },
  // Watcher on polldleOptions
  watch: {
    polldleOptions() {
      this.buttonShown =
        this.polldleOptions != null && !(this.polldleOptions.length === 0);
    }
  },
  // Computed property listSize when polldleOptions changes
  computed: {
    listSize() {
      return this.polldleOptions.length;
    }
  },
  // Use mounted hook to log the text content of the DOM
  mounted() {
    // eslint-disable-next-line no-console
    console.log(this.$el.textContent);
  },
  methods: {
    removedPolldleOption(polldleOption) {
      let index = this.polldleOptions.indexOf(polldleOption);
      this.polldleOptions.splice(index, 1);
      this.errorMessage = "";
    },

    addPolldleOption() {
      this.polldleOptions.push({
        text: this.newPolldleOptionText
      });
      this.newPolldleOptionText = "";
    },

    clearAllPolldleOptions() {
      this.polldleOptions = [];
      this.errorMessage = "";
    },

    createPolldle() {
      var polldleObject = {
        question: this.question,
        polldleOptions: []
      };

      this.polldleOptions.forEach(element => {
        var newPollOptionElement = { name: element.text };
        if (element.text !== "") {
          polldleObject.polldleOptions.push(newPollOptionElement);
        }
      });

      // Call REST web service with fetch API
      var request = new Request("http://127.0.0.1:9991" + "/polldles", {
        method: "POST",
        body: JSON.stringify(polldleObject),
        headers: {
          'Content-Type': 'application/json'
        }
      })

      fetch(request).then(response => {
        if (response.ok) {
          return response.json();
        } else {
          this.errorMessage = "Problem to create a new Polldle.";
        }
      }).then(data => {
        console.log(data.pathUrl);
        // Programmatic navigation to display VotePolldle component
      }).catch((error) => {
        this.errorMessage = "Problem to create a new Polldle.";
        console.error(error);
      });
    },

    isCreatePolldleDisabled() {
      return (
        this.polldleOptions === null ||
        this.polldleOptions.length < 2 ||
        this.question === ""
      );
    }
  }
};
</script>

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