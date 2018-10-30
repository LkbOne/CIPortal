<template>
  <div class="mapping">
    <div @drop='dropdiv()' @dragover='allowDrop($event)' class="mapping-content">
      <div class="sentry-mapping" v-if="oneProjects.length!==0">
        <div>SentryMapping</div>
        <div class="sentry" :id="item.id" draggable='true' @dragstart='drag($event)'
             v-for="(item,index) in oneProjects" :key="index">
          {{item.projectName}}
        </div>
      </div>
    </div>
    <div class="trello-mapping">
      <div class="trello" :id="titem.id" @drop='drop($event)' @dragover='allowDrop($event)'
           v-for="(titem,index) in manyProjects" :key="index">
        <span style="display: block;">{{titem.projectName}}</span>
        <div class="sentry" :id="item.id" v-for="item in manyMapping" :key="item.id" draggable='true'
             @dragstart='drag($event)'
             v-if="item.trello===titem.id">{{item.projectName}}
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapState } from 'vuex'

export default {
  props: {
    // 不能直接修改prop传过来的值
    //sentryProjects
    oneProjects: {
      type: Array
    },
    //trelloProjects
    manyProjects: {
      type: Array
    },
    //after
    // manyMapping: {
    //   type: Array
    // },
    //mapping关系
    // mapping:{
    //   type: Array
    // }
  },
  data() {
    return {
      dom: '',
      manyMapping: [],
      mapping: []
    }
  },
  computed: {
    ...mapState({
      sentryTrelloMapping: state => state.sentryTrelloMapping,
      sentryGitLabMapping: state => state.sentryGitLabMapping,
      sonarTrelloMapping: state => state.sonarTrelloMapping
    })
  },
  name: 'group',
  methods: {
    drag(event) {
      this.dom = event.currentTarget
    },
    drop(event) {
      event.preventDefault()
      if (this.dom.className === 'sentry') {
        var index = this.oneProjects.findIndex(list => list.id === this.dom.id)
        if (index !== -1) {
          this.manyMapping.push({
            id: this.dom.id,
            trello: event.currentTarget.id,
            projectName: this.oneProjects[index].projectName
          })
          console.log('oneProjects:',this.oneProjects)
          console.log('manyProjects:',this.manyProjects)
          console.log('manyMapping:',this.manyMapping)
          console.log('mapping:',this.mapping)
          this.oneProjects.splice(index, 1)
          console.log('555:',this.oneProjects)
          var index1 = this.mapping.findIndex(list => list.trello === event.currentTarget.id)
          let sentry = []
          if (index1 === -1) {
            sentry = []
            this.mapping.push({trello: event.currentTarget.id})
            sentry.push({id: this.dom.id})
            this.mapping[this.mapping.length - 1].sentry = sentry
          } else {
            sentry = this.mapping[index1].sentry
            sentry.push({id: this.dom.id})
            this.mapping[index1].sentry = sentry
          }
        }
      }
      console.log('666:',this.manyMapping)
    },
    allowDrop(event) {
      event.preventDefault()
    },
    dropdiv() {
      if (this.oneProjects.findIndex(list => list.id === this.dom.id) === -1 && this.sonarProjects.findIndex(list => list.id === this.dom.id) === -1) {
        var index = this.manyMapping.findIndex(list => list.id === this.dom.id)
        if (index !== -1) {
          this.oneProjects.push({id: this.dom.id, projectName: this.manyMapping[index].projectName})
          this.manyMapping.splice(index, 1)
          for (let i = 0; i < this.mapping.length; i++) {
            let sentry = this.mapping[i].sentry
            let sentryindex = sentry.findIndex(list => list.id === this.dom.id)
            if (sentryindex !== -1) {
              sentry.splice(sentryindex, 1)
              this.mapping[i].sentry = sentry
            }
          }
        }
      }
    }

  },
  mounted() {
    // 没改完
    this.manyMapping = []
    this.mapping = this.sentryGitLabMapping
    if (this.oneProjects.length) {
      for (let i = 0; i < this.sentryGitLabMapping.length; i++) {
        for (let j = 0; j < this.sentryGitLabMapping[i].sentry.length; j++) {
          let index = this.oneProjects.findIndex(list => list.id === this.sentryGitLabMapping[i].sentry[j].id)
          this.sentryAfter2.push({
            id: this.sentryGitLabMapping[i].sentry[j].id,
            trello: this.sentryGitLabMapping[i].trello,
            projectName: this.oneProjects[index].projectName
          })
          this.oneProjects.splice(index, 1)
        }
      }
    }
  }
}
</script>
<style scoped>
  .mapping {
    margin: auto;
    text-align: center;
  }

  .mapping-content {
    border: 1px solid grey;
    float: left;
    min-width: 185px;
    height: 500px
  }

  .sentry-mapping {
    height: 500px;
    overflow: auto;
    float: left;
  }

  .sentry {
    background-color: #55c4f7;
    height: 40px;
    margin: 5px;
    line-height: 40px;
    border-radius: 5px;
    color: white;
    min-width: 175px
  }

  .trello-mapping {
    float: left;
    overflow: auto;
    height: 500px;
  }

  .trello {
    border: 1px solid grey;
    min-height: 60px;
    width: 200px;
    margin: 0 10px 10px;
    overflow: auto
  }
</style>
