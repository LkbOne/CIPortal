<template>
  <div class="login" @keydown.enter="handleSubmit">
    <div class="login-con">
      <Card :bordered="false">
        <p slot="title">
          <Icon type="log-in"></Icon>
          注册用户
        </p>
        <div class="form-con">
          <Form ref="RegisterForm" :model="form" :rules="rules">
            <div v-show="this.current===0" class="register-step">
              <FormItem prop="userName">
                <Input v-model="form.account" placeholder="请输入用户名">
                <span slot="prepend">
                  <Icon :size="16" type="person"></Icon>
                </span>
                </Input>
              </FormItem>
              <FormItem prop="password">
                <Input type="password" v-model="form.password" placeholder="请输入密码">
                <span slot="prepend">
                  <Icon :size="14" type="locked"></Icon>
                </span>
                </Input>
              </FormItem>
            </div>
            <br>
            <div v-show="this.current===1" class="register-step">
              <span class="config-title">Job Config</span>
              <Button type="primary" shape="circle" icon="ios-search" size="small" @click="validateGitAuth"
                      class="register-button"></Button>
              <FormItem prop="gitIp" style="margin-top: 10px">
                <Input v-model="form.gitIp" placeholder="gitlab Ip地址">
                <span slot="prepend">
                  <Icon :size="14" type="locked"></Icon>
                </span>
                </Input>
              </FormItem>
              <FormItem prop="gitPrivateToken">
                <Input v-model="form.gitPrivateToken" placeholder="gitlab PrivateToken">
                <span slot="prepend">
                  <Icon :size="15" type="locked"></Icon>
                </span>
                </Input>
              </FormItem>
              <FormItem>
              </FormItem>
              <CheckboxGroup v-model="form.chosenGitProjects">
                <Checkbox :label="project.id" v-for="project in form.allGitProjects" :key="project.id"
                          class="register-checkBox">
                  <span>{{project.projectName}}</span>
                </Checkbox>
              </CheckboxGroup>
            </div>

            <br>
            <div v-show="this.current===2" class="register-step">
              <span class="config-title">Trello Config</span>
              <Button type="primary" shape="circle" icon="ios-search" size="small" @click="validateTrelloAuth"
                      class="register-button"></Button>
              <FormItem prop="storyToken">
                <Input v-model="form.trelloToken" placeholder="storyToken">
                <span slot="prepend">
                  <Icon :size="14" type="locked"></Icon>
                </span>
                </Input>
              </FormItem>
              <FormItem prop="storyAppkey">
                <Input v-model="form.trelloAppkey" placeholder="storyAppkey">
                <span slot="prepend">
                  <Icon :size="14" type="locked"></Icon>
                </span>
                </Input>
              </FormItem>
              <CheckboxGroup v-model="form.chosenTrelloProjects">
                <Checkbox :label="project.id" v-for="project in form.allTrelloProjects" :key="project.id"
                          class="register-checkBox">
                  <span>{{project.projectName}}</span>
                </Checkbox>
              </CheckboxGroup>
            </div>

            <br>
            <div v-show="this.current===3" class="register-step">
              <span class="config-title">Sentry Config</span>
              <Button type="primary" shape="circle" icon="ios-search" size="small" @click="validateSentryAuth"
                      class="register-button"></Button>
              <FormItem prop="sentryToken">
                <Input v-model="form.sentryToken" placeholder="sentryToken">
                <span slot="prepend">
                  <Icon :size="14" type="locked"></Icon>
                </span>
                </Input>
              </FormItem>
              <CheckboxGroup v-model="form.chosenSentryProjects">
                <Checkbox :label="project.id" v-for="project in form.allSentryProjects" :key="project.id"
                          class="register-checkBox">
                  <span>{{project.projectName}}</span>
                </Checkbox>
              </CheckboxGroup>
            </div>

            <br>
            <div v-show="this.current===4" class="register-step">
              <span class="config-title">Sonar Config</span>
              <Button type="primary" shape="circle" icon="ios-search" size="small" @click="validateSonarHost"
                      class="register-button"></Button>
              <FormItem prop="sonalHost">
                <Input v-model="form.sonarHost" placeholder="sonalHost">
                <span slot="prepend">
                  <Icon :size="14" type="locked"></Icon>
                </span>
                </Input>
              </FormItem>
              <CheckboxGroup v-model="form.chosenSonarProjects">
                <Checkbox :label="project.id" v-for="project in form.allSonarProjects" :key="project.id"
                          class="register-checkBox">
                  <span>{{project.projectName}}</span>
                </Checkbox>
              </CheckboxGroup>
            </div>
            <div v-show="this.current===5">
              <div id="forRegister" style="text-align: center">
                <mapping style="display: inline-block" :trelloProjects="allTrelloProjectsTemp"
                         :sentryProjects="allSentryProjectsTemp" :sonarProjects="allSonarProjectsTemp"
                         v-if="flag"></mapping>
              </div>
            </div>
          </Form>
          <Steps :current="current" size="small" class="register-progress">
            <Step title="步骤1"></Step>
            <Step title="步骤2"></Step>
            <Step title="步骤3"></Step>
            <Step title="步骤4"></Step>
            <Step title="步骤5"></Step>
            <Step title="步骤6"></Step>
          </Steps>
          <Row :gutter="10">
            <Col span="8">
            <Button class="step-button" type="primary" @click="last" v-if="this.current!==5"
                    :disabled="this.current<=0">上一步
            </Button>
            </Col>
            <Col span="8">
            <Button class="step-button" type="primary" @click="skip" v-if="this.current!==5">跳过</Button>
            </Col>
            <Col span="8">
            <Button class="step-button" type="primary" @click="next" v-if="this.current!==5" :disabled="this.current>4">
              下一步
            </Button>
            </Col>
          </Row>
          <Button type="primary" @click="handleSubmit" class="step-button" v-if="this.current===5">注册</Button>
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
import user from '@/service/user'
import gitlab from '@/service/gitlab'
import storys from '@/service/storys'
import sentry from '@/service/sentry'
import sonal from '@/service/sonal'
import mapping from '@/components/DragDemo3'

import { mapState } from 'vuex'

export default {
  components: {mapping},
  name: 'register-pages',
  data() {
    return {
      flag: false,
      current: 0,
      status: {},
      allTrelloProjectsTemp: [],
      allSentryProjectsTemp: [],
      allSonarProjectsTemp: [],
      form: {
        account: 'admin010',
        password: '123456',
        gitIp: '146.222.94.208',
        gitPrivateToken: 'bSSzMHFP7fB5gpGUM27w',
        trelloToken: 'a4f99d894674645f9debb528cbd0ae86bf88d5119eb67e125d48904e2b369983',
        trelloAppkey: '2f5cba1d75d5e6e2303d17e39f5a9ea6',
        sentryToken: 'd49f8f5f4b274144a664d2ab120e76bf40a9e604a37341bba9b78be431cbd9bf',
        sonarHost: '9000',

        allTrelloProjects: [],
        chosenTrelloProjects: [],
        chosenSonarProjects: [],

        allSonarProjects: [],
        sonarTrelloMapping: {},
        sentryTrelloMapping: {},

        chosenSentryProjects: [],
        allSentryProjects: [],

        chosenGitProjects: [],
        allGitProjects: []
      },
      rules: {
        account: [
          {required: true, message: '账号不能为空', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '密码不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  computed: {
    ...mapState({
      TrelloAndSentry: state => state.sentryTrelloMapping,
      TrelloAndSonal: state => state.sonarTrelloMapping
    })
  },
  methods: {
    last() {
      this.flag = true
      this.current -= 1
    },
    skip() {
      this.flag = true
      this.current += 1
    },
    next() {
      this.flag = true
      this.current += 1
    },
    async handleSubmit() {
      this.$refs.RegisterForm.validate((valid) => {
        if (valid) {
          this.validationAndLogin()
        }
      })
    },
    async validateGitAuth() {
      let receiveData = await gitlab.getGitlabId(this.form.gitIp, this.form.gitPrivateToken)
      this.form.allGitProjects = receiveData.projects
    },
    async validateTrelloAuth() {
      let receiveData = await storys.getBroadsId(this.form.trelloToken, this.form.trelloAppkey)
      this.form.allTrelloProjects = receiveData.projects
      for (let i = 0; i < this.form.allTrelloProjects.length; i++) {
        this.allTrelloProjectsTemp.push(this.form.allTrelloProjects[i])
      }
    },
    async validateSentryAuth() {
      let receiveData = await sentry.getSentryId(this.form.sentryToken)
      this.form.allSentryProjects = receiveData.projects
      for (let i = 0; i < this.form.allSentryProjects.length; i++) {
        this.allSentryProjectsTemp.push(this.form.allSentryProjects[i])
      }
    },
    async validateSonarHost() {
      let allSonalProjectsTemp = await sonal.getProjectIdAndName(this.form.sonarHost)
      this.form.allSonarProjects = allSonalProjectsTemp.projects
      for (let i = 0; i < this.form.allSonarProjects.length; i++) {
        this.allSonarProjectsTemp.push(this.form.allSonarProjects[i])
      }
    },
    async validationAndLogin() {
      this.form.sonarTrelloMapping = this.sonarTrelloMapping
      this.form.sentryTrelloMapping = this.sentryTrelloMapping
      let receiveData = await user.regis(this.form)
      this.flag = false
      this.status = receiveData.status
      console.log('status+++:' + this.status)
      if (this.status === 'success') {
        alert('注册成功')
        this.$router.push({
          name: 'login'
        })
      }
    }
  }
}
</script>

<style scoped lang="scss">
  .login {
    width: 100%;
    height: 100%;
    // background-image: url('https://file.iviewui.com/iview-admin/login_bg.jpg');
    background-size: cover;
    background-position: center;
    position: relative;
    &-con {
      margin: auto;
      transform: translateY(10%);
      width: 760px;
      &-header {
        font-size: 16px;
        font-weight: 300;
        text-align: center;
        padding: 30px 0;
      }
      .form-con {
        padding: 10px 0 0;
        height: 800px
      }
      .login-tip {
        font-size: 10px;
        text-align: center;
        color: #c3c3c3;
      }
    }
  }

  .register-step {
    height: 500px;
    overflow: auto;
  }

  .config-title {
    font-weight: bold;
    display: inline-block;
  }

  .register-button {
    display: inline-block;
    float: right;
    margin-bottom: 10px;
  }

  .register-checkBox {
    display: block;
    line-height: 30px;
    font-size: 14px
  }

  .step-button {
    width: 100%;
    position: relative;
    top: 100px
  }

  .register-progress {
    position: relative;
    top: 80px;
    left: 30px;
  }
</style>
