<template>
  <v-container fluid>
    <v-row align="center">
      <v-col class="d-flex" cols="12">
        <v-select
          label="언어를 선택해주세요"
          :items="items"
        ></v-select>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  export default {
    name: "Language",
    data: () => ({
      items: ['C', 'C++', 'Java', 'Python'],
    }),
  }
</script>