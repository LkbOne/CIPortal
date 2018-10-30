<template>
  <div class="mapping">
    <div @drop='dropdiv()' @dragover='allowDrop($event)' class="mapping-content">
      <div class="sentry-mapping" v-if="sentryProjects1.length!==0&&trelloProjects.platform==='Trello'">
        <div>SentryMapping</div>
        <div class="sentry" :id="item.id" draggable='true' @dragstart='drag($event)'
             v-for="(item,index) in sentryProjects1" :key="index">
          {{item.projectName}}
        </div>
      </div>
      <div class="sentry-mapping" v-if="sentryProjects2.length!==0&&trelloProjects.platform==='GitLab'">
        <div>SentryMapping</div>
        <div class="sentry" :id="item.id" draggable='true' @dragstart='drag($event)'
             v-for="(item,index) in sentryProjects2" :key="index">
          {{item.projectName}}
        </div>
      </div>
      <div class="sonar-mapping" v-if="sonarProjects.length!==0">
        <div>SonarMapping</div>
        <div class="sonar" :id="item.id" draggable='true' @dragstart='drag($event)'
             v-for="(item,index) in sonarProjects"
             :key="index">
          {{item.projectName}}
        </div>
      </div>
    </div>

    <div class="trello-mapping">
      <div class="trello" :id="titem.id" @drop='drop($event)' @dragover='allowDrop($event)'
           v-for="(titem,index) in trelloProjects.projects" :key="index">
        <span style="display: block;">{{titem.projectName}}</span>
        <div class="sentry" :id="item.id" v-for="item in sentryAfter1" :key="item.id" draggable='true'
             @dragstart='drag($event)'
             v-if="trelloProjects.platform === 'Trello'&&item.trello===titem.id">{{item.projectName}}
        </div>
        <div class="sentry" :id="item.id" v-for="item in sentryAfter2" :key="item.id" draggable='true'
             @dragstart='drag($event)'
             v-if="trelloProjects.platform === 'GitLab'&&item.trello===titem.id">{{item.projectName}}
        </div>
        <div class="sonar" :id="item.id" v-for="(item,index) in sonarAfter" :key="index" draggable='true'
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
    trelloProjects: {
      type: Object
    },
    sentryProjects: {
      type: Array
    },
    sonarProjects: {
      type: Array
    }
  },
  data() {
    return {
      sentryProjects1:[],
      sentryProjects2:[],
      sentryMapping1: [],
      sentryMapping2: [],
      sonarMapping: [],
      sentryBefore1: [],
      sentryBefore2: [],
      sonarBefore: [],
      sentryAfter1: [],
      sentryAfter2: [],
      sonarAfter: [],
      dom: ''
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
      if (this.trelloProjects.platform === 'GitLab') {
        console.log('gitlab')
        if (this.dom.className === 'sentry') {
          var index = this.sentryProjects2.findIndex(list => list.id === this.dom.id)
          if (index !== -1) {
            this.sentryAfter2.push({
              id: this.dom.id,
              trello: event.currentTarget.id,
              projectName: this.sentryProjects2[index].projectName
            })
            this.sentryProjects2.splice(index, 1)
            var index1 = this.sentryMapping2.findIndex(list => list.trello === event.currentTarget.id)
            let sentry = []
            if (index1 === -1) {
              sentry = []
              this.sentryMapping2.push({trello: event.currentTarget.id})
              sentry.push({id: this.dom.id})
              this.sentryMapping2[this.sentryMapping2.length - 1].sentry = sentry
              // if (this.trelloProjects.platform === 'GitLab') {
              //   this.$store.dispatch('setGitLabAndSentry', this.sentryMapping2)
              // } else if (this.trelloProjects.platform === 'Trello') {
              //   console.log()
              //   this.$store.dispatch('setTrelloAndSentry', this.sentryMapping1)
              // }
              this.$store.dispatch('setGitLabAndSentry', this.sentryMapping2)
            } else {
              sentry = this.sentryMapping2[index1].sentry
              sentry.push({id: this.dom.id})
              this.sentryMapping2[index1].sentry = sentry
            }
          }
        } else if (this.dom.className === 'sonar') {
          index = this.sonarProjects.findIndex(list => list.id === this.dom.id)
          if (index !== -1) {
            this.sonarAfter.push({
              id: this.dom.id,
              trello: event.currentTarget.id,
              projectName: this.sonarProjects[index].projectName
            })
            this.sonarProjects.splice(index, 1)
            var index2 = this.sonarMapping.findIndex(list => list.trello === event.currentTarget.id)
            let sonar = []
            if (index2 === -1) {
              sonar = []
              this.sonarMapping.push({trello: event.currentTarget.id})
              sonar.push({id: this.dom.id})
              this.sonarMapping[this.sonarMapping.length - 1].sonar = sonar
              this.$store.dispatch('setTrelloAndSonal', this.sonarMapping)
            } else {
              sonar = this.sonarMapping[index2].sonar
              sonar.push({id: this.dom.id})
              this.sonarMapping[index2].sonar = sonar
            }
          }
        }
      } else if (this.trelloProjects.platform === 'Trello') {
        console.log('Trello')
        if (this.dom.className === 'sentry') {
          var indexTrello = this.sentryProjects1.findIndex(list => list.id === this.dom.id)
          if (indexTrello !== -1) {
            this.sentryAfter1.push({
              id: this.dom.id,
              trello: event.currentTarget.id,
              projectName: this.sentryProjects1[indexTrello].projectName
            })
            this.sentryProjects1.splice(indexTrello, 1)
            var indexTrello1 = this.sentryMapping1.findIndex(list => list.trello === event.currentTarget.id)
            let sentry = []
            if (indexTrello1 === -1) {
              sentry = []
              this.sentryMapping1.push({trello: event.currentTarget.id})
              sentry.push({id: this.dom.id})
              this.sentryMapping1[this.sentryMapping1.length - 1].sentry = sentry
              // if (this.trelloProjects.platform === 'GitLab') {
              //   this.$store.dispatch('setGitLabAndSentry', this.sentryMapping2)
              // } else if (this.trelloProjects.platform === 'Trello') {
              //   this.$store.dispatch('setTrelloAndSentry', this.sentryMapping1)
              // }
              this.$store.dispatch('setTrelloAndSentry', this.sentryMapping1)
              // console.log('this.sentryMapping1',this.sentryMapping1)
            } else {
              sentry = this.sentryMapping1[indexTrello1].sentry
              sentry.push({id: this.dom.id})
              this.sentryMapping1[indexTrello1].sentry = sentry
            }
          }
        } else if (this.dom.className === 'sonar') {
          indexTrello = this.sonarProjects.findIndex(list => list.id === this.dom.id)
          if (indexTrello !== -1) {
            this.sonarAfter.push({
              id: this.dom.id,
              trello: event.currentTarget.id,
              projectName: this.sonarProjects[indexTrello].projectName
            })
            this.sonarProjects.splice(indexTrello, 1)
            var indexTrello2 = this.sonarMapping.findIndex(list => list.trello === event.currentTarget.id)
            let sonar = []
            if (indexTrello2 === -1) {
              sonar = []
              this.sonarMapping.push({trello: event.currentTarget.id})
              sonar.push({id: this.dom.id})
              this.sonarMapping[this.sonarMapping.length - 1].sonar = sonar
              this.$store.dispatch('setTrelloAndSonal', this.sonarMapping)
            } else {
              sonar = this.sonarMapping[indexTrello2].sonar
              sonar.push({id: this.dom.id})
              this.sonarMapping[indexTrello2].sonar = sonar
            }
          }
        }
      }
      // if (this.dom.className === 'sentry') {
      //   var index = this.sentryProjects.findIndex(list => list.id === this.dom.id)
      //   if (index !== -1) {
      //     this.sentryAfter.push({
      //       id: this.dom.id,
      //       trello: event.currentTarget.id,
      //       projectName: this.sentryProjects[index].projectName
      //     })
      //     this.sentryProjects.splice(index, 1)
      //     var index1 = this.sentryMapping.findIndex(list => list.trello === event.currentTarget.id)
      //     let sentry = []
      //     if (index1 === -1) {
      //       sentry = []
      //       this.sentryMapping.push({trello: event.currentTarget.id})
      //       console.log('this.current:', event.currentTarget.id)
      //       sentry.push({id: this.dom.id})
      //       this.sentryMapping[this.sentryMapping.length - 1].sentry = sentry
      //       if (this.trelloProjects.platform === 'GitLab') {
      //         this.$store.dispatch('setGitLabAndSentry', this.sentryMapping)
      //       } else if (this.trelloProjects.platform === 'Trello') {
      //         this.$store.dispatch('setTrelloAndSentry', this.sentryMapping)
      //       }
      //     } else {
      //       sentry = this.sentryMapping[index1].sentry
      //       sentry.push({id: this.dom.id})
      //       this.sentryMapping[index1].sentry = sentry
      //     }
      //   }
      // } else if (this.dom.className === 'sonar') {
      //   index = this.sonarProjects.findIndex(list => list.id === this.dom.id)
      //   if (index !== -1) {
      //     this.sonarAfter.push({
      //       id: this.dom.id,
      //       trello: event.currentTarget.id,
      //       projectName: this.sonarProjects[index].projectName
      //     })
      //     this.sonarProjects.splice(index, 1)
      //     var index2 = this.sonarMapping.findIndex(list => list.trello === event.currentTarget.id)
      //     let sonar = []
      //     if (index2 === -1) {
      //       sonar = []
      //       this.sonarMapping.push({trello: event.currentTarget.id})
      //       sonar.push({id: this.dom.id})
      //       this.sonarMapping[this.sonarMapping.length - 1].sonar = sonar
      //       this.$store.dispatch('setTrelloAndSonal', this.sonarMapping)
      //     } else {
      //       sonar = this.sonarMapping[index2].sonar
      //       sonar.push({id: this.dom.id})
      //       this.sonarMapping[index2].sonar = sonar
      //     }
      //   }
      // }
    },
    allowDrop(event) {
      event.preventDefault()
    },
    dropdiv() {
      if (this.trelloProjects.platform === 'GitLab') {
        if (this.sentryProjects2.findIndex(list => list.id === this.dom.id) === -1 && this.sonarProjects.findIndex(list => list.id === this.dom.id) === -1) {
          var index = this.sentryAfter2.findIndex(list => list.id === this.dom.id)
          if (index !== -1) {
            this.sentryProjects2.push({id: this.dom.id, projectName: this.sentryAfter2[index].projectName})
            this.sentryAfter2.splice(index, 1)
            for (let i = 0; i < this.sentryMapping2.length; i++) {
              let sentry = this.sentryMapping2[i].sentry
              let sentryindex = sentry.findIndex(list => list.id === this.dom.id)
              if (sentryindex !== -1) {
                sentry.splice(sentryindex, 1)
                this.sentryMapping2[i].sentry = sentry
                this.$store.dispatch('setGitLabAndSentry', this.sentryMapping2)
              }
            }
          } else {
            index = this.sonarAfter.findIndex(list => list.id === this.dom.id)
            this.sonarProjects.push({id: this.dom.id, projectName: this.sonarAfter[index].projectName})
            this.sonarAfter.splice(index, 1)
            for (let i = 0; i < this.sonarMapping.length; i++) {
              let sonar = this.sonarMapping[i].sonar
              let sonarindex = sonar.findIndex(list => list.id === this.dom.id)
              if (sonarindex !== -1) {
                sonar.splice(sonarindex, 1)
                this.sonarMapping[i].sonar = sonar
                this.$store.dispatch('setTrelloAndSonal', this.sonarMapping)
              }
            }
          }
        }
      } else if (this.trelloProjects.platform === 'Trello') {
        if (this.sentryProjects1.findIndex(list => list.id === this.dom.id) === -1 && this.sonarProjects.findIndex(list => list.id === this.dom.id) === -1) {
          var index1 = this.sentryAfter1.findIndex(list => list.id === this.dom.id)
          if (index1 !== -1) {
            this.sentryProjects1.push({id: this.dom.id, projectName: this.sentryAfter1[index1].projectName})
            this.sentryAfter1.splice(index1, 1)
            for (let i = 0; i < this.sentryMapping1.length; i++) {
              let sentry = this.sentryMapping1[i].sentry
              let sentryindex = sentry.findIndex(list => list.id === this.dom.id)
              if (sentryindex !== -1) {
                sentry.splice(sentryindex, 1)
                this.sentryMapping1[i].sentry = sentry
                this.$store.dispatch('setTrelloAndSentry', this.sentryMapping1)
                // console.log('this.sentryMapping1',this.sentryMapping1)
              }
            }
          } else {
            index1 = this.sonarAfter.findIndex(list => list.id === this.dom.id)
            this.sonarProjects.push({id: this.dom.id, projectName: this.sonarAfter[index1].projectName})
            this.sonarAfter.splice(index1, 1)
            for (let i = 0; i < this.sonarMapping.length; i++) {
              let sonar = this.sonarMapping[i].sonar
              let sonarindex = sonar.findIndex(list => list.id === this.dom.id)
              if (sonarindex !== -1) {
                sonar.splice(sonarindex, 1)
                this.sonarMapping[i].sonar = sonar
                this.$store.dispatch('setTrelloAndSonal', this.sonarMapping)
              }
            }
          }
        }
      }
      // if (this.sentryProjects.findIndex(list => list.id === this.dom.id) === -1 && this.sonarProjects.findIndex(list => list.id === this.dom.id) === -1) {
      //   var index = this.sentryAfter.findIndex(list => list.id === this.dom.id)
      //   if (index !== -1) {
      //     this.sentryProjects.push({id: this.dom.id, projectName: this.sentryAfter[index].projectName})
      //     this.sentryAfter.splice(index, 1)
      //     for (let i = 0; i < this.sentryMapping.length; i++) {
      //       let sentry = this.sentryMapping[i].sentry
      //       let sentryindex = sentry.findIndex(list => list.id === this.dom.id)
      //       if (sentryindex !== -1) {
      //         sentry.splice(sentryindex, 1)
      //         this.sentryMapping[i].sentry = sentry
      //         this.$store.dispatch('setTrelloAndSentry', this.sentryMapping)
      //       }
      //     }
      //   } else {
      //     index = this.sonarAfter.findIndex(list => list.id === this.dom.id)
      //     this.sonarProjects.push({id: this.dom.id, projectName: this.sonarAfter[index].projectName})
      //     this.sonarAfter.splice(index, 1)
      //     for (let i = 0; i < this.sonarMapping.length; i++) {
      //       let sonar = this.sonarMapping[i].sonar
      //       let sonarindex = sonar.findIndex(list => list.id === this.dom.id)
      //       if (sonarindex !== -1) {
      //         sonar.splice(sonarindex, 1)
      //         this.sonarMapping[i].sonar = sonar
      //         this.$store.dispatch('setTrelloAndSonal', this.sonarMapping)
      //       }
      //     }
      //   }
    }

  },
  mounted() {
    // console.log('this.sentrytrellomapping:', this.sentryTrelloMapping)
    // console.log('this.sentrygitlabmapping:', this.sentryGitLabMapping)
    // console.log('sentryProjects:',this.sentryProjects)
    // this.sentryAfter1 = []
    // this.sentryAfter2 = []
    if (this.trelloProjects.platform === 'GitLab') {
      this.sentryAfter2 = []
      this.sentryMapping2 = this.sentryGitLabMapping
      if (this.sentryProjects2.length) {
        for (let i = 0; i < this.sentryGitLabMapping.length; i++) {
          for (let j = 0; j < this.sentryGitLabMapping[i].sentry.length; j++) {
            let index = this.sentryProjects2.findIndex(list => list.id === this.sentryGitLabMapping[i].sentry[j].id)
            this.sentryAfter2.push({
              id: this.sentryGitLabMapping[i].sentry[j].id,
              trello: this.sentryGitLabMapping[i].trello,
              projectName: this.sentryProjects2[index].projectName
            })
            this.sentryProjects2.splice(index, 1)
          }
        }
        // console.log('this.sentryAfter2:', this.sentryAfter2)
        // console.log('this.sentryProjects2:', this.sentryProjects2)
      }
    } else if (this.trelloProjects.platform === 'Trello') {
      this.sentryAfter1 = []
      this.sentryMapping1 = this.sentryTrelloMapping
      if (this.sentryProjects1.length) {
        for (let i = 0; i < this.sentryTrelloMapping.length; i++) {
          for (let j = 0; j < this.sentryTrelloMapping[i].sentry.length; j++) {
            let index = this.sentryProjects1.findIndex(list => list.id === this.sentryTrelloMapping[i].sentry[j].id)
            this.sentryAfter1.push({
              id: this.sentryTrelloMapping[i].sentry[j].id,
              trello: this.sentryTrelloMapping[i].trello,
              projectName: this.sentryProjects1[index].projectName
            })
            this.sentryProjects1.splice(index, 1)
          }
        }
      }
      // console.log('this.sentryAfter1:', this.sentryAfter1)
      // console.log('this.sentryProjects1:', this.sentryProjects1)
    }
    // this.sentryMapping = this.sentryTrelloMapping
    // 09.02
    this.sonarAfter = []
    this.sonarMapping = this.sonarTrelloMapping
    // if (this.sentryProjects.length) {
    //   for (let i = 0; i < this.sentryTrelloMapping.length; i++) {
    //     for (let j = 0; j < this.sentryTrelloMapping[i].sentry.length; j++) {
    //       let index = this.sentryProjects.findIndex(list => list.id === this.sentryTrelloMapping[i].sentry[j].id)
    //       this.sentryAfter.push({
    //         id: this.sentryTrelloMapping[i].sentry[j].id,
    //         trello: this.sentryTrelloMapping[i].trello,
    //         projectName: this.sentryProjects[index].projectName
    //       })
    //       this.sentryProjects.splice(index, 1)
    //     }
    //   }
    // }
    // 09.02
    if (this.sonarProjects.length) {
      for (let i = 0; i < this.sonarTrelloMapping.length; i++) {
        for (let j = 0; j < this.sonarTrelloMapping[i].sonar.length; j++) {
          let index = this.sonarProjects.findIndex(list => list.id === this.sonarTrelloMapping[i].sonar[j].id)
          this.sonarAfter.push({
            id: this.sonarTrelloMapping[i].sonar[j].id,
            trello: this.sonarTrelloMapping[i].trello,
            projectName: this.sonarProjects[index].projectName
          })
          this.sonarProjects.splice(index, 1)
        }
      }
    }
  },
  created() {
    // console.log('000000000000000000000,',this.sentryProjects)
    for (let i = 0; i < this.sentryProjects.length; i++) {
      this.sentryProjects1.push(this.sentryProjects[i])
      this.sentryProjects2.push(this.sentryProjects[i])
    }
    // console.log('this.sentryProjects1.push(this.sentryProjects[i])',this.sentryProjects1)
    // console.log('this.sentryProjects2.push(this.sentryProjects[i])',this.sentryProjects2)
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

  .sonar-mapping {
    height: 500px;
    overflow: auto;
    float: left
  }

  .sonar {
    background-color: #47cb89;
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
