<template>
  <div class="container">
    <div v-if="!isErrorState()">
      <h1>{{ question }}</h1>

      <form>
        <div
          class="row justify-content-md-center"
          v-for="polldleOption in polldleOptions"
          :key="polldleOption.key"
        >
          <div class="col-4">
            <div class="radio">
              <label>
                <input
                  type="radio"
                  v-model="polldleOptionResponses"
                  :value="polldleOption.id.toString()"
                >
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

<script>
// Add dependency to AXIO JavaScript library

const stateResult = {
  WAITING_VOTE: "waiting_vote",
  VOTE_ERROR: "vote_error",
  ERROR: "error"
};

export default {
  name: "VotePolldle",
  data() {
    return {
      state: null,
      errorMessage: "",
      question: "",
      polldleOptions: [],
      polldleOptionResponses: null
    };
  },
  created() {
    // To retrieve PollDLE information from REST web service
  },
  methods: {
    vote() {
      // Prepare the data.
      var polldleVote = {
        pathUrl: this.$route.params.pathurl,
        polldleOptionResponses: [this.polldleOptionResponses]
      };

      // To vote for a PollDLE from REST web service
    },
    isWaitingVoteState() {
      return this.state === stateResult.WAITING_VOTE;
    },
    isVoteErrorState() {
      return this.state === stateResult.VOTE_ERROR;
    },
    isErrorState() {
      return this.state === stateResult.ERROR;
    }
  }
};
</script>

<style>
</style>
