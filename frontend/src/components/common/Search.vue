<template>
  <div class="row">
    <div  class="search-bar mx-auto mb-4">
      <input @keypress.enter="onInput" ref="searchInput" type="text">
      <button @click="onInput">검색</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Search',
  methods: {
    onInput() {
      const searchInputValue = this.$refs.searchInput.value
      if (searchInputValue) {
        this.$emit('input-change', searchInputValue)
      }
    }
  }
}
</script>

<style scoped>
  div.search-bar {
    text-align: center;
    margin: 20px;
  }
  div.search-bar > input {
    width: 50vw;
    max-width: 800px;
    padding-left: 15px;
  }
</style>