><template>
  <div>
    <img src="./assets/img/logo.jpg" alt="main image">
    <Search @input-change="onInputChage"/>
  </div>
</template>

<script>
import Search from '../src/components/common/Search.vue'

export default {
    name: 'main'
}
</script>

<style>

</style>