<template>
  <a :href="issue.url" target="_blank">
    <Card class="cardstyle" :padding="9" style="margin: 5px">
      <div class="sentry-card"></div>
      <Badge class="sentry-badge" :count="issue.eventCount"/>
        <div class="sentry-icon">
          <img v-if="issue.gitLabProjectImage!==undefined&&issue.gitLabProjectImage!==''" :src="issue.gitLabProjectImage" width="35px" height="35px" />
          <img v-if="issue.gitLabProjectImage!==undefined&&issue.gitLabProjectImage===''" src="@/common/images/perception_none_img-2.png" width="35px" height="35px" />
          <img v-if="issue.gitLabProjectImage===undefined&&issue.platform==='javascript'" src="@/common/images/node-js.png" width="35px" height="35px" />
          <img v-if="issue.gitLabProjectImage===undefined&&issue.platform==='java-android'" src="@/common/images/Android.png" width="35px" height="35px"/>
          <img v-if="issue.gitLabProjectImage===undefined&&issue.platform==='objc'" src="@/common/images/ios-3.png" width="35px" height="35px"/>
          <img v-else-if="issue.gitLabProjectImage===undefined&&issue.platform===''" src="@/common/images/perception_none_img-2.png" width="35px" height="35px" style=""/>
        </div>
        <div class="sentry-content">
          <span class="sentry-content_text">Project: {{issue.projectName}}</span><br/>
          <span class="sentry-content_text" style=" font-size: 16px;">Lastest time: {{issue.seenTime}}</span>
        </div>
    </Card>
  </a>
</template>
<script>

import Icon from 'iview/src/components/icon/icon'

export default {
  components: {Icon},
  props: {
    issue: {
      type: Object
    }
  }
}
</script>
<style scoped lang="scss">
  .sentry-badge {
    float: right;
  }
  .sentry-icon{
    margin:0 5px;
    width: 35px;
    height:35px;
    float: left;
    margin:0.5rem 5px
  }
  .sentry-card {
    background-color: #ee3f16;
    width: 10px;
    height: 100%;
    float: left;
    border-radius: 10px;
    position: absolute;
    left: 0px;
    top: 0px;
    border: 1px solid grey
  }

  .sentry-content{
    padding-left: 45px;
    &_text{
      font-size: 20px;
      font-weight: bold;
      text-overflow: ellipsis;
      overflow: hidden;
      white-space: nowrap;
      width: 80%;
      display: inline-block;
      line-height: 22px;
      color: #495060;
    }
  }
</style>
