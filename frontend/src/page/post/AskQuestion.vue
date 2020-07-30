<template>
  <div class="container">
    <v-form>
      <v-autocomplete
        v-model="selected"
        :items="['Trevor Handsen', 'Alex Nelson']"
        chips
        label="언어를 선택해주세요"
        full-width
        hide-details
        hide-no-data
        hide-selected
        multiple
        single-line
      ></v-autocomplete>
      <v-divider></v-divider>
      <v-text-field
        v-model="inputTitle"
        label="제목을 입력해주세요"
        single-line
        maxlength="200"
        full-width
        hide-details
      ></v-text-field>
      <v-divider></v-divider>
      <v-textarea
        v-model="inputContext"
        label="내용을 입력해주세요"
        counter
        maxlength="2000"
        full-width
        single-line
      ></v-textarea>
      <v-card-actions>
        <v-btn text>Cancel</v-btn>
        <v-spacer></v-spacer>
        <v-slide-x-reverse-transition>
          <v-tooltip
            v-if="formHasErrors"
            left
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                icon
                class="my-0"
                v-bind="attrs"
                @click="resetForm"
                v-on="on"
              >
                <v-icon>mdi-refresh</v-icon>
              </v-btn>
            </template>
            <span>Refresh form</span>
          </v-tooltip>
        </v-slide-x-reverse-transition>
        <v-btn color="primary" text @click="submit" v-model="submit">Submit</v-btn>
      </v-card-actions>
    </v-form>
  </div>
</template>

<script>
  export default {
    name: 'AskQuestion',
    data () {
      return {
        selected: ['Python'],
        items: ['C++', 'Java', 'Python'],
        inputTitle: '',
        inputContext: '',
      }
    },
    methods: {
      submit() {
        this.$router.push({ name: 'Detail', query: { title: this.inputTitle, context: this.inputContext} })
      }
    },
    computed: {
      progress () {
        return Math.min(100, this.value.length * 10)
      },
      color () {
        return ['error', 'warning', 'success'][Math.floor(this.progress / 40)]
      },
    },
  }
</script>