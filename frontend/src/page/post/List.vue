<template>
  <div>
    <b-table :items="data" :fields="fields" striped responsive="sm">
      <slot></slot>
      <template v-slot:cell(actions)="row">
        <b-button size="sm" @click="detail(row.item.queNo)" class="mr-1">
          상세보기
        </b-button>
      </template>
    </b-table>
  </div>
</template>

<script>
import axios from 'axios'
  export default {
    data(){
      return {
        data: null,
        fields: [{key:'rpCnt', label:'답글 수'},
        {key:'lang', label: '언어'},
        {key:'title', label: '제목'},
        {key: 'createDate', label: '작성 시간'},
        {key: 'name', label:'질문자'},
        {key: 'actions', label: '상세 보기' }]
      }
    },
    methods: {
      getlist() {
        axios.get(this.$store.state.base_url +'/question/question')
        .then((response) => {
          console.log(response)
          this.data = response.data.data.list
        })
      },
      detail(queNo) {
        this.$store.commit('checkqueNo',queNo)
        console.log(this.$store.state.queNo)
        this.$router.push('/detail')
      }
    },
    created() {
      this.getlist()
    }
  }
</script>