<template>
  <div @click="gitlabConfigModal = true">
    <span><Icon type="ios-navigate"/> GitLabConfig</span>
    <Modal
      v-model="gitlabConfigModal"
      title="gitlabConfigModal"
      @on-ok="ok"
      :mask-closable="false"
      :loading="true"
      @on-cancel="cancel">
      <div slot="footer">
        <Button type="text" size="large" @click="cancel">取消</Button>
        <Button type="primary" size="large" @click="ok" :loading="loading"
                :disabled="!changedSetting.chosenProjects.length||this.changedSetting.allProjects===undefined">确定
        </Button>
      </div>
      <!--做一个配置页面-->
      <div class="git-config-content">
        <Form :label-width="80" v-if="this.authority===0">
          <FormItem label="IP" class="git-form-item">
            <Input class="git-input" v-model="changedSetting.ip" placeholder="Enter IP" clearable/>
          </FormItem>
          <FormItem label="privateToken" class="git-form-item">
            <Input class="git-input" v-model="changedSetting.privateToken" placeholder="Enter PrivateToken"
                   clearable/>
          </FormItem>
          <FormItem label="Interval" class="git-form-item">
            <Input class="git-input" v-model="changedSetting.interval" placeholder="Enter Interval" clearable/>
          </FormItem>
        </Form>
        <Button v-if="this.authority===0" class="validate-keys" type="primary" shape="circle" icon="ios-search"
                size="small"
                @click="validate_keys_new"></Button>
        <div style="clear: both;">
          <CheckboxGroup v-model="changedSetting.chosenProjects" class="git-checkgroup">
            <Checkbox :label="project.id" v-for="project in changedSetting.allProjects" :key="project.id"
                      class="git-checkbox">
              <span>{{project.projectName}}</span>
            </Checkbox>
          </CheckboxGroup>
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import gitlab from '@/service/gitlab'

export default {
  components: {},
  props: {
    boardName: {
      type: String
    }
  },
  data() {
    return {
      gitlabConfigModal: false,
      loading: false,
      changedSetting: {
        ip: '',
        interval: 0,
        name: '',
        privateToken: '',
        chosenProjects: [],
        allProjects: []
      }
    }
  },
  computed: {
    ...mapState('Git', {
      // gitIp: state => state.gitIp,
      // gitInterval: state => state.gitInterval,
      // gitPrivateToken: state => state.gitPrivateToken,
      // chosenGitProjects: state => state.chosenGitProjects,
      // allGitProjects: state => state.allGitProjects,
      CITesting: state => state.CITesting,
      mergeRequest: state => state.mergeRequest
    }),
    ...mapState({
      account: state => state.account,
      JobSliceNum: state => state.JobSliceNum,
      CISliceNum: state => state.CISliceNum,
      authority: state => state.authority
    })
  },
  created() {
    this.setSetting()
  },
  methods: {
    ...mapActions('Git', [
      'saveCITestingSetting',
      'saveMergeRequestSetting',
      'setCIData',
      'setJobData'
    ]),
    async ok() {
      this.loading = true
      let receiveData = await gitlab.getAllGitLabHook(this.account, this.changedSetting)
      console.log('Job receiveData:', receiveData)
      if (receiveData.status === 200) {
        switch (this.boardName) {
          case 'CI Testing':
            await this.saveCITestingSetting(this.changedSetting)
            this.setCIData(receiveData.projects)
            break
          case 'Merge Request':
            await this.saveMergeRequestSetting(this.changedSetting)
            this.setJobData(receiveData.projects)
            break
        }
      }
      this.gitlabConfigModal = false
      this.loading = false
    },
    cancel() {
      this.gitlabConfigModal = false
      this.loading = false
    },
    async validate_keys_new() {
      let receiveData = await gitlab.getGitlabId({
        name: this.changedSetting.name,
        ip: this.changedSetting.ip,
        privateToken: this.changedSetting.privateToken
      })
      this.changedSetting.allProjects = receiveData.projects.boardData
      // error handle
      // switch (receiveData.status) {
      //   case 'success':
      //     break
      //   case 'hostOrIp':
      //     alert('IP 错误')
      //     break
      //   case 'gitToken':
      //     alert('Token 错误')
      //     break
      // }
    },
    async setSetting() {
      switch (this.boardName) {
        case 'CI Testing':
          this.changedSetting.interval = this.CITesting.interval
          this.changedSetting.ip = this.CITesting.ip
          this.changedSetting.privateToken = this.CITesting.privateToken
          this.changedSetting.name = this.CITesting.name
          this.changedSetting.chosenProjects = this.CITesting.chosenProjects
          this.changedSetting.allProjects = this.CITesting.allProjects
          break
        case 'Merge Request':
          this.changedSetting.interval = this.mergeRequest.interval
          this.changedSetting.ip = this.mergeRequest.ip
          this.changedSetting.privateToken = this.mergeRequest.privateToken
          this.changedSetting.name = this.mergeRequest.name
          this.changedSetting.chosenProjects = this.mergeRequest.chosenProjects
          this.changedSetting.allProjects = this.mergeRequest.allProjects
          break
      }
    }
  }
}
</script>

<style scoped>
  .git-config-content {
    display: inline-block;
    position: relative;
    transform: translateX(35%);
  }

  .git-input {
    width: 190px;

  }

  .validate-keys {
    float: right;
  }
  .git-checkgroup{
    position: relative;
    transform: translateX(35%);
  }
  .git-checkbox {
    display: block;
    line-height: 30px;
    font-size: 14px;
    /*padding-left: 10%;*/
  }
</style>
