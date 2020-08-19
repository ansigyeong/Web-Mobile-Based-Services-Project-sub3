<template>
<div class="container">
    <div class="questionBox">
        <transition :duration="{ enter: 500, leave: 300 }" enter-active-class="animated zoomIn" leave-active-class="animated zoomOut" mode="out-in">
            <div class="questionContainer" v-if="questionIndex<quiz.questions.length" v-bind:key="questionIndex">

                <header>
                    <h1 class="title is-6" style="font-family: 'CookieRun-Regular'!important;">오늘의 Quiz</h1>
                    <div class="progressContainer">
                        <progress class="progress is-info is-small" :value="(questionIndex/quiz.questions.length)*100" max="100">{{(questionIndex/quiz.questions.length)*100}}%</progress>
                        <p>{{(questionIndex/quiz.questions.length)*100}}% complete</p>
                    </div>
                </header>
                <h2 class="titleContainer title" style="font-family: 'CookieRun-Regular'!important;">{{ quiz.questions[questionIndex].text }}</h2>

                <div class="optionContainer">
                    <div class="option" v-for="(response, index) in quiz.questions[questionIndex].responses" @click="selectOption(index)" :class="{ 'is-selected': userResponses[questionIndex] == index}" :key="index">
                        {{ index | charIndex }}. {{ response.text }}
                    </div>
                </div>

                <footer class="questionFooter">
                    <nav class="pagination" role="navigation" aria-label="pagination">

                        <a class="button" v-on:click="prev();" :disabled="questionIndex < 1">Back</a>

                        <a class="button" :class="(userResponses[questionIndex]==null)?'':'is-active'" v-on:click="next();" :disabled="questionIndex>=quiz.questions.length">
                    {{ (userResponses[questionIndex]==null)?'Skip':'Next' }}
                  </a>
                    </nav>

                </footer>

            </div>
            
               <div v-if="questionIndex == quiz.questions.length" > 
                  <h2 class="result">결과확인</h2>
                  <img class="Question_Block" @click="submit" src="../../assets/img/Question_Block.gif">
               </div>
            
            <div v-if="questionIndex > quiz.questions.length" v-bind:key="questionIndex" class="quizCompleted has-text-centered">
               <div>     
                  <span class="icon">
                     <i class="fa" :class="score()>3?'fa-check-circle-o is-active':'fa-times-circle'"></i>
                  </span>

                     <!-- <h2 class="title">
                        - 나의 점수 -
                     </h2> -->
                     <p class="subtitle">
                        내 점수 : {{ score() }} / {{ quiz.questions.length }}
                     </p>
                        <br>
                        <a class="button" @click="restart">restart <i class="fa fa-refresh"></i></a>
               </div>

            </div>
            

        </transition>

    </div>
        <div class="outter">
           <a href="https://www.ssafy.com/ksp/jsp/swp/swpMain.jsp">
               <img src="../../assets/img/banner_quiz.png"> 
           </a>
      </div>
</div>
</template>

<script>
import axios from 'axios'
  export default {
    name: 'Quiz',
    data(){
      return {
        grade: 0,
        quiz: quiz,
        questionIndex: 0,
        grade:0,
        userResponses: userResponseSkelaton,
        isActive: false,
        }
    },
    filters: {
      charIndex: function(i) {
         return String.fromCharCode(97 + i);
      }
   },
  
   methods: {
         // restart: function(){
         //         this.questionIndex=0;
         //         this.userResponses=Array(this.quiz.questions.length).fill(null);
         // },
      selectOption: function(index) {
         this.$set(this.userResponses, this.questionIndex, index);
         console.log(this.userResponses);
      },
      next: function() {
         if (this.questionIndex < this.quiz.questions.length)
            this.questionIndex++;
      },

      prev: function() {
         if (this.quiz.questions.length > 0) this.questionIndex--;
      },
      // Return "true" count in userResponses
      score: function() {
         var score = 0;
         for (let i = 0; i < this.userResponses.length; i++) {
            if (
               typeof this.quiz.questions[i].responses[
                  this.userResponses[i]
               ] !== "undefined" &&
               this.quiz.questions[i].responses[this.userResponses[i]].correct
            ) {
               score = score + 1;
            }
         }
         this.grade = score*5;
         return score;

         //return this.userResponses.filter(function(val) { return val }).length;
      },
submit() {
         console.log(this.grade)
        let config = {
          headers: {
            "ACCESS-TOKEN": this.$store.state.token
          }
        }
        let body = {
          contents: this.contents,
          title: this.title,
          grade: this.grade
        }
        this.questionIndex++;
        axios.post(this.$store.state.base_url + '/quiz', body, config)
        .then((response) => {
         //   console.log(response)
           if(response.data.data=="todayEnd")   swal('', '오늘의 퀴즈를 이미 완료하였습니다.', 'warning')
           else swal('', '오늘의 퀴즈를 완료하였습니다.', 'success')
        //   this.$router.push('/')
        })
        .catch((error) => {
         //  console.log(error)
         swal('','로그인 시 점수 획득이 가능합니다.','warning')
           })
    },
   
   }
}

  var quiz = {
      user: "Dave",
      questions: [
         {
            text: "논리값(True 또는 False)을 나타내는 변수형은?",
            responses: [
               { text: "Logical" },
               { text: "Integer",  },
               { text: "Boolean" ,correct: true},
               { text: "Long" }
            ]
         },
         {
            text: "객체지향 프로그램에서 데이터를 추상화 하는 단위는?",
            responses: [
               { text: "메소드" },
               { text: "클래스", correct: true },
               { text: "상속성" },
               { text: "메시지" }
            ]
         },
         {
            text: "병행제어의 로킹(Locking)단위에 대한 설명으로 옳지 않은 것은?",
            responses: [
               { text: "데이터베이스, 파일, 레코드 등은 로킹 단위가 될 수 있다." },
               { text: "로킹 단위가 작아지면 로킹 오버헤드가 감소한다.", correct: true },
               { text: "로킹 단위가 작아지면 데이터베이스 공유도가 증가한다." },
               { text: "한꺼번에 로킹 할 수 있는 객체의 크기를 로킹 단위라 한다." }
            ]
         },
         {
            text: "DML에 해당하는 SQL명령으로만 나열 된 것은?",
            responses: [
               { text: "DELETE, UPDATE, CREATE, ALTER" },
               { text: "INSERT, DELETE, UPDATE, DROP" },
               {
                  text: "SELECT, INSERT, DELETE, UPDATE", correct: true},
               { text: "SELECT, INSERT, DELETE, ALTER" }
            ]
         },
         {
            text: "TCP/IP 프로토콜 중 전송계층 프로토콜은?",
            responses: [
               { text: "HTTP	 " },
               {
                  text: "SMTP"
               },
               { text: "FTP" },
               { text: "TCP" , correct: true} 
               ]
         },
         {
            text:
               "애자일 선언문은 애자일 방법론이 추구하고 있는 가치를 요약하고 있다. 애자일 선언문의 내용으로 옳은 것은?",
            responses: [
               { text: " 포괄적인 문서보다는 제대로 동작하는 소프트웨어에 더 가치를 둔다." , correct: true},
               { text: "고객과의 협력보다는 계약 협상에 더 가치를 둔다." },
               { text: "변화에 대응하는 것보다는 계획을 따르는 것에 더 가치를 둔다." },
               { text: "개인과 상호작용보다는 도구나 프로세스에 더 가치를 둔다." }
            ]
         },
         {
            text: "스크럼(Scrum)의 제품 백로그(product backlog)에 대한 설명으로 옳지 않은 것은? ",
            responses: [
               { text: " 제품 백로그에 있는 업무 목록은 프로젝트를 수행하는 동안 수정되고 정제된다." },
               { text: "제품 백로그의 업무 중 높은 우선순위를 갖는 항목부터 개발한다. " },
               {
                  text: "제품 백로그에 있는 업무의 우선순위를 결정한 후에는 변경하지 않는다.", correct: true
               },
               { text: " 제품 책임자(product owner)가 제품 백로그를 관리한다." }
            ]
         },
         {
            text: "What is the full form of TCP/IP? ",
            responses: [
               { text: "Telephone call protocol / international protocol" },
               { text: "Transmission control protocol / internet protocol", correct: true },
               { text: "Transport control protocol / internet protocol " },
               { text: "None of the above" }
            ]
         },
         {
            text:
               "다음 정렬(Sort)알고리즘 중 구현이 복잡하지만 가장 효율적인 정렬 알고리즘으로 올바른 것은?",
            responses: [
               {
                  text: "선택 정렬(Selection Sort)"
               },
               { text: "삽입 정렬(Insertion Sort)" },
               { text: "버블 정렬(Bubble Sort)"},
               { text: "힙 정렬(Heap Sort)", correct: true }
            ]
         },
         {
            text: "인터프리터(Interpreter) 방식의 언어로 옳지 않은 것은?",
            responses: [
               { text: "JavaScript" },
               { text: "C", correct: true },
               { text: "Basic" },
               { text: "LISP" }
            ]
         }
      ]
   },
   userResponseSkelaton = Array(quiz.questions.length).fill(null);
</script>

<style lang="scss" scoped>
$trans_duration: 0.3s;
$primary_color: #3D5AFE;

            @font-face {
    font-family: 'CookieRun-Regular';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/CookieRun-Regular.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

  * {
      font-family: 'CookieRun-Regular' !important;
  }

body {
   
   font-size: 14px;
      font-family: 'CookieRun-Regular';
	height: 100vh;

	background: #CFD8DC;
	
   /* mocking native UI */
   cursor: default !important; /* remove text selection cursor */
   user-select: none; /* remove text selection */
   user-drag: none; /* disbale element dragging */
	
	display: flex;
	align-items: center;
	justify-content: center;
}

.button {
   transition: $trans_duration;
}
.title,
.subtitle {
   // font-family: Montserrat, sans-serif;
   font-weight: normal;
}
.animated {
   transition-duration: $trans_duration/2;
}



.questionBox {
	
	// max-width: 30rem;
	width: 35rem;
	min-height: 30rem;
    	
	 background: #FAFAFA;
    position: relative;
    left: 200px;
    top: 50px;
    display: flex;
	border-radius: 0.5rem;
	overflow: hidden;
    box-shadow: 0 10px 20px rgba(0,0,0,0.19), 0 6px 6px rgba(0,0,0,0.23);
	
	header{
		background:rgba(0,0,0,0.025);
		padding: 1.5rem;
		text-align: center;
		border-bottom: 1px solid rgba(0,0,0,0.1);
		
		h1{
			font-weight: bold;
			margin-bottom: 1rem !important;
		}
		 .progressContainer {
       width: 60%;
			 margin: 0 auto;
			 >progress{
				 margin-left: 99px;
				 border-radius: 5rem;
				 overflow: hidden;
				 border:none;
				 color:$primary_color;
				&::-moz-progress-bar { background: $primary_color; }
				&::-webkit-progress-value { background: $primary_color; }
			 }
			 >p{
				 margin:0;
				 margin-top: 0.5rem;
			 }
         }
	}
   .titleContainer {
		 text-align: center;
		 margin: 0 auto;
		 padding: 1.5rem;

      }

   .quizForm {
      display: block;
      white-space: normal;

      height: 100%;
      width: 100%;

      .quizFormContainer {
         height: 100%;
         margin: 15px 18px;

         .field-label {
            text-align: left;
            margin-bottom: 0.5rem;
         }
      }
   }
   .quizCompleted {
      position: relative;

      width: 100%;
      padding: 1rem;
		 text-align:center;
		 
		 > .icon{
			 color: #FF5252;
			 font-size: 5rem;
			 
			 .is-active{
				 color: #00E676;
			 }
		 }
   }
   .questionContainer {
      white-space: normal;
      
      height: 100%;
      width: 100%;

      .optionContainer {
         margin-top: 12px;
         flex-grow: 1;
         .option {
            border-radius: 290486px;
            padding: 9px 18px;
            margin: 0 18px;
            margin-bottom: 12px;
            transition: $trans_duration;
            cursor: pointer;
            background-color: rgba(0, 0, 0, 0.05);
					 color: rgba(0,0,0,0.85);
            border: transparent 1px solid;

            &.is-selected {
               border-color: rgba(black,0.25);
               background-color: white;
            }
            &:hover {
               background-color: rgba(0, 0, 0, 0.1);
            }
            &:active {
               transform: scaleX(0.9);
            }
         }
      }

      .questionFooter {
				background:rgba(0,0,0,0.025);
				border-top: 1px solid rgba(0,0,0,0.1);
         width: 100%;
         align-self: flex-end;

         .pagination {
            //padding: 10px 15px;
            margin: 15px 25px;
         }
      }
   }
}
.pagination{
	display: flex;
	justify-content: space-between;
}
.button{
	padding: 0.5rem 1rem;
	border: 1px solid rgba(0,0,0,0.25);
	border-radius: 5rem;
	margin: 0 0.25rem;
	
	transition:0.3s;
	
	&:hover{
		cursor: pointer;
		background: #ECEFF1;
		border-color:rgba(0,0,0,0.25);
	}
	&.is-active{
		background: $primary_color;
		color: white;
		border-color: transparent;
		
		&:hover{
			background: darken($primary_color,10%);
			
		}
	}
   
}

// @media screen and (min-width: 769px) {
//    .questionBox {
//       align-items: center;
//       justify-content: center;

//       .questionContainer {
//          display: flex;
//          flex-direction: column;
//       }
//    }
// }

@media screen and (max-width: 768px) {
   .sidebar {
      height: auto !important;
      border-radius: 6px 6px 0px 0px;
   }
}

.result{
   position: relative;
   left: 150px;
   top: 45px;
}

.submit{
   position: relative;
   left: 10px;
   top: 250px;
}

.Question_Block{
   position: relative;
   left: 150px;
   top: 90px;
   cursor: pointer;
}
.outter{
    position: fixed;
    right:50px;
    bottom:0;
    width: 190px;
    top: 200px
           /* padding: 150px 50px 0 0 */
    }
</style>