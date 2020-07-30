<template> 
	<div class="container text-box-border">
		<v-form-textarea>
			<v-container> 
				<v-row><h1> {{ questionTitle }} </h1></v-row>
				<br>
				<hr>
				<br>
				<v-row> {{ questionContext }} </v-row> 
			</v-container> 
		</v-form-textarea> 
		<!-- <router-link to="/commentlist">Comment List</router-link> | 
		<router-link to="/commentlistitem">Comment List Item</router-link> |
		<router-link to="/commentCreate">Comment Create</router-link> |
		<router-view/> -->
	</div>
</template> 

<script> 
	import CommentList from '../../components/comment/CommentList.vue'
	import CommentListItem from '../../components/comment/CommentListItem.vue'
	import CommentCreate from '../../components/comment/CommentCreate.vue'

	export default { 
		name: 'Detail', 
		component: {
			CommentList,
			CommentListItem,
			CommentCreate,
		},
		data () {
			return {
				questionTitle : this.$route.query.title, 
				questionContext: this.$route.query.context,
			} 
		} 
	} 
</script>

<style>
	.text-box-border {
		border-top-style: 3px;
		border-style: groove;
	}
</style>