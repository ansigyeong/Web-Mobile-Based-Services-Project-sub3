<template>
  <div class="container">
      <h2>CPP</h2>
        <b-table :items="cpp" :fields="fields" striped responsive="sm">
        <slot></slot>
        <template v-slot:cell(actions)="row">
            <b-button size="sm" @click="detail(row.item.queNo)" class="mr-1">
            상세보기
            </b-button>
        </template>
        <template v-slot:cell(deletebtn)="row">
            <b-button size="sm" @click="selectdelete(row.item.queNo,0)" class="mr-1">
            찜 삭제
            </b-button>
        </template>
        </b-table>
      <h2>C</h2>
        <b-table :items="c" :fields="fields" striped responsive="sm">
        <slot></slot>
        <template v-slot:cell(actions)="row">
            <b-button size="sm" @click="detail(row.item.queNo)" class="mr-1">
            상세보기
            </b-button>
        </template>
        <template v-slot:cell(deletebtn)="row">
            <b-button size="sm" @click="selectdelete(row.item.queNo,1)" class="mr-1">
            찜 삭제
            </b-button>
        </template>
        </b-table>
      <h2>Java</h2>
        <b-table :items="java" :fields="fields" striped responsive="sm">
        <slot></slot>
        <template v-slot:cell(actions)="row">
            <b-button size="sm" @click="detail(row.item.queNo)" class="mr-1">
            상세보기
            </b-button>
        </template>
        <template v-slot:cell(deletebtn)="row">
            <b-button size="sm" @click="selectdelete(row.item.queNo,2)" class="mr-1">
            찜 삭제
            </b-button>
        </template>
        </b-table>
      <h2>Python</h2>
        <b-table :items="python" :fields="fields" striped responsive="sm">
        <slot></slot>
        <template v-slot:cell(actions)="row">
            <b-button size="sm" @click="detail(row.item.queNo)" class="mr-1">
            상세보기
            </b-button>
        </template>
        <template v-slot:cell(deletebtn)="row">
            <b-button size="sm" @click="selectdelete(row.item.queNo,3)" class="mr-1">
            찜 삭제
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
          cpp : null,
          python : null,
          c : null,
          java : null,
            fields: [{key:'rpCnt', label:'답글 수'},
            {key:'title', label: '제목'},
            {key: 'createDate', label: '작성 시간'},
            {key: 'actions', label: '상세 보기' },
            {key: 'deletebtn', label:'찜 삭제'}]
      }
    },
    methods: {
      getlist() {
        axios.get(this.$store.state.base_url +'/cart/list',{     
            params: null, 
            headers : {
                'ACCESS-TOKEN' : this.$store.state.token
            }   
        })
        .then((response) => {
          console.log(response)
          this.cpp = response.data.data.cpp;
          this.c = response.data.data.c;
          this.java = response.data.data.java;
          this.python = response.data.data.python;
          console.log(this.python)
        })
        .catch((error) => {
            alert('세션 만료.\n다시 로그인 해주세요.')
            this.$cookies.remove('auth-token')
            this.$store.commit('checkToken',this.$cookies.get('auth-token'))
            this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
            this.$router.push('/login')
        })
      },
      detail(queNo) {
        this.$router.push('/detail/'+queNo)
      },
      selectdelete(queNo,num){
        axios.get(this.$store.state.base_url +'/cart/delete',{     
            params: {
                queNo: queNo
            }, 
            headers : {
                'ACCESS-TOKEN' : this.$store.state.token
            }   
        })
        .then((response) => {
            axios.get(this.$store.state.base_url +'/cart/list',{     
                params: null, 
                headers : {
                    'ACCESS-TOKEN' : this.$store.state.token
                }   
            })
            .then((response) => {
                if (num == 0) { this.cpp = response.data.data.cpp; }
                if (num == 1) { this.c = response.data.data.c; }
                if (num == 2) { this.java = response.data.data.java; }
                if (num == 3) { this.python = response.data.data.python; }        
            })
        })
        .catch((error) => {
            alert('세션 만료.\n다시 로그인 해주세요.')
            this.$cookies.remove('auth-token')
            this.$store.commit('checkToken',this.$cookies.get('auth-token'))
            this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
            this.$router.push('/login')
        })
      }
    },
    created() {
      this.getlist()
    }
  }
</script>