<template>
  <div class="container">
    <h1>{{this.$route.params.lang}}</h1>
    <b-table :items="data" :fields="fields" striped responsive="sm">
      <slot></slot>
      <template v-slot:cell(useractions)="row">
        <a size="sm" @click="userdetail(row.item.userNo)" class="mr-1">
          {{row.item.name}}
        </a>
      </template>
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
        {key: 'useractions', label:'질문자'},
        {key: 'actions', label: '상세 보기' }],
        sorting_type: 0,
        lang: this.$route.params.lang,
        keyword: this.$route.params.keyword
      }
    },
    methods: {
      getlist(lang, keyword) {
        axios.get(this.$store.state.base_url +'/question',{
          params:{
            lang: lang,
            type: this.sorting_type,
            keyword: keyword}
        })
        .then((response) => {
          console.log(response)
          this.data = response.data.data.list
        })
      },
      detail(queNo) {
        this.$router.push('/detail/'+queNo)
      },
      userdetail(userNo){
        this.$router.push('/profile/'+userNo)
      }
    },
    created() {
      this.getlist(this.$route.params.lang,this.$route.params.keyword)
    },
    beforeRouteUpdate (to, from, next){
        this.getlist(to.params.lang, to.params.keyword);
        next();
    }
  }
</script>