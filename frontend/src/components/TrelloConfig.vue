<template>
  <div @click="storysConfigModal = true">
    <span><Icon type="ios-keypad"></Icon> Story Config</span>

    <Modal
      v-model="storysConfigModal"
      title="StorysConfigModal"
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
      <div class="trello-config-content">
        <Form :label-width="80" v-if="this.authority===0">
          <FormItem label="Token">
            <Input class="trello-input" v-model="changedSetting.privateToken" placeholder="Enter Token..." clearable/>
          </FormItem>
          <FormItem label="AppKey">
            <Input class="trello-input" v-model="changedSetting.appKey" placeholder="Enter AppKey..." clearable/>
          </FormItem>
          <FormItem label="Interval">
            <Input class="trello-input" v-model="changedSetting.interval" placeholder="Enter Interval..."
                   clearable/>
          </FormItem>
        </Form>
        <Button v-if="this.authority===0" class="validate-keys" type="primary" shape="circle" icon="ios-search" size="small"
                @click="validate_keys_new"></Button>
        <div style="clear: both">
          <CheckboxGroup v-model="changedSetting.chosenProjects" class="trello-checkgroup">
            <Checkbox :label="project.id" v-for="project in changedSetting.allProjects" :key="project.id"
                      class="trello-checkbox">
              <span style="">{{project.projectName}}</span>
            </Checkbox>
          </CheckboxGroup>
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import storys from '@/service/storys'

export default {
  components: {},
  props: {
    boardName: {
      type: String
    }
  },
  data() {
    return {
      storysConfigModal: false,
      storysAllBoard: [],
      loading: false,
      changedSetting: {
        privateToken: '',
        appKey: '',
        name: 'Trello',
        allProjects: [],
        chosenProjects: []
      }
    }
  },
  computed: {
    ...mapState('Trello', {
      // trelloToken: state => state.trelloToken,
      // trelloAppkey: state => state.trelloAppkey,
      // allTrelloProjects: state => state.allTrelloProjects,
      // trelloInterval: state => state.trelloInterval,
      // chosenTrelloProjects: state => state.chosenTrelloProjects,
      trello: state => state.trello
    }),
    ...mapState({
      account: state => state.account,
      authority: state => state.authority
    })
  },
  created() {
    this.setSetting()
    console.log('trello authority:',this.authority)
  },
  methods: {
    ...mapActions('Trello', [
      'saveTrelloSetting',
      'setTrelloData'
    ]),
    async ok() {
      this.loading = true
      let receiveData=await storys.getTodoCardByBoardId(this.account, this.changedSetting)
      switch (this.boardName) {
        case 'Trello':
          await this.saveTrelloSetting(this.changedSetting)
          this.setTrelloData(receiveData.projects)
          console.log('trelloData:',receiveData.projects)
          break
      }
      // await this.saveTrelloSetting(this.changedSetting)
      this.storysConfigModal = false
      this.loading = false
    },
    cancel() {
      this.storysConfigModal = false
      this.loading = false
    },
    async validate_keys_new() {
      let receiveData = await storys.getBroadsId({
        name: this.changedSetting.name,
        privateToken: this.changedSetting.privateToken,
        appKey: this.changedSetting.appKey
      })
      this.changedSetting.allProjects = receiveData.projects.boardData
      switch (receiveData.status) {
        case 'success':
          break
        case 'trelloKey':
          alert('AppKey 错误')
          break
        case 'trelloToken':
          alert('Token 错误')
          break
      }
    },
    async setSetting() {
      // this.changedSetting.trelloInterval = this.trelloInterval
      // this.changedSetting.trelloToken = this.trelloToken
      // this.changedSetting.trelloAppkey = this.trelloAppkey
      // this.changedSetting.allTrelloProjects = this.allTrelloProjects
      // this.changedSetting.choice = 2
      // this.changedSetting.chosenTrelloProjects = this.chosenTrelloProjects
      switch (this.boardName) {
        case 'Trello':
          this.changedSetting.interval = this.trello.interval
          this.changedSetting.privateToken = this.trello.privateToken
          this.changedSetting.appKey = this.trello.appKey
          this.changedSetting.name = this.trello.name
          this.changedSetting.chosenProjects = this.trello.chosenProjects
          this.changedSetting.allProjects = this.trello.allProjects
          break
      }
    }
  }
}
</script>

<style scoped>
  .trello-config-content {
    display: inline-block;
    position: relative;
    transform: translateX(32%);
  }

  .trello-input {
    width: 190px;

  }

  .validate-keys {
    float: right;
  }

  .trello-checkbox {
    display: block;
    line-height: 30px;
    font-size: 14px;
    /*padding-left: 25%;*/
  }
  .trello-checkgroup{
    position: relative;
    transform: translateX(35%);
  }
</style>
