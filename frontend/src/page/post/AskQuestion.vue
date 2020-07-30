<template>
  <div class="container">
      <v-divider></v-divider>
    <v-form>
      <Language/>
      <v-text-field
        label="제목을 입력해주세요"
        single-line
        full-width
        hide-details
      ></v-text-field>
      <v-divider></v-divider>
      <v-textarea
        v-model="title"
        label="내용을 입력해주세요"
        counter
        maxlength="120"
        full-width
        single-line
      ></v-textarea>
      <v-card-actions>
        <v-btn type="reset">Cancel</v-btn>
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
        <v-btn  text @click="submit">Submit</v-btn>
      </v-card-actions>
    </v-form>
  </div>
</template>

<script>
  import Language from "../../components/Language.vue"

  export default {
    components: {
      Language
    },
    data () {
      return {
        selected: ['Python'],
        items: ['C', 'C++', 'Java', 'Python'],
        title: '',
      }
    },
    computed: {
      progress () {
        return Math.min(100, this.value.length * 10)
      },
      color () {
        return ['error', 'warning', 'success'][Math.floor(this.progress / 40)]
      },
      method: {
       
      }
    },
  }
</script>