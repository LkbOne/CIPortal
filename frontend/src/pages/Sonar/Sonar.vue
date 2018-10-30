<template>
  <div>
    <div id="sonar-content">
      <Card style="margin: 5px;" v-for="(sonar,index) in sonarData" :key="index">
        <a :href="sonar.url" target="_blank">
          <span class="sonar-project-name">{{sonar.projectName}}</span>
          <div v-if="sonar.alert_status==='OK'" class="sonar-project-state">Passed</div>
          <div v-if="sonar.alert_status==='ERROR'" class="sonar-project-state" style="background-color: #d4333f">
            Failed
          </div>
          <Sonal :sonar="sonar"></Sonal>
        </a>
      </Card>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex'
import Sonal from '@/pages/Trello/Sonal'

export default {
  name: 'sonar',
  data() {
    return {}
  },
  components: {
    Sonal
  },
  computed: {
    ...mapState('Sonar', {
      sonarData: state => state.sonarData
    })
  },
  methods: {},
  mounted() {
    setTimeout(() => {
      // if (this.isShowCI) {
      var content = document.getElementById('sonar-content')
      var board = document.getElementById('board')
      content.style.height = board.offsetHeight - 20 + 'px'
      console.log(content.offsetHeight)
      // }
    }, 100)
  }
}
</script>

<style scoped>
  #sonar-content {
    height: 100%;
    overflow-y: scroll;
    -webkit-overflow-scrolling: touch;
    overflow: auto;
    margin: 10px;
  }

  .sonar-project-name {
    font-size: 16px;
    font-weight: bold;
    color: #236a97;
    border-bottom: 1px solid #cae3f2;
    margin-bottom: 15px;
    margin-right: 40px;
    display: inline-block;
  }

  .sonar-project-state {
    width: 65px;
    text-align: center;
    font-size: 12px;
    font-weight: bold;
    display: inline-block;
    background-color: #00aa00;
    color: #fff;
    border-width: 5px;
    border-radius: 15px;
  }
</style>
