<template>
    <v-container fluid>
 
      <v-col class="d-flex" cols="12" sm="6">
        <v-select
          :items="items"
          label="Standard"
          dense
        ></v-select>
      </v-col>
  </v-container>
 
</template>

<script>
  export default {
    name: "Language",
  data: () => ({
      items: ['Foo', 'Bar', 'Fizz', 'Buzz'],
    }),
 
  
  }
</script>
<style scoped>

</style>