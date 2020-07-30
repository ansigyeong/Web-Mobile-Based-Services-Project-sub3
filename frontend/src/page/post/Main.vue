<template>
  <div>
    <img class="main_logo" src="../../assets/img/hello_logo.png" alt="">

    <v-row>
        <v-col
        v-for="(bar, i) in bars"
        :key="i"
        cols="12"
        sm="12"
        md="6"
        class="my-4"
        >
        
        <v-card color="grey lighten-4" flat height="200px">
            <v-toolbar :color="bar.class" :dark="bar.dark">
            <v-app-bar-nav-icon></v-app-bar-nav-icon>
            <v-toolbar-title>Title</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn icon>
                <v-icon>mdi-magni   fy</v-icon>
            </v-btn>
            <v-btn icon>
                <v-icon>mdi-heart</v-icon>
            </v-btn>
            <v-btn icon>
                <v-icon>mdi-dots-vertical</v-icon>
            </v-btn>
            </v-toolbar>
        </v-card>
        </v-col>
    </v-row>

  </div>
</template>

<script>
    // import '../../assets/css/post.scss'

    
    export default {
        name:"Main",
        components:{
        },
        watch: {
        },
        created() {
        },
        methods: {
        },
        data: () => {
            return {

            }
        }
    }

</script>

<style>
    .main_logo {
        padding-top: 50px;
    }
    
    .searchbar {
        padding: 200px;
    }
</style>