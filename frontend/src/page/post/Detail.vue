<template> 
	<v-form>
		<v-container> 
			<v-row> 제목 | {{ title }} </v-row> 
			<v-row> 내용 | {{ context }} </v-row> 
			<v-row> <v-btn block outlined color="blue" @click="listClick"> 목록 </v-btn> </v-row> 
		</v-container> 
	</v-form> 
</template> 

<script> 
	import axios from 'axios' 
	export default { 
		name: 'Detail', 
		created() { 
			this.fetch() 
		}, 
		methods: { 
			fetch() { 
				axios.get('http://localhost:8000/api/board/' + this.$$router.params.seq) 
				.then((response) => { 
					console.log(response) 
				}) 
				.catch((error) => { 
					console.log(error) 
				}) 
			}, 
			listClick() { 
				this.$router.push('/') 
			}, 
			deleteClick() { 
				if(this.$data.seq) { 
					axios.delete('http://localhost:8000/api/board/' + this.$data.seq) 
					.then((response) => { 
						console.log(response) 
						this.$router.push('/') 
					}) 
					.catch((error) => { 
						console.log(error) 
					}) 
				} 
			} 
		}, 
		data () {
			return {
				title : "전역변수 질문있어요", 
				context: "사실은 없어요" 
			} 
		} 
	} 
</script>