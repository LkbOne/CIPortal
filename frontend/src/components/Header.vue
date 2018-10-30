<template>
  <Header class="ci-portal-header">
    <Menu mode="horizontal" theme="dark">
      <span class="layout-logo">MBC CI Portal</span>
      <div class="layout-nav">
        <Dropdown class="drop-down" trigger="click" placement="bottom-start" @on-click="tologin">
          <a href="javascript:void(0)">
            <img class="menu-img" src="../common/images/menu2.png" width="30px" height="30px"/>
          </a>
          <DropdownMenu slot="list">
            <DropdownItem class="setting-drop-down-item login" name="1-1" v-if="priority==='0'" style="font-size: 16px">
              <span style="font-size: 16px">Login</span></DropdownItem>
            <DropdownItem class="setting-drop-down-item logout" name="1-2" v-if="priority==='1'"><span
              style="font-size: 16px">Logout</span></DropdownItem>
            <DropdownItem class="setting-drop-down-item register" name="1-3" v-if="priority==='1'&&authority===0"><span
              style="font-size: 16px">Create Sub Account</span></DropdownItem>
            <DropdownItem class="setting-drop-down-item setting" name="1-4" v-if="priority==='1'"><span
              style="font-size: 16px">Setting</span></DropdownItem>
            <DropdownItem class="setting-drop-down-item resetpassword" name="1-5" v-if="priority==='1'"><span
              style="font-size: 16px">Reset Password</span></DropdownItem>
          </DropdownMenu>
        </Dropdown>
      </div>
    </Menu>
  </Header>
</template>
<script>
import { mapState } from 'vuex'

import TrelloConfig from '@/components/TrelloConfig'
import SentryConfig from '@/components/SentryConfig'
import GitLabConfig from '@/components/GitLabConfig'
import SonarConfig from '@/components/SonarConfig'
import FlurryConfig from '@/components/FlurryConfig'
import sentry from '@/service/sentry'

export default {
  components: {
    TrelloConfig,
    SentryConfig,
    GitLabConfig,
    SonarConfig,
    FlurryConfig
  },
  data() {
    return {}
  },
  computed: {
    ...mapState({
      // TrelloAndSentry: state => state.TrelloAndSentry,
      priority: state => state.priority,
      authority: state => state.authority,
      // alertRadioFlag: state => state.alertRadioFlag,
      // windowclientwidth: state => state.windowclientwidth
    })
  },
  methods: {
    tologin(name) {
      switch (name) {
        case '1-1': {
          this.$router.push({
            name: 'login'
          })
          break
        }
        case '1-2': {
          this.$store.dispatch('setPriority', '0')
          this.$router.push({
            name: 'ContentShow'
          })
          break
        }
        case '1-3': {
          this.$router.push({
            name: 'register'
          })
          break
        }
        case '1-4': {
          this.$router.push({
            name: 'setting-boards'
          })
          break
        }
        case '1-5': {
          this.$router.push({
            name: 'ResetPassword'
          })
          break
        }
      }
    }
  }
}
</script>
<style scoped lang="scss">
  .layout-logo {
    color: #fff;
  }

  .layout-nav {
    float: right;
    margin: 0 auto;
    &_config {
      float: left
    }
  }

  .ci-portal-header {
    z-index: 100;
    position: fixed;
    width: 100%
  }

  .menu-img {
    position: relative;
    top: 10px;
  }
</style>
