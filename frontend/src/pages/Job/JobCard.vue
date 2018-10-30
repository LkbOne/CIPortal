<template>
  <a :href="merge.url" target="_blank">
    <Card :padding="5" style="margin: 5px;border:3px solid #abd6f2" v-if="merge.timeFlag">
      <div class="job-card" :style="{backgroundColor: bgc}"></div>
      <img :src="merge.authorImage" width="30px" height="30px" class="job-img"/>
      <div class="job-content">
        <!--style="width: 35px;height:35px;float: left;margin:0.5rem 10px;"-->
        <div class="job-content_projectImg">
          <img :src="merge.projectImage" width="35px" height="35px" class="project-img"/>
        </div>
        <span class="job-content_text">{{merge.projectName}} ({{merge.sourceBranch}})</span><br/>
        <span
          class="job-content_text">Opened about {{merge.updatedAt}} by {{merge.authorName}} {{merge.targetBranch}}</span><br/>
      </div>
    </Card>
    <Card :padding="5" style="margin: 5px;" v-else-if="!merge.timeFlag">
      <div class="job-card" :style="{backgroundColor: bgc}"></div>
      <img :src="merge.authorImage" width="30px" height="30px" class="job-img"/>
      <div class="job-content">
        <div class="job-content_projectImg">
          <img v-if="merge.projectImage" :src="merge.projectImage" width="35px" height="35px" class="project-img"/>
          <img v-else-if="merge.projectImage===''" src="@/common/images/perception_none_img-2.png" width="35px" height="35px" class="project-img"/>
        </div>
        <span class="job-content_text">{{merge.projectName}} ({{merge.sourceBranch}})</span><br/>
        <span
          class="job-content_text">Opened about {{merge.updatedAt}} by {{merge.authorName}} {{merge.targetBranch}}</span><br/>
      </div>
    </Card>
  </a>
</template>
<script>
export default {
  props: {
    merge: {
      type: Object
    }
  },
  data() {
    return {
      bgc: '#fff'
    }
  },
  created() {
    switch (this.merge.mergeStatus) {
      case 'false':
        this.bgc = '#ee3f16'
        break
      case 'true':
        this.bgc = '#1abe6a'
        break
      default:
        this.bgc = '#fff'
        break
    }
  }
}
</script>
<style scoped lang="scss">
  .job-card {
    width: 10px;
    height: 100%;
    float: left;
    border-radius: 10px;
    position: absolute;
    left: 0px;
    top: 0px;
    border: 1px solid grey
  }

  .job-img {
    float: right;
    position: absolute;
    top: 1rem;
    right: 10px
  }

  .job-content {
    &_text {
      font-size: 1.5em;
      font-weight: bold;
      text-overflow: ellipsis;
      overflow: hidden;
      white-space: nowrap;
      width: 70%;
      display: inline-block;
      line-height: 23px;
      color: #495060;
    }
    &_projectImg{
      width: 35px;
      height:35px;
      float: left;
      margin:0.5rem 10px;
    }
  }

</style>
