<template>
  <div @click="init()" style="height: 100%">
    <span style="font-size: 14px;"><Icon type="ios-analytics">Exception</Icon> Sentry Config</span>
    <Modal
      id="modal"
      v-model="sentryConfigModal"
      title="SentryConfigModal"
      @on-ok="ok"
      :mask-closable="false"
      :loading="true"
      @on-cancel="cancel">
      <div slot="footer">
        <Button type="text" size="large" @click="cancel">取消</Button>
        <Button type="primary" size="large" @click="ok" :loading="loading"
                :disabled="!changedSetting.chosenProjects.length||this.changedSetting.allProjects===undefined">
          确定
        </Button>
      </div>
      <!--做一个配置页面-->
      <div class="sentry-config-content"  v-if="this.authority===0">
        <Form :label-width="80">
          <FormItem label="Token">
            <Input class="sentry-input" v-model="changedSetting.privateToken" placeholder="Enter Token" clearable/>
            <img src="../common/images/help.png" width="15px" height="15px" style="position:relative;left: 10px;top: 3px"/>
          </FormItem>
          <FormItem label="Interval">
            <Input class="sentry-input" v-model="changedSetting.interval" placeholder="Enter Interval"
                   clearable/>
          </FormItem>
        </Form>
        <Button class="validate-keys" type="primary" shape="circle" icon="ios-search" size="small"
                @click="validate_keys_new"></Button>
      </div>
      <div id="forSentryConfig">
        <div>
          <CheckboxGroup v-model="changedSetting.chosenProjects" @on-change="chosenChanged">
            <Checkbox class="sentry-checkbox" :label="project.id" v-for="project in changedSetting.allProjects"
                      :key="project.id">
              <span>{{project.projectName}}</span>
            </Checkbox>
          </CheckboxGroup>
        </div>
        <div style="text-align: center"  v-if="this.authority===0">
          <mapping style="display: inline-block" :trelloProjects="{projects:allTrelloProjectsTemp,platform:'Trello'}"
                   :sentryProjects="allSentryProjectsTemp1" :sonarProjects="[]"
                   v-if="this.flag===true"></mapping>
        </div>
        <div style="text-align: center"  v-if="this.authority===0">
          <mapping style="display: inline-block" :trelloProjects="{projects:allMergeRequestProjectsTemp,platform:'GitLab'}"
                   :sentryProjects="allSentryProjectsTemp2" :sonarProjects="[]"
                   v-if="this.flag===true"></mapping>
        </div>
        <!--<div style="text-align: center"  v-if="this.authority===0">-->
          <!--<mapping style="display: inline-block" :manyProjects="allMergeRequestProjectsTemp"-->
                   <!--:oneProjects="allSentryProjectsTemp2" :manyMapping="[]" :mapping="[]"-->
                   <!--v-if="this.flag===true"></mapping>-->
        <!--</div>-->
      </div>
    </Modal>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import sentry from '@/service/sentry'
import mapping from '@/components/DealMapping.vue'
// import mapping from '@/components/MappingDemo.vue'

export default {
  components: {mapping},
  props: {
    boardName: {
      type: String
    }
  },
  data() {
    return {
      allTrelloProjectsTemp: [],
      allSentryProjectsTemp1: [],
      allSentryProjectsTemp2: [],
      allMergeRequestProjectsTemp: [],
      sentryConfigModal: false,
      sentryProjectsChoice: [],
      loading: false,
      sliceNum: 0,
      sentryIntervalTemp: '',
      changedSetting: {
        interval: '',
        privateToken: '',
        chosenProjects: [],
        name: 'Sentry',
        // account: '',
        allProjects: [],
        sentryTrelloMapping: {},
        sentryGitLabMapping: {}
      },
      flag: false
    }
  },
  computed: {
    ...mapState('Sentry', {
      // sentryInterval: state => state.sentryInterval,
      // sentryToken: state => state.sentryToken,
      // chosenSentryProjects: state => state.sentry.chosenProjects,
      // allSentryProjects: state => state.sentry.allProjects,
      // allSentryProjectAndIssue: state => state.allSentryProjectAndIssue,
      sentry: state => state.sentry
    }),
    ...mapState('Trello', {
      chosenTrelloProjects: state => state.trello.chosenProjects,
      allTrelloProjects: state => state.trello.allProjects
    }),
    ...mapState('Git', {
      chosenMergeRequestProjects: state => state.mergeRequest.chosenProjects,
      allMergeRequestProjects: state => state.mergeRequest.allProjects
    }),
    ...mapState({
      account: state => state.account,
      sentryTrelloMapping: state => state.sentryTrelloMapping,
      sentryGitLabMapping: state => state.sentryGitLabMapping,
      SentrySliceNum: state => state.SentrySliceNum,
      clientWidth: state => state.clientWidth,
      authority: state => state.authority
      // sentry: state => state.sentry
    })
  },
  created() {
    this.setSetting()
    for (let j = 0; j < this.chosenTrelloProjects.length; j++) {
      let index = this.allTrelloProjects.findIndex(list => list.id === this.chosenTrelloProjects[j])
      if (index !== -1) {
        this.allTrelloProjectsTemp.push(this.allTrelloProjects[index])
      }
    }
    console.log(' this.allTrelloProjectsTemp', this.allTrelloProjectsTemp)
    for (let j = 0; j < this.chosenMergeRequestProjects.length; j++) {
      let index = this.allMergeRequestProjects.findIndex(list => list.id === this.chosenMergeRequestProjects[j])
      if (index !== -1) {
        this.allMergeRequestProjectsTemp.push(this.allMergeRequestProjects[index])
      }
    }
    console.log(' this.allMergeRequestProjectsTemp', this.allMergeRequestProjectsTemp)
    for (let j = 0; j < this.sentry.chosenProjects.length; j++) {
      let index = this.sentry.allProjects.findIndex(list => list.id === this.sentry.chosenProjects[j])
      if (index !== -1) {
        this.allSentryProjectsTemp1.push(this.sentry.allProjects[index])
        this.allSentryProjectsTemp2.push(this.sentry.allProjects[index])
      }
    }
  },
  methods: {
    ...mapActions('Sentry', [
      'saveSentrySetting',
      'setSentryData'
    ]),
    async ok() {
      this.loading = true
      if (this.clientWidth < 550) {
        this.sliceNum = 4
      } else {
        this.sliceNum = 8
      }
      this.changedSetting.sentryTrelloMapping = this.sentryTrelloMapping
      this.changedSetting.sentryGitLabMapping = this.sentryGitLabMapping
      console.log('this.changedSetting.sentryTrelloMapping',this.changedSetting.sentryTrelloMapping)
      console.log('this.changedSetting.sentryGitLabMapping',this.changedSetting.sentryGitLabMapping)
      let receive = await sentry.getAllSentryIssue(this.account, this.changedSetting)
      console.log('receive---', receive)
      this.setSentryData(receive.projects)
      switch (this.boardName) {
        case 'Sentry':
          console.log('111111111111111111111111111',this.changedSetting)
          await this.$store.dispatch('setTrelloAndSentry', this.changedSetting.sentryTrelloMapping)
          await this.$store.dispatch('setGitLabAndSentry', this.changedSetting.sentryGitLabMapping)
          await this.saveSentrySetting(this.changedSetting)
          break
      }
      this.sentryConfigModal = false
      this.loading = false
      this.flag = false
    },
    cancel() {
      this.loading = false
      this.sentryConfigModal = false
      this.flag = false
    },
    async validate_keys_new() {
      let receiveData = await sentry.getSentryId({
        name: this.changedSetting.name,
        privateToken: this.changedSetting.privateToken
      })
      console.log('receiveData:', receiveData)
      this.changedSetting.allProjects = receiveData.projects.boardData
      switch (receiveData.status) {
        case 'success':
          break
        case 'sentryToken':
          alert('Token 错误')
          break
      }
    },
    async setSetting() {
      // this.changedSetting.interval = this.sentryInterval
      // this.changedSetting.privateToken = this.sentryToken
      // this.changedSetting.allProjects = this.allSentryProjects
      // // this.changedSetting.choice = 4
      // this.changedSetting.name = 'Sentry'
      // this.changedSetting.chosenProjects = this.chosenSentryProjects
      // this.changedSetting.sentryTrelloMapping = this.sentryTrelloMapping

      switch (this.boardName) {
        case 'Sentry':
          this.changedSetting.interval = this.sentry.interval
          this.changedSetting.privateToken = this.sentry.privateToken
          this.changedSetting.name = this.sentry.name
          this.changedSetting.chosenProjects = this.sentry.chosenProjects
          this.changedSetting.allProjects = this.sentry.allProjects
          this.changedSetting.sentryTrelloMapping = this.sentryTrelloMapping
          this.changedSetting.sentryGitLabMapping = this.sentryGitLabMapping
          break
      }
    },
    init() {
      this.sentryConfigModal = true
      this.flag = true
      this.allTrelloProjectsTemp = []
      this.allSentryProjectsTemp1 = []
      this.allSentryProjectsTemp2 = []
      this.allMergeRequestProjectsTemp = []
      for (let j = 0; j < this.chosenMergeRequestProjects.length; j++) {
        let index = this.allMergeRequestProjects.findIndex(list => list.id === this.chosenMergeRequestProjects[j])
        if (index !== -1) {
          this.allMergeRequestProjectsTemp.push(this.allMergeRequestProjects[index])
        }
      }
      console.log('this.allMergeRequestProjectsTemp',this.allMergeRequestProjectsTemp)
      for (let j = 0; j < this.chosenTrelloProjects.length; j++) {
        let index = this.allTrelloProjects.findIndex(list => list.id === this.chosenTrelloProjects[j])
        if (index !== -1) {
          this.allTrelloProjectsTemp.push(this.allTrelloProjects[index])
        }
      }
      for (let j = 0; j < this.sentry.chosenProjects.length; j++) {
        let index = this.sentry.allProjects.findIndex(list => list.id === this.sentry.chosenProjects[j])
        if (index !== -1) {
          this.allSentryProjectsTemp1.push(this.sentry.allProjects[index])
          this.allSentryProjectsTemp2.push(this.sentry.allProjects[index])
        }
      }
    },
    chosenChanged() {
      console.log(this.chosenTrelloProjects.length)
      this.allSentryProjectsTemp1 = []
      this.allSentryProjectsTemp2 = []
      this.allTrelloProjectsTemp = []
      this.allMergeRequestProjectsTemp = []
      for (let j = 0; j < this.chosenMergeRequestProjects.length; j++) {
        let index = this.allMergeRequestProjects.findIndex(list => list.id === this.chosenMergeRequestProjects[j])
        if (index !== -1) {
          this.allMergeRequestProjectsTemp.push(this.allMergeRequestProjects[index])
        }
      }
      for (let j = 0; j < this.sentry.chosenProjects.length; j++) {
        let index = this.sentry.allProjects.findIndex(list => list.id === this.sentry.chosenProjects[j])
        if (index !== -1) {
          this.allSentryProjectsTemp1.push(this.sentry.allProjects[index])
          this.allSentryProjectsTemp2.push(this.sentry.allProjects[index])
        }
      }
      for (let j = 0; j < this.chosenProjects.length; j++) {
        let index = this.allTrelloProjects.findIndex(list => list.id === this.chosenTrelloProjects[j])
        if (index !== -1) {
          this.allTrelloProjectsTemp.push(this.allTrelloProjects[index])
        }
      }
    }
  }
}
</script>

<style scoped>
  #forSentryConfig {
    overflow: auto;
    height: 500px
  }

  .sentry-config-content {
    display: inline-block;
    position: relative;
    transform: translateX(40%);
  }

  .sentry-input {
    width: 190px;

  }

  .validate-keys {
    float: right;
  }

  .sentry-checkbox {
    display: block;
    line-height: 30px;
    font-size: 14px;
    padding-left: 35%;
  }
</style>
